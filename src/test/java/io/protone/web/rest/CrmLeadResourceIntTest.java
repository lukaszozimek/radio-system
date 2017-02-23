package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CrmLead;
import io.protone.repository.CrmLeadRepository;
import io.protone.service.dto.CrmLeadDTO;
import io.protone.service.mapper.CrmLeadMapper;

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
 * Test class for the CrmLeadResource REST controller.
 *
 * @see CrmLeadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmLeadResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORTNAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CrmLeadRepository crmLeadRepository;

    @Autowired
    private CrmLeadMapper crmLeadMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCrmLeadMockMvc;

    private CrmLead crmLead;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CrmLeadResource crmLeadResource = new CrmLeadResource(crmLeadRepository, crmLeadMapper);
        this.restCrmLeadMockMvc = MockMvcBuilders.standaloneSetup(crmLeadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrmLead createEntity(EntityManager em) {
        CrmLead crmLead = new CrmLead()
                .name(DEFAULT_NAME)
                .shortname(DEFAULT_SHORTNAME)
                .description(DEFAULT_DESCRIPTION);
        return crmLead;
    }

    @Before
    public void initTest() {
        crmLead = createEntity(em);
    }

    @Test
    @Transactional
    public void createCrmLead() throws Exception {
        int databaseSizeBeforeCreate = crmLeadRepository.findAll().size();

        // Create the CrmLead
        CrmLeadDTO crmLeadDTO = crmLeadMapper.crmLeadToCrmLeadDTO(crmLead);

        restCrmLeadMockMvc.perform(post("/api/crm-leads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmLeadDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmLead in the database
        List<CrmLead> crmLeadList = crmLeadRepository.findAll();
        assertThat(crmLeadList).hasSize(databaseSizeBeforeCreate + 1);
        CrmLead testCrmLead = crmLeadList.get(crmLeadList.size() - 1);
        assertThat(testCrmLead.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCrmLead.getShortname()).isEqualTo(DEFAULT_SHORTNAME);
        assertThat(testCrmLead.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCrmLeadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = crmLeadRepository.findAll().size();

        // Create the CrmLead with an existing ID
        CrmLead existingCrmLead = new CrmLead();
        existingCrmLead.setId(1L);
        CrmLeadDTO existingCrmLeadDTO = crmLeadMapper.crmLeadToCrmLeadDTO(existingCrmLead);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrmLeadMockMvc.perform(post("/api/crm-leads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCrmLeadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CrmLead> crmLeadList = crmLeadRepository.findAll();
        assertThat(crmLeadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCrmLeads() throws Exception {
        // Initialize the database
        crmLeadRepository.saveAndFlush(crmLead);

        // Get all the crmLeadList
        restCrmLeadMockMvc.perform(get("/api/crm-leads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmLead.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].shortname").value(hasItem(DEFAULT_SHORTNAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCrmLead() throws Exception {
        // Initialize the database
        crmLeadRepository.saveAndFlush(crmLead);

        // Get the crmLead
        restCrmLeadMockMvc.perform(get("/api/crm-leads/{id}", crmLead.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(crmLead.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.shortname").value(DEFAULT_SHORTNAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCrmLead() throws Exception {
        // Get the crmLead
        restCrmLeadMockMvc.perform(get("/api/crm-leads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrmLead() throws Exception {
        // Initialize the database
        crmLeadRepository.saveAndFlush(crmLead);
        int databaseSizeBeforeUpdate = crmLeadRepository.findAll().size();

        // Update the crmLead
        CrmLead updatedCrmLead = crmLeadRepository.findOne(crmLead.getId());
        updatedCrmLead
                .name(UPDATED_NAME)
                .shortname(UPDATED_SHORTNAME)
                .description(UPDATED_DESCRIPTION);
        CrmLeadDTO crmLeadDTO = crmLeadMapper.crmLeadToCrmLeadDTO(updatedCrmLead);

        restCrmLeadMockMvc.perform(put("/api/crm-leads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmLeadDTO)))
            .andExpect(status().isOk());

        // Validate the CrmLead in the database
        List<CrmLead> crmLeadList = crmLeadRepository.findAll();
        assertThat(crmLeadList).hasSize(databaseSizeBeforeUpdate);
        CrmLead testCrmLead = crmLeadList.get(crmLeadList.size() - 1);
        assertThat(testCrmLead.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCrmLead.getShortname()).isEqualTo(UPDATED_SHORTNAME);
        assertThat(testCrmLead.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCrmLead() throws Exception {
        int databaseSizeBeforeUpdate = crmLeadRepository.findAll().size();

        // Create the CrmLead
        CrmLeadDTO crmLeadDTO = crmLeadMapper.crmLeadToCrmLeadDTO(crmLead);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCrmLeadMockMvc.perform(put("/api/crm-leads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmLeadDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmLead in the database
        List<CrmLead> crmLeadList = crmLeadRepository.findAll();
        assertThat(crmLeadList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCrmLead() throws Exception {
        // Initialize the database
        crmLeadRepository.saveAndFlush(crmLead);
        int databaseSizeBeforeDelete = crmLeadRepository.findAll().size();

        // Get the crmLead
        restCrmLeadMockMvc.perform(delete("/api/crm-leads/{id}", crmLead.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CrmLead> crmLeadList = crmLeadRepository.findAll();
        assertThat(crmLeadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrmLead.class);
    }
}
