package io.protone.application.web.api.scheduler;


import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.application.web.api.scheduler.impl.SchClockTemplateResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchClockTemplateDTO;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchClockTemplate;
import io.protone.scheduler.mapper.SchClockTemplateMapper;
import io.protone.scheduler.repository.SchClockTemplateRepository;
import io.protone.scheduler.service.SchClockTemplateService;
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
public class SchClockTemplateResourceImplTest {
    private static final String CLOCK_TEST_CATEGORY = "Czwartkowe";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORTNAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORTNAME = "BBBBBBBBBB";
    @Autowired
    private SchClockTemplateRepository schClockTemplateRepository;


    @Autowired
    private SchClockTemplateService schClockTemplateService;

    @Autowired
    private SchClockTemplateMapper schClockTemplateMapper;

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

    private SchClockTemplate clockConfiguration;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchClockTemplate createEntity(EntityManager em) {
        SchClockTemplate schClockTemplate = new SchClockTemplate()
                .name(DEFAULT_NAME)
                .shortName(DEFAULT_SHORTNAME);
        return schClockTemplate;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchClockTemplateResourceImpl traPlaylistResource = new SchClockTemplateResourceImpl();
        ReflectionTestUtils.setField(traPlaylistResource, "schClockTemplateService", schClockTemplateService);
        ReflectionTestUtils.setField(traPlaylistResource, "schClockTemplateMapper", schClockTemplateMapper);
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
        int databaseSizeBeforeCreate = schClockTemplateRepository.findAll().size();

        // Create the SchClock
        SchClockTemplateDTO schClockTemplateDTO = schClockTemplateMapper.DB2DTO(clockConfiguration);

        restSchClockMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schClockTemplateDTO)))
                .andExpect(status().isCreated());

        // Validate the SchClock in the database
        List<SchClockTemplate> traPlaylistList = schClockTemplateRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate + 1);
        SchClockTemplate testSchClock = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchClock.getName()).isEqualTo(DEFAULT_NAME);
    }


    @Test
    @Transactional
    public void createSchClockConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schClockTemplateRepository.findAll().size();

        // Create the SchClock with an existing ID
        SchClockTemplate existingSchClock = new SchClockTemplate();
        existingSchClock.setId(1L);
        SchClockTemplateDTO existingSchClockTemplateDTO = schClockTemplateMapper.DB2DTO(existingSchClock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchClockMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingSchClockTemplateDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchClockTemplate> traPlaylistList = schClockTemplateRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchClockConfiguration() throws Exception {
        // Initialize the database
        schClockTemplateRepository.saveAndFlush(clockConfiguration.network(corNetwork).channel(corChannel));

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
        CorDictionary corDictionary = new CorDictionary();
        corDictionary.setId(48L);

        schClockTemplateRepository.saveAndFlush(clockConfiguration.network(corNetwork).channel(corChannel).clockCategory(corDictionary));

        // Get all the traPlaylistList
        restSchClockMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration/category/{name}?sort=id,desc", corNetwork.getShortcut(), corChannel.getShortcut(), CLOCK_TEST_CATEGORY))
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
        schClockTemplateRepository.saveAndFlush(clockConfiguration.network(corNetwork).channel(corChannel));

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
        schClockTemplateRepository.saveAndFlush(clockConfiguration.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeUpdate = schClockTemplateRepository.findAll().size();

        // Update the clockConfiguration
        SchClockTemplate updatedSchClock = schClockTemplateRepository.findOne(clockConfiguration.getId());
        updatedSchClock
                .name(UPDATED_NAME).shortName(UPDATED_SHORTNAME);
        SchClockTemplateDTO schClockTemplateDTO = schClockTemplateMapper.DB2DTO(updatedSchClock);

        restSchClockMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schClockTemplateDTO)))
                .andExpect(status().isOk());

        // Validate the SchClock in the database
        List<SchClockTemplate> traPlaylistList = schClockTemplateRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate);
        SchClockTemplate testSchClock = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchClock.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchClock.getShortName()).isEqualTo(UPDATED_SHORTNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSchClockConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = schClockTemplateRepository.findAll().size();

        // Create the SchClock
        SchClockTemplateDTO schClockTemplateDTO = schClockTemplateMapper.DB2DTO(clockConfiguration);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchClockMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schClockTemplateDTO)))
                .andExpect(status().isCreated());

        // Validate the SchClock in the database
        List<SchClockTemplate> traPlaylistList = schClockTemplateRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchClockConfiguration() throws Exception {
        // Initialize the database
        schClockTemplateRepository.saveAndFlush(clockConfiguration.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeDelete = schClockTemplateRepository.findAll().size();

        // Get the clockConfiguration
        restSchClockMockMvc.perform(delete("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/configuration/{shortName}", corNetwork.getShortcut(), corChannel.getShortcut(), DEFAULT_SHORTNAME)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SchClockTemplate> traPlaylistList = schClockTemplateRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchClock.class);
    }
}
