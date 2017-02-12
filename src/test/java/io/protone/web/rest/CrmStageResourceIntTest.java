package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CrmStage;
import io.protone.repository.CrmStageRepository;
import io.protone.service.dto.CrmStageDTO;
import io.protone.service.mapper.CrmStageMapper;

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
 * Test class for the CrmStageResource REST controller.
 *
 * @see CrmStageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmStageResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CrmStageRepository crmStageRepository;

    @Autowired
    private CrmStageMapper crmStageMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCrmStageMockMvc;

    private CrmStage crmStage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CrmStageResource crmStageResource = new CrmStageResource(crmStageRepository, crmStageMapper);
        this.restCrmStageMockMvc = MockMvcBuilders.standaloneSetup(crmStageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrmStage createEntity(EntityManager em) {
        CrmStage crmStage = new CrmStage()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return crmStage;
    }

    @Before
    public void initTest() {
        crmStage = createEntity(em);
    }

    @Test
    @Transactional
    public void createCrmStage() throws Exception {
        int databaseSizeBeforeCreate = crmStageRepository.findAll().size();

        // Create the CrmStage
        CrmStageDTO crmStageDTO = crmStageMapper.crmStageToCrmStageDTO(crmStage);

        restCrmStageMockMvc.perform(post("/api/crm-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmStageDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmStage in the database
        List<CrmStage> crmStageList = crmStageRepository.findAll();
        assertThat(crmStageList).hasSize(databaseSizeBeforeCreate + 1);
        CrmStage testCrmStage = crmStageList.get(crmStageList.size() - 1);
        assertThat(testCrmStage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCrmStage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCrmStageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = crmStageRepository.findAll().size();

        // Create the CrmStage with an existing ID
        CrmStage existingCrmStage = new CrmStage();
        existingCrmStage.setId(1L);
        CrmStageDTO existingCrmStageDTO = crmStageMapper.crmStageToCrmStageDTO(existingCrmStage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrmStageMockMvc.perform(post("/api/crm-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCrmStageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CrmStage> crmStageList = crmStageRepository.findAll();
        assertThat(crmStageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCrmStages() throws Exception {
        // Initialize the database
        crmStageRepository.saveAndFlush(crmStage);

        // Get all the crmStageList
        restCrmStageMockMvc.perform(get("/api/crm-stages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmStage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCrmStage() throws Exception {
        // Initialize the database
        crmStageRepository.saveAndFlush(crmStage);

        // Get the crmStage
        restCrmStageMockMvc.perform(get("/api/crm-stages/{id}", crmStage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(crmStage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCrmStage() throws Exception {
        // Get the crmStage
        restCrmStageMockMvc.perform(get("/api/crm-stages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrmStage() throws Exception {
        // Initialize the database
        crmStageRepository.saveAndFlush(crmStage);
        int databaseSizeBeforeUpdate = crmStageRepository.findAll().size();

        // Update the crmStage
        CrmStage updatedCrmStage = crmStageRepository.findOne(crmStage.getId());
        updatedCrmStage
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        CrmStageDTO crmStageDTO = crmStageMapper.crmStageToCrmStageDTO(updatedCrmStage);

        restCrmStageMockMvc.perform(put("/api/crm-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmStageDTO)))
            .andExpect(status().isOk());

        // Validate the CrmStage in the database
        List<CrmStage> crmStageList = crmStageRepository.findAll();
        assertThat(crmStageList).hasSize(databaseSizeBeforeUpdate);
        CrmStage testCrmStage = crmStageList.get(crmStageList.size() - 1);
        assertThat(testCrmStage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCrmStage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCrmStage() throws Exception {
        int databaseSizeBeforeUpdate = crmStageRepository.findAll().size();

        // Create the CrmStage
        CrmStageDTO crmStageDTO = crmStageMapper.crmStageToCrmStageDTO(crmStage);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCrmStageMockMvc.perform(put("/api/crm-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmStageDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmStage in the database
        List<CrmStage> crmStageList = crmStageRepository.findAll();
        assertThat(crmStageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCrmStage() throws Exception {
        // Initialize the database
        crmStageRepository.saveAndFlush(crmStage);
        int databaseSizeBeforeDelete = crmStageRepository.findAll().size();

        // Get the crmStage
        restCrmStageMockMvc.perform(delete("/api/crm-stages/{id}", crmStage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CrmStage> crmStageList = crmStageRepository.findAll();
        assertThat(crmStageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrmStage.class);
    }
}
