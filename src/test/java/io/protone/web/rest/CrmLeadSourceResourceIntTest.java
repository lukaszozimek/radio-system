package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CrmLeadSource;
import io.protone.repository.CrmLeadSourceRepository;
import io.protone.service.dto.CrmLeadSourceDTO;
import io.protone.service.mapper.CrmLeadSourceMapper;

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
 * Test class for the CrmLeadSourceResource REST controller.
 *
 * @see CrmLeadSourceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmLeadSourceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CrmLeadSourceRepository crmLeadSourceRepository;

    @Autowired
    private CrmLeadSourceMapper crmLeadSourceMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCrmLeadSourceMockMvc;

    private CrmLeadSource crmLeadSource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CrmLeadSourceResource crmLeadSourceResource = new CrmLeadSourceResource(crmLeadSourceRepository, crmLeadSourceMapper);
        this.restCrmLeadSourceMockMvc = MockMvcBuilders.standaloneSetup(crmLeadSourceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrmLeadSource createEntity(EntityManager em) {
        CrmLeadSource crmLeadSource = new CrmLeadSource()
                .name(DEFAULT_NAME);
        return crmLeadSource;
    }

    @Before
    public void initTest() {
        crmLeadSource = createEntity(em);
    }

    @Test
    @Transactional
    public void createCrmLeadSource() throws Exception {
        int databaseSizeBeforeCreate = crmLeadSourceRepository.findAll().size();

        // Create the CrmLeadSource
        CrmLeadSourceDTO crmLeadSourceDTO = crmLeadSourceMapper.crmLeadSourceToCrmLeadSourceDTO(crmLeadSource);

        restCrmLeadSourceMockMvc.perform(post("/api/crm-lead-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmLeadSourceDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmLeadSource in the database
        List<CrmLeadSource> crmLeadSourceList = crmLeadSourceRepository.findAll();
        assertThat(crmLeadSourceList).hasSize(databaseSizeBeforeCreate + 1);
        CrmLeadSource testCrmLeadSource = crmLeadSourceList.get(crmLeadSourceList.size() - 1);
        assertThat(testCrmLeadSource.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCrmLeadSourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = crmLeadSourceRepository.findAll().size();

        // Create the CrmLeadSource with an existing ID
        CrmLeadSource existingCrmLeadSource = new CrmLeadSource();
        existingCrmLeadSource.setId(1L);
        CrmLeadSourceDTO existingCrmLeadSourceDTO = crmLeadSourceMapper.crmLeadSourceToCrmLeadSourceDTO(existingCrmLeadSource);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrmLeadSourceMockMvc.perform(post("/api/crm-lead-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCrmLeadSourceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CrmLeadSource> crmLeadSourceList = crmLeadSourceRepository.findAll();
        assertThat(crmLeadSourceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCrmLeadSources() throws Exception {
        // Initialize the database
        crmLeadSourceRepository.saveAndFlush(crmLeadSource);

        // Get all the crmLeadSourceList
        restCrmLeadSourceMockMvc.perform(get("/api/crm-lead-sources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmLeadSource.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCrmLeadSource() throws Exception {
        // Initialize the database
        crmLeadSourceRepository.saveAndFlush(crmLeadSource);

        // Get the crmLeadSource
        restCrmLeadSourceMockMvc.perform(get("/api/crm-lead-sources/{id}", crmLeadSource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(crmLeadSource.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCrmLeadSource() throws Exception {
        // Get the crmLeadSource
        restCrmLeadSourceMockMvc.perform(get("/api/crm-lead-sources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrmLeadSource() throws Exception {
        // Initialize the database
        crmLeadSourceRepository.saveAndFlush(crmLeadSource);
        int databaseSizeBeforeUpdate = crmLeadSourceRepository.findAll().size();

        // Update the crmLeadSource
        CrmLeadSource updatedCrmLeadSource = crmLeadSourceRepository.findOne(crmLeadSource.getId());
        updatedCrmLeadSource
                .name(UPDATED_NAME);
        CrmLeadSourceDTO crmLeadSourceDTO = crmLeadSourceMapper.crmLeadSourceToCrmLeadSourceDTO(updatedCrmLeadSource);

        restCrmLeadSourceMockMvc.perform(put("/api/crm-lead-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmLeadSourceDTO)))
            .andExpect(status().isOk());

        // Validate the CrmLeadSource in the database
        List<CrmLeadSource> crmLeadSourceList = crmLeadSourceRepository.findAll();
        assertThat(crmLeadSourceList).hasSize(databaseSizeBeforeUpdate);
        CrmLeadSource testCrmLeadSource = crmLeadSourceList.get(crmLeadSourceList.size() - 1);
        assertThat(testCrmLeadSource.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCrmLeadSource() throws Exception {
        int databaseSizeBeforeUpdate = crmLeadSourceRepository.findAll().size();

        // Create the CrmLeadSource
        CrmLeadSourceDTO crmLeadSourceDTO = crmLeadSourceMapper.crmLeadSourceToCrmLeadSourceDTO(crmLeadSource);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCrmLeadSourceMockMvc.perform(put("/api/crm-lead-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmLeadSourceDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmLeadSource in the database
        List<CrmLeadSource> crmLeadSourceList = crmLeadSourceRepository.findAll();
        assertThat(crmLeadSourceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCrmLeadSource() throws Exception {
        // Initialize the database
        crmLeadSourceRepository.saveAndFlush(crmLeadSource);
        int databaseSizeBeforeDelete = crmLeadSourceRepository.findAll().size();

        // Get the crmLeadSource
        restCrmLeadSourceMockMvc.perform(delete("/api/crm-lead-sources/{id}", crmLeadSource.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CrmLeadSource> crmLeadSourceList = crmLeadSourceRepository.findAll();
        assertThat(crmLeadSourceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrmLeadSource.class);
    }
}
