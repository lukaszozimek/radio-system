package io.protone.application.web.api.traffic;

import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.traffic.impl.TraMediaPlanTemplateResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.service.CorChannelService;
import io.protone.traffic.api.dto.TraMediaPlanTemplateDTO;
import io.protone.traffic.domain.TraMediaPlanTemplate;
import io.protone.traffic.mapper.TraMediaPlanTemplateMapper;
import io.protone.traffic.repository.TraMediaPlanTemplateRepository;
import io.protone.traffic.service.TraMediaPlanTemplateService;
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
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 31/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraMediaPlanTemplateResourceImplTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BLOCK_START_COLUMN = "AAAAAAAAAA";
    private static final String UPDATED_BLOCK_START_COLUMN = "BBBBBBBBBB";

    private static final String DEFAULT_BLOCK_START_CELL = "AAAAAAAAAA";
    private static final String UPDATED_BLOCK_START_CELL = "BBBBBBBBBB";

    private static final String DEFAULT_BLOCK_END_CELL = "AAAAAAAAAA";
    private static final String UPDATED_BLOCK_END_CELL = "BBBBBBBBBB";

    private static final String DEFAULT_BLOCK_HOUR_SEPARATOR = "AAAAAAAAAA";
    private static final String UPDATED_BLOCK_HOUR_SEPARATOR = "BBBBBBBBBB";

    private static final String DEFAULT_PLAYLIST_DATE_START_COLUMN = "AAAAAAAAAA";
    private static final String UPDATED_PLAYLIST_DATE_START_COLUMN = "BBBBBBBBBB";

    private static final String DEFAULT_PLAYLIST_DATE_END_COLUMN = "AAAAAAAAAA";
    private static final String UPDATED_PLAYLIST_DATE_END_COLUMN = "BBBBBBBBBB";

    private static final String DEFAULT_PLAYLIST_FIRST_VALUE_CELL = "AAAAAAAAAA";
    private static final String UPDATED_PLAYLIST_FIRST_VALUE_CELL = "BBBBBBBBBB";

    private static final String DEFAULT_PLAYLIST_DATE_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_PLAYLIST_DATE_PATTERN = "BBBBBBBBBB";

    private static final Integer DEFAULT_SHEET_INDEX_OF_MEDIAPLAN = 0;
    private static final Integer UPDATED_SHEET_INDEX_OF_MEDIAPLAN = 1;

    private static final String DEFAULT_FIRST_EMISSION_VALUE_CELL = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_EMISSION_VALUE_CELL = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_EMISSION_VALUE_CELL = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EMISSION_VALUE_CELL = "BBBBBBBBBB";


    @Autowired
    private TraMediaPlanTemplateRepository traMediaPlanTemplateRepository;

    @Autowired
    private TraMediaPlanTemplateMapper traMediaPlanTemplateMapper;

    @Autowired
    private CorChannelService corChannelService;
    @Autowired
    private TraMediaPlanTemplateService traMediaPlanTemplateService;
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTraMediaPlantMockMvc;

    private CorOrganization corOrganization;

    private CorChannel corChannel;


    private TraMediaPlanTemplate traMediaPlanTemplate;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraMediaPlanTemplate createEntity(EntityManager em) {

        return new TraMediaPlanTemplate()
                .name(DEFAULT_NAME).blockStartColumn(DEFAULT_BLOCK_START_COLUMN)
                .blockStartCell(DEFAULT_BLOCK_START_CELL).blockEndCell(DEFAULT_BLOCK_END_CELL)
                .blockHourSeparator(DEFAULT_BLOCK_HOUR_SEPARATOR)
                .playlistDateStartColumn(DEFAULT_PLAYLIST_DATE_START_COLUMN)
                .playlistDateEndColumn(DEFAULT_PLAYLIST_DATE_END_COLUMN)
                .playlistFirsValueCell(DEFAULT_PLAYLIST_FIRST_VALUE_CELL)
                .playlistDatePattern(DEFAULT_PLAYLIST_DATE_PATTERN)
                .sheetIndexOfMediaPlan(DEFAULT_SHEET_INDEX_OF_MEDIAPLAN)
                .firstEmissionValueCell(DEFAULT_FIRST_EMISSION_VALUE_CELL)
                .lastEmissionValueCell(DEFAULT_LAST_EMISSION_VALUE_CELL);
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TraMediaPlanTemplateResourceImpl traMediaPlanTemplateResource = new TraMediaPlanTemplateResourceImpl();

        ReflectionTestUtils.setField(traMediaPlanTemplateResource, "corChannelService", corChannelService);
        ReflectionTestUtils.setField(traMediaPlanTemplateResource, "traMediaPlanTemplateService", traMediaPlanTemplateService);
        ReflectionTestUtils.setField(traMediaPlanTemplateResource, "traMediaPlanTemplateMapper", traMediaPlanTemplateMapper);

        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);

        this.restTraMediaPlantMockMvc = MockMvcBuilders.standaloneSetup(traMediaPlanTemplateResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        traMediaPlanTemplate = createEntity(em).channel(corChannel);
    }

    @Test
    @Transactional
    public void createTraMediaPlanTemplate() throws Exception {
        int databaseSizeBeforeCreate = traMediaPlanTemplateRepository.findAll().size();

        // Create the TraDiscount
        TraMediaPlanTemplateDTO traMediaPlanTemplateDTO = traMediaPlanTemplateMapper.DB2DTO(traMediaPlanTemplate);

        restTraMediaPlantMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//configuration/traffic/mediaplan/template", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traMediaPlanTemplateDTO)))
                .andExpect(status().isCreated());

        // Validate the TraDiscount in the database
        List<TraMediaPlanTemplate> traMediaPlanList = traMediaPlanTemplateRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeCreate + 1);
        TraMediaPlanTemplate testTraDiscount = traMediaPlanList.get(traMediaPlanList.size() - 1);

    }

    @Test
    @Transactional
    public void createTraMediaPlanTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traMediaPlanTemplateRepository.findAll().size();

        // Create the TraDiscount with an existing ID
        TraMediaPlanTemplate existingTraDiscount = new TraMediaPlanTemplate();
        existingTraDiscount.setId(1L);
        TraMediaPlanTemplateDTO existingTraMediaPlanTemplateDTO = traMediaPlanTemplateMapper.DB2DTO(existingTraDiscount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraMediaPlantMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//configuration/traffic/mediaplan/template", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingTraMediaPlanTemplateDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraMediaPlanTemplate> traMediaPlanList = traMediaPlanTemplateRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTraMediaPlanTemplate() throws Exception {
        // Initialize the database
        traMediaPlanTemplateRepository.saveAndFlush(traMediaPlanTemplate.channel(corChannel));

        // Get all the traMediaPlanList
        restTraMediaPlantMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//configuration/traffic/mediaplan/template?sort=id,desc", corOrganization.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(traMediaPlanTemplate.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].blockStartColumn").value(hasItem(DEFAULT_BLOCK_START_COLUMN.toString())))
                .andExpect(jsonPath("$.[*].blockStartCell").value(hasItem(DEFAULT_BLOCK_START_CELL.toString())))
                .andExpect(jsonPath("$.[*].blockEndCell").value(hasItem(DEFAULT_BLOCK_END_CELL.toString())))
                .andExpect(jsonPath("$.[*].blockHourSeparator").value(hasItem(DEFAULT_BLOCK_HOUR_SEPARATOR.toString())))
                .andExpect(jsonPath("$.[*].playlistDateStartColumn").value(hasItem(DEFAULT_PLAYLIST_DATE_START_COLUMN.toString())))
                .andExpect(jsonPath("$.[*].playlistDateEndColumn").value(hasItem(DEFAULT_PLAYLIST_DATE_END_COLUMN.toString())))
                .andExpect(jsonPath("$.[*].playlistFirsValueCell").value(hasItem(DEFAULT_PLAYLIST_FIRST_VALUE_CELL.toString())))
                .andExpect(jsonPath("$.[*].playlistDatePattern").value(hasItem(DEFAULT_PLAYLIST_DATE_PATTERN.toString())))
                .andExpect(jsonPath("$.[*].sheetIndexOfMediaPlan").value(hasItem(DEFAULT_SHEET_INDEX_OF_MEDIAPLAN.intValue())))
                .andExpect(jsonPath("$.[*].firstEmissionValueCell").value(hasItem(DEFAULT_FIRST_EMISSION_VALUE_CELL.toString())))
                .andExpect(jsonPath("$.[*].lastEmissionValueCell").value(hasItem(DEFAULT_LAST_EMISSION_VALUE_CELL.toString())));
    }

    @Test
    @Transactional
    public void getTraMediaPlanTemplate() throws Exception {
        // Initialize the database
        traMediaPlanTemplateRepository.saveAndFlush(traMediaPlanTemplate.channel(corChannel));

        // Get the traMediaPlanTemplate
        restTraMediaPlantMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//configuration/traffic/mediaplan/template/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), traMediaPlanTemplate.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(traMediaPlanTemplate.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.blockStartColumn").value(DEFAULT_BLOCK_START_COLUMN.toString()))
                .andExpect(jsonPath("$.blockStartCell").value(DEFAULT_BLOCK_START_CELL.toString()))
                .andExpect(jsonPath("$.blockEndCell").value(DEFAULT_BLOCK_END_CELL.toString()))
                .andExpect(jsonPath("$.blockHourSeparator").value(DEFAULT_BLOCK_HOUR_SEPARATOR.toString()))
                .andExpect(jsonPath("$.playlistDateStartColumn").value(DEFAULT_PLAYLIST_DATE_START_COLUMN.toString()))
                .andExpect(jsonPath("$.playlistDateEndColumn").value(DEFAULT_PLAYLIST_DATE_END_COLUMN.toString()))
                .andExpect(jsonPath("$.playlistFirsValueCell").value(DEFAULT_PLAYLIST_FIRST_VALUE_CELL.toString()))
                .andExpect(jsonPath("$.playlistDatePattern").value(DEFAULT_PLAYLIST_DATE_PATTERN.toString()))
                .andExpect(jsonPath("$.sheetIndexOfMediaPlan").value(DEFAULT_SHEET_INDEX_OF_MEDIAPLAN.intValue()))
                .andExpect(jsonPath("$.firstEmissionValueCell").value(DEFAULT_FIRST_EMISSION_VALUE_CELL.toString()))
                .andExpect(jsonPath("$.lastEmissionValueCell").value(DEFAULT_LAST_EMISSION_VALUE_CELL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraMediaPlanTemplate() throws Exception {
        // Get the traMediaPlanTemplate
        restTraMediaPlantMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//configuration/traffic/mediaplan/template/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraMediaPlanTemplate() throws Exception {
        // Initialize the database
        traMediaPlanTemplateRepository.saveAndFlush(traMediaPlanTemplate.channel(corChannel));
        int databaseSizeBeforeUpdate = traMediaPlanTemplateRepository.findAll().size();

        // Update the traMediaPlanTemplate
        TraMediaPlanTemplate traMediaPlanTemplate = traMediaPlanTemplateRepository.findOne(this.traMediaPlanTemplate.getId());
        traMediaPlanTemplate.name(UPDATED_NAME).blockStartColumn(UPDATED_BLOCK_START_COLUMN)
                .blockStartCell(UPDATED_BLOCK_START_CELL).blockEndCell(UPDATED_BLOCK_END_CELL)
                .blockHourSeparator(UPDATED_BLOCK_HOUR_SEPARATOR)
                .playlistDateStartColumn(UPDATED_PLAYLIST_DATE_START_COLUMN)
                .playlistDateEndColumn(UPDATED_PLAYLIST_DATE_END_COLUMN)
                .playlistFirsValueCell(UPDATED_PLAYLIST_FIRST_VALUE_CELL)
                .playlistDatePattern(UPDATED_PLAYLIST_DATE_PATTERN)
                .sheetIndexOfMediaPlan(UPDATED_SHEET_INDEX_OF_MEDIAPLAN)
                .firstEmissionValueCell(UPDATED_FIRST_EMISSION_VALUE_CELL)
                .lastEmissionValueCell(UPDATED_LAST_EMISSION_VALUE_CELL);
        TraMediaPlanTemplateDTO traMediaPlanTemplateDTO = traMediaPlanTemplateMapper.DB2DTO(this.traMediaPlanTemplate);

        restTraMediaPlantMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//configuration/traffic/mediaplan/template", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traMediaPlanTemplateDTO)))
                .andExpect(status().isOk());

        // Validate the TraDiscount in the database
        List<TraMediaPlanTemplate> traMediaPlanList = traMediaPlanTemplateRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeUpdate);
        TraMediaPlanTemplate mediaPlanTemplate = traMediaPlanList.get(traMediaPlanList.size() - 1);
        assertEquals(mediaPlanTemplate.getName(), UPDATED_NAME);
        assertEquals(mediaPlanTemplate.getBlockStartCell(), UPDATED_BLOCK_START_CELL);
        assertEquals(mediaPlanTemplate.getBlockEndCell(), UPDATED_BLOCK_END_CELL);
        assertEquals(mediaPlanTemplate.getBlockHourSeparator(), UPDATED_BLOCK_HOUR_SEPARATOR);
        assertEquals(mediaPlanTemplate.getPlaylistDateStartColumn(), UPDATED_PLAYLIST_DATE_START_COLUMN);
        assertEquals(mediaPlanTemplate.getBlockStartColumn(), UPDATED_BLOCK_START_COLUMN);
        assertEquals(mediaPlanTemplate.getPlaylistDateEndColumn(), UPDATED_PLAYLIST_DATE_END_COLUMN);
        assertEquals(mediaPlanTemplate.getPlaylistFirsValueCell(), UPDATED_PLAYLIST_FIRST_VALUE_CELL);
        assertEquals(mediaPlanTemplate.getPlaylistDatePattern(), UPDATED_PLAYLIST_DATE_PATTERN);
        assertEquals(mediaPlanTemplate.getSheetIndexOfMediaPlan(), UPDATED_SHEET_INDEX_OF_MEDIAPLAN);
        assertEquals(mediaPlanTemplate.getFirstEmissionValueCell(), UPDATED_FIRST_EMISSION_VALUE_CELL);
        assertEquals(mediaPlanTemplate.getLastEmissionValueCell(), UPDATED_LAST_EMISSION_VALUE_CELL);

    }

    @Test
    @Transactional
    public void updateNonExistingTraMediaPlanTemplate() throws Exception {
        int databaseSizeBeforeUpdate = traMediaPlanTemplateRepository.findAll().size();

        // Create the TraDiscount
        TraMediaPlanTemplateDTO traMediaPlanTemplateDTO = traMediaPlanTemplateMapper.DB2DTO(traMediaPlanTemplate);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraMediaPlantMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//configuration/traffic/mediaplan/template", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traMediaPlanTemplateDTO)))
                .andExpect(status().isCreated());

        // Validate the TraDiscount in the database
        List<TraMediaPlanTemplate> traMediaPlanList = traMediaPlanTemplateRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraMediaPlanTemplate() throws Exception {
        // Initialize the database
        traMediaPlanTemplateRepository.saveAndFlush(traMediaPlanTemplate.channel(corChannel));
        int databaseSizeBeforeDelete = traMediaPlanTemplateRepository.findAll().size();

        // Get the traMediaPlanTemplate
        restTraMediaPlantMockMvc.perform(delete("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//configuration/traffic/mediaplan/template/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), traMediaPlanTemplate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TraMediaPlanTemplate> traMediaPlanList = traMediaPlanTemplateRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraMediaPlanTemplate.class);
    }

}