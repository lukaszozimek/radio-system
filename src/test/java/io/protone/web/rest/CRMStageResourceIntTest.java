package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CRMStage;
import io.protone.repository.CRMStageRepository;
import io.protone.service.dto.CRMStageDTO;
import io.protone.service.mapper.CRMStageMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CRMStageResource REST controller.
 *
 * @see CRMStageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CRMStageResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private CRMStageRepository cRMStageRepository;

    @Inject
    private CRMStageMapper cRMStageMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCRMStageMockMvc;

    private CRMStage cRMStage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CRMStageResource cRMStageResource = new CRMStageResource();
        ReflectionTestUtils.setField(cRMStageResource, "cRMStageRepository", cRMStageRepository);
        ReflectionTestUtils.setField(cRMStageResource, "cRMStageMapper", cRMStageMapper);
        this.restCRMStageMockMvc = MockMvcBuilders.standaloneSetup(cRMStageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRMStage createEntity(EntityManager em) {
        CRMStage cRMStage = new CRMStage()
                .name(DEFAULT_NAME);
        return cRMStage;
    }

    @Before
    public void initTest() {
        cRMStage = createEntity(em);
    }

    @Test
    @Transactional
    public void createCRMStage() throws Exception {
        int databaseSizeBeforeCreate = cRMStageRepository.findAll().size();

        // Create the CRMStage
        CRMStageDTO cRMStageDTO = cRMStageMapper.cRMStageToCRMStageDTO(cRMStage);

        restCRMStageMockMvc.perform(post("/api/c-rm-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMStageDTO)))
            .andExpect(status().isCreated());

        // Validate the CRMStage in the database
        List<CRMStage> cRMStageList = cRMStageRepository.findAll();
        assertThat(cRMStageList).hasSize(databaseSizeBeforeCreate + 1);
        CRMStage testCRMStage = cRMStageList.get(cRMStageList.size() - 1);
        assertThat(testCRMStage.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCRMStageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cRMStageRepository.findAll().size();

        // Create the CRMStage with an existing ID
        CRMStage existingCRMStage = new CRMStage();
        existingCRMStage.setId(1L);
        CRMStageDTO existingCRMStageDTO = cRMStageMapper.cRMStageToCRMStageDTO(existingCRMStage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCRMStageMockMvc.perform(post("/api/c-rm-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCRMStageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CRMStage> cRMStageList = cRMStageRepository.findAll();
        assertThat(cRMStageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCRMStages() throws Exception {
        // Initialize the database
        cRMStageRepository.saveAndFlush(cRMStage);

        // Get all the cRMStageList
        restCRMStageMockMvc.perform(get("/api/c-rm-stages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRMStage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCRMStage() throws Exception {
        // Initialize the database
        cRMStageRepository.saveAndFlush(cRMStage);

        // Get the cRMStage
        restCRMStageMockMvc.perform(get("/api/c-rm-stages/{id}", cRMStage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cRMStage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCRMStage() throws Exception {
        // Get the cRMStage
        restCRMStageMockMvc.perform(get("/api/c-rm-stages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCRMStage() throws Exception {
        // Initialize the database
        cRMStageRepository.saveAndFlush(cRMStage);
        int databaseSizeBeforeUpdate = cRMStageRepository.findAll().size();

        // Update the cRMStage
        CRMStage updatedCRMStage = cRMStageRepository.findOne(cRMStage.getId());
        updatedCRMStage
                .name(UPDATED_NAME);
        CRMStageDTO cRMStageDTO = cRMStageMapper.cRMStageToCRMStageDTO(updatedCRMStage);

        restCRMStageMockMvc.perform(put("/api/c-rm-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMStageDTO)))
            .andExpect(status().isOk());

        // Validate the CRMStage in the database
        List<CRMStage> cRMStageList = cRMStageRepository.findAll();
        assertThat(cRMStageList).hasSize(databaseSizeBeforeUpdate);
        CRMStage testCRMStage = cRMStageList.get(cRMStageList.size() - 1);
        assertThat(testCRMStage.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCRMStage() throws Exception {
        int databaseSizeBeforeUpdate = cRMStageRepository.findAll().size();

        // Create the CRMStage
        CRMStageDTO cRMStageDTO = cRMStageMapper.cRMStageToCRMStageDTO(cRMStage);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCRMStageMockMvc.perform(put("/api/c-rm-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMStageDTO)))
            .andExpect(status().isCreated());

        // Validate the CRMStage in the database
        List<CRMStage> cRMStageList = cRMStageRepository.findAll();
        assertThat(cRMStageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCRMStage() throws Exception {
        // Initialize the database
        cRMStageRepository.saveAndFlush(cRMStage);
        int databaseSizeBeforeDelete = cRMStageRepository.findAll().size();

        // Get the cRMStage
        restCRMStageMockMvc.perform(delete("/api/c-rm-stages/{id}", cRMStage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CRMStage> cRMStageList = cRMStageRepository.findAll();
        assertThat(cRMStageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
