package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CRMLeadSource;
import io.protone.repository.CRMLeadSourceRepository;
import io.protone.service.dto.CRMLeadSourceDTO;
import io.protone.service.mapper.CRMLeadSourceMapper;

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
 * Test class for the CRMLeadSourceResource REST controller.
 *
 * @see CRMLeadSourceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CRMLeadSourceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private CRMLeadSourceRepository cRMLeadSourceRepository;

    @Inject
    private CRMLeadSourceMapper cRMLeadSourceMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCRMLeadSourceMockMvc;

    private CRMLeadSource cRMLeadSource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CRMLeadSourceResource cRMLeadSourceResource = new CRMLeadSourceResource();
        ReflectionTestUtils.setField(cRMLeadSourceResource, "cRMLeadSourceRepository", cRMLeadSourceRepository);
        ReflectionTestUtils.setField(cRMLeadSourceResource, "cRMLeadSourceMapper", cRMLeadSourceMapper);
        this.restCRMLeadSourceMockMvc = MockMvcBuilders.standaloneSetup(cRMLeadSourceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRMLeadSource createEntity(EntityManager em) {
        CRMLeadSource cRMLeadSource = new CRMLeadSource()
                .name(DEFAULT_NAME);
        return cRMLeadSource;
    }

    @Before
    public void initTest() {
        cRMLeadSource = createEntity(em);
    }

    @Test
    @Transactional
    public void createCRMLeadSource() throws Exception {
        int databaseSizeBeforeCreate = cRMLeadSourceRepository.findAll().size();

        // Create the CRMLeadSource
        CRMLeadSourceDTO cRMLeadSourceDTO = cRMLeadSourceMapper.cRMLeadSourceToCRMLeadSourceDTO(cRMLeadSource);

        restCRMLeadSourceMockMvc.perform(post("/api/c-rm-lead-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMLeadSourceDTO)))
            .andExpect(status().isCreated());

        // Validate the CRMLeadSource in the database
        List<CRMLeadSource> cRMLeadSourceList = cRMLeadSourceRepository.findAll();
        assertThat(cRMLeadSourceList).hasSize(databaseSizeBeforeCreate + 1);
        CRMLeadSource testCRMLeadSource = cRMLeadSourceList.get(cRMLeadSourceList.size() - 1);
        assertThat(testCRMLeadSource.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCRMLeadSourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cRMLeadSourceRepository.findAll().size();

        // Create the CRMLeadSource with an existing ID
        CRMLeadSource existingCRMLeadSource = new CRMLeadSource();
        existingCRMLeadSource.setId(1L);
        CRMLeadSourceDTO existingCRMLeadSourceDTO = cRMLeadSourceMapper.cRMLeadSourceToCRMLeadSourceDTO(existingCRMLeadSource);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCRMLeadSourceMockMvc.perform(post("/api/c-rm-lead-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCRMLeadSourceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CRMLeadSource> cRMLeadSourceList = cRMLeadSourceRepository.findAll();
        assertThat(cRMLeadSourceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCRMLeadSources() throws Exception {
        // Initialize the database
        cRMLeadSourceRepository.saveAndFlush(cRMLeadSource);

        // Get all the cRMLeadSourceList
        restCRMLeadSourceMockMvc.perform(get("/api/c-rm-lead-sources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRMLeadSource.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCRMLeadSource() throws Exception {
        // Initialize the database
        cRMLeadSourceRepository.saveAndFlush(cRMLeadSource);

        // Get the cRMLeadSource
        restCRMLeadSourceMockMvc.perform(get("/api/c-rm-lead-sources/{id}", cRMLeadSource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cRMLeadSource.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCRMLeadSource() throws Exception {
        // Get the cRMLeadSource
        restCRMLeadSourceMockMvc.perform(get("/api/c-rm-lead-sources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCRMLeadSource() throws Exception {
        // Initialize the database
        cRMLeadSourceRepository.saveAndFlush(cRMLeadSource);
        int databaseSizeBeforeUpdate = cRMLeadSourceRepository.findAll().size();

        // Update the cRMLeadSource
        CRMLeadSource updatedCRMLeadSource = cRMLeadSourceRepository.findOne(cRMLeadSource.getId());
        updatedCRMLeadSource
                .name(UPDATED_NAME);
        CRMLeadSourceDTO cRMLeadSourceDTO = cRMLeadSourceMapper.cRMLeadSourceToCRMLeadSourceDTO(updatedCRMLeadSource);

        restCRMLeadSourceMockMvc.perform(put("/api/c-rm-lead-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMLeadSourceDTO)))
            .andExpect(status().isOk());

        // Validate the CRMLeadSource in the database
        List<CRMLeadSource> cRMLeadSourceList = cRMLeadSourceRepository.findAll();
        assertThat(cRMLeadSourceList).hasSize(databaseSizeBeforeUpdate);
        CRMLeadSource testCRMLeadSource = cRMLeadSourceList.get(cRMLeadSourceList.size() - 1);
        assertThat(testCRMLeadSource.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCRMLeadSource() throws Exception {
        int databaseSizeBeforeUpdate = cRMLeadSourceRepository.findAll().size();

        // Create the CRMLeadSource
        CRMLeadSourceDTO cRMLeadSourceDTO = cRMLeadSourceMapper.cRMLeadSourceToCRMLeadSourceDTO(cRMLeadSource);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCRMLeadSourceMockMvc.perform(put("/api/c-rm-lead-sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMLeadSourceDTO)))
            .andExpect(status().isCreated());

        // Validate the CRMLeadSource in the database
        List<CRMLeadSource> cRMLeadSourceList = cRMLeadSourceRepository.findAll();
        assertThat(cRMLeadSourceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCRMLeadSource() throws Exception {
        // Initialize the database
        cRMLeadSourceRepository.saveAndFlush(cRMLeadSource);
        int databaseSizeBeforeDelete = cRMLeadSourceRepository.findAll().size();

        // Get the cRMLeadSource
        restCRMLeadSourceMockMvc.perform(delete("/api/c-rm-lead-sources/{id}", cRMLeadSource.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CRMLeadSource> cRMLeadSourceList = cRMLeadSourceRepository.findAll();
        assertThat(cRMLeadSourceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
