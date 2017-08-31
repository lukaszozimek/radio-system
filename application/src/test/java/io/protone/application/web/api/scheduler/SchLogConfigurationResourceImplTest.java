package io.protone.application.web.api.scheduler;

import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.application.web.api.scheduler.impl.SchLogConfigurationResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchLogConfigurationDTO;
import io.protone.scheduler.domain.SchLogConfiguration;
import io.protone.scheduler.mapper.SchLogConfigurationMapper;
import io.protone.scheduler.repository.SchLogConfigurationRepository;
import io.protone.scheduler.service.SchLogConfigurationService;
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
public class SchLogConfigurationResourceImplTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";


    @Autowired
    private SchLogConfigurationRepository schLogConfigurationRepository;


    @Autowired
    private SchLogConfigurationService schLogConfigurationService;

    @Autowired
    private SchLogConfigurationMapper schLogConfigurationMapper;

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

    private MockMvc restSchLogConfigurationMockMvc;

    private SchLogConfiguration schLogConfiguration;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchLogConfiguration createEntity(EntityManager em) {
        SchLogConfiguration traPlaylist = new SchLogConfiguration()
                .name(DEFAULT_NAME);
        return traPlaylist;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchLogConfigurationResourceImpl traPlaylistResource = new SchLogConfigurationResourceImpl();
        ReflectionTestUtils.setField(traPlaylistResource, "schLogConfigurationService", schLogConfigurationService);
        ReflectionTestUtils.setField(traPlaylistResource, "schLogConfigurationMapper", schLogConfigurationMapper);
        ReflectionTestUtils.setField(traPlaylistResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(traPlaylistResource, "corChannelService", corChannelService);


        this.restSchLogConfigurationMockMvc = MockMvcBuilders.standaloneSetup(traPlaylistResource)
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
        schLogConfiguration = createEntity(em).network(corNetwork).channel(corChannel);
    }

    @Test
    @Transactional
    public void createSchLogConfiguration() throws Exception {
        int databaseSizeBeforeCreate = schLogConfigurationRepository.findAll().size();

        // Create the SchLogConfiguration
        SchLogConfigurationDTO traPlaylistDTO = schLogConfigurationMapper.DB2DTO(schLogConfiguration);

        restSchLogConfigurationMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/log/configuration", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isCreated());

        // Validate the SchLogConfiguration in the database
        List<SchLogConfiguration> traPlaylistList = schLogConfigurationRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate + 1);
        SchLogConfiguration testSchLogConfiguration = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchLogConfiguration.getName()).isEqualTo(DEFAULT_NAME);
    }


    @Test
    @Transactional
    public void createSchLogConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schLogConfigurationRepository.findAll().size();

        // Create the SchLogConfiguration with an existing ID
        SchLogConfiguration existingSchLogConfiguration = new SchLogConfiguration();
        existingSchLogConfiguration.setId(1L);
        SchLogConfigurationDTO existingSchLogConfigurationDTO = schLogConfigurationMapper.DB2DTO(existingSchLogConfiguration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchLogConfigurationMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/log/configuration", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingSchLogConfigurationDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchLogConfiguration> traPlaylistList = schLogConfigurationRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchLogConfigurations() throws Exception {
        // Initialize the database
        schLogConfigurationRepository.saveAndFlush(schLogConfiguration.network(corNetwork).channel(corChannel));

        // Get all the traPlaylistList
        restSchLogConfigurationMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/log/configuration?sort=id,desc", corNetwork.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(schLogConfiguration.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));


    }

    @Test
    @Transactional
    public void getSchLogConfiguration() throws Exception {
        // Initialize the database
        schLogConfigurationRepository.saveAndFlush(schLogConfiguration.network(corNetwork).channel(corChannel));

        // Get the schLogConfiguration
        restSchLogConfigurationMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/log/configuration/{shortName}", corNetwork.getShortcut(), corChannel.getShortcut(), schLogConfiguration.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(schLogConfiguration.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));

    }

    @Test
    @Transactional
    public void getNonExistingSchLogConfiguration() throws Exception {

        restSchLogConfigurationMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/log/configuration/{shortName}", corNetwork.getShortcut(), corChannel.getShortcut(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchLogConfiguration() throws Exception {
        // Initialize the database
        schLogConfigurationRepository.saveAndFlush(schLogConfiguration.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeUpdate = schLogConfigurationRepository.findAll().size();

        // Update the schLogConfiguration
        SchLogConfiguration updatedSchLogConfiguration = schLogConfigurationRepository.findOne(schLogConfiguration.getId());
        updatedSchLogConfiguration
                .name(UPDATED_NAME);
        SchLogConfigurationDTO traPlaylistDTO = schLogConfigurationMapper.DB2DTO(updatedSchLogConfiguration);

        restSchLogConfigurationMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/log/configuration", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isOk());

        // Validate the SchLogConfiguration in the database
        List<SchLogConfiguration> traPlaylistList = schLogConfigurationRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate);
        SchLogConfiguration testSchLogConfiguration = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchLogConfiguration.getName()).isEqualTo(UPDATED_NAME);

    }

    @Test
    @Transactional
    public void updateNonExistingSchLogConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = schLogConfigurationRepository.findAll().size();

        // Create the SchLogConfiguration
        SchLogConfigurationDTO traPlaylistDTO = schLogConfigurationMapper.DB2DTO(schLogConfiguration);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchLogConfigurationMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isCreated());

        // Validate the SchLogConfiguration in the database
        List<SchLogConfiguration> traPlaylistList = schLogConfigurationRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchLogConfiguration() throws Exception {
        // Initialize the database
        schLogConfigurationRepository.saveAndFlush(schLogConfiguration.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeDelete = schLogConfigurationRepository.findAll().size();

        // Get the schLogConfiguration
        restSchLogConfigurationMockMvc.perform(delete("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/{shortName}", corNetwork.getShortcut(), corChannel.getShortcut(), schLogConfiguration.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SchLogConfiguration> traPlaylistList = schLogConfigurationRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeDelete - 1);
    }


    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchLogConfiguration.class);
    }
}
