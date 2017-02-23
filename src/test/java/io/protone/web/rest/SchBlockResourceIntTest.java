package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.SchBlock;
import io.protone.repository.SchBlockRepository;
import io.protone.service.dto.SchBlockDTO;
import io.protone.service.mapper.SchBlockMapper;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

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

import io.protone.domain.enumeration.SchBlockTypeEnum;
import io.protone.domain.enumeration.SchStartTypeEnum;
/**
 * Test class for the SchBlockResource REST controller.
 *
 * @see SchBlockResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SchBlockResourceIntTest {

    private static final Integer DEFAULT_SEQ = 1;
    private static final Integer UPDATED_SEQ = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final SchBlockTypeEnum DEFAULT_TYPE = SchBlockTypeEnum.BT_DAY;
    private static final SchBlockTypeEnum UPDATED_TYPE = SchBlockTypeEnum.BT_BAND;

    private static final SchStartTypeEnum DEFAULT_START_TYPE = SchStartTypeEnum.ST_FREE;
    private static final SchStartTypeEnum UPDATED_START_TYPE = SchStartTypeEnum.ST_RELATIVE;

    private static final Long DEFAULT_RELATIVE_DELAY = 1L;
    private static final Long UPDATED_RELATIVE_DELAY = 2L;

    private static final ZonedDateTime DEFAULT_SCHEDULED_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SCHEDULED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_SCHEDULED_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SCHEDULED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_SCHEDULED_LENGTH = 1L;
    private static final Long UPDATED_SCHEDULED_LENGTH = 2L;

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

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

    @Autowired
    private SchBlockRepository schBlockRepository;

    @Autowired
    private SchBlockMapper schBlockMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restSchBlockMockMvc;

    private SchBlock schBlock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            SchBlockResource schBlockResource = new SchBlockResource(schBlockRepository, schBlockMapper);
        this.restSchBlockMockMvc = MockMvcBuilders.standaloneSetup(schBlockResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchBlock createEntity(EntityManager em) {
        SchBlock schBlock = new SchBlock()
                .seq(DEFAULT_SEQ)
                .name(DEFAULT_NAME)
                .type(DEFAULT_TYPE)
                .startType(DEFAULT_START_TYPE)
                .relativeDelay(DEFAULT_RELATIVE_DELAY)
                .scheduledStartTime(DEFAULT_SCHEDULED_START_TIME)
                .scheduledEndTime(DEFAULT_SCHEDULED_END_TIME)
                .scheduledLength(DEFAULT_SCHEDULED_LENGTH)
                .startTime(DEFAULT_START_TIME)
                .endTime(DEFAULT_END_TIME)
                .length(DEFAULT_LENGTH)
                .dimYear(DEFAULT_DIM_YEAR)
                .dimMonth(DEFAULT_DIM_MONTH)
                .dimDay(DEFAULT_DIM_DAY)
                .dimHour(DEFAULT_DIM_HOUR)
                .dimMinute(DEFAULT_DIM_MINUTE)
                .dimSecond(DEFAULT_DIM_SECOND);
        return schBlock;
    }

    @Before
    public void initTest() {
        schBlock = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchBlock() throws Exception {
        int databaseSizeBeforeCreate = schBlockRepository.findAll().size();

        // Create the SchBlock
        SchBlockDTO schBlockDTO = schBlockMapper.schBlockToSchBlockDTO(schBlock);

        restSchBlockMockMvc.perform(post("/api/sch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schBlockDTO)))
            .andExpect(status().isCreated());

        // Validate the SchBlock in the database
        List<SchBlock> schBlockList = schBlockRepository.findAll();
        assertThat(schBlockList).hasSize(databaseSizeBeforeCreate + 1);
        SchBlock testSchBlock = schBlockList.get(schBlockList.size() - 1);
        assertThat(testSchBlock.getSeq()).isEqualTo(DEFAULT_SEQ);
        assertThat(testSchBlock.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSchBlock.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSchBlock.getStartType()).isEqualTo(DEFAULT_START_TYPE);
        assertThat(testSchBlock.getRelativeDelay()).isEqualTo(DEFAULT_RELATIVE_DELAY);
        assertThat(testSchBlock.getScheduledStartTime()).isEqualTo(DEFAULT_SCHEDULED_START_TIME);
        assertThat(testSchBlock.getScheduledEndTime()).isEqualTo(DEFAULT_SCHEDULED_END_TIME);
        assertThat(testSchBlock.getScheduledLength()).isEqualTo(DEFAULT_SCHEDULED_LENGTH);
        assertThat(testSchBlock.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testSchBlock.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testSchBlock.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testSchBlock.getDimYear()).isEqualTo(DEFAULT_DIM_YEAR);
        assertThat(testSchBlock.getDimMonth()).isEqualTo(DEFAULT_DIM_MONTH);
        assertThat(testSchBlock.getDimDay()).isEqualTo(DEFAULT_DIM_DAY);
        assertThat(testSchBlock.getDimHour()).isEqualTo(DEFAULT_DIM_HOUR);
        assertThat(testSchBlock.getDimMinute()).isEqualTo(DEFAULT_DIM_MINUTE);
        assertThat(testSchBlock.getDimSecond()).isEqualTo(DEFAULT_DIM_SECOND);
    }

    @Test
    @Transactional
    public void createSchBlockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schBlockRepository.findAll().size();

        // Create the SchBlock with an existing ID
        SchBlock existingSchBlock = new SchBlock();
        existingSchBlock.setId(1L);
        SchBlockDTO existingSchBlockDTO = schBlockMapper.schBlockToSchBlockDTO(existingSchBlock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchBlockMockMvc.perform(post("/api/sch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSchBlockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchBlock> schBlockList = schBlockRepository.findAll();
        assertThat(schBlockList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSeqIsRequired() throws Exception {
        int databaseSizeBeforeTest = schBlockRepository.findAll().size();
        // set the field null
        schBlock.setSeq(null);

        // Create the SchBlock, which fails.
        SchBlockDTO schBlockDTO = schBlockMapper.schBlockToSchBlockDTO(schBlock);

        restSchBlockMockMvc.perform(post("/api/sch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schBlockDTO)))
            .andExpect(status().isBadRequest());

        List<SchBlock> schBlockList = schBlockRepository.findAll();
        assertThat(schBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = schBlockRepository.findAll().size();
        // set the field null
        schBlock.setName(null);

        // Create the SchBlock, which fails.
        SchBlockDTO schBlockDTO = schBlockMapper.schBlockToSchBlockDTO(schBlock);

        restSchBlockMockMvc.perform(post("/api/sch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schBlockDTO)))
            .andExpect(status().isBadRequest());

        List<SchBlock> schBlockList = schBlockRepository.findAll();
        assertThat(schBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchBlocks() throws Exception {
        // Initialize the database
        schBlockRepository.saveAndFlush(schBlock);

        // Get all the schBlockList
        restSchBlockMockMvc.perform(get("/api/sch-blocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schBlock.getId().intValue())))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].startType").value(hasItem(DEFAULT_START_TYPE.toString())))
            .andExpect(jsonPath("$.[*].relativeDelay").value(hasItem(DEFAULT_RELATIVE_DELAY.intValue())))
            .andExpect(jsonPath("$.[*].scheduledStartTime").value(hasItem(sameInstant(DEFAULT_SCHEDULED_START_TIME))))
            .andExpect(jsonPath("$.[*].scheduledEndTime").value(hasItem(sameInstant(DEFAULT_SCHEDULED_END_TIME))))
            .andExpect(jsonPath("$.[*].scheduledLength").value(hasItem(DEFAULT_SCHEDULED_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
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
    public void getSchBlock() throws Exception {
        // Initialize the database
        schBlockRepository.saveAndFlush(schBlock);

        // Get the schBlock
        restSchBlockMockMvc.perform(get("/api/sch-blocks/{id}", schBlock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schBlock.getId().intValue()))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.startType").value(DEFAULT_START_TYPE.toString()))
            .andExpect(jsonPath("$.relativeDelay").value(DEFAULT_RELATIVE_DELAY.intValue()))
            .andExpect(jsonPath("$.scheduledStartTime").value(sameInstant(DEFAULT_SCHEDULED_START_TIME)))
            .andExpect(jsonPath("$.scheduledEndTime").value(sameInstant(DEFAULT_SCHEDULED_END_TIME)))
            .andExpect(jsonPath("$.scheduledLength").value(DEFAULT_SCHEDULED_LENGTH.intValue()))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
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
    public void getNonExistingSchBlock() throws Exception {
        // Get the schBlock
        restSchBlockMockMvc.perform(get("/api/sch-blocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchBlock() throws Exception {
        // Initialize the database
        schBlockRepository.saveAndFlush(schBlock);
        int databaseSizeBeforeUpdate = schBlockRepository.findAll().size();

        // Update the schBlock
        SchBlock updatedSchBlock = schBlockRepository.findOne(schBlock.getId());
        updatedSchBlock
                .seq(UPDATED_SEQ)
                .name(UPDATED_NAME)
                .type(UPDATED_TYPE)
                .startType(UPDATED_START_TYPE)
                .relativeDelay(UPDATED_RELATIVE_DELAY)
                .scheduledStartTime(UPDATED_SCHEDULED_START_TIME)
                .scheduledEndTime(UPDATED_SCHEDULED_END_TIME)
                .scheduledLength(UPDATED_SCHEDULED_LENGTH)
                .startTime(UPDATED_START_TIME)
                .endTime(UPDATED_END_TIME)
                .length(UPDATED_LENGTH)
                .dimYear(UPDATED_DIM_YEAR)
                .dimMonth(UPDATED_DIM_MONTH)
                .dimDay(UPDATED_DIM_DAY)
                .dimHour(UPDATED_DIM_HOUR)
                .dimMinute(UPDATED_DIM_MINUTE)
                .dimSecond(UPDATED_DIM_SECOND);
        SchBlockDTO schBlockDTO = schBlockMapper.schBlockToSchBlockDTO(updatedSchBlock);

        restSchBlockMockMvc.perform(put("/api/sch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schBlockDTO)))
            .andExpect(status().isOk());

        // Validate the SchBlock in the database
        List<SchBlock> schBlockList = schBlockRepository.findAll();
        assertThat(schBlockList).hasSize(databaseSizeBeforeUpdate);
        SchBlock testSchBlock = schBlockList.get(schBlockList.size() - 1);
        assertThat(testSchBlock.getSeq()).isEqualTo(UPDATED_SEQ);
        assertThat(testSchBlock.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchBlock.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSchBlock.getStartType()).isEqualTo(UPDATED_START_TYPE);
        assertThat(testSchBlock.getRelativeDelay()).isEqualTo(UPDATED_RELATIVE_DELAY);
        assertThat(testSchBlock.getScheduledStartTime()).isEqualTo(UPDATED_SCHEDULED_START_TIME);
        assertThat(testSchBlock.getScheduledEndTime()).isEqualTo(UPDATED_SCHEDULED_END_TIME);
        assertThat(testSchBlock.getScheduledLength()).isEqualTo(UPDATED_SCHEDULED_LENGTH);
        assertThat(testSchBlock.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testSchBlock.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testSchBlock.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testSchBlock.getDimYear()).isEqualTo(UPDATED_DIM_YEAR);
        assertThat(testSchBlock.getDimMonth()).isEqualTo(UPDATED_DIM_MONTH);
        assertThat(testSchBlock.getDimDay()).isEqualTo(UPDATED_DIM_DAY);
        assertThat(testSchBlock.getDimHour()).isEqualTo(UPDATED_DIM_HOUR);
        assertThat(testSchBlock.getDimMinute()).isEqualTo(UPDATED_DIM_MINUTE);
        assertThat(testSchBlock.getDimSecond()).isEqualTo(UPDATED_DIM_SECOND);
    }

    @Test
    @Transactional
    public void updateNonExistingSchBlock() throws Exception {
        int databaseSizeBeforeUpdate = schBlockRepository.findAll().size();

        // Create the SchBlock
        SchBlockDTO schBlockDTO = schBlockMapper.schBlockToSchBlockDTO(schBlock);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchBlockMockMvc.perform(put("/api/sch-blocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schBlockDTO)))
            .andExpect(status().isCreated());

        // Validate the SchBlock in the database
        List<SchBlock> schBlockList = schBlockRepository.findAll();
        assertThat(schBlockList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchBlock() throws Exception {
        // Initialize the database
        schBlockRepository.saveAndFlush(schBlock);
        int databaseSizeBeforeDelete = schBlockRepository.findAll().size();

        // Get the schBlock
        restSchBlockMockMvc.perform(delete("/api/sch-blocks/{id}", schBlock.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SchBlock> schBlockList = schBlockRepository.findAll();
        assertThat(schBlockList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchBlock.class);
    }
}
