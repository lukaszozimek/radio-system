package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.TraCampaingStatus;
import io.protone.repository.TraCampaingStatusRepository;
import io.protone.service.dto.TraCampaingStatusDTO;
import io.protone.service.mapper.TraCampaingStatusMapper;

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
 * Test class for the TraCampaingStatusResource REST controller.
 *
 * @see TraCampaingStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraCampaingStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TraCampaingStatusRepository traCampaingStatusRepository;

    @Autowired
    private TraCampaingStatusMapper traCampaingStatusMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTraCampaingStatusMockMvc;

    private TraCampaingStatus traCampaingStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TraCampaingStatusResource traCampaingStatusResource = new TraCampaingStatusResource(traCampaingStatusRepository, traCampaingStatusMapper);
        this.restTraCampaingStatusMockMvc = MockMvcBuilders.standaloneSetup(traCampaingStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraCampaingStatus createEntity(EntityManager em) {
        TraCampaingStatus traCampaingStatus = new TraCampaingStatus()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return traCampaingStatus;
    }

    @Before
    public void initTest() {
        traCampaingStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraCampaingStatus() throws Exception {
        int databaseSizeBeforeCreate = traCampaingStatusRepository.findAll().size();

        // Create the TraCampaingStatus
        TraCampaingStatusDTO traCampaingStatusDTO = traCampaingStatusMapper.traCampaingStatusToTraCampaingStatusDTO(traCampaingStatus);

        restTraCampaingStatusMockMvc.perform(post("/api/tra-campaing-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traCampaingStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the TraCampaingStatus in the database
        List<TraCampaingStatus> traCampaingStatusList = traCampaingStatusRepository.findAll();
        assertThat(traCampaingStatusList).hasSize(databaseSizeBeforeCreate + 1);
        TraCampaingStatus testTraCampaingStatus = traCampaingStatusList.get(traCampaingStatusList.size() - 1);
        assertThat(testTraCampaingStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTraCampaingStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTraCampaingStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traCampaingStatusRepository.findAll().size();

        // Create the TraCampaingStatus with an existing ID
        TraCampaingStatus existingTraCampaingStatus = new TraCampaingStatus();
        existingTraCampaingStatus.setId(1L);
        TraCampaingStatusDTO existingTraCampaingStatusDTO = traCampaingStatusMapper.traCampaingStatusToTraCampaingStatusDTO(existingTraCampaingStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraCampaingStatusMockMvc.perform(post("/api/tra-campaing-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTraCampaingStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraCampaingStatus> traCampaingStatusList = traCampaingStatusRepository.findAll();
        assertThat(traCampaingStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTraCampaingStatuses() throws Exception {
        // Initialize the database
        traCampaingStatusRepository.saveAndFlush(traCampaingStatus);

        // Get all the traCampaingStatusList
        restTraCampaingStatusMockMvc.perform(get("/api/tra-campaing-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traCampaingStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTraCampaingStatus() throws Exception {
        // Initialize the database
        traCampaingStatusRepository.saveAndFlush(traCampaingStatus);

        // Get the traCampaingStatus
        restTraCampaingStatusMockMvc.perform(get("/api/tra-campaing-statuses/{id}", traCampaingStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traCampaingStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraCampaingStatus() throws Exception {
        // Get the traCampaingStatus
        restTraCampaingStatusMockMvc.perform(get("/api/tra-campaing-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraCampaingStatus() throws Exception {
        // Initialize the database
        traCampaingStatusRepository.saveAndFlush(traCampaingStatus);
        int databaseSizeBeforeUpdate = traCampaingStatusRepository.findAll().size();

        // Update the traCampaingStatus
        TraCampaingStatus updatedTraCampaingStatus = traCampaingStatusRepository.findOne(traCampaingStatus.getId());
        updatedTraCampaingStatus
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        TraCampaingStatusDTO traCampaingStatusDTO = traCampaingStatusMapper.traCampaingStatusToTraCampaingStatusDTO(updatedTraCampaingStatus);

        restTraCampaingStatusMockMvc.perform(put("/api/tra-campaing-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traCampaingStatusDTO)))
            .andExpect(status().isOk());

        // Validate the TraCampaingStatus in the database
        List<TraCampaingStatus> traCampaingStatusList = traCampaingStatusRepository.findAll();
        assertThat(traCampaingStatusList).hasSize(databaseSizeBeforeUpdate);
        TraCampaingStatus testTraCampaingStatus = traCampaingStatusList.get(traCampaingStatusList.size() - 1);
        assertThat(testTraCampaingStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraCampaingStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTraCampaingStatus() throws Exception {
        int databaseSizeBeforeUpdate = traCampaingStatusRepository.findAll().size();

        // Create the TraCampaingStatus
        TraCampaingStatusDTO traCampaingStatusDTO = traCampaingStatusMapper.traCampaingStatusToTraCampaingStatusDTO(traCampaingStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraCampaingStatusMockMvc.perform(put("/api/tra-campaing-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traCampaingStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the TraCampaingStatus in the database
        List<TraCampaingStatus> traCampaingStatusList = traCampaingStatusRepository.findAll();
        assertThat(traCampaingStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraCampaingStatus() throws Exception {
        // Initialize the database
        traCampaingStatusRepository.saveAndFlush(traCampaingStatus);
        int databaseSizeBeforeDelete = traCampaingStatusRepository.findAll().size();

        // Get the traCampaingStatus
        restTraCampaingStatusMockMvc.perform(delete("/api/tra-campaing-statuses/{id}", traCampaingStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TraCampaingStatus> traCampaingStatusList = traCampaingStatusRepository.findAll();
        assertThat(traCampaingStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraCampaingStatus.class);
    }
}
