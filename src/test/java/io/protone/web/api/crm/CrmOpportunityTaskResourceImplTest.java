package io.protone.web.api.crm;

import io.protone.ProtoneApp;
import io.protone.web.rest.dto.crm.CrmTaskDTO;
import io.protone.custom.web.rest.network.TestUtil;
import io.protone.web.api.crm.impl.CrmOpportunityTaskResourceImpl;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmOpportunity;
import io.protone.domain.CrmTask;
import io.protone.repository.crm.CrmOpportunityRepository;
import io.protone.repository.crm.CrmTaskRepository;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.crm.CrmOpportunityService;
import io.protone.web.rest.mapper.CrmTaskMapper;
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

import static io.protone.web.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by lukaszozimek on 02/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmOpportunityTaskResourceImplTest {


    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ACTIVITY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVITY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_ACTIVITY_LENGTH = 1L;
    private static final Long UPDATED_ACTIVITY_LENGTH = 2L;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Inject
    private CrmTaskRepository crmTaskRepository;
    @Inject
    private CorNetworkService corNetworkService;
    @Inject
    private CrmTaskMapper crmTaskMapper;
    @Inject
    private CrmOpportunityService crmOpportunityService;
    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    @Inject
    private CrmOpportunityRepository crmOpportunityRepository;


    private MockMvc restCrmTaskMockMvc;

    private CrmTask crmTask;

    private CorNetwork corNetwork;

    private CrmOpportunity crmOpportunity;


    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrmTask createEntity(EntityManager em) {
        CrmTask crmTask = new CrmTask()
            .subject(DEFAULT_SUBJECT)
            .activityDate(DEFAULT_ACTIVITY_DATE)
            .activityLength(DEFAULT_ACTIVITY_LENGTH)
            .comment(DEFAULT_COMMENT);
        return crmTask;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CrmOpportunityTaskResourceImpl crmTaskResource = new CrmOpportunityTaskResourceImpl();
        ReflectionTestUtils.setField(crmTaskResource, "crmOpportunityService", crmOpportunityService);
        ReflectionTestUtils.setField(crmTaskResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(crmTaskResource, "crmTaskMapper", crmTaskMapper);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);

        this.restCrmTaskMockMvc = MockMvcBuilders.standaloneSetup(crmTaskResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        crmOpportunity = CrmOpportunityResourceImplTest.createEntity(em).network(corNetwork);
        crmTask = createEntity(em).network(corNetwork);
    }

    @Test
    @Transactional
    public void createCrmTask() throws Exception {
        crmOpportunityRepository.deleteAll();
        crmOpportunity = crmOpportunityRepository.save(crmOpportunity.network(corNetwork));
        int databaseSizeBeforeCreate = crmTaskRepository.findAll().size();

        // Create the CrmTask
        CrmTaskDTO crmTaskDTO = crmTaskMapper.DB2DTO(crmTask.network(corNetwork).opportunity(crmOpportunity));

        restCrmTaskMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task", corNetwork.getShortcut(), crmOpportunity.getName())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmTaskDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmTask in the database
        List<CrmTask> crmTaskList = crmTaskRepository.findAll();
        assertThat(crmTaskList).hasSize(databaseSizeBeforeCreate + 1);
        CrmTask testCrmTask = crmTaskList.get(crmTaskList.size() - 1);
        assertThat(testCrmTask.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testCrmTask.getActivityDate()).isEqualTo(DEFAULT_ACTIVITY_DATE);
        assertThat(testCrmTask.getActivityLength()).isEqualTo(DEFAULT_ACTIVITY_LENGTH);
        assertThat(testCrmTask.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createCrmTaskWithExistingId() throws Exception {
        crmOpportunityRepository.deleteAll();
        crmOpportunityRepository.save(crmOpportunity.network(corNetwork));
        int databaseSizeBeforeCreate = crmTaskRepository.findAll().size();

        // Create the CrmTask with an existing ID
        CrmTask existingCrmTask = new CrmTask();
        existingCrmTask.setId(1L);
        CrmTaskDTO existingCrmTaskDTO = crmTaskMapper.DB2DTO(existingCrmTask.opportunity(crmOpportunity));

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrmTaskMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task", corNetwork.getShortcut(), crmOpportunity.getName())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCrmTaskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CrmTask> crmTaskList = crmTaskRepository.findAll();
        assertThat(crmTaskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCrmTasks() throws Exception {
        crmOpportunityRepository.deleteAll();

        crmOpportunityRepository.save(crmOpportunity.network(corNetwork));
        // Initialize the database
        crmTaskRepository.saveAndFlush(crmTask.network(corNetwork).opportunity(crmOpportunity));

        // Get all the crmTaskList
        restCrmTaskMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task?sort=id,desc", corNetwork.getShortcut(), crmOpportunity.getName()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmTask.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].activityDate").value(hasItem(DEFAULT_ACTIVITY_DATE.toString())))
            .andExpect(jsonPath("$.[*].activityLength").value(hasItem(DEFAULT_ACTIVITY_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getCrmTask() throws Exception {
        crmOpportunityRepository.deleteAll();
        crmOpportunityRepository.save(crmOpportunity.network(corNetwork));
        // Initialize the database
        crmTaskRepository.saveAndFlush(crmTask.network(corNetwork).opportunity(crmOpportunity));

        // Get the crmTask
        restCrmTaskMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task/{id}", corNetwork.getShortcut(), crmOpportunity.getName(), crmTask.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(crmTask.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.activityDate").value(DEFAULT_ACTIVITY_DATE.toString()))
            .andExpect(jsonPath("$.activityLength").value(DEFAULT_ACTIVITY_LENGTH.intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCrmTask() throws Exception {
        // Get the crmTask
        restCrmTaskMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task/{id}", corNetwork.getShortcut(), crmOpportunity.getName(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrmTask() throws Exception {
        crmOpportunityRepository.deleteAll();
        crmOpportunityRepository.save(crmOpportunity.network(corNetwork));
        // Initialize the database
        crmTaskRepository.saveAndFlush(crmTask.opportunity(crmOpportunity));
        int databaseSizeBeforeUpdate = crmTaskRepository.findAll().size();

        // Update the crmTask
        CrmTask updatedCrmTask = crmTaskRepository.findOne(crmTask.getId());
        updatedCrmTask
            .subject(UPDATED_SUBJECT)
            .activityDate(UPDATED_ACTIVITY_DATE)
            .activityLength(UPDATED_ACTIVITY_LENGTH)
            .comment(UPDATED_COMMENT);
        CrmTaskDTO crmTaskDTO = crmTaskMapper.DB2DTO(updatedCrmTask);

        restCrmTaskMockMvc.perform(put("/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task", corNetwork.getShortcut(), crmOpportunity.getName())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmTaskDTO)))
            .andExpect(status().isOk());

        // Validate the CrmTask in the database
        List<CrmTask> crmTaskList = crmTaskRepository.findAll();
        assertThat(crmTaskList).hasSize(databaseSizeBeforeUpdate);
        CrmTask testCrmTask = crmTaskList.get(crmTaskList.size() - 1);
        assertThat(testCrmTask.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testCrmTask.getActivityDate()).isEqualTo(UPDATED_ACTIVITY_DATE);
        assertThat(testCrmTask.getActivityLength()).isEqualTo(UPDATED_ACTIVITY_LENGTH);
        assertThat(testCrmTask.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingCrmTask() throws Exception {
        crmOpportunityRepository.deleteAll();
        crmOpportunityRepository.save(crmOpportunity.network(corNetwork));
        int databaseSizeBeforeUpdate = crmTaskRepository.findAll().size();

        // Create the CrmTask
        CrmTaskDTO crmTaskDTO = crmTaskMapper.DB2DTO(crmTask.opportunity(crmOpportunity));

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCrmTaskMockMvc.perform(put("/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task", corNetwork.getShortcut(), crmOpportunity.getName())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmTaskDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmTask in the database
        List<CrmTask> crmTaskList = crmTaskRepository.findAll();
        assertThat(crmTaskList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCrmTask() throws Exception {
        crmOpportunityRepository.deleteAll();
        crmOpportunityRepository.save(crmOpportunity.network(corNetwork));
        // Initialize the database
        crmTaskRepository.saveAndFlush(crmTask.network(corNetwork).opportunity(crmOpportunity));
        int databaseSizeBeforeDelete = crmTaskRepository.findAll().size();

        // Get the crmTask
        restCrmTaskMockMvc.perform(delete("/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task/{id}", corNetwork.getShortcut(), crmOpportunity.getName(), crmTask.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CrmTask> crmTaskList = crmTaskRepository.findAll();
        assertThat(crmTaskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void checkShortNameIsRequired() throws Exception {
        crmOpportunityRepository.deleteAll();
        crmOpportunityRepository.save(crmOpportunity.network(corNetwork));
        int databaseSizeBeforeTest = crmTaskRepository.findAll().size();
        // set the field null
        crmTask.setSubject(null);

        // Create the CfgMarkerConfiguration, which fails.
        CrmTaskDTO cfgMarkerConfigurationDTO = crmTaskMapper.DB2DTO(crmTask);

        restCrmTaskMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}/task", corNetwork.getShortcut(), crmOpportunity.getName())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfgMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<CrmTask> crmTasks = crmTaskRepository.findAll();
        assertThat(crmTasks).hasSize(databaseSizeBeforeTest);
    }

}