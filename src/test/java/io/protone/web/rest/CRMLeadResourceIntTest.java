package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CRMLead;
import io.protone.repository.CRMLeadRepository;
import io.protone.service.dto.CRMLeadDTO;
import io.protone.service.mapper.CRMLeadMapper;

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
 * Test class for the CRMLeadResource REST controller.
 *
 * @see CRMLeadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CRMLeadResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private CRMLeadRepository cRMLeadRepository;

    @Inject
    private CRMLeadMapper cRMLeadMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCRMLeadMockMvc;

    private CRMLead cRMLead;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CRMLeadResource cRMLeadResource = new CRMLeadResource();
        ReflectionTestUtils.setField(cRMLeadResource, "cRMLeadRepository", cRMLeadRepository);
        ReflectionTestUtils.setField(cRMLeadResource, "cRMLeadMapper", cRMLeadMapper);
        this.restCRMLeadMockMvc = MockMvcBuilders.standaloneSetup(cRMLeadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRMLead createEntity(EntityManager em) {
        CRMLead cRMLead = new CRMLead()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return cRMLead;
    }

    @Before
    public void initTest() {
        cRMLead = createEntity(em);
    }

    @Test
    @Transactional
    public void createCRMLead() throws Exception {
        int databaseSizeBeforeCreate = cRMLeadRepository.findAll().size();

        // Create the CRMLead
        CRMLeadDTO cRMLeadDTO = cRMLeadMapper.cRMLeadToCRMLeadDTO(cRMLead);

        restCRMLeadMockMvc.perform(post("/api/c-rm-leads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMLeadDTO)))
            .andExpect(status().isCreated());

        // Validate the CRMLead in the database
        List<CRMLead> cRMLeadList = cRMLeadRepository.findAll();
        assertThat(cRMLeadList).hasSize(databaseSizeBeforeCreate + 1);
        CRMLead testCRMLead = cRMLeadList.get(cRMLeadList.size() - 1);
        assertThat(testCRMLead.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCRMLead.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCRMLeadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cRMLeadRepository.findAll().size();

        // Create the CRMLead with an existing ID
        CRMLead existingCRMLead = new CRMLead();
        existingCRMLead.setId(1L);
        CRMLeadDTO existingCRMLeadDTO = cRMLeadMapper.cRMLeadToCRMLeadDTO(existingCRMLead);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCRMLeadMockMvc.perform(post("/api/c-rm-leads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCRMLeadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CRMLead> cRMLeadList = cRMLeadRepository.findAll();
        assertThat(cRMLeadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCRMLeads() throws Exception {
        // Initialize the database
        cRMLeadRepository.saveAndFlush(cRMLead);

        // Get all the cRMLeadList
        restCRMLeadMockMvc.perform(get("/api/c-rm-leads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRMLead.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCRMLead() throws Exception {
        // Initialize the database
        cRMLeadRepository.saveAndFlush(cRMLead);

        // Get the cRMLead
        restCRMLeadMockMvc.perform(get("/api/c-rm-leads/{id}", cRMLead.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cRMLead.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCRMLead() throws Exception {
        // Get the cRMLead
        restCRMLeadMockMvc.perform(get("/api/c-rm-leads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCRMLead() throws Exception {
        // Initialize the database
        cRMLeadRepository.saveAndFlush(cRMLead);
        int databaseSizeBeforeUpdate = cRMLeadRepository.findAll().size();

        // Update the cRMLead
        CRMLead updatedCRMLead = cRMLeadRepository.findOne(cRMLead.getId());
        updatedCRMLead
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        CRMLeadDTO cRMLeadDTO = cRMLeadMapper.cRMLeadToCRMLeadDTO(updatedCRMLead);

        restCRMLeadMockMvc.perform(put("/api/c-rm-leads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMLeadDTO)))
            .andExpect(status().isOk());

        // Validate the CRMLead in the database
        List<CRMLead> cRMLeadList = cRMLeadRepository.findAll();
        assertThat(cRMLeadList).hasSize(databaseSizeBeforeUpdate);
        CRMLead testCRMLead = cRMLeadList.get(cRMLeadList.size() - 1);
        assertThat(testCRMLead.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCRMLead.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCRMLead() throws Exception {
        int databaseSizeBeforeUpdate = cRMLeadRepository.findAll().size();

        // Create the CRMLead
        CRMLeadDTO cRMLeadDTO = cRMLeadMapper.cRMLeadToCRMLeadDTO(cRMLead);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCRMLeadMockMvc.perform(put("/api/c-rm-leads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMLeadDTO)))
            .andExpect(status().isCreated());

        // Validate the CRMLead in the database
        List<CRMLead> cRMLeadList = cRMLeadRepository.findAll();
        assertThat(cRMLeadList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCRMLead() throws Exception {
        // Initialize the database
        cRMLeadRepository.saveAndFlush(cRMLead);
        int databaseSizeBeforeDelete = cRMLeadRepository.findAll().size();

        // Get the cRMLead
        restCRMLeadMockMvc.perform(delete("/api/c-rm-leads/{id}", cRMLead.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CRMLead> cRMLeadList = cRMLeadRepository.findAll();
        assertThat(cRMLeadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
