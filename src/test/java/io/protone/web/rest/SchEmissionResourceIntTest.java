package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.SchEmission;
import io.protone.repository.SchEmissionRepository;
import io.protone.service.dto.SchEmissionDTO;
import io.protone.service.mapper.SchEmissionMapper;

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

/**
 * Test class for the SchEmissionResource REST controller.
 *
 * @see SchEmissionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SchEmissionResourceIntTest {

    private static final Integer DEFAULT_SEQ = 1;
    private static final Integer UPDATED_SEQ = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

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
    private SchEmissionRepository schEmissionRepository;

    @Autowired
    private SchEmissionMapper schEmissionMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restSchEmissionMockMvc;

    private SchEmission schEmission;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            SchEmissionResource schEmissionResource = new SchEmissionResource(schEmissionRepository, schEmissionMapper);
        this.restSchEmissionMockMvc = MockMvcBuilders.standaloneSetup(schEmissionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchEmission createEntity(EntityManager em) {
        SchEmission schEmission = new SchEmission()
                .seq(DEFAULT_SEQ)
                .name(DEFAULT_NAME)
                .startTime(DEFAULT_START_TIME)
                .endTime(DEFAULT_END_TIME)
                .length(DEFAULT_LENGTH)
                .dimYear(DEFAULT_DIM_YEAR)
                .dimMonth(DEFAULT_DIM_MONTH)
                .dimDay(DEFAULT_DIM_DAY)
                .dimHour(DEFAULT_DIM_HOUR)
                .dimMinute(DEFAULT_DIM_MINUTE)
                .dimSecond(DEFAULT_DIM_SECOND);
        return schEmission;
    }

    @Before
    public void initTest() {
        schEmission = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchEmission() throws Exception {
        int databaseSizeBeforeCreate = schEmissionRepository.findAll().size();

        // Create the SchEmission
        SchEmissionDTO schEmissionDTO = schEmissionMapper.schEmissionToSchEmissionDTO(schEmission);

        restSchEmissionMockMvc.perform(post("/api/sch-emissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schEmissionDTO)))
            .andExpect(status().isCreated());

        // Validate the SchEmission in the database
        List<SchEmission> schEmissionList = schEmissionRepository.findAll();
        assertThat(schEmissionList).hasSize(databaseSizeBeforeCreate + 1);
        SchEmission testSchEmission = schEmissionList.get(schEmissionList.size() - 1);
        assertThat(testSchEmission.getSeq()).isEqualTo(DEFAULT_SEQ);
        assertThat(testSchEmission.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSchEmission.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testSchEmission.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testSchEmission.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testSchEmission.getDimYear()).isEqualTo(DEFAULT_DIM_YEAR);
        assertThat(testSchEmission.getDimMonth()).isEqualTo(DEFAULT_DIM_MONTH);
        assertThat(testSchEmission.getDimDay()).isEqualTo(DEFAULT_DIM_DAY);
        assertThat(testSchEmission.getDimHour()).isEqualTo(DEFAULT_DIM_HOUR);
        assertThat(testSchEmission.getDimMinute()).isEqualTo(DEFAULT_DIM_MINUTE);
        assertThat(testSchEmission.getDimSecond()).isEqualTo(DEFAULT_DIM_SECOND);
    }

    @Test
    @Transactional
    public void createSchEmissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schEmissionRepository.findAll().size();

        // Create the SchEmission with an existing ID
        SchEmission existingSchEmission = new SchEmission();
        existingSchEmission.setId(1L);
        SchEmissionDTO existingSchEmissionDTO = schEmissionMapper.schEmissionToSchEmissionDTO(existingSchEmission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchEmissionMockMvc.perform(post("/api/sch-emissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSchEmissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchEmission> schEmissionList = schEmissionRepository.findAll();
        assertThat(schEmissionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSeqIsRequired() throws Exception {
        int databaseSizeBeforeTest = schEmissionRepository.findAll().size();
        // set the field null
        schEmission.setSeq(null);

        // Create the SchEmission, which fails.
        SchEmissionDTO schEmissionDTO = schEmissionMapper.schEmissionToSchEmissionDTO(schEmission);

        restSchEmissionMockMvc.perform(post("/api/sch-emissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schEmissionDTO)))
            .andExpect(status().isBadRequest());

        List<SchEmission> schEmissionList = schEmissionRepository.findAll();
        assertThat(schEmissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = schEmissionRepository.findAll().size();
        // set the field null
        schEmission.setName(null);

        // Create the SchEmission, which fails.
        SchEmissionDTO schEmissionDTO = schEmissionMapper.schEmissionToSchEmissionDTO(schEmission);

        restSchEmissionMockMvc.perform(post("/api/sch-emissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schEmissionDTO)))
            .andExpect(status().isBadRequest());

        List<SchEmission> schEmissionList = schEmissionRepository.findAll();
        assertThat(schEmissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchEmissions() throws Exception {
        // Initialize the database
        schEmissionRepository.saveAndFlush(schEmission);

        // Get all the schEmissionList
        restSchEmissionMockMvc.perform(get("/api/sch-emissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schEmission.getId().intValue())))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
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
    public void getSchEmission() throws Exception {
        // Initialize the database
        schEmissionRepository.saveAndFlush(schEmission);

        // Get the schEmission
        restSchEmissionMockMvc.perform(get("/api/sch-emissions/{id}", schEmission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schEmission.getId().intValue()))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
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
    public void getNonExistingSchEmission() throws Exception {
        // Get the schEmission
        restSchEmissionMockMvc.perform(get("/api/sch-emissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchEmission() throws Exception {
        // Initialize the database
        schEmissionRepository.saveAndFlush(schEmission);
        int databaseSizeBeforeUpdate = schEmissionRepository.findAll().size();

        // Update the schEmission
        SchEmission updatedSchEmission = schEmissionRepository.findOne(schEmission.getId());
        updatedSchEmission
                .seq(UPDATED_SEQ)
                .name(UPDATED_NAME)
                .startTime(UPDATED_START_TIME)
                .endTime(UPDATED_END_TIME)
                .length(UPDATED_LENGTH)
                .dimYear(UPDATED_DIM_YEAR)
                .dimMonth(UPDATED_DIM_MONTH)
                .dimDay(UPDATED_DIM_DAY)
                .dimHour(UPDATED_DIM_HOUR)
                .dimMinute(UPDATED_DIM_MINUTE)
                .dimSecond(UPDATED_DIM_SECOND);
        SchEmissionDTO schEmissionDTO = schEmissionMapper.schEmissionToSchEmissionDTO(updatedSchEmission);

        restSchEmissionMockMvc.perform(put("/api/sch-emissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schEmissionDTO)))
            .andExpect(status().isOk());

        // Validate the SchEmission in the database
        List<SchEmission> schEmissionList = schEmissionRepository.findAll();
        assertThat(schEmissionList).hasSize(databaseSizeBeforeUpdate);
        SchEmission testSchEmission = schEmissionList.get(schEmissionList.size() - 1);
        assertThat(testSchEmission.getSeq()).isEqualTo(UPDATED_SEQ);
        assertThat(testSchEmission.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchEmission.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testSchEmission.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testSchEmission.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testSchEmission.getDimYear()).isEqualTo(UPDATED_DIM_YEAR);
        assertThat(testSchEmission.getDimMonth()).isEqualTo(UPDATED_DIM_MONTH);
        assertThat(testSchEmission.getDimDay()).isEqualTo(UPDATED_DIM_DAY);
        assertThat(testSchEmission.getDimHour()).isEqualTo(UPDATED_DIM_HOUR);
        assertThat(testSchEmission.getDimMinute()).isEqualTo(UPDATED_DIM_MINUTE);
        assertThat(testSchEmission.getDimSecond()).isEqualTo(UPDATED_DIM_SECOND);
    }

    @Test
    @Transactional
    public void updateNonExistingSchEmission() throws Exception {
        int databaseSizeBeforeUpdate = schEmissionRepository.findAll().size();

        // Create the SchEmission
        SchEmissionDTO schEmissionDTO = schEmissionMapper.schEmissionToSchEmissionDTO(schEmission);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchEmissionMockMvc.perform(put("/api/sch-emissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schEmissionDTO)))
            .andExpect(status().isCreated());

        // Validate the SchEmission in the database
        List<SchEmission> schEmissionList = schEmissionRepository.findAll();
        assertThat(schEmissionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchEmission() throws Exception {
        // Initialize the database
        schEmissionRepository.saveAndFlush(schEmission);
        int databaseSizeBeforeDelete = schEmissionRepository.findAll().size();

        // Get the schEmission
        restSchEmissionMockMvc.perform(delete("/api/sch-emissions/{id}", schEmission.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SchEmission> schEmissionList = schEmissionRepository.findAll();
        assertThat(schEmissionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchEmission.class);
    }
}
