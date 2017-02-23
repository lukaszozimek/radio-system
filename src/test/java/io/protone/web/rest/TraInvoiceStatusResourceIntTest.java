package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.TraInvoiceStatus;
import io.protone.repository.TraInvoiceStatusRepository;
import io.protone.service.dto.TraInvoiceStatusDTO;
import io.protone.service.mapper.TraInvoiceStatusMapper;

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
 * Test class for the TraInvoiceStatusResource REST controller.
 *
 * @see TraInvoiceStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraInvoiceStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TraInvoiceStatusRepository traInvoiceStatusRepository;

    @Autowired
    private TraInvoiceStatusMapper traInvoiceStatusMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTraInvoiceStatusMockMvc;

    private TraInvoiceStatus traInvoiceStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TraInvoiceStatusResource traInvoiceStatusResource = new TraInvoiceStatusResource(traInvoiceStatusRepository, traInvoiceStatusMapper);
        this.restTraInvoiceStatusMockMvc = MockMvcBuilders.standaloneSetup(traInvoiceStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraInvoiceStatus createEntity(EntityManager em) {
        TraInvoiceStatus traInvoiceStatus = new TraInvoiceStatus()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return traInvoiceStatus;
    }

    @Before
    public void initTest() {
        traInvoiceStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraInvoiceStatus() throws Exception {
        int databaseSizeBeforeCreate = traInvoiceStatusRepository.findAll().size();

        // Create the TraInvoiceStatus
        TraInvoiceStatusDTO traInvoiceStatusDTO = traInvoiceStatusMapper.traInvoiceStatusToTraInvoiceStatusDTO(traInvoiceStatus);

        restTraInvoiceStatusMockMvc.perform(post("/api/tra-invoice-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traInvoiceStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the TraInvoiceStatus in the database
        List<TraInvoiceStatus> traInvoiceStatusList = traInvoiceStatusRepository.findAll();
        assertThat(traInvoiceStatusList).hasSize(databaseSizeBeforeCreate + 1);
        TraInvoiceStatus testTraInvoiceStatus = traInvoiceStatusList.get(traInvoiceStatusList.size() - 1);
        assertThat(testTraInvoiceStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTraInvoiceStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTraInvoiceStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traInvoiceStatusRepository.findAll().size();

        // Create the TraInvoiceStatus with an existing ID
        TraInvoiceStatus existingTraInvoiceStatus = new TraInvoiceStatus();
        existingTraInvoiceStatus.setId(1L);
        TraInvoiceStatusDTO existingTraInvoiceStatusDTO = traInvoiceStatusMapper.traInvoiceStatusToTraInvoiceStatusDTO(existingTraInvoiceStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraInvoiceStatusMockMvc.perform(post("/api/tra-invoice-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTraInvoiceStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraInvoiceStatus> traInvoiceStatusList = traInvoiceStatusRepository.findAll();
        assertThat(traInvoiceStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTraInvoiceStatuses() throws Exception {
        // Initialize the database
        traInvoiceStatusRepository.saveAndFlush(traInvoiceStatus);

        // Get all the traInvoiceStatusList
        restTraInvoiceStatusMockMvc.perform(get("/api/tra-invoice-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traInvoiceStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTraInvoiceStatus() throws Exception {
        // Initialize the database
        traInvoiceStatusRepository.saveAndFlush(traInvoiceStatus);

        // Get the traInvoiceStatus
        restTraInvoiceStatusMockMvc.perform(get("/api/tra-invoice-statuses/{id}", traInvoiceStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traInvoiceStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraInvoiceStatus() throws Exception {
        // Get the traInvoiceStatus
        restTraInvoiceStatusMockMvc.perform(get("/api/tra-invoice-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraInvoiceStatus() throws Exception {
        // Initialize the database
        traInvoiceStatusRepository.saveAndFlush(traInvoiceStatus);
        int databaseSizeBeforeUpdate = traInvoiceStatusRepository.findAll().size();

        // Update the traInvoiceStatus
        TraInvoiceStatus updatedTraInvoiceStatus = traInvoiceStatusRepository.findOne(traInvoiceStatus.getId());
        updatedTraInvoiceStatus
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        TraInvoiceStatusDTO traInvoiceStatusDTO = traInvoiceStatusMapper.traInvoiceStatusToTraInvoiceStatusDTO(updatedTraInvoiceStatus);

        restTraInvoiceStatusMockMvc.perform(put("/api/tra-invoice-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traInvoiceStatusDTO)))
            .andExpect(status().isOk());

        // Validate the TraInvoiceStatus in the database
        List<TraInvoiceStatus> traInvoiceStatusList = traInvoiceStatusRepository.findAll();
        assertThat(traInvoiceStatusList).hasSize(databaseSizeBeforeUpdate);
        TraInvoiceStatus testTraInvoiceStatus = traInvoiceStatusList.get(traInvoiceStatusList.size() - 1);
        assertThat(testTraInvoiceStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraInvoiceStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTraInvoiceStatus() throws Exception {
        int databaseSizeBeforeUpdate = traInvoiceStatusRepository.findAll().size();

        // Create the TraInvoiceStatus
        TraInvoiceStatusDTO traInvoiceStatusDTO = traInvoiceStatusMapper.traInvoiceStatusToTraInvoiceStatusDTO(traInvoiceStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraInvoiceStatusMockMvc.perform(put("/api/tra-invoice-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traInvoiceStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the TraInvoiceStatus in the database
        List<TraInvoiceStatus> traInvoiceStatusList = traInvoiceStatusRepository.findAll();
        assertThat(traInvoiceStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraInvoiceStatus() throws Exception {
        // Initialize the database
        traInvoiceStatusRepository.saveAndFlush(traInvoiceStatus);
        int databaseSizeBeforeDelete = traInvoiceStatusRepository.findAll().size();

        // Get the traInvoiceStatus
        restTraInvoiceStatusMockMvc.perform(delete("/api/tra-invoice-statuses/{id}", traInvoiceStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TraInvoiceStatus> traInvoiceStatusList = traInvoiceStatusRepository.findAll();
        assertThat(traInvoiceStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraInvoiceStatus.class);
    }
}
