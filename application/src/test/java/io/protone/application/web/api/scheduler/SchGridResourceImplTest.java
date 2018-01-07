package io.protone.application.web.api.scheduler;


import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.scheduler.impl.SchGridConfigurationResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorOrganization;
import io.protone.core.service.CorChannelService;
import io.protone.scheduler.api.dto.SchGridDTO;
import io.protone.scheduler.domain.SchGrid;
import io.protone.scheduler.mapper.SchGridMapper;
import io.protone.scheduler.repository.SchGridRepository;
import io.protone.scheduler.service.SchGridService;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 14.05.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SchGridResourceImplTest {
    private static final String GRID_TEST_CATEGORY = "Zapasowe";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORTNAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORTNAME = "BBBBBBBBBB";

    @Autowired
    private SchGridRepository schGridRepository;


    @Autowired
    private SchGridService schGridService;

    @Autowired
    private SchGridMapper schGridMapper;

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

    private MockMvc restSchGridMockMvc;

    private SchGrid schGrid;

    private CorOrganization corOrganization;

    private CorChannel corChannel;


    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchGrid createEntity(EntityManager em) {
        SchGrid traPlaylist = new SchGrid()
                .name(DEFAULT_NAME)
                .shortName(DEFAULT_SHORTNAME);
        return traPlaylist;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchGridConfigurationResourceImpl traPlaylistResource = new SchGridConfigurationResourceImpl();
        ReflectionTestUtils.setField(traPlaylistResource, "schGridService", schGridService);
        ReflectionTestUtils.setField(traPlaylistResource, "schGridMapper", schGridMapper);
        ReflectionTestUtils.setField(traPlaylistResource, "corChannelService", corChannelService);


        this.restSchGridMockMvc = MockMvcBuilders.standaloneSetup(traPlaylistResource)
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
        schGrid = createEntity(em).channel(corChannel);
    }

    @Test
    @Transactional
    public void createSchGrid() throws Exception {
        int databaseSizeBeforeCreate = schGridRepository.findAll().size();

        // Create the SchGrid
        SchGridDTO traPlaylistDTO = schGridMapper.DB2DTO(schGrid);

        restSchGridMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/grid/configuration", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isCreated());

        // Validate the SchGrid in the database
        List<SchGrid> traPlaylistList = schGridRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate + 1);
        SchGrid testSchGrid = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchGrid.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSchGrid.getShortName()).isEqualTo(DEFAULT_SHORTNAME);
    }


    @Test
    @Transactional
    public void createSchGridWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schGridRepository.findAll().size();

        // Create the SchGrid with an existing ID
        SchGrid existingSchGrid = new SchGrid();
        existingSchGrid.setId(1L);
        SchGridDTO existingSchGridDTO = schGridMapper.DB2DTO(existingSchGrid);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchGridMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/grid/configuration", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingSchGridDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchGrid> traPlaylistList = schGridRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchGrids() throws Exception {
        // Initialize the database
        schGridRepository.saveAndFlush(schGrid.channel(corChannel));

        // Get all the traPlaylistList
        restSchGridMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/grid/configuration?sort=id,desc", corOrganization.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(schGrid.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORTNAME.toString())));

    }


    @Test
    @Transactional
    public void getAllSchGridsDefaults() throws Exception {
        // Initialize the database
        schGridRepository.saveAndFlush(schGrid.channel(corChannel).defaultGrid(true));

        // Get all the traPlaylistList
        restSchGridMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/grid/configuration/default?sort=id,desc", corOrganization.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(schGrid.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORTNAME.toString())));

    }


    @Test
    @Transactional
    public void getAllSchGridsGroupedByCategory() throws Exception {
        // Initialize the database
        CorDictionary corDictionary = new CorDictionary();
        corDictionary.setId(51L);
        schGridRepository.saveAndFlush(schGrid.channel(corChannel).gridCategory(corDictionary));

        // Get all the traPlaylistList
        restSchGridMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/grid/configuration/category/{name}?sort=id,desc", corOrganization.getShortcut(), corChannel.getShortcut(),GRID_TEST_CATEGORY))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(schGrid.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORTNAME.toString())));

    }

    @Test
    @Transactional
    public void getSchGrid() throws Exception {
        // Initialize the database
        schGridRepository.saveAndFlush(schGrid.channel(corChannel));

        // Get the schGrid
        restSchGridMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/grid/configuration/{shortName}", corOrganization.getShortcut(), corChannel.getShortcut(), DEFAULT_SHORTNAME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORTNAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchGrid() throws Exception {
        // Get the schGrid
        restSchGridMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/grid/configuration/{shortName}", corOrganization.getShortcut(), corChannel.getShortcut(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchGrid() throws Exception {
        // Initialize the database
        schGridRepository.saveAndFlush(schGrid.channel(corChannel));
        int databaseSizeBeforeUpdate = schGridRepository.findAll().size();

        // Update the schGrid
        SchGrid updatedSchGrid = schGridRepository.findOne(schGrid.getId());
        updatedSchGrid
                .name(UPDATED_NAME).shortName(UPDATED_SHORTNAME);
        SchGridDTO traPlaylistDTO = schGridMapper.DB2DTO(updatedSchGrid);

        restSchGridMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/grid/configuration", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isOk());

        // Validate the SchGrid in the database
        List<SchGrid> traPlaylistList = schGridRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate);
        SchGrid testSchGrid = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchGrid.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchGrid.getShortName()).isEqualTo(UPDATED_SHORTNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSchGrid() throws Exception {
        int databaseSizeBeforeUpdate = schGridRepository.findAll().size();

        // Create the SchGrid
        SchGridDTO traPlaylistDTO = schGridMapper.DB2DTO(schGrid);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchGridMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/grid/configuration", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isCreated());

        // Validate the SchGrid in the database
        List<SchGrid> traPlaylistList = schGridRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchGrid() throws Exception {
        // Initialize the database
        schGridRepository.saveAndFlush(schGrid.channel(corChannel));
        int databaseSizeBeforeDelete = schGridRepository.findAll().size();

        // Get the schGrid
        restSchGridMockMvc.perform(delete("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/grid/configuration/{shortName}", corOrganization.getShortcut(), corChannel.getShortcut(), DEFAULT_SHORTNAME)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SchGrid> traPlaylistList = schGridRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeDelete - 1);
    }


    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchGrid.class);
    }
}
