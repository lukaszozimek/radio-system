package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CrmTaskStatus;
import io.protone.repository.CrmTaskStatusRepository;
import io.protone.service.dto.CrmTaskStatusDTO;
import io.protone.service.mapper.CrmTaskStatusMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CrmTaskStatusResource REST controller.
 *
 * @see CrmTaskStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmTaskStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CrmTaskStatusRepository crmTaskStatusRepository;

    @Autowired
    private CrmTaskStatusMapper crmTaskStatusMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCrmTaskStatusMockMvc;

    private CrmTaskStatus crmTaskStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CrmTaskStatusResource crmTaskStatusResource = new CrmTaskStatusResource(crmTaskStatusRepository, crmTaskStatusMapper);
        this.restCrmTaskStatusMockMvc = MockMvcBuilders.standaloneSetup(crmTaskStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrmTaskStatus createEntity(EntityManager em) {
        CrmTaskStatus crmTaskStatus = new CrmTaskStatus()
                .name(DEFAULT_NAME);
        return crmTaskStatus;
    }

    @Before
    public void initTest() {
        crmTaskStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCrmTaskStatus() throws Exception {
        int databaseSizeBeforeCreate = crmTaskStatusRepository.findAll().size();

        // Create the CrmTaskStatus
        CrmTaskStatusDTO crmTaskStatusDTO = crmTaskStatusMapper.crmTaskStatusToCrmTaskStatusDTO(crmTaskStatus);

        restCrmTaskStatusMockMvc.perform(post("/api/crm-task-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmTaskStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmTaskStatus in the database
        List<CrmTaskStatus> crmTaskStatusList = crmTaskStatusRepository.findAll();
        assertThat(crmTaskStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CrmTaskStatus testCrmTaskStatus = crmTaskStatusList.get(crmTaskStatusList.size() - 1);
        assertThat(testCrmTaskStatus.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCrmTaskStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = crmTaskStatusRepository.findAll().size();

        // Create the CrmTaskStatus with an existing ID
        CrmTaskStatus existingCrmTaskStatus = new CrmTaskStatus();
        existingCrmTaskStatus.setId(1L);
        CrmTaskStatusDTO existingCrmTaskStatusDTO = crmTaskStatusMapper.crmTaskStatusToCrmTaskStatusDTO(existingCrmTaskStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrmTaskStatusMockMvc.perform(post("/api/crm-task-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCrmTaskStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CrmTaskStatus> crmTaskStatusList = crmTaskStatusRepository.findAll();
        assertThat(crmTaskStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCrmTaskStatuses() throws Exception {
        // Initialize the database
        crmTaskStatusRepository.saveAndFlush(crmTaskStatus);

        // Get all the crmTaskStatusList
        restCrmTaskStatusMockMvc.perform(get("/api/crm-task-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmTaskStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCrmTaskStatus() throws Exception {
        // Initialize the database
        crmTaskStatusRepository.saveAndFlush(crmTaskStatus);

        // Get the crmTaskStatus
        restCrmTaskStatusMockMvc.perform(get("/api/crm-task-statuses/{id}", crmTaskStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(crmTaskStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCrmTaskStatus() throws Exception {
        // Get the crmTaskStatus
        restCrmTaskStatusMockMvc.perform(get("/api/crm-task-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrmTaskStatus() throws Exception {
        // Initialize the database
        crmTaskStatusRepository.saveAndFlush(crmTaskStatus);
        int databaseSizeBeforeUpdate = crmTaskStatusRepository.findAll().size();

        // Update the crmTaskStatus
        CrmTaskStatus updatedCrmTaskStatus = crmTaskStatusRepository.findOne(crmTaskStatus.getId());
        updatedCrmTaskStatus
                .name(UPDATED_NAME);
        CrmTaskStatusDTO crmTaskStatusDTO = crmTaskStatusMapper.crmTaskStatusToCrmTaskStatusDTO(updatedCrmTaskStatus);

        restCrmTaskStatusMockMvc.perform(put("/api/crm-task-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmTaskStatusDTO)))
            .andExpect(status().isOk());

        // Validate the CrmTaskStatus in the database
        List<CrmTaskStatus> crmTaskStatusList = crmTaskStatusRepository.findAll();
        assertThat(crmTaskStatusList).hasSize(databaseSizeBeforeUpdate);
        CrmTaskStatus testCrmTaskStatus = crmTaskStatusList.get(crmTaskStatusList.size() - 1);
        assertThat(testCrmTaskStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCrmTaskStatus() throws Exception {
        int databaseSizeBeforeUpdate = crmTaskStatusRepository.findAll().size();

        // Create the CrmTaskStatus
        CrmTaskStatusDTO crmTaskStatusDTO = crmTaskStatusMapper.crmTaskStatusToCrmTaskStatusDTO(crmTaskStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCrmTaskStatusMockMvc.perform(put("/api/crm-task-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmTaskStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmTaskStatus in the database
        List<CrmTaskStatus> crmTaskStatusList = crmTaskStatusRepository.findAll();
        assertThat(crmTaskStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCrmTaskStatus() throws Exception {
        // Initialize the database
        crmTaskStatusRepository.saveAndFlush(crmTaskStatus);
        int databaseSizeBeforeDelete = crmTaskStatusRepository.findAll().size();

        // Get the crmTaskStatus
        restCrmTaskStatusMockMvc.perform(delete("/api/crm-task-statuses/{id}", crmTaskStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CrmTaskStatus> crmTaskStatusList = crmTaskStatusRepository.findAll();
        assertThat(crmTaskStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrmTaskStatus.class);
    }
}
