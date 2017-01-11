package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.SCHBlock;
import io.protone.repository.SCHBlockRepository;
import io.protone.service.dto.SCHBlockDTO;
import io.protone.service.mapper.SCHBlockMapper;

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
 * Test class for the SCHBlockResource REST controller.
 *
 * @see SCHBlockResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SCHBlockResourceIntTest {

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
    private SCHBlockRepository sCHBlockRepository;

    @Inject
    private SCHBlockMapper sCHBlockMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSCHBlockMockMvc;

    private SCHBlock sCHBlock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SCHBlockResource sCHBlockResource = new SCHBlockResource();
        ReflectionTestUtils.setField(sCHBlockResource, "sCHBlockRepository", sCHBlockRepository);
        ReflectionTestUtils.setField(sCHBlockResource, "sCHBlockMapper", sCHBlockMapper);
        this.restSCHBlockMockMvc = MockMvcBuilders.standaloneSetup(sCHBlockResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SCHBlock createEntity(EntityManager em) {
        SCHBlock sCHBlock = new SCHBlock()
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
        return sCHBlock;
    }

    @Before
    public void initTest() {
        sCHBlock = createEntity(em);
    }

    @Test
    @Transactional
    public void createSCHBlock() throws Exception {
        int databaseSizeBeforeCreate = sCHBlockRepository.findAll().size();

        // Create the SCHBlock
        SCHBlockDTO sCHBlockDTO = sCHBlockMapper.sCHBlockToSCHBlockDTO(sCHBlock);

        restSCHBlockMockMvc.perform(post("/api/s-ch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHBlockDTO)))
            .andExpect(status().isCreated());

        // Validate the SCHBlock in the database
        List<SCHBlock> sCHBlockList = sCHBlockRepository.findAll();
        assertThat(sCHBlockList).hasSize(databaseSizeBeforeCreate + 1);
        SCHBlock testSCHBlock = sCHBlockList.get(sCHBlockList.size() - 1);
        assertThat(testSCHBlock.getSeq()).isEqualTo(DEFAULT_SEQ);
        assertThat(testSCHBlock.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSCHBlock.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSCHBlock.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testSCHBlock.getStartType()).isEqualTo(DEFAULT_START_TYPE);
        assertThat(testSCHBlock.getRelativeDelay()).isEqualTo(DEFAULT_RELATIVE_DELAY);
        assertThat(testSCHBlock.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testSCHBlock.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testSCHBlock.getDimYear()).isEqualTo(DEFAULT_DIM_YEAR);
        assertThat(testSCHBlock.getDimMonth()).isEqualTo(DEFAULT_DIM_MONTH);
        assertThat(testSCHBlock.getDimDay()).isEqualTo(DEFAULT_DIM_DAY);
        assertThat(testSCHBlock.getDimHour()).isEqualTo(DEFAULT_DIM_HOUR);
        assertThat(testSCHBlock.getDimMinute()).isEqualTo(DEFAULT_DIM_MINUTE);
        assertThat(testSCHBlock.getDimSecond()).isEqualTo(DEFAULT_DIM_SECOND);
    }

    @Test
    @Transactional
    public void createSCHBlockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sCHBlockRepository.findAll().size();

        // Create the SCHBlock with an existing ID
        SCHBlock existingSCHBlock = new SCHBlock();
        existingSCHBlock.setId(1L);
        SCHBlockDTO existingSCHBlockDTO = sCHBlockMapper.sCHBlockToSCHBlockDTO(existingSCHBlock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSCHBlockMockMvc.perform(post("/api/s-ch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSCHBlockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SCHBlock> sCHBlockList = sCHBlockRepository.findAll();
        assertThat(sCHBlockList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSeqIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHBlockRepository.findAll().size();
        // set the field null
        sCHBlock.setSeq(null);

        // Create the SCHBlock, which fails.
        SCHBlockDTO sCHBlockDTO = sCHBlockMapper.sCHBlockToSCHBlockDTO(sCHBlock);

        restSCHBlockMockMvc.perform(post("/api/s-ch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHBlockDTO)))
            .andExpect(status().isBadRequest());

        List<SCHBlock> sCHBlockList = sCHBlockRepository.findAll();
        assertThat(sCHBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHBlockRepository.findAll().size();
        // set the field null
        sCHBlock.setName(null);

        // Create the SCHBlock, which fails.
        SCHBlockDTO sCHBlockDTO = sCHBlockMapper.sCHBlockToSCHBlockDTO(sCHBlock);

        restSCHBlockMockMvc.perform(post("/api/s-ch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHBlockDTO)))
            .andExpect(status().isBadRequest());

        List<SCHBlock> sCHBlockList = sCHBlockRepository.findAll();
        assertThat(sCHBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHBlockRepository.findAll().size();
        // set the field null
        sCHBlock.setType(null);

        // Create the SCHBlock, which fails.
        SCHBlockDTO sCHBlockDTO = sCHBlockMapper.sCHBlockToSCHBlockDTO(sCHBlock);

        restSCHBlockMockMvc.perform(post("/api/s-ch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHBlockDTO)))
            .andExpect(status().isBadRequest());

        List<SCHBlock> sCHBlockList = sCHBlockRepository.findAll();
        assertThat(sCHBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHBlockRepository.findAll().size();
        // set the field null
        sCHBlock.setStartTime(null);

        // Create the SCHBlock, which fails.
        SCHBlockDTO sCHBlockDTO = sCHBlockMapper.sCHBlockToSCHBlockDTO(sCHBlock);

        restSCHBlockMockMvc.perform(post("/api/s-ch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHBlockDTO)))
            .andExpect(status().isBadRequest());

        List<SCHBlock> sCHBlockList = sCHBlockRepository.findAll();
        assertThat(sCHBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHBlockRepository.findAll().size();
        // set the field null
        sCHBlock.setStartType(null);

        // Create the SCHBlock, which fails.
        SCHBlockDTO sCHBlockDTO = sCHBlockMapper.sCHBlockToSCHBlockDTO(sCHBlock);

        restSCHBlockMockMvc.perform(post("/api/s-ch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHBlockDTO)))
            .andExpect(status().isBadRequest());

        List<SCHBlock> sCHBlockList = sCHBlockRepository.findAll();
        assertThat(sCHBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHBlockRepository.findAll().size();
        // set the field null
        sCHBlock.setEndTime(null);

        // Create the SCHBlock, which fails.
        SCHBlockDTO sCHBlockDTO = sCHBlockMapper.sCHBlockToSCHBlockDTO(sCHBlock);

        restSCHBlockMockMvc.perform(post("/api/s-ch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHBlockDTO)))
            .andExpect(status().isBadRequest());

        List<SCHBlock> sCHBlockList = sCHBlockRepository.findAll();
        assertThat(sCHBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHBlockRepository.findAll().size();
        // set the field null
        sCHBlock.setLength(null);

        // Create the SCHBlock, which fails.
        SCHBlockDTO sCHBlockDTO = sCHBlockMapper.sCHBlockToSCHBlockDTO(sCHBlock);

        restSCHBlockMockMvc.perform(post("/api/s-ch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHBlockDTO)))
            .andExpect(status().isBadRequest());

        List<SCHBlock> sCHBlockList = sCHBlockRepository.findAll();
        assertThat(sCHBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDimYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHBlockRepository.findAll().size();
        // set the field null
        sCHBlock.setDimYear(null);

        // Create the SCHBlock, which fails.
        SCHBlockDTO sCHBlockDTO = sCHBlockMapper.sCHBlockToSCHBlockDTO(sCHBlock);

        restSCHBlockMockMvc.perform(post("/api/s-ch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHBlockDTO)))
            .andExpect(status().isBadRequest());

        List<SCHBlock> sCHBlockList = sCHBlockRepository.findAll();
        assertThat(sCHBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDimMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHBlockRepository.findAll().size();
        // set the field null
        sCHBlock.setDimMonth(null);

        // Create the SCHBlock, which fails.
        SCHBlockDTO sCHBlockDTO = sCHBlockMapper.sCHBlockToSCHBlockDTO(sCHBlock);

        restSCHBlockMockMvc.perform(post("/api/s-ch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHBlockDTO)))
            .andExpect(status().isBadRequest());

        List<SCHBlock> sCHBlockList = sCHBlockRepository.findAll();
        assertThat(sCHBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDimDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHBlockRepository.findAll().size();
        // set the field null
        sCHBlock.setDimDay(null);

        // Create the SCHBlock, which fails.
        SCHBlockDTO sCHBlockDTO = sCHBlockMapper.sCHBlockToSCHBlockDTO(sCHBlock);

        restSCHBlockMockMvc.perform(post("/api/s-ch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHBlockDTO)))
            .andExpect(status().isBadRequest());

        List<SCHBlock> sCHBlockList = sCHBlockRepository.findAll();
        assertThat(sCHBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDimHourIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHBlockRepository.findAll().size();
        // set the field null
        sCHBlock.setDimHour(null);

        // Create the SCHBlock, which fails.
        SCHBlockDTO sCHBlockDTO = sCHBlockMapper.sCHBlockToSCHBlockDTO(sCHBlock);

        restSCHBlockMockMvc.perform(post("/api/s-ch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHBlockDTO)))
            .andExpect(status().isBadRequest());

        List<SCHBlock> sCHBlockList = sCHBlockRepository.findAll();
        assertThat(sCHBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDimMinuteIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHBlockRepository.findAll().size();
        // set the field null
        sCHBlock.setDimMinute(null);

        // Create the SCHBlock, which fails.
        SCHBlockDTO sCHBlockDTO = sCHBlockMapper.sCHBlockToSCHBlockDTO(sCHBlock);

        restSCHBlockMockMvc.perform(post("/api/s-ch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHBlockDTO)))
            .andExpect(status().isBadRequest());

        List<SCHBlock> sCHBlockList = sCHBlockRepository.findAll();
        assertThat(sCHBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDimSecondIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHBlockRepository.findAll().size();
        // set the field null
        sCHBlock.setDimSecond(null);

        // Create the SCHBlock, which fails.
        SCHBlockDTO sCHBlockDTO = sCHBlockMapper.sCHBlockToSCHBlockDTO(sCHBlock);

        restSCHBlockMockMvc.perform(post("/api/s-ch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHBlockDTO)))
            .andExpect(status().isBadRequest());

        List<SCHBlock> sCHBlockList = sCHBlockRepository.findAll();
        assertThat(sCHBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSCHBlocks() throws Exception {
        // Initialize the database
        sCHBlockRepository.saveAndFlush(sCHBlock);

        // Get all the sCHBlockList
        restSCHBlockMockMvc.perform(get("/api/s-ch-blocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sCHBlock.getId().intValue())))
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
    public void getSCHBlock() throws Exception {
        // Initialize the database
        sCHBlockRepository.saveAndFlush(sCHBlock);

        // Get the sCHBlock
        restSCHBlockMockMvc.perform(get("/api/s-ch-blocks/{id}", sCHBlock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sCHBlock.getId().intValue()))
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
    public void getNonExistingSCHBlock() throws Exception {
        // Get the sCHBlock
        restSCHBlockMockMvc.perform(get("/api/s-ch-blocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSCHBlock() throws Exception {
        // Initialize the database
        sCHBlockRepository.saveAndFlush(sCHBlock);
        int databaseSizeBeforeUpdate = sCHBlockRepository.findAll().size();

        // Update the sCHBlock
        SCHBlock updatedSCHBlock = sCHBlockRepository.findOne(sCHBlock.getId());
        updatedSCHBlock
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
        SCHBlockDTO sCHBlockDTO = sCHBlockMapper.sCHBlockToSCHBlockDTO(updatedSCHBlock);

        restSCHBlockMockMvc.perform(put("/api/s-ch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHBlockDTO)))
            .andExpect(status().isOk());

        // Validate the SCHBlock in the database
        List<SCHBlock> sCHBlockList = sCHBlockRepository.findAll();
        assertThat(sCHBlockList).hasSize(databaseSizeBeforeUpdate);
        SCHBlock testSCHBlock = sCHBlockList.get(sCHBlockList.size() - 1);
        assertThat(testSCHBlock.getSeq()).isEqualTo(UPDATED_SEQ);
        assertThat(testSCHBlock.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSCHBlock.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSCHBlock.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testSCHBlock.getStartType()).isEqualTo(UPDATED_START_TYPE);
        assertThat(testSCHBlock.getRelativeDelay()).isEqualTo(UPDATED_RELATIVE_DELAY);
        assertThat(testSCHBlock.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testSCHBlock.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testSCHBlock.getDimYear()).isEqualTo(UPDATED_DIM_YEAR);
        assertThat(testSCHBlock.getDimMonth()).isEqualTo(UPDATED_DIM_MONTH);
        assertThat(testSCHBlock.getDimDay()).isEqualTo(UPDATED_DIM_DAY);
        assertThat(testSCHBlock.getDimHour()).isEqualTo(UPDATED_DIM_HOUR);
        assertThat(testSCHBlock.getDimMinute()).isEqualTo(UPDATED_DIM_MINUTE);
        assertThat(testSCHBlock.getDimSecond()).isEqualTo(UPDATED_DIM_SECOND);
    }

    @Test
    @Transactional
    public void updateNonExistingSCHBlock() throws Exception {
        int databaseSizeBeforeUpdate = sCHBlockRepository.findAll().size();

        // Create the SCHBlock
        SCHBlockDTO sCHBlockDTO = sCHBlockMapper.sCHBlockToSCHBlockDTO(sCHBlock);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSCHBlockMockMvc.perform(put("/api/s-ch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHBlockDTO)))
            .andExpect(status().isCreated());

        // Validate the SCHBlock in the database
        List<SCHBlock> sCHBlockList = sCHBlockRepository.findAll();
        assertThat(sCHBlockList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSCHBlock() throws Exception {
        // Initialize the database
        sCHBlockRepository.saveAndFlush(sCHBlock);
        int databaseSizeBeforeDelete = sCHBlockRepository.findAll().size();

        // Get the sCHBlock
        restSCHBlockMockMvc.perform(delete("/api/s-ch-blocks/{id}", sCHBlock.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SCHBlock> sCHBlockList = sCHBlockRepository.findAll();
        assertThat(sCHBlockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
