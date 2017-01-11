package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CRMTask;
import io.protone.repository.CRMTaskRepository;
import io.protone.service.dto.CRMTaskDTO;
import io.protone.service.mapper.CRMTaskMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CRMTaskResource REST controller.
 *
 * @see CRMTaskResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CRMTaskResourceIntTest {

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ACTIVITY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVITY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_ACTIVITY_LENGTH = 1L;
    private static final Long UPDATED_ACTIVITY_LENGTH = 2L;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Inject
    private CRMTaskRepository cRMTaskRepository;

    @Inject
    private CRMTaskMapper cRMTaskMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCRMTaskMockMvc;

    private CRMTask cRMTask;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CRMTaskResource cRMTaskResource = new CRMTaskResource();
        ReflectionTestUtils.setField(cRMTaskResource, "cRMTaskRepository", cRMTaskRepository);
        ReflectionTestUtils.setField(cRMTaskResource, "cRMTaskMapper", cRMTaskMapper);
        this.restCRMTaskMockMvc = MockMvcBuilders.standaloneSetup(cRMTaskResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRMTask createEntity(EntityManager em) {
        CRMTask cRMTask = new CRMTask()
                .subject(DEFAULT_SUBJECT)
                .activityDate(DEFAULT_ACTIVITY_DATE)
                .activityLength(DEFAULT_ACTIVITY_LENGTH)
                .comment(DEFAULT_COMMENT);
        return cRMTask;
    }

    @Before
    public void initTest() {
        cRMTask = createEntity(em);
    }

    @Test
    @Transactional
    public void createCRMTask() throws Exception {
        int databaseSizeBeforeCreate = cRMTaskRepository.findAll().size();

        // Create the CRMTask
        CRMTaskDTO cRMTaskDTO = cRMTaskMapper.cRMTaskToCRMTaskDTO(cRMTask);

        restCRMTaskMockMvc.perform(post("/api/c-rm-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMTaskDTO)))
            .andExpect(status().isCreated());

        // Validate the CRMTask in the database
        List<CRMTask> cRMTaskList = cRMTaskRepository.findAll();
        assertThat(cRMTaskList).hasSize(databaseSizeBeforeCreate + 1);
        CRMTask testCRMTask = cRMTaskList.get(cRMTaskList.size() - 1);
        assertThat(testCRMTask.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testCRMTask.getActivityDate()).isEqualTo(DEFAULT_ACTIVITY_DATE);
        assertThat(testCRMTask.getActivityLength()).isEqualTo(DEFAULT_ACTIVITY_LENGTH);
        assertThat(testCRMTask.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createCRMTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cRMTaskRepository.findAll().size();

        // Create the CRMTask with an existing ID
        CRMTask existingCRMTask = new CRMTask();
        existingCRMTask.setId(1L);
        CRMTaskDTO existingCRMTaskDTO = cRMTaskMapper.cRMTaskToCRMTaskDTO(existingCRMTask);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCRMTaskMockMvc.perform(post("/api/c-rm-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCRMTaskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CRMTask> cRMTaskList = cRMTaskRepository.findAll();
        assertThat(cRMTaskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCRMTasks() throws Exception {
        // Initialize the database
        cRMTaskRepository.saveAndFlush(cRMTask);

        // Get all the cRMTaskList
        restCRMTaskMockMvc.perform(get("/api/c-rm-tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRMTask.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].activityDate").value(hasItem(DEFAULT_ACTIVITY_DATE.toString())))
            .andExpect(jsonPath("$.[*].activityLength").value(hasItem(DEFAULT_ACTIVITY_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getCRMTask() throws Exception {
        // Initialize the database
        cRMTaskRepository.saveAndFlush(cRMTask);

        // Get the cRMTask
        restCRMTaskMockMvc.perform(get("/api/c-rm-tasks/{id}", cRMTask.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cRMTask.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.activityDate").value(DEFAULT_ACTIVITY_DATE.toString()))
            .andExpect(jsonPath("$.activityLength").value(DEFAULT_ACTIVITY_LENGTH.intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCRMTask() throws Exception {
        // Get the cRMTask
        restCRMTaskMockMvc.perform(get("/api/c-rm-tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCRMTask() throws Exception {
        // Initialize the database
        cRMTaskRepository.saveAndFlush(cRMTask);
        int databaseSizeBeforeUpdate = cRMTaskRepository.findAll().size();

        // Update the cRMTask
        CRMTask updatedCRMTask = cRMTaskRepository.findOne(cRMTask.getId());
        updatedCRMTask
                .subject(UPDATED_SUBJECT)
                .activityDate(UPDATED_ACTIVITY_DATE)
                .activityLength(UPDATED_ACTIVITY_LENGTH)
                .comment(UPDATED_COMMENT);
        CRMTaskDTO cRMTaskDTO = cRMTaskMapper.cRMTaskToCRMTaskDTO(updatedCRMTask);

        restCRMTaskMockMvc.perform(put("/api/c-rm-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMTaskDTO)))
            .andExpect(status().isOk());

        // Validate the CRMTask in the database
        List<CRMTask> cRMTaskList = cRMTaskRepository.findAll();
        assertThat(cRMTaskList).hasSize(databaseSizeBeforeUpdate);
        CRMTask testCRMTask = cRMTaskList.get(cRMTaskList.size() - 1);
        assertThat(testCRMTask.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testCRMTask.getActivityDate()).isEqualTo(UPDATED_ACTIVITY_DATE);
        assertThat(testCRMTask.getActivityLength()).isEqualTo(UPDATED_ACTIVITY_LENGTH);
        assertThat(testCRMTask.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingCRMTask() throws Exception {
        int databaseSizeBeforeUpdate = cRMTaskRepository.findAll().size();

        // Create the CRMTask
        CRMTaskDTO cRMTaskDTO = cRMTaskMapper.cRMTaskToCRMTaskDTO(cRMTask);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCRMTaskMockMvc.perform(put("/api/c-rm-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMTaskDTO)))
            .andExpect(status().isCreated());

        // Validate the CRMTask in the database
        List<CRMTask> cRMTaskList = cRMTaskRepository.findAll();
        assertThat(cRMTaskList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCRMTask() throws Exception {
        // Initialize the database
        cRMTaskRepository.saveAndFlush(cRMTask);
        int databaseSizeBeforeDelete = cRMTaskRepository.findAll().size();

        // Get the cRMTask
        restCRMTaskMockMvc.perform(delete("/api/c-rm-tasks/{id}", cRMTask.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CRMTask> cRMTaskList = cRMTaskRepository.findAll();
        assertThat(cRMTaskList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
