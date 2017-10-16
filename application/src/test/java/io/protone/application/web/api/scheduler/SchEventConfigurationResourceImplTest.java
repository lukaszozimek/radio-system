package io.protone.application.web.api.scheduler;

import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.application.web.api.scheduler.impl.SchEventConfigurationResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchEventConfigurationDTO;
import io.protone.scheduler.domain.SchEventConfiguration;
import io.protone.scheduler.mapper.SchEventConfigurationMapper;
import io.protone.scheduler.repository.SchEventConfigurationRepository;
import io.protone.scheduler.service.SchEventConfigurationService;
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

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 14.05.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SchEventConfigurationResourceImplTest {
    private static final String TEST_EVENT_CATEGORY = "News";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORTNAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORTNAME = "BBBBBBBBBB";
    @Autowired
    private SchEventConfigurationRepository schEventRepository;


    @Autowired
    private SchEventConfigurationService schEventConfigurationService;

    @Autowired
    private SchEventConfigurationMapper schEventConfigurationMapper;

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

    private MockMvc restSchEventMockMvc;

    private SchEventConfiguration schEventConfiguration;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchEventConfiguration createEntity(EntityManager em) {
        SchEventConfiguration schEventConfiguration = new SchEventConfiguration()
                .name(DEFAULT_NAME)
                .shortName(DEFAULT_SHORTNAME);
        return schEventConfiguration;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchEventConfigurationResourceImpl traPlaylistResource = new SchEventConfigurationResourceImpl();
        ReflectionTestUtils.setField(traPlaylistResource, "schEventConfigurationService", schEventConfigurationService);
        ReflectionTestUtils.setField(traPlaylistResource, "schEventConfigurationMapper", schEventConfigurationMapper);
        ReflectionTestUtils.setField(traPlaylistResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(traPlaylistResource, "corChannelService", corChannelService);


        this.restSchEventMockMvc = MockMvcBuilders.standaloneSetup(traPlaylistResource)
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
        schEventConfiguration = createEntity(em).network(corNetwork).channel(corChannel);
    }

    @Test
    @Transactional
    public void createSchEvent() throws Exception {
        int databaseSizeBeforeCreate = schEventRepository.findAll().size();

        // Create the SchEvent
        SchEventConfigurationDTO schEventConfigurationDTO = schEventConfigurationMapper.DB2DTO(schEventConfiguration);

        restSchEventMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schEventConfigurationDTO)))
                .andExpect(status().isCreated());

        // Validate the SchEvent in the database
        List<SchEventConfiguration> traPlaylistList = schEventRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate + 1);
        SchEventConfiguration testSchEvent = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchEvent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSchEvent.getShortName()).isEqualTo(DEFAULT_SHORTNAME);
    }


    @Test
    @Transactional
    public void createSchEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schEventRepository.findAll().size();

        // Create the SchEvent with an existing ID
        SchEventConfiguration existingSchEvent = new SchEventConfiguration();
        existingSchEvent.setId(1L);
        SchEventConfigurationDTO existingSchEventConfigurationDTO = schEventConfigurationMapper.DB2DTO(existingSchEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchEventMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingSchEventConfigurationDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchEventConfiguration> traPlaylistList = schEventRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchEvents() throws Exception {
        // Initialize the database
         schEventRepository.saveAndFlush(schEventConfiguration.network(corNetwork).channel(corChannel));

        // Get all the traPlaylistList
        restSchEventMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration?sort=id,desc", corNetwork.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(schEventConfiguration.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORTNAME.toString())));

    }

    @Test
    @Transactional
    public void getAllSchEventsGroupedByCategory() throws Exception {
        // Initialize the database
        CorDictionary corDictionary = new CorDictionary();
        corDictionary.setId(43L);
        schEventRepository.saveAndFlush(schEventConfiguration.network(corNetwork).channel(corChannel).eventCategory(corDictionary));

        // Get all the traPlaylistList
        restSchEventMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration/category/{name}?sort=id,desc", corNetwork.getShortcut(), corChannel.getShortcut(), TEST_EVENT_CATEGORY))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(schEventConfiguration.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORTNAME.toString())));

    }

    @Test
    @Transactional
    public void getSchEvent() throws Exception {
        // Initialize the database
          schEventRepository.saveAndFlush(schEventConfiguration.network(corNetwork).channel(corChannel));

        // Get the schEventConfiguration
        restSchEventMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration/{shortName}", corNetwork.getShortcut(), corChannel.getShortcut(), DEFAULT_SHORTNAME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(schEventConfiguration.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORTNAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchEvent() throws Exception {

        restSchEventMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration/{shortName}", corNetwork.getShortcut(), corChannel.getShortcut(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchEvent() throws Exception {
        // Initialize the database
        schEventRepository.saveAndFlush(schEventConfiguration.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeUpdate = schEventRepository.findAll().size();

        // Update the schEventConfiguration
        SchEventConfiguration updatedSchEvent = schEventRepository.findOne(schEventConfiguration.getId());
        updatedSchEvent
                .name(UPDATED_NAME).shortName(UPDATED_SHORTNAME);
        SchEventConfigurationDTO traPlaylistDTO = schEventConfigurationMapper.DB2DTO(updatedSchEvent);

        restSchEventMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isOk());

        // Validate the SchEvent in the database
        List<SchEventConfiguration> traPlaylistList = schEventRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate);
        SchEventConfiguration testSchEvent = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchEvent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchEvent.getShortName()).isEqualTo(UPDATED_SHORTNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSchEvent() throws Exception {
        int databaseSizeBeforeUpdate = schEventRepository.findAll().size();

        // Create the SchEvent
        SchEventConfigurationDTO traPlaylistDTO = schEventConfigurationMapper.DB2DTO(schEventConfiguration);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchEventMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isCreated());

        // Validate the SchEvent in the database
        List<SchEventConfiguration> traPlaylistList = schEventRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchEvent() throws Exception {
        // Initialize the database
        schEventRepository.saveAndFlush(schEventConfiguration.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeDelete = schEventRepository.findAll().size();

        // Get the schEventConfiguration
        restSchEventMockMvc.perform(delete("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/configuration/{shortName}", corNetwork.getShortcut(), corChannel.getShortcut(), DEFAULT_SHORTNAME)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SchEventConfiguration> traPlaylistList = schEventRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeDelete - 1);
    }


}
