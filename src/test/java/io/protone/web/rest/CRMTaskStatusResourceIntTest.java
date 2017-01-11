package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CRMTaskStatus;
import io.protone.repository.CRMTaskStatusRepository;
import io.protone.service.dto.CRMTaskStatusDTO;
import io.protone.service.mapper.CRMTaskStatusMapper;

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
 * Test class for the CRMTaskStatusResource REST controller.
 *
 * @see CRMTaskStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CRMTaskStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private CRMTaskStatusRepository cRMTaskStatusRepository;

    @Inject
    private CRMTaskStatusMapper cRMTaskStatusMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCRMTaskStatusMockMvc;

    private CRMTaskStatus cRMTaskStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CRMTaskStatusResource cRMTaskStatusResource = new CRMTaskStatusResource();
        ReflectionTestUtils.setField(cRMTaskStatusResource, "cRMTaskStatusRepository", cRMTaskStatusRepository);
        ReflectionTestUtils.setField(cRMTaskStatusResource, "cRMTaskStatusMapper", cRMTaskStatusMapper);
        this.restCRMTaskStatusMockMvc = MockMvcBuilders.standaloneSetup(cRMTaskStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRMTaskStatus createEntity(EntityManager em) {
        CRMTaskStatus cRMTaskStatus = new CRMTaskStatus()
                .name(DEFAULT_NAME);
        return cRMTaskStatus;
    }

    @Before
    public void initTest() {
        cRMTaskStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCRMTaskStatus() throws Exception {
        int databaseSizeBeforeCreate = cRMTaskStatusRepository.findAll().size();

        // Create the CRMTaskStatus
        CRMTaskStatusDTO cRMTaskStatusDTO = cRMTaskStatusMapper.cRMTaskStatusToCRMTaskStatusDTO(cRMTaskStatus);

        restCRMTaskStatusMockMvc.perform(post("/api/c-rm-task-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMTaskStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the CRMTaskStatus in the database
        List<CRMTaskStatus> cRMTaskStatusList = cRMTaskStatusRepository.findAll();
        assertThat(cRMTaskStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CRMTaskStatus testCRMTaskStatus = cRMTaskStatusList.get(cRMTaskStatusList.size() - 1);
        assertThat(testCRMTaskStatus.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCRMTaskStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cRMTaskStatusRepository.findAll().size();

        // Create the CRMTaskStatus with an existing ID
        CRMTaskStatus existingCRMTaskStatus = new CRMTaskStatus();
        existingCRMTaskStatus.setId(1L);
        CRMTaskStatusDTO existingCRMTaskStatusDTO = cRMTaskStatusMapper.cRMTaskStatusToCRMTaskStatusDTO(existingCRMTaskStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCRMTaskStatusMockMvc.perform(post("/api/c-rm-task-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCRMTaskStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CRMTaskStatus> cRMTaskStatusList = cRMTaskStatusRepository.findAll();
        assertThat(cRMTaskStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCRMTaskStatuses() throws Exception {
        // Initialize the database
        cRMTaskStatusRepository.saveAndFlush(cRMTaskStatus);

        // Get all the cRMTaskStatusList
        restCRMTaskStatusMockMvc.perform(get("/api/c-rm-task-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRMTaskStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCRMTaskStatus() throws Exception {
        // Initialize the database
        cRMTaskStatusRepository.saveAndFlush(cRMTaskStatus);

        // Get the cRMTaskStatus
        restCRMTaskStatusMockMvc.perform(get("/api/c-rm-task-statuses/{id}", cRMTaskStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cRMTaskStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCRMTaskStatus() throws Exception {
        // Get the cRMTaskStatus
        restCRMTaskStatusMockMvc.perform(get("/api/c-rm-task-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCRMTaskStatus() throws Exception {
        // Initialize the database
        cRMTaskStatusRepository.saveAndFlush(cRMTaskStatus);
        int databaseSizeBeforeUpdate = cRMTaskStatusRepository.findAll().size();

        // Update the cRMTaskStatus
        CRMTaskStatus updatedCRMTaskStatus = cRMTaskStatusRepository.findOne(cRMTaskStatus.getId());
        updatedCRMTaskStatus
                .name(UPDATED_NAME);
        CRMTaskStatusDTO cRMTaskStatusDTO = cRMTaskStatusMapper.cRMTaskStatusToCRMTaskStatusDTO(updatedCRMTaskStatus);

        restCRMTaskStatusMockMvc.perform(put("/api/c-rm-task-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMTaskStatusDTO)))
            .andExpect(status().isOk());

        // Validate the CRMTaskStatus in the database
        List<CRMTaskStatus> cRMTaskStatusList = cRMTaskStatusRepository.findAll();
        assertThat(cRMTaskStatusList).hasSize(databaseSizeBeforeUpdate);
        CRMTaskStatus testCRMTaskStatus = cRMTaskStatusList.get(cRMTaskStatusList.size() - 1);
        assertThat(testCRMTaskStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCRMTaskStatus() throws Exception {
        int databaseSizeBeforeUpdate = cRMTaskStatusRepository.findAll().size();

        // Create the CRMTaskStatus
        CRMTaskStatusDTO cRMTaskStatusDTO = cRMTaskStatusMapper.cRMTaskStatusToCRMTaskStatusDTO(cRMTaskStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCRMTaskStatusMockMvc.perform(put("/api/c-rm-task-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMTaskStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the CRMTaskStatus in the database
        List<CRMTaskStatus> cRMTaskStatusList = cRMTaskStatusRepository.findAll();
        assertThat(cRMTaskStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCRMTaskStatus() throws Exception {
        // Initialize the database
        cRMTaskStatusRepository.saveAndFlush(cRMTaskStatus);
        int databaseSizeBeforeDelete = cRMTaskStatusRepository.findAll().size();

        // Get the cRMTaskStatus
        restCRMTaskStatusMockMvc.perform(delete("/api/c-rm-task-statuses/{id}", cRMTaskStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CRMTaskStatus> cRMTaskStatusList = cRMTaskStatusRepository.findAll();
        assertThat(cRMTaskStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
