package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CorStatus;
import io.protone.repository.CorStatusRepository;
import io.protone.service.dto.CorStatusDTO;
import io.protone.service.mapper.CorStatusMapper;

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
 * Test class for the CorStatusResource REST controller.
 *
 * @see CorStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CorStatusRepository corStatusRepository;

    @Autowired
    private CorStatusMapper corStatusMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCorStatusMockMvc;

    private CorStatus corStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CorStatusResource corStatusResource = new CorStatusResource(corStatusRepository, corStatusMapper);
        this.restCorStatusMockMvc = MockMvcBuilders.standaloneSetup(corStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorStatus createEntity(EntityManager em) {
        CorStatus corStatus = new CorStatus()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return corStatus;
    }

    @Before
    public void initTest() {
        corStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorStatus() throws Exception {
        int databaseSizeBeforeCreate = corStatusRepository.findAll().size();

        // Create the CorStatus
        CorStatusDTO corStatusDTO = corStatusMapper.corStatusToCorStatusDTO(corStatus);

        restCorStatusMockMvc.perform(post("/api/cor-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the CorStatus in the database
        List<CorStatus> corStatusList = corStatusRepository.findAll();
        assertThat(corStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CorStatus testCorStatus = corStatusList.get(corStatusList.size() - 1);
        assertThat(testCorStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCorStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCorStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corStatusRepository.findAll().size();

        // Create the CorStatus with an existing ID
        CorStatus existingCorStatus = new CorStatus();
        existingCorStatus.setId(1L);
        CorStatusDTO existingCorStatusDTO = corStatusMapper.corStatusToCorStatusDTO(existingCorStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorStatusMockMvc.perform(post("/api/cor-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorStatus> corStatusList = corStatusRepository.findAll();
        assertThat(corStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorStatuses() throws Exception {
        // Initialize the database
        corStatusRepository.saveAndFlush(corStatus);

        // Get all the corStatusList
        restCorStatusMockMvc.perform(get("/api/cor-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCorStatus() throws Exception {
        // Initialize the database
        corStatusRepository.saveAndFlush(corStatus);

        // Get the corStatus
        restCorStatusMockMvc.perform(get("/api/cor-statuses/{id}", corStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorStatus() throws Exception {
        // Get the corStatus
        restCorStatusMockMvc.perform(get("/api/cor-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorStatus() throws Exception {
        // Initialize the database
        corStatusRepository.saveAndFlush(corStatus);
        int databaseSizeBeforeUpdate = corStatusRepository.findAll().size();

        // Update the corStatus
        CorStatus updatedCorStatus = corStatusRepository.findOne(corStatus.getId());
        updatedCorStatus
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        CorStatusDTO corStatusDTO = corStatusMapper.corStatusToCorStatusDTO(updatedCorStatus);

        restCorStatusMockMvc.perform(put("/api/cor-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corStatusDTO)))
            .andExpect(status().isOk());

        // Validate the CorStatus in the database
        List<CorStatus> corStatusList = corStatusRepository.findAll();
        assertThat(corStatusList).hasSize(databaseSizeBeforeUpdate);
        CorStatus testCorStatus = corStatusList.get(corStatusList.size() - 1);
        assertThat(testCorStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCorStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCorStatus() throws Exception {
        int databaseSizeBeforeUpdate = corStatusRepository.findAll().size();

        // Create the CorStatus
        CorStatusDTO corStatusDTO = corStatusMapper.corStatusToCorStatusDTO(corStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorStatusMockMvc.perform(put("/api/cor-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the CorStatus in the database
        List<CorStatus> corStatusList = corStatusRepository.findAll();
        assertThat(corStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorStatus() throws Exception {
        // Initialize the database
        corStatusRepository.saveAndFlush(corStatus);
        int databaseSizeBeforeDelete = corStatusRepository.findAll().size();

        // Get the corStatus
        restCorStatusMockMvc.perform(delete("/api/cor-statuses/{id}", corStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorStatus> corStatusList = corStatusRepository.findAll();
        assertThat(corStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorStatus.class);
    }
}
