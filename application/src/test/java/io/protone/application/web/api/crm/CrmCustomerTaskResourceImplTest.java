package io.protone.application.web.api.crm;


import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.application.web.api.crm.impl.CrmCustomerTaskResourceImpl;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.crm.api.dto.CrmTaskDTO;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.domain.CrmTask;
import io.protone.crm.mapper.CrmTaskMapper;
import io.protone.crm.repostiory.CrmAccountRepository;
import io.protone.crm.repostiory.CrmTaskRepository;
import io.protone.crm.service.CrmCustomerService;
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
 * Created by lukaszozimek on 02/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmCustomerTaskResourceImplTest {


    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ACTIVITY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVITY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_ACTIVITY_LENGTH = 1L;
    private static final Long UPDATED_ACTIVITY_LENGTH = 2L;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Inject
    private CrmCustomerService crmCustomerService;

    @Inject
    private CrmTaskRepository crmTaskRepository;

    @Inject
    private CrmTaskMapper crmTaskMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    @Inject
    private CrmAccountRepository crmAccountRepository;


    private MockMvc restCrmTaskMockMvc;

    private CrmTask crmTask;

    private CorNetwork corNetwork;

    private CrmAccount crmAccount;

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
        CrmCustomerTaskResourceImpl crmTaskResource = new CrmCustomerTaskResourceImpl();
        ReflectionTestUtils.setField(crmTaskResource, "crmCustomerService", crmCustomerService);
        ReflectionTestUtils.setField(crmTaskResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(crmTaskResource, "crmTaskMapper", crmTaskMapper);

        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);

        this.restCrmTaskMockMvc = MockMvcBuilders.standaloneSetup(crmTaskResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        crmAccount = CrmCustomerResourceImplTest.createEntity(em).network(corNetwork);
        crmTask = createEntity(em).network(corNetwork);
    }

    @Test
    @Transactional
    public void createCrmTask() throws Exception {
        crmAccountRepository.deleteAll();
        crmAccount = crmAccountRepository.save(crmAccount.network(corNetwork));
        int databaseSizeBeforeCreate = crmTaskRepository.findAll().size();

        // Create the CrmTask
        CrmTaskDTO crmTaskDTO = crmTaskMapper.DB2DTO(crmTask.network(corNetwork).account(crmAccount));

        restCrmTaskMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/customer/{shortName}/task", corNetwork.getShortcut(), crmAccount.getShortName())
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
        crmAccountRepository.deleteAll();
        crmAccountRepository.save(crmAccount.network(corNetwork));
        int databaseSizeBeforeCreate = crmTaskRepository.findAll().size();

        // Create the CrmTask with an existing ID
        CrmTask existingCrmTask = new CrmTask();
        existingCrmTask.setId(1L);
        CrmTaskDTO existingCrmTaskDTO = crmTaskMapper.DB2DTO(existingCrmTask.account(crmAccount));

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrmTaskMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/customer/{shortName}/task", corNetwork.getShortcut(), crmAccount.getShortName())
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
        crmAccountRepository.deleteAll();
        crmAccountRepository.save(crmAccount.network(corNetwork));
        // Initialize the database
        crmTaskRepository.saveAndFlush(crmTask.network(corNetwork).account(crmAccount));

        // Get all the crmTaskList
        restCrmTaskMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/customer/{shortName}/task?sort=id,desc", corNetwork.getShortcut(), crmAccount.getShortName()))
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
        crmAccountRepository.deleteAll();
        crmAccountRepository.save(crmAccount.network(corNetwork));
        // Initialize the database
        crmTaskRepository.saveAndFlush(crmTask.network(corNetwork).account(crmAccount));

        // Get the crmTask
        restCrmTaskMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/customer/{shortName}/task/{id}", corNetwork.getShortcut(), crmAccount.getShortName(), crmTask.getId()))
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
        restCrmTaskMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/customer/{shortName}/task/{id}", corNetwork.getShortcut(), crmAccount.getShortName(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrmTask() throws Exception {
        crmAccountRepository.deleteAll();
        crmAccountRepository.save(crmAccount.network(corNetwork));
        // Initialize the database
        crmTaskRepository.saveAndFlush(crmTask.account(crmAccount).network(corNetwork));
        int databaseSizeBeforeUpdate = crmTaskRepository.findAll().size();

        // Update the crmTask
        CrmTask updatedCrmTask = crmTaskRepository.findOne(crmTask.getId());
        updatedCrmTask
            .subject(UPDATED_SUBJECT)
            .activityDate(UPDATED_ACTIVITY_DATE)
            .activityLength(UPDATED_ACTIVITY_LENGTH)
            .comment(UPDATED_COMMENT);
        CrmTaskDTO crmTaskDTO = crmTaskMapper.DB2DTO(updatedCrmTask);

        restCrmTaskMockMvc.perform(put("/api/v1/network/{networkShortcut}/crm/customer/{shortName}/task", corNetwork.getShortcut(), CrmContactResourceImplTest.createEntity(em).getShortName())
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
        crmAccountRepository.deleteAll();
        crmAccountRepository.save(crmAccount.network(corNetwork));
        int databaseSizeBeforeUpdate = crmTaskRepository.findAll().size();

        // Create the CrmTask
        CrmTaskDTO crmTaskDTO = crmTaskMapper.DB2DTO(crmTask.account(crmAccount));

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCrmTaskMockMvc.perform(put("/api/v1/network/{networkShortcut}/crm/customer/{shortName}/task", corNetwork.getShortcut(), crmAccount.getShortName())
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
        crmAccountRepository.deleteAll();
        crmAccountRepository.save(crmAccount.network(corNetwork));
        // Initialize the database
        crmTaskRepository.saveAndFlush(crmTask.network(corNetwork).account(crmAccount));
        int databaseSizeBeforeDelete = crmTaskRepository.findAll().size();

        // Get the crmTask
        restCrmTaskMockMvc.perform(delete("/api/v1/network/{networkShortcut}/crm/customer/{shortName}/task/{id}", corNetwork.getShortcut(), crmAccount.getShortName(), crmTask.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CrmTask> crmTaskList = crmTaskRepository.findAll();
        assertThat(crmTaskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void checkShortNameIsRequired() throws Exception {
        crmAccountRepository.deleteAll();
        crmAccountRepository.save(crmAccount.network(corNetwork));
        int databaseSizeBeforeTest = crmTaskRepository.findAll().size();
        // set the field null
        crmTask.setSubject(null);

        // Create the CfgMarkerConfiguration, which fails.
        CrmTaskDTO cfgMarkerConfigurationDTO = crmTaskMapper.DB2DTO(crmTask);

        restCrmTaskMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/customer/{shortName}/task", corNetwork.getShortcut(), crmAccount.getShortName())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfgMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<CrmTask> crmAccounts = crmTaskRepository.findAll();
        assertThat(crmAccounts).hasSize(databaseSizeBeforeTest);
    }
}
