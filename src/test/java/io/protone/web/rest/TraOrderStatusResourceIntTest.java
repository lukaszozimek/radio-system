package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.TraOrderStatus;
import io.protone.repository.TraOrderStatusRepository;
import io.protone.service.dto.TraOrderStatusDTO;
import io.protone.service.mapper.TraOrderStatusMapper;

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
 * Test class for the TraOrderStatusResource REST controller.
 *
 * @see TraOrderStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraOrderStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TraOrderStatusRepository traOrderStatusRepository;

    @Autowired
    private TraOrderStatusMapper traOrderStatusMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTraOrderStatusMockMvc;

    private TraOrderStatus traOrderStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TraOrderStatusResource traOrderStatusResource = new TraOrderStatusResource(traOrderStatusRepository, traOrderStatusMapper);
        this.restTraOrderStatusMockMvc = MockMvcBuilders.standaloneSetup(traOrderStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraOrderStatus createEntity(EntityManager em) {
        TraOrderStatus traOrderStatus = new TraOrderStatus()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return traOrderStatus;
    }

    @Before
    public void initTest() {
        traOrderStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraOrderStatus() throws Exception {
        int databaseSizeBeforeCreate = traOrderStatusRepository.findAll().size();

        // Create the TraOrderStatus
        TraOrderStatusDTO traOrderStatusDTO = traOrderStatusMapper.traOrderStatusToTraOrderStatusDTO(traOrderStatus);

        restTraOrderStatusMockMvc.perform(post("/api/tra-order-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traOrderStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the TraOrderStatus in the database
        List<TraOrderStatus> traOrderStatusList = traOrderStatusRepository.findAll();
        assertThat(traOrderStatusList).hasSize(databaseSizeBeforeCreate + 1);
        TraOrderStatus testTraOrderStatus = traOrderStatusList.get(traOrderStatusList.size() - 1);
        assertThat(testTraOrderStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTraOrderStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTraOrderStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traOrderStatusRepository.findAll().size();

        // Create the TraOrderStatus with an existing ID
        TraOrderStatus existingTraOrderStatus = new TraOrderStatus();
        existingTraOrderStatus.setId(1L);
        TraOrderStatusDTO existingTraOrderStatusDTO = traOrderStatusMapper.traOrderStatusToTraOrderStatusDTO(existingTraOrderStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraOrderStatusMockMvc.perform(post("/api/tra-order-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTraOrderStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraOrderStatus> traOrderStatusList = traOrderStatusRepository.findAll();
        assertThat(traOrderStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTraOrderStatuses() throws Exception {
        // Initialize the database
        traOrderStatusRepository.saveAndFlush(traOrderStatus);

        // Get all the traOrderStatusList
        restTraOrderStatusMockMvc.perform(get("/api/tra-order-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traOrderStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTraOrderStatus() throws Exception {
        // Initialize the database
        traOrderStatusRepository.saveAndFlush(traOrderStatus);

        // Get the traOrderStatus
        restTraOrderStatusMockMvc.perform(get("/api/tra-order-statuses/{id}", traOrderStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traOrderStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraOrderStatus() throws Exception {
        // Get the traOrderStatus
        restTraOrderStatusMockMvc.perform(get("/api/tra-order-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraOrderStatus() throws Exception {
        // Initialize the database
        traOrderStatusRepository.saveAndFlush(traOrderStatus);
        int databaseSizeBeforeUpdate = traOrderStatusRepository.findAll().size();

        // Update the traOrderStatus
        TraOrderStatus updatedTraOrderStatus = traOrderStatusRepository.findOne(traOrderStatus.getId());
        updatedTraOrderStatus
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        TraOrderStatusDTO traOrderStatusDTO = traOrderStatusMapper.traOrderStatusToTraOrderStatusDTO(updatedTraOrderStatus);

        restTraOrderStatusMockMvc.perform(put("/api/tra-order-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traOrderStatusDTO)))
            .andExpect(status().isOk());

        // Validate the TraOrderStatus in the database
        List<TraOrderStatus> traOrderStatusList = traOrderStatusRepository.findAll();
        assertThat(traOrderStatusList).hasSize(databaseSizeBeforeUpdate);
        TraOrderStatus testTraOrderStatus = traOrderStatusList.get(traOrderStatusList.size() - 1);
        assertThat(testTraOrderStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraOrderStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTraOrderStatus() throws Exception {
        int databaseSizeBeforeUpdate = traOrderStatusRepository.findAll().size();

        // Create the TraOrderStatus
        TraOrderStatusDTO traOrderStatusDTO = traOrderStatusMapper.traOrderStatusToTraOrderStatusDTO(traOrderStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraOrderStatusMockMvc.perform(put("/api/tra-order-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traOrderStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the TraOrderStatus in the database
        List<TraOrderStatus> traOrderStatusList = traOrderStatusRepository.findAll();
        assertThat(traOrderStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraOrderStatus() throws Exception {
        // Initialize the database
        traOrderStatusRepository.saveAndFlush(traOrderStatus);
        int databaseSizeBeforeDelete = traOrderStatusRepository.findAll().size();

        // Get the traOrderStatus
        restTraOrderStatusMockMvc.perform(delete("/api/tra-order-statuses/{id}", traOrderStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TraOrderStatus> traOrderStatusList = traOrderStatusRepository.findAll();
        assertThat(traOrderStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraOrderStatus.class);
    }
}
