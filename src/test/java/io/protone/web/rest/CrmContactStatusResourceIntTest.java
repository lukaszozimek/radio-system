package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CrmContactStatus;
import io.protone.repository.CrmContactStatusRepository;
import io.protone.service.dto.CrmContactStatusDTO;
import io.protone.service.mapper.CrmContactStatusMapper;

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
 * Test class for the CrmContactStatusResource REST controller.
 *
 * @see CrmContactStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmContactStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CrmContactStatusRepository crmContactStatusRepository;

    @Autowired
    private CrmContactStatusMapper crmContactStatusMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCrmContactStatusMockMvc;

    private CrmContactStatus crmContactStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CrmContactStatusResource crmContactStatusResource = new CrmContactStatusResource(crmContactStatusRepository, crmContactStatusMapper);
        this.restCrmContactStatusMockMvc = MockMvcBuilders.standaloneSetup(crmContactStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrmContactStatus createEntity(EntityManager em) {
        CrmContactStatus crmContactStatus = new CrmContactStatus()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return crmContactStatus;
    }

    @Before
    public void initTest() {
        crmContactStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCrmContactStatus() throws Exception {
        int databaseSizeBeforeCreate = crmContactStatusRepository.findAll().size();

        // Create the CrmContactStatus
        CrmContactStatusDTO crmContactStatusDTO = crmContactStatusMapper.crmContactStatusToCrmContactStatusDTO(crmContactStatus);

        restCrmContactStatusMockMvc.perform(post("/api/crm-contact-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmContactStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmContactStatus in the database
        List<CrmContactStatus> crmContactStatusList = crmContactStatusRepository.findAll();
        assertThat(crmContactStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CrmContactStatus testCrmContactStatus = crmContactStatusList.get(crmContactStatusList.size() - 1);
        assertThat(testCrmContactStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCrmContactStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCrmContactStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = crmContactStatusRepository.findAll().size();

        // Create the CrmContactStatus with an existing ID
        CrmContactStatus existingCrmContactStatus = new CrmContactStatus();
        existingCrmContactStatus.setId(1L);
        CrmContactStatusDTO existingCrmContactStatusDTO = crmContactStatusMapper.crmContactStatusToCrmContactStatusDTO(existingCrmContactStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrmContactStatusMockMvc.perform(post("/api/crm-contact-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCrmContactStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CrmContactStatus> crmContactStatusList = crmContactStatusRepository.findAll();
        assertThat(crmContactStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCrmContactStatuses() throws Exception {
        // Initialize the database
        crmContactStatusRepository.saveAndFlush(crmContactStatus);

        // Get all the crmContactStatusList
        restCrmContactStatusMockMvc.perform(get("/api/crm-contact-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmContactStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCrmContactStatus() throws Exception {
        // Initialize the database
        crmContactStatusRepository.saveAndFlush(crmContactStatus);

        // Get the crmContactStatus
        restCrmContactStatusMockMvc.perform(get("/api/crm-contact-statuses/{id}", crmContactStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(crmContactStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCrmContactStatus() throws Exception {
        // Get the crmContactStatus
        restCrmContactStatusMockMvc.perform(get("/api/crm-contact-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrmContactStatus() throws Exception {
        // Initialize the database
        crmContactStatusRepository.saveAndFlush(crmContactStatus);
        int databaseSizeBeforeUpdate = crmContactStatusRepository.findAll().size();

        // Update the crmContactStatus
        CrmContactStatus updatedCrmContactStatus = crmContactStatusRepository.findOne(crmContactStatus.getId());
        updatedCrmContactStatus
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        CrmContactStatusDTO crmContactStatusDTO = crmContactStatusMapper.crmContactStatusToCrmContactStatusDTO(updatedCrmContactStatus);

        restCrmContactStatusMockMvc.perform(put("/api/crm-contact-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmContactStatusDTO)))
            .andExpect(status().isOk());

        // Validate the CrmContactStatus in the database
        List<CrmContactStatus> crmContactStatusList = crmContactStatusRepository.findAll();
        assertThat(crmContactStatusList).hasSize(databaseSizeBeforeUpdate);
        CrmContactStatus testCrmContactStatus = crmContactStatusList.get(crmContactStatusList.size() - 1);
        assertThat(testCrmContactStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCrmContactStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCrmContactStatus() throws Exception {
        int databaseSizeBeforeUpdate = crmContactStatusRepository.findAll().size();

        // Create the CrmContactStatus
        CrmContactStatusDTO crmContactStatusDTO = crmContactStatusMapper.crmContactStatusToCrmContactStatusDTO(crmContactStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCrmContactStatusMockMvc.perform(put("/api/crm-contact-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmContactStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmContactStatus in the database
        List<CrmContactStatus> crmContactStatusList = crmContactStatusRepository.findAll();
        assertThat(crmContactStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCrmContactStatus() throws Exception {
        // Initialize the database
        crmContactStatusRepository.saveAndFlush(crmContactStatus);
        int databaseSizeBeforeDelete = crmContactStatusRepository.findAll().size();

        // Get the crmContactStatus
        restCrmContactStatusMockMvc.perform(delete("/api/crm-contact-statuses/{id}", crmContactStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CrmContactStatus> crmContactStatusList = crmContactStatusRepository.findAll();
        assertThat(crmContactStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrmContactStatus.class);
    }
}
