package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.SCHEmission;
import io.protone.repository.SCHEmissionRepository;
import io.protone.service.dto.SCHEmissionDTO;
import io.protone.service.mapper.SCHEmissionMapper;

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

import io.protone.domain.enumeration.SCHStartTypeEnum;
/**
 * Test class for the SCHEmissionResource REST controller.
 *
 * @see SCHEmissionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SCHEmissionResourceIntTest {

    private static final Integer DEFAULT_SEQ = 1;
    private static final Integer UPDATED_SEQ = 2;

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

    private static final Boolean DEFAULT_FINISHED = false;
    private static final Boolean UPDATED_FINISHED = true;

    @Inject
    private SCHEmissionRepository sCHEmissionRepository;

    @Inject
    private SCHEmissionMapper sCHEmissionMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSCHEmissionMockMvc;

    private SCHEmission sCHEmission;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SCHEmissionResource sCHEmissionResource = new SCHEmissionResource();
        ReflectionTestUtils.setField(sCHEmissionResource, "sCHEmissionRepository", sCHEmissionRepository);
        ReflectionTestUtils.setField(sCHEmissionResource, "sCHEmissionMapper", sCHEmissionMapper);
        this.restSCHEmissionMockMvc = MockMvcBuilders.standaloneSetup(sCHEmissionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SCHEmission createEntity(EntityManager em) {
        SCHEmission sCHEmission = new SCHEmission()
                .seq(DEFAULT_SEQ)
                .startTime(DEFAULT_START_TIME)
                .startType(DEFAULT_START_TYPE)
                .relativeDelay(DEFAULT_RELATIVE_DELAY)
                .endTime(DEFAULT_END_TIME)
                .length(DEFAULT_LENGTH)
                .finished(DEFAULT_FINISHED);
        return sCHEmission;
    }

    @Before
    public void initTest() {
        sCHEmission = createEntity(em);
    }

    @Test
    @Transactional
    public void createSCHEmission() throws Exception {
        int databaseSizeBeforeCreate = sCHEmissionRepository.findAll().size();

        // Create the SCHEmission
        SCHEmissionDTO sCHEmissionDTO = sCHEmissionMapper.sCHEmissionToSCHEmissionDTO(sCHEmission);

        restSCHEmissionMockMvc.perform(post("/api/s-ch-emissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHEmissionDTO)))
            .andExpect(status().isCreated());

        // Validate the SCHEmission in the database
        List<SCHEmission> sCHEmissionList = sCHEmissionRepository.findAll();
        assertThat(sCHEmissionList).hasSize(databaseSizeBeforeCreate + 1);
        SCHEmission testSCHEmission = sCHEmissionList.get(sCHEmissionList.size() - 1);
        assertThat(testSCHEmission.getSeq()).isEqualTo(DEFAULT_SEQ);
        assertThat(testSCHEmission.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testSCHEmission.getStartType()).isEqualTo(DEFAULT_START_TYPE);
        assertThat(testSCHEmission.getRelativeDelay()).isEqualTo(DEFAULT_RELATIVE_DELAY);
        assertThat(testSCHEmission.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testSCHEmission.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testSCHEmission.isFinished()).isEqualTo(DEFAULT_FINISHED);
    }

    @Test
    @Transactional
    public void createSCHEmissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sCHEmissionRepository.findAll().size();

        // Create the SCHEmission with an existing ID
        SCHEmission existingSCHEmission = new SCHEmission();
        existingSCHEmission.setId(1L);
        SCHEmissionDTO existingSCHEmissionDTO = sCHEmissionMapper.sCHEmissionToSCHEmissionDTO(existingSCHEmission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSCHEmissionMockMvc.perform(post("/api/s-ch-emissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSCHEmissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SCHEmission> sCHEmissionList = sCHEmissionRepository.findAll();
        assertThat(sCHEmissionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSeqIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHEmissionRepository.findAll().size();
        // set the field null
        sCHEmission.setSeq(null);

        // Create the SCHEmission, which fails.
        SCHEmissionDTO sCHEmissionDTO = sCHEmissionMapper.sCHEmissionToSCHEmissionDTO(sCHEmission);

        restSCHEmissionMockMvc.perform(post("/api/s-ch-emissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHEmissionDTO)))
            .andExpect(status().isBadRequest());

        List<SCHEmission> sCHEmissionList = sCHEmissionRepository.findAll();
        assertThat(sCHEmissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHEmissionRepository.findAll().size();
        // set the field null
        sCHEmission.setStartTime(null);

        // Create the SCHEmission, which fails.
        SCHEmissionDTO sCHEmissionDTO = sCHEmissionMapper.sCHEmissionToSCHEmissionDTO(sCHEmission);

        restSCHEmissionMockMvc.perform(post("/api/s-ch-emissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHEmissionDTO)))
            .andExpect(status().isBadRequest());

        List<SCHEmission> sCHEmissionList = sCHEmissionRepository.findAll();
        assertThat(sCHEmissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHEmissionRepository.findAll().size();
        // set the field null
        sCHEmission.setStartType(null);

        // Create the SCHEmission, which fails.
        SCHEmissionDTO sCHEmissionDTO = sCHEmissionMapper.sCHEmissionToSCHEmissionDTO(sCHEmission);

        restSCHEmissionMockMvc.perform(post("/api/s-ch-emissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHEmissionDTO)))
            .andExpect(status().isBadRequest());

        List<SCHEmission> sCHEmissionList = sCHEmissionRepository.findAll();
        assertThat(sCHEmissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHEmissionRepository.findAll().size();
        // set the field null
        sCHEmission.setEndTime(null);

        // Create the SCHEmission, which fails.
        SCHEmissionDTO sCHEmissionDTO = sCHEmissionMapper.sCHEmissionToSCHEmissionDTO(sCHEmission);

        restSCHEmissionMockMvc.perform(post("/api/s-ch-emissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHEmissionDTO)))
            .andExpect(status().isBadRequest());

        List<SCHEmission> sCHEmissionList = sCHEmissionRepository.findAll();
        assertThat(sCHEmissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHEmissionRepository.findAll().size();
        // set the field null
        sCHEmission.setLength(null);

        // Create the SCHEmission, which fails.
        SCHEmissionDTO sCHEmissionDTO = sCHEmissionMapper.sCHEmissionToSCHEmissionDTO(sCHEmission);

        restSCHEmissionMockMvc.perform(post("/api/s-ch-emissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHEmissionDTO)))
            .andExpect(status().isBadRequest());

        List<SCHEmission> sCHEmissionList = sCHEmissionRepository.findAll();
        assertThat(sCHEmissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFinishedIsRequired() throws Exception {
        int databaseSizeBeforeTest = sCHEmissionRepository.findAll().size();
        // set the field null
        sCHEmission.setFinished(null);

        // Create the SCHEmission, which fails.
        SCHEmissionDTO sCHEmissionDTO = sCHEmissionMapper.sCHEmissionToSCHEmissionDTO(sCHEmission);

        restSCHEmissionMockMvc.perform(post("/api/s-ch-emissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHEmissionDTO)))
            .andExpect(status().isBadRequest());

        List<SCHEmission> sCHEmissionList = sCHEmissionRepository.findAll();
        assertThat(sCHEmissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSCHEmissions() throws Exception {
        // Initialize the database
        sCHEmissionRepository.saveAndFlush(sCHEmission);

        // Get all the sCHEmissionList
        restSCHEmissionMockMvc.perform(get("/api/s-ch-emissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sCHEmission.getId().intValue())))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].startType").value(hasItem(DEFAULT_START_TYPE.toString())))
            .andExpect(jsonPath("$.[*].relativeDelay").value(hasItem(DEFAULT_RELATIVE_DELAY.intValue())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].finished").value(hasItem(DEFAULT_FINISHED.booleanValue())));
    }

    @Test
    @Transactional
    public void getSCHEmission() throws Exception {
        // Initialize the database
        sCHEmissionRepository.saveAndFlush(sCHEmission);

        // Get the sCHEmission
        restSCHEmissionMockMvc.perform(get("/api/s-ch-emissions/{id}", sCHEmission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sCHEmission.getId().intValue()))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.startType").value(DEFAULT_START_TYPE.toString()))
            .andExpect(jsonPath("$.relativeDelay").value(DEFAULT_RELATIVE_DELAY.intValue()))
            .andExpect(jsonPath("$.endTime").value(sameInstant(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.intValue()))
            .andExpect(jsonPath("$.finished").value(DEFAULT_FINISHED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSCHEmission() throws Exception {
        // Get the sCHEmission
        restSCHEmissionMockMvc.perform(get("/api/s-ch-emissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSCHEmission() throws Exception {
        // Initialize the database
        sCHEmissionRepository.saveAndFlush(sCHEmission);
        int databaseSizeBeforeUpdate = sCHEmissionRepository.findAll().size();

        // Update the sCHEmission
        SCHEmission updatedSCHEmission = sCHEmissionRepository.findOne(sCHEmission.getId());
        updatedSCHEmission
                .seq(UPDATED_SEQ)
                .startTime(UPDATED_START_TIME)
                .startType(UPDATED_START_TYPE)
                .relativeDelay(UPDATED_RELATIVE_DELAY)
                .endTime(UPDATED_END_TIME)
                .length(UPDATED_LENGTH)
                .finished(UPDATED_FINISHED);
        SCHEmissionDTO sCHEmissionDTO = sCHEmissionMapper.sCHEmissionToSCHEmissionDTO(updatedSCHEmission);

        restSCHEmissionMockMvc.perform(put("/api/s-ch-emissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHEmissionDTO)))
            .andExpect(status().isOk());

        // Validate the SCHEmission in the database
        List<SCHEmission> sCHEmissionList = sCHEmissionRepository.findAll();
        assertThat(sCHEmissionList).hasSize(databaseSizeBeforeUpdate);
        SCHEmission testSCHEmission = sCHEmissionList.get(sCHEmissionList.size() - 1);
        assertThat(testSCHEmission.getSeq()).isEqualTo(UPDATED_SEQ);
        assertThat(testSCHEmission.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testSCHEmission.getStartType()).isEqualTo(UPDATED_START_TYPE);
        assertThat(testSCHEmission.getRelativeDelay()).isEqualTo(UPDATED_RELATIVE_DELAY);
        assertThat(testSCHEmission.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testSCHEmission.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testSCHEmission.isFinished()).isEqualTo(UPDATED_FINISHED);
    }

    @Test
    @Transactional
    public void updateNonExistingSCHEmission() throws Exception {
        int databaseSizeBeforeUpdate = sCHEmissionRepository.findAll().size();

        // Create the SCHEmission
        SCHEmissionDTO sCHEmissionDTO = sCHEmissionMapper.sCHEmissionToSCHEmissionDTO(sCHEmission);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSCHEmissionMockMvc.perform(put("/api/s-ch-emissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sCHEmissionDTO)))
            .andExpect(status().isCreated());

        // Validate the SCHEmission in the database
        List<SCHEmission> sCHEmissionList = sCHEmissionRepository.findAll();
        assertThat(sCHEmissionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSCHEmission() throws Exception {
        // Initialize the database
        sCHEmissionRepository.saveAndFlush(sCHEmission);
        int databaseSizeBeforeDelete = sCHEmissionRepository.findAll().size();

        // Get the sCHEmission
        restSCHEmissionMockMvc.perform(delete("/api/s-ch-emissions/{id}", sCHEmission.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SCHEmission> sCHEmissionList = sCHEmissionRepository.findAll();
        assertThat(sCHEmissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
