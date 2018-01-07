package io.protone.application.web.api.scheduler;

import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.scheduler.impl.SchEventTemplateResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorOrganization;
import io.protone.core.service.CorChannelService;
import io.protone.scheduler.api.dto.SchEventTemplateDTO;
import io.protone.scheduler.domain.SchEventTemplate;
import io.protone.scheduler.mapper.SchEventTemplateMapper;
import io.protone.scheduler.repository.SchEventTemplateRepository;
import io.protone.scheduler.service.SchEventTemplateService;
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

import static io.protone.application.util.TestConstans.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 14.05.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SchEventTemplateConfigurationResourceImplTest {
    private static final String TEST_EVENT_CATEGORY = "News";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORTNAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORTNAME = "BBBBBBBBBB";
    @Autowired
    private SchEventTemplateRepository schEventRepository;


    @Autowired
    private SchEventTemplateService schEventTemplateService;

    @Autowired
    private SchEventTemplateMapper schEventTemplateMapper;

    @Autowired
    private CorChannelService corChannelService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchEventMockMvc;

    private SchEventTemplate schEventConfiguration;

    private CorOrganization corOrganization;

    private CorChannel corChannel;


    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchEventTemplate createEntity(EntityManager em) {
        SchEventTemplate schEventConfiguration = new SchEventTemplate()
                .name(DEFAULT_NAME)
                .shortName(DEFAULT_SHORTNAME);
        return schEventConfiguration;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchEventTemplateResourceImpl traPlaylistResource = new SchEventTemplateResourceImpl();
        ReflectionTestUtils.setField(traPlaylistResource, "schEventTemplateService", schEventTemplateService);
        ReflectionTestUtils.setField(traPlaylistResource, "schEventTemplateMapper", schEventTemplateMapper);
        ReflectionTestUtils.setField(traPlaylistResource, "corChannelService", corChannelService);


        this.restSchEventMockMvc = MockMvcBuilders.standaloneSetup(traPlaylistResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);

        schEventConfiguration = createEntity(em).channel(corChannel);
    }

    @Test
    @Transactional
    public void createSchEvent() throws Exception {
        int databaseSizeBeforeCreate = schEventRepository.findAll().size();

        // Create the SchEventTemplate
        SchEventTemplateDTO schEventTemplateDTO = schEventTemplateMapper.DB2DTO(schEventConfiguration);

        restSchEventMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/event/configuration", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schEventTemplateDTO)))
                .andExpect(status().isCreated());

        // Validate the SchEventTemplate in the database
        List<SchEventTemplate> traPlaylistList = schEventRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate + 1);
        SchEventTemplate testSchEvent = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchEvent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSchEvent.getShortName()).isEqualTo(DEFAULT_SHORTNAME);
    }


    @Test
    @Transactional
    public void createSchEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schEventRepository.findAll().size();

        // Create the SchEventTemplate with an existing ID
        SchEventTemplate existingSchEvent = new SchEventTemplate();
        existingSchEvent.setId(1L);
        SchEventTemplateDTO existingSchEventTemplateDTO = schEventTemplateMapper.DB2DTO(existingSchEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchEventMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/event/configuration", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingSchEventTemplateDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchEventTemplate> traPlaylistList = schEventRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchEvents() throws Exception {
        // Initialize the database
         schEventRepository.saveAndFlush(schEventConfiguration.channel(corChannel));

        // Get all the traPlaylistList
        restSchEventMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/event/configuration?sort=id,desc", corOrganization.getShortcut(), corChannel.getShortcut()))
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
        schEventRepository.saveAndFlush(schEventConfiguration.channel(corChannel).eventCategory(corDictionary));

        // Get all the traPlaylistList
        restSchEventMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/event/configuration/category/{name}?sort=id,desc", corOrganization.getShortcut(), corChannel.getShortcut(), TEST_EVENT_CATEGORY))
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
          schEventRepository.saveAndFlush(schEventConfiguration.channel(corChannel));

        // Get the schEventConfiguration
        restSchEventMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/event/configuration/{shortName}", corOrganization.getShortcut(), corChannel.getShortcut(), DEFAULT_SHORTNAME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(schEventConfiguration.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORTNAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchEvent() throws Exception {

        restSchEventMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/event/configuration/{shortName}", corOrganization.getShortcut(), corChannel.getShortcut(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchEvent() throws Exception {
        // Initialize the database
        schEventRepository.saveAndFlush(schEventConfiguration.channel(corChannel));
        int databaseSizeBeforeUpdate = schEventRepository.findAll().size();

        // Update the schEventConfiguration
        SchEventTemplate updatedSchEvent = schEventRepository.findOne(schEventConfiguration.getId());
        updatedSchEvent
                .name(UPDATED_NAME).shortName(UPDATED_SHORTNAME);
        SchEventTemplateDTO traPlaylistDTO = schEventTemplateMapper.DB2DTO(updatedSchEvent);

        restSchEventMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/event/configuration", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isOk());

        // Validate the SchEventTemplate in the database
        List<SchEventTemplate> traPlaylistList = schEventRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate);
        SchEventTemplate testSchEvent = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchEvent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchEvent.getShortName()).isEqualTo(UPDATED_SHORTNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSchEvent() throws Exception {
        int databaseSizeBeforeUpdate = schEventRepository.findAll().size();

        // Create the SchEventTemplate
        SchEventTemplateDTO traPlaylistDTO = schEventTemplateMapper.DB2DTO(schEventConfiguration);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchEventMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/event/configuration", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isCreated());

        // Validate the SchEventTemplate in the database
        List<SchEventTemplate> traPlaylistList = schEventRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchEvent() throws Exception {
        // Initialize the database
        schEventRepository.saveAndFlush(schEventConfiguration.channel(corChannel));
        int databaseSizeBeforeDelete = schEventRepository.findAll().size();

        // Get the schEventConfiguration
        restSchEventMockMvc.perform(delete("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/event/configuration/{shortName}", corOrganization.getShortcut(), corChannel.getShortcut(), DEFAULT_SHORTNAME)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SchEventTemplate> traPlaylistList = schEventRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeDelete - 1);
    }


}
