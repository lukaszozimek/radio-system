package io.protone.application.web.api.scheduler;


import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.application.web.api.scheduler.impl.SchClockConfigurationResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchClockConfigurationDTO;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchClockConfiguration;
import io.protone.scheduler.mapper.SchClockConfigurationMapper;
import io.protone.scheduler.repository.SchClockConfigurationRepository;
import io.protone.scheduler.service.SchClockConfigurationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 14.05.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SchClockConfigurationResourceImplTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORTNAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORTNAME = "BBBBBBBBBB";
    @Autowired
    private SchClockConfigurationRepository schClockConfigurationRepository;


    @Autowired
    private SchClockConfigurationService schClockConfigurationService;

    @Autowired
    private SchClockConfigurationMapper schClockConfigurationMapper;

    @Autowired
    private CorChannelService corChannelService;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchClockMockMvc;

    private SchClockConfiguration clockConfiguration;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchClockConfiguration createEntity(EntityManager em) {
        SchClockConfiguration schClockConfiguration = new SchClockConfiguration()
                .name(DEFAULT_NAME)
                .shortName(DEFAULT_SHORTNAME);
        return schClockConfiguration;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchClockConfigurationResourceImpl traPlaylistResource = new SchClockConfigurationResourceImpl();
        ReflectionTestUtils.setField(traPlaylistResource, "schClockConfigurationService", schClockConfigurationService);
        ReflectionTestUtils.setField(traPlaylistResource, "schClockConfigurationMapper", schClockConfigurationMapper);
        ReflectionTestUtils.setField(traPlaylistResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(traPlaylistResource, "corChannelService", corChannelService);


        this.restSchClockMockMvc = MockMvcBuilders.standaloneSetup(traPlaylistResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);
        corChannel = new CorChannel().shortcut("tes");
        corChannel.setId(1L);
        clockConfiguration = createEntity(em).network(corNetwork).channel(corChannel);
    }

    @Test
    @Transactional
    public void createSchClockConfiguration() throws Exception {
        int databaseSizeBeforeCreate = schClockConfigurationRepository.findAll().size();

        // Create the SchClock
        SchClockConfigurationDTO schClockConfigurationDTO = schClockConfigurationMapper.DB2DTO(clockConfiguration);

        restSchClockMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schClockConfigurationDTO)))
                .andExpect(status().isCreated());

        // Validate the SchClock in the database
        List<SchClockConfiguration> traPlaylistList = schClockConfigurationRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate + 1);
        SchClockConfiguration testSchClock = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchClock.getName()).isEqualTo(DEFAULT_NAME);
    }


    @Test
    @Transactional
    public void createSchClockConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schClockConfigurationRepository.findAll().size();

        // Create the SchClock with an existing ID
        SchClockConfiguration existingSchClock = new SchClockConfiguration();
        existingSchClock.setId(1L);
        SchClockConfigurationDTO existingSchClockConfigurationDTO = schClockConfigurationMapper.DB2DTO(existingSchClock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchClockMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingSchClockConfigurationDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchClockConfiguration> traPlaylistList = schClockConfigurationRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchClockConfiguration() throws Exception {
        // Initialize the database
        schClockConfigurationRepository.saveAndFlush(clockConfiguration.network(corNetwork).channel(corChannel));

        // Get all the traPlaylistList
        restSchClockMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration?sort=id,desc", corNetwork.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(clockConfiguration.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORTNAME.toString())));

    }


    @Test
    @Transactional
    public void getAllSchClockConfigurationGroupedByCategory() throws Exception {
        // Initialize the database
        schClockConfigurationRepository.saveAndFlush(clockConfiguration.network(corNetwork).channel(corChannel));

        // Get all the traPlaylistList
        restSchClockMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration/category/{name}?sort=id,desc", corNetwork.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(clockConfiguration.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORTNAME.toString())));

    }

    @Test
    @Transactional
    public void getSchClockConfiguration() throws Exception {
        // Initialize the database
        schClockConfigurationRepository.saveAndFlush(clockConfiguration.network(corNetwork).channel(corChannel));

        // Get the clockConfiguration
        restSchClockMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration/{shortName}", corNetwork.getShortcut(), corChannel.getShortcut(), DEFAULT_SHORTNAME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(clockConfiguration.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORTNAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchClockConfiguration() throws Exception {
        // Get the clockConfiguration
        restSchClockMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration/{shortName}", corNetwork.getShortcut(), corChannel.getShortcut(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());

    }

    @Test
    @Transactional
    public void updateSchClockConfiguration() throws Exception {
        // Initialize the database
        schClockConfigurationRepository.saveAndFlush(clockConfiguration.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeUpdate = schClockConfigurationRepository.findAll().size();

        // Update the clockConfiguration
        SchClockConfiguration updatedSchClock = schClockConfigurationRepository.findOne(clockConfiguration.getId());
        updatedSchClock
                .name(UPDATED_NAME).shortName(UPDATED_SHORTNAME);
        SchClockConfigurationDTO schClockConfigurationDTO = schClockConfigurationMapper.DB2DTO(updatedSchClock);

        restSchClockMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schClockConfigurationDTO)))
                .andExpect(status().isOk());

        // Validate the SchClock in the database
        List<SchClockConfiguration> traPlaylistList = schClockConfigurationRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate);
        SchClockConfiguration testSchClock = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchClock.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchClock.getShortName()).isEqualTo(UPDATED_SHORTNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSchClockConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = schClockConfigurationRepository.findAll().size();

        // Create the SchClock
        SchClockConfigurationDTO schClockConfigurationDTO = schClockConfigurationMapper.DB2DTO(clockConfiguration);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchClockMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schClockConfigurationDTO)))
                .andExpect(status().isCreated());

        // Validate the SchClock in the database
        List<SchClockConfiguration> traPlaylistList = schClockConfigurationRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchClockConfiguration() throws Exception {
        // Initialize the database
        schClockConfigurationRepository.saveAndFlush(clockConfiguration.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeDelete = schClockConfigurationRepository.findAll().size();

        // Get the clockConfiguration
        restSchClockMockMvc.perform(delete("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration/{shortName}", corNetwork.getShortcut(), corChannel.getShortcut(), DEFAULT_SHORTNAME)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SchClockConfiguration> traPlaylistList = schClockConfigurationRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchClock.class);
    }
}
