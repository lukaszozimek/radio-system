package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.SCHTemplate;
import io.protone.repository.SCHTemplateRepository;
import io.protone.service.dto.SCHTemplateDTO;
import io.protone.service.mapper.SCHTemplateMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static io.protone.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.protone.domain.enumeration.SCHBlockTypeEnum;
import io.protone.domain.enumeration.SCHStartTypeEnum;
/**
 * Test class for the SCHTemplateResource REST controller.
 *
 * @see SCHTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SCHTemplateResourceIntTest {

    private static final Integer DEFAULT_SEQ = 1;
    private static final Integer UPDATED_SEQ = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final SCHBlockTypeEnum DEFAULT_TYPE = SCHBlockTypeEnum.BT_DAY;
    private static final SCHBlockTypeEnum UPDATED_TYPE = SCHBlockTypeEnum.BT_BAND;

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final SCHStartTypeEnum DEFAULT_START_TYPE = SCHStartTypeEnum.ST_FREE;
    private static final SCHStartTypeEnum UPDATED_START_TYPE = SCHStartTypeEnum.ST_RELATIVE;

    private static final Long DEFAULT_RELATIVE_DELAY = 1L;
    private static final Long UPDATED_RELATIVE_DELAY = 2L;

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_LENGTH = 1L;
    private static final Long UPDATED_LENGTH = 2L;

    private static final Integer DEFAULT_DIM_YEAR = 1;
    private static final Integer UPDATED_DIM_YEAR = 2;

    private static final Integer DEFAULT_DIM_MONTH = 1;
    private static final Integer UPDATED_DIM_MONTH = 2;

    private static final Integer DEFAULT_DIM_DAY = 1;
    private static final Integer UPDATED_DIM_DAY = 2;

    private static final Integer DEFAULT_DIM_HOUR = 1;
    private static final Integer UPDATED_DIM_HOUR = 2;

    private static final Integer DEFAULT_DIM_MINUTE = 1;
    private static final Integer UPDATED_DIM_MINUTE = 2;

    private static final Integer DEFAULT_DIM_SECOND = 1;
    private static final Integer UPDATED_DIM_SECOND = 2;

    @Inject
    private SCHTemplateRepository sCHTemplateRepository;

    @Inject
    private SCHTemplateMapper sCHTemplateMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSCHTemplateMockMvc;

    private SCHTemplate sCHTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SCHTemplateResource sCHTemplateResource = new SCHTemplateResource();
        ReflectionTestUtils.setField(sCHTemplateResource, "sCHTemplateRepository", sCHTemplateRepository);
        ReflectionTestUtils.setField(sCHTemplateResource, "sCHTemplateMapper", sCHTemplateMapper);
        this.restSCHTemplateMockMvc = MockMvcBuilders.standaloneSetup(sCHTemplateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SCHTemplate createEntity(EntityManager em) {
        SCHTemplate sCHTemplate = new SCHTemplate()
                .seq(DEFAULT_SEQ)
                .name(DEFAULT_NAME)
                .type(DEFAULT_TYPE)
                .startTime(DEFAULT_START_TIME)
                .startType(DEFAULT_START_TYPE)
                .relativeDelay(DEFAULT_RELATIVE_DELAY)
                .endTime(DEFAULT_END_TIME)
                .length(DEFAULT_LENGTH)
                .dimYear(DEFAULT_DIM_YEAR)
                .dimMonth(DEFAULT_DIM_MONTH)
                .dimDay(DEFAULT_DIM_DAY)
                .dimHour(DEFAULT_DIM_HOUR)
                .dimMinute(DEFAULT_DIM_MINUTE)
                .dimSecond(DEFAULT_DIM_SECOND);
        return sCHTemplate;
    }

    @Before
    public void initTest() {
        sCHTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createSCHTemplate() throws Exception {
        int databaseSizeBeforeCreate = sCHTemplateRepository.findAll().size();

        // Create the SCHTemplate
        SCHTemplateDTO sCHTemplateDTO = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(sCHTemplate);

        restSCHTemplateMockMvc.perform(post("/api/s-ch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHTemplateDTO)))
            .andExpect(status().isCreated());

        // Validate the SCHTemplate in the database
        List<SCHTemplate> sCHTemplateList = sCHTemplateRepository.findAll();
        assertThat(sCHTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        SCHTemplate testSCHTemplate = sCHTemplateList.get(sCHTemplateList.size() - 1);
        assertThat(testSCHTemplate.getSeq()).isEqualTo(DEFAULT_SEQ);
        assertThat(testSCHTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSCHTemplate.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSCHTemplate.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testSCHTemplate.getStartType()).isEqualTo(DEFAULT_START_TYPE);
        assertThat(testSCHTemplate.getRelativeDelay()).isEqualTo(DEFAULT_RELATIVE_DELAY);
        assertThat(testSCHTemplate.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testSCHTemplate.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testSCHTemplate.getDimYear()).isEqualTo(DEFAULT_DIM_YEAR);
        assertThat(testSCHTemplate.getDimMonth()).isEqualTo(DEFAULT_DIM_MONTH);
        assertThat(testSCHTemplate.getDimDay()).isEqualTo(DEFAULT_DIM_DAY);
        assertThat(testSCHTemplate.getDimHour()).isEqualTo(DEFAULT_DIM_HOUR);
        assertThat(testSCHTemplate.getDimMinute()).isEqualTo(DEFAULT_DIM_MINUTE);
        assertThat(testSCHTemplate.getDimSecond()).isEqualTo(DEFAULT_DIM_SECOND);
    }

    @Test
    @Transactional
    public void createSCHTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sCHTemplateRepository.findAll().size();

        // Create the SCHTemplate with an existing ID
        SCHTemplate existingSCHTemplate = new SCHTemplate();
        existingSCHTemplate.setId(1L);
        SCHTemplateDTO existingSCHTemplateDTO = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(existingSCHTemplate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSCHTemplateMockMvc.perform(post("/api/s-ch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSCHTemplateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SCHTemplate> sCHTemplateList = sCHTemplateRepository.findAll();
        assertThat(sCHTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSeqIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHTemplateRepository.findAll().size();
        // set the field null
        sCHTemplate.setSeq(null);

        // Create the SCHTemplate, which fails.
        SCHTemplateDTO sCHTemplateDTO = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(sCHTemplate);

        restSCHTemplateMockMvc.perform(post("/api/s-ch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<SCHTemplate> sCHTemplateList = sCHTemplateRepository.findAll();
        assertThat(sCHTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHTemplateRepository.findAll().size();
        // set the field null
        sCHTemplate.setName(null);

        // Create the SCHTemplate, which fails.
        SCHTemplateDTO sCHTemplateDTO = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(sCHTemplate);

        restSCHTemplateMockMvc.perform(post("/api/s-ch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<SCHTemplate> sCHTemplateList = sCHTemplateRepository.findAll();
        assertThat(sCHTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHTemplateRepository.findAll().size();
        // set the field null
        sCHTemplate.setType(null);

        // Create the SCHTemplate, which fails.
        SCHTemplateDTO sCHTemplateDTO = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(sCHTemplate);

        restSCHTemplateMockMvc.perform(post("/api/s-ch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<SCHTemplate> sCHTemplateList = sCHTemplateRepository.findAll();
        assertThat(sCHTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHTemplateRepository.findAll().size();
        // set the field null
        sCHTemplate.setStartTime(null);

        // Create the SCHTemplate, which fails.
        SCHTemplateDTO sCHTemplateDTO = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(sCHTemplate);

        restSCHTemplateMockMvc.perform(post("/api/s-ch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<SCHTemplate> sCHTemplateList = sCHTemplateRepository.findAll();
        assertThat(sCHTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHTemplateRepository.findAll().size();
        // set the field null
        sCHTemplate.setStartType(null);

        // Create the SCHTemplate, which fails.
        SCHTemplateDTO sCHTemplateDTO = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(sCHTemplate);

        restSCHTemplateMockMvc.perform(post("/api/s-ch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<SCHTemplate> sCHTemplateList = sCHTemplateRepository.findAll();
        assertThat(sCHTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHTemplateRepository.findAll().size();
        // set the field null
        sCHTemplate.setEndTime(null);

        // Create the SCHTemplate, which fails.
        SCHTemplateDTO sCHTemplateDTO = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(sCHTemplate);

        restSCHTemplateMockMvc.perform(post("/api/s-ch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<SCHTemplate> sCHTemplateList = sCHTemplateRepository.findAll();
        assertThat(sCHTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHTemplateRepository.findAll().size();
        // set the field null
        sCHTemplate.setLength(null);

        // Create the SCHTemplate, which fails.
        SCHTemplateDTO sCHTemplateDTO = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(sCHTemplate);

        restSCHTemplateMockMvc.perform(post("/api/s-ch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<SCHTemplate> sCHTemplateList = sCHTemplateRepository.findAll();
        assertThat(sCHTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDimYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHTemplateRepository.findAll().size();
        // set the field null
        sCHTemplate.setDimYear(null);

        // Create the SCHTemplate, which fails.
        SCHTemplateDTO sCHTemplateDTO = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(sCHTemplate);

        restSCHTemplateMockMvc.perform(post("/api/s-ch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<SCHTemplate> sCHTemplateList = sCHTemplateRepository.findAll();
        assertThat(sCHTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDimMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHTemplateRepository.findAll().size();
        // set the field null
        sCHTemplate.setDimMonth(null);

        // Create the SCHTemplate, which fails.
        SCHTemplateDTO sCHTemplateDTO = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(sCHTemplate);

        restSCHTemplateMockMvc.perform(post("/api/s-ch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<SCHTemplate> sCHTemplateList = sCHTemplateRepository.findAll();
        assertThat(sCHTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDimDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHTemplateRepository.findAll().size();
        // set the field null
        sCHTemplate.setDimDay(null);

        // Create the SCHTemplate, which fails.
        SCHTemplateDTO sCHTemplateDTO = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(sCHTemplate);

        restSCHTemplateMockMvc.perform(post("/api/s-ch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<SCHTemplate> sCHTemplateList = sCHTemplateRepository.findAll();
        assertThat(sCHTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDimHourIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHTemplateRepository.findAll().size();
        // set the field null
        sCHTemplate.setDimHour(null);

        // Create the SCHTemplate, which fails.
        SCHTemplateDTO sCHTemplateDTO = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(sCHTemplate);

        restSCHTemplateMockMvc.perform(post("/api/s-ch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<SCHTemplate> sCHTemplateList = sCHTemplateRepository.findAll();
        assertThat(sCHTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDimMinuteIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHTemplateRepository.findAll().size();
        // set the field null
        sCHTemplate.setDimMinute(null);

        // Create the SCHTemplate, which fails.
        SCHTemplateDTO sCHTemplateDTO = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(sCHTemplate);

        restSCHTemplateMockMvc.perform(post("/api/s-ch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<SCHTemplate> sCHTemplateList = sCHTemplateRepository.findAll();
        assertThat(sCHTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDimSecondIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHTemplateRepository.findAll().size();
        // set the field null
        sCHTemplate.setDimSecond(null);

        // Create the SCHTemplate, which fails.
        SCHTemplateDTO sCHTemplateDTO = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(sCHTemplate);

        restSCHTemplateMockMvc.perform(post("/api/s-ch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHTemplateDTO)))
            .andExpect(status().isBadRequest());

        List<SCHTemplate> sCHTemplateList = sCHTemplateRepository.findAll();
        assertThat(sCHTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSCHTemplates() throws Exception {
        // Initialize the database
        sCHTemplateRepository.saveAndFlush(sCHTemplate);

        // Get all the sCHTemplateList
        restSCHTemplateMockMvc.perform(get("/api/s-ch-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sCHTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].startType").value(hasItem(DEFAULT_START_TYPE.toString())))
            .andExpect(jsonPath("$.[*].relativeDelay").value(hasItem(DEFAULT_RELATIVE_DELAY.intValue())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].dimYear").value(hasItem(DEFAULT_DIM_YEAR)))
            .andExpect(jsonPath("$.[*].dimMonth").value(hasItem(DEFAULT_DIM_MONTH)))
            .andExpect(jsonPath("$.[*].dimDay").value(hasItem(DEFAULT_DIM_DAY)))
            .andExpect(jsonPath("$.[*].dimHour").value(hasItem(DEFAULT_DIM_HOUR)))
            .andExpect(jsonPath("$.[*].dimMinute").value(hasItem(DEFAULT_DIM_MINUTE)))
            .andExpect(jsonPath("$.[*].dimSecond").value(hasItem(DEFAULT_DIM_SECOND)));
    }

    @Test
    @Transactional
    public void getSCHTemplate() throws Exception {
        // Initialize the database
        sCHTemplateRepository.saveAndFlush(sCHTemplate);

        // Get the sCHTemplate
        restSCHTemplateMockMvc.perform(get("/api/s-ch-templates/{id}", sCHTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sCHTemplate.getId().intValue()))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.startType").value(DEFAULT_START_TYPE.toString()))
            .andExpect(jsonPath("$.relativeDelay").value(DEFAULT_RELATIVE_DELAY.intValue()))
            .andExpect(jsonPath("$.endTime").value(sameInstant(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.intValue()))
            .andExpect(jsonPath("$.dimYear").value(DEFAULT_DIM_YEAR))
            .andExpect(jsonPath("$.dimMonth").value(DEFAULT_DIM_MONTH))
            .andExpect(jsonPath("$.dimDay").value(DEFAULT_DIM_DAY))
            .andExpect(jsonPath("$.dimHour").value(DEFAULT_DIM_HOUR))
            .andExpect(jsonPath("$.dimMinute").value(DEFAULT_DIM_MINUTE))
            .andExpect(jsonPath("$.dimSecond").value(DEFAULT_DIM_SECOND));
    }

    @Test
    @Transactional
    public void getNonExistingSCHTemplate() throws Exception {
        // Get the sCHTemplate
        restSCHTemplateMockMvc.perform(get("/api/s-ch-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSCHTemplate() throws Exception {
        // Initialize the database
        sCHTemplateRepository.saveAndFlush(sCHTemplate);
        int databaseSizeBeforeUpdate = sCHTemplateRepository.findAll().size();

        // Update the sCHTemplate
        SCHTemplate updatedSCHTemplate = sCHTemplateRepository.findOne(sCHTemplate.getId());
        updatedSCHTemplate
                .seq(UPDATED_SEQ)
                .name(UPDATED_NAME)
                .type(UPDATED_TYPE)
                .startTime(UPDATED_START_TIME)
                .startType(UPDATED_START_TYPE)
                .relativeDelay(UPDATED_RELATIVE_DELAY)
                .endTime(UPDATED_END_TIME)
                .length(UPDATED_LENGTH)
                .dimYear(UPDATED_DIM_YEAR)
                .dimMonth(UPDATED_DIM_MONTH)
                .dimDay(UPDATED_DIM_DAY)
                .dimHour(UPDATED_DIM_HOUR)
                .dimMinute(UPDATED_DIM_MINUTE)
                .dimSecond(UPDATED_DIM_SECOND);
        SCHTemplateDTO sCHTemplateDTO = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(updatedSCHTemplate);

        restSCHTemplateMockMvc.perform(put("/api/s-ch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHTemplateDTO)))
            .andExpect(status().isOk());

        // Validate the SCHTemplate in the database
        List<SCHTemplate> sCHTemplateList = sCHTemplateRepository.findAll();
        assertThat(sCHTemplateList).hasSize(databaseSizeBeforeUpdate);
        SCHTemplate testSCHTemplate = sCHTemplateList.get(sCHTemplateList.size() - 1);
        assertThat(testSCHTemplate.getSeq()).isEqualTo(UPDATED_SEQ);
        assertThat(testSCHTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSCHTemplate.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSCHTemplate.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testSCHTemplate.getStartType()).isEqualTo(UPDATED_START_TYPE);
        assertThat(testSCHTemplate.getRelativeDelay()).isEqualTo(UPDATED_RELATIVE_DELAY);
        assertThat(testSCHTemplate.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testSCHTemplate.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testSCHTemplate.getDimYear()).isEqualTo(UPDATED_DIM_YEAR);
        assertThat(testSCHTemplate.getDimMonth()).isEqualTo(UPDATED_DIM_MONTH);
        assertThat(testSCHTemplate.getDimDay()).isEqualTo(UPDATED_DIM_DAY);
        assertThat(testSCHTemplate.getDimHour()).isEqualTo(UPDATED_DIM_HOUR);
        assertThat(testSCHTemplate.getDimMinute()).isEqualTo(UPDATED_DIM_MINUTE);
        assertThat(testSCHTemplate.getDimSecond()).isEqualTo(UPDATED_DIM_SECOND);
    }

    @Test
    @Transactional
    public void updateNonExistingSCHTemplate() throws Exception {
        int databaseSizeBeforeUpdate = sCHTemplateRepository.findAll().size();

        // Create the SCHTemplate
        SCHTemplateDTO sCHTemplateDTO = sCHTemplateMapper.sCHTemplateToSCHTemplateDTO(sCHTemplate);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSCHTemplateMockMvc.perform(put("/api/s-ch-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHTemplateDTO)))
            .andExpect(status().isCreated());

        // Validate the SCHTemplate in the database
        List<SCHTemplate> sCHTemplateList = sCHTemplateRepository.findAll();
        assertThat(sCHTemplateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSCHTemplate() throws Exception {
        // Initialize the database
        sCHTemplateRepository.saveAndFlush(sCHTemplate);
        int databaseSizeBeforeDelete = sCHTemplateRepository.findAll().size();

        // Get the sCHTemplate
        restSCHTemplateMockMvc.perform(delete("/api/s-ch-templates/{id}", sCHTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SCHTemplate> sCHTemplateList = sCHTemplateRepository.findAll();
        assertThat(sCHTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
