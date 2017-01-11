package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CRMLeadStatus;
import io.protone.repository.CRMLeadStatusRepository;
import io.protone.service.dto.CRMLeadStatusDTO;
import io.protone.service.mapper.CRMLeadStatusMapper;

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
 * Test class for the CRMLeadStatusResource REST controller.
 *
 * @see CRMLeadStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CRMLeadStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private CRMLeadStatusRepository cRMLeadStatusRepository;

    @Inject
    private CRMLeadStatusMapper cRMLeadStatusMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCRMLeadStatusMockMvc;

    private CRMLeadStatus cRMLeadStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CRMLeadStatusResource cRMLeadStatusResource = new CRMLeadStatusResource();
        ReflectionTestUtils.setField(cRMLeadStatusResource, "cRMLeadStatusRepository", cRMLeadStatusRepository);
        ReflectionTestUtils.setField(cRMLeadStatusResource, "cRMLeadStatusMapper", cRMLeadStatusMapper);
        this.restCRMLeadStatusMockMvc = MockMvcBuilders.standaloneSetup(cRMLeadStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRMLeadStatus createEntity(EntityManager em) {
        CRMLeadStatus cRMLeadStatus = new CRMLeadStatus()
                .name(DEFAULT_NAME);
        return cRMLeadStatus;
    }

    @Before
    public void initTest() {
        cRMLeadStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCRMLeadStatus() throws Exception {
        int databaseSizeBeforeCreate = cRMLeadStatusRepository.findAll().size();

        // Create the CRMLeadStatus
        CRMLeadStatusDTO cRMLeadStatusDTO = cRMLeadStatusMapper.cRMLeadStatusToCRMLeadStatusDTO(cRMLeadStatus);

        restCRMLeadStatusMockMvc.perform(post("/api/c-rm-lead-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMLeadStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the CRMLeadStatus in the database
        List<CRMLeadStatus> cRMLeadStatusList = cRMLeadStatusRepository.findAll();
        assertThat(cRMLeadStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CRMLeadStatus testCRMLeadStatus = cRMLeadStatusList.get(cRMLeadStatusList.size() - 1);
        assertThat(testCRMLeadStatus.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCRMLeadStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cRMLeadStatusRepository.findAll().size();

        // Create the CRMLeadStatus with an existing ID
        CRMLeadStatus existingCRMLeadStatus = new CRMLeadStatus();
        existingCRMLeadStatus.setId(1L);
        CRMLeadStatusDTO existingCRMLeadStatusDTO = cRMLeadStatusMapper.cRMLeadStatusToCRMLeadStatusDTO(existingCRMLeadStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCRMLeadStatusMockMvc.perform(post("/api/c-rm-lead-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCRMLeadStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CRMLeadStatus> cRMLeadStatusList = cRMLeadStatusRepository.findAll();
        assertThat(cRMLeadStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCRMLeadStatuses() throws Exception {
        // Initialize the database
        cRMLeadStatusRepository.saveAndFlush(cRMLeadStatus);

        // Get all the cRMLeadStatusList
        restCRMLeadStatusMockMvc.perform(get("/api/c-rm-lead-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRMLeadStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCRMLeadStatus() throws Exception {
        // Initialize the database
        cRMLeadStatusRepository.saveAndFlush(cRMLeadStatus);

        // Get the cRMLeadStatus
        restCRMLeadStatusMockMvc.perform(get("/api/c-rm-lead-statuses/{id}", cRMLeadStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cRMLeadStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCRMLeadStatus() throws Exception {
        // Get the cRMLeadStatus
        restCRMLeadStatusMockMvc.perform(get("/api/c-rm-lead-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCRMLeadStatus() throws Exception {
        // Initialize the database
        cRMLeadStatusRepository.saveAndFlush(cRMLeadStatus);
        int databaseSizeBeforeUpdate = cRMLeadStatusRepository.findAll().size();

        // Update the cRMLeadStatus
        CRMLeadStatus updatedCRMLeadStatus = cRMLeadStatusRepository.findOne(cRMLeadStatus.getId());
        updatedCRMLeadStatus
                .name(UPDATED_NAME);
        CRMLeadStatusDTO cRMLeadStatusDTO = cRMLeadStatusMapper.cRMLeadStatusToCRMLeadStatusDTO(updatedCRMLeadStatus);

        restCRMLeadStatusMockMvc.perform(put("/api/c-rm-lead-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMLeadStatusDTO)))
            .andExpect(status().isOk());

        // Validate the CRMLeadStatus in the database
        List<CRMLeadStatus> cRMLeadStatusList = cRMLeadStatusRepository.findAll();
        assertThat(cRMLeadStatusList).hasSize(databaseSizeBeforeUpdate);
        CRMLeadStatus testCRMLeadStatus = cRMLeadStatusList.get(cRMLeadStatusList.size() - 1);
        assertThat(testCRMLeadStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCRMLeadStatus() throws Exception {
        int databaseSizeBeforeUpdate = cRMLeadStatusRepository.findAll().size();

        // Create the CRMLeadStatus
        CRMLeadStatusDTO cRMLeadStatusDTO = cRMLeadStatusMapper.cRMLeadStatusToCRMLeadStatusDTO(cRMLeadStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCRMLeadStatusMockMvc.perform(put("/api/c-rm-lead-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMLeadStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the CRMLeadStatus in the database
        List<CRMLeadStatus> cRMLeadStatusList = cRMLeadStatusRepository.findAll();
        assertThat(cRMLeadStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCRMLeadStatus() throws Exception {
        // Initialize the database
        cRMLeadStatusRepository.saveAndFlush(cRMLeadStatus);
        int databaseSizeBeforeDelete = cRMLeadStatusRepository.findAll().size();

        // Get the cRMLeadStatus
        restCRMLeadStatusMockMvc.perform(delete("/api/c-rm-lead-statuses/{id}", cRMLeadStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CRMLeadStatus> cRMLeadStatusList = cRMLeadStatusRepository.findAll();
        assertThat(cRMLeadStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
