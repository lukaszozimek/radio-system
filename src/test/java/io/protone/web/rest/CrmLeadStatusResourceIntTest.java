package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CrmLeadStatus;
import io.protone.repository.CrmLeadStatusRepository;
import io.protone.service.dto.CrmLeadStatusDTO;
import io.protone.service.mapper.CrmLeadStatusMapper;

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
 * Test class for the CrmLeadStatusResource REST controller.
 *
 * @see CrmLeadStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmLeadStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CrmLeadStatusRepository crmLeadStatusRepository;

    @Autowired
    private CrmLeadStatusMapper crmLeadStatusMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCrmLeadStatusMockMvc;

    private CrmLeadStatus crmLeadStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CrmLeadStatusResource crmLeadStatusResource = new CrmLeadStatusResource(crmLeadStatusRepository, crmLeadStatusMapper);
        this.restCrmLeadStatusMockMvc = MockMvcBuilders.standaloneSetup(crmLeadStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrmLeadStatus createEntity(EntityManager em) {
        CrmLeadStatus crmLeadStatus = new CrmLeadStatus()
                .name(DEFAULT_NAME);
        return crmLeadStatus;
    }

    @Before
    public void initTest() {
        crmLeadStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCrmLeadStatus() throws Exception {
        int databaseSizeBeforeCreate = crmLeadStatusRepository.findAll().size();

        // Create the CrmLeadStatus
        CrmLeadStatusDTO crmLeadStatusDTO = crmLeadStatusMapper.crmLeadStatusToCrmLeadStatusDTO(crmLeadStatus);

        restCrmLeadStatusMockMvc.perform(post("/api/crm-lead-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmLeadStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmLeadStatus in the database
        List<CrmLeadStatus> crmLeadStatusList = crmLeadStatusRepository.findAll();
        assertThat(crmLeadStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CrmLeadStatus testCrmLeadStatus = crmLeadStatusList.get(crmLeadStatusList.size() - 1);
        assertThat(testCrmLeadStatus.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCrmLeadStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = crmLeadStatusRepository.findAll().size();

        // Create the CrmLeadStatus with an existing ID
        CrmLeadStatus existingCrmLeadStatus = new CrmLeadStatus();
        existingCrmLeadStatus.setId(1L);
        CrmLeadStatusDTO existingCrmLeadStatusDTO = crmLeadStatusMapper.crmLeadStatusToCrmLeadStatusDTO(existingCrmLeadStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrmLeadStatusMockMvc.perform(post("/api/crm-lead-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCrmLeadStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CrmLeadStatus> crmLeadStatusList = crmLeadStatusRepository.findAll();
        assertThat(crmLeadStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCrmLeadStatuses() throws Exception {
        // Initialize the database
        crmLeadStatusRepository.saveAndFlush(crmLeadStatus);

        // Get all the crmLeadStatusList
        restCrmLeadStatusMockMvc.perform(get("/api/crm-lead-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmLeadStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCrmLeadStatus() throws Exception {
        // Initialize the database
        crmLeadStatusRepository.saveAndFlush(crmLeadStatus);

        // Get the crmLeadStatus
        restCrmLeadStatusMockMvc.perform(get("/api/crm-lead-statuses/{id}", crmLeadStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(crmLeadStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCrmLeadStatus() throws Exception {
        // Get the crmLeadStatus
        restCrmLeadStatusMockMvc.perform(get("/api/crm-lead-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrmLeadStatus() throws Exception {
        // Initialize the database
        crmLeadStatusRepository.saveAndFlush(crmLeadStatus);
        int databaseSizeBeforeUpdate = crmLeadStatusRepository.findAll().size();

        // Update the crmLeadStatus
        CrmLeadStatus updatedCrmLeadStatus = crmLeadStatusRepository.findOne(crmLeadStatus.getId());
        updatedCrmLeadStatus
                .name(UPDATED_NAME);
        CrmLeadStatusDTO crmLeadStatusDTO = crmLeadStatusMapper.crmLeadStatusToCrmLeadStatusDTO(updatedCrmLeadStatus);

        restCrmLeadStatusMockMvc.perform(put("/api/crm-lead-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmLeadStatusDTO)))
            .andExpect(status().isOk());

        // Validate the CrmLeadStatus in the database
        List<CrmLeadStatus> crmLeadStatusList = crmLeadStatusRepository.findAll();
        assertThat(crmLeadStatusList).hasSize(databaseSizeBeforeUpdate);
        CrmLeadStatus testCrmLeadStatus = crmLeadStatusList.get(crmLeadStatusList.size() - 1);
        assertThat(testCrmLeadStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCrmLeadStatus() throws Exception {
        int databaseSizeBeforeUpdate = crmLeadStatusRepository.findAll().size();

        // Create the CrmLeadStatus
        CrmLeadStatusDTO crmLeadStatusDTO = crmLeadStatusMapper.crmLeadStatusToCrmLeadStatusDTO(crmLeadStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCrmLeadStatusMockMvc.perform(put("/api/crm-lead-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmLeadStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmLeadStatus in the database
        List<CrmLeadStatus> crmLeadStatusList = crmLeadStatusRepository.findAll();
        assertThat(crmLeadStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCrmLeadStatus() throws Exception {
        // Initialize the database
        crmLeadStatusRepository.saveAndFlush(crmLeadStatus);
        int databaseSizeBeforeDelete = crmLeadStatusRepository.findAll().size();

        // Get the crmLeadStatus
        restCrmLeadStatusMockMvc.perform(delete("/api/crm-lead-statuses/{id}", crmLeadStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CrmLeadStatus> crmLeadStatusList = crmLeadStatusRepository.findAll();
        assertThat(crmLeadStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrmLeadStatus.class);
    }
}
