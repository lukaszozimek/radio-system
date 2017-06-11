package io.protone.web.api.traffic;

import io.protone.ProtoneApp;
import io.protone.domain.TraMediaPlan;
import io.protone.repository.traffic.TraMediaPlanRepository;
import io.protone.util.TestUtil;
import io.protone.web.api.traffic.impl.TraMediaPlanResourceImpl;
import io.protone.web.rest.dto.traffic.TraMediaPlanDTO;
import io.protone.web.rest.errors.ExceptionTranslator;
import io.protone.web.rest.mapper.TraMediaPlanMapper;
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
 * Created by lukaszozimek on 11/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraMediaPlanResourceImplTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TraMediaPlanRepository traMediaPlanRepository;

    @Autowired
    private TraMediaPlanMapper traMediaPlanMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTraMediaPlanMockMvc;

    private TraMediaPlan traMediaPlan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TraMediaPlanResourceImpl traMediaPlanResource = new TraMediaPlanResourceImpl();
        this.restTraMediaPlanMockMvc = MockMvcBuilders.standaloneSetup(traMediaPlanResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraMediaPlan createEntity(EntityManager em) {
        TraMediaPlan traMediaPlan = new TraMediaPlan()
            .name(DEFAULT_NAME);
        return traMediaPlan;
    }

    @Before
    public void initTest() {
        traMediaPlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraMediaPlan() throws Exception {
        int databaseSizeBeforeCreate = traMediaPlanRepository.findAll().size();

        // Create the TraMediaPlan
        TraMediaPlanDTO traMediaPlanDTO = traMediaPlanMapper.DB2DTO(traMediaPlan);

        restTraMediaPlanMockMvc.perform(post("/api/tra-media-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traMediaPlanDTO)))
            .andExpect(status().isCreated());

        // Validate the TraMediaPlan in the database
        List<TraMediaPlan> traMediaPlanList = traMediaPlanRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeCreate + 1);
        TraMediaPlan testTraMediaPlan = traMediaPlanList.get(traMediaPlanList.size() - 1);
        assertThat(testTraMediaPlan.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTraMediaPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traMediaPlanRepository.findAll().size();

        // Create the TraMediaPlan with an existing ID
        TraMediaPlan existingTraMediaPlan = new TraMediaPlan();
        existingTraMediaPlan.setId(1L);
        TraMediaPlanDTO existingTraMediaPlanDTO = traMediaPlanMapper.DB2DTO(existingTraMediaPlan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraMediaPlanMockMvc.perform(post("/api/tra-media-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTraMediaPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraMediaPlan> traMediaPlanList = traMediaPlanRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTraMediaPlans() throws Exception {
        // Initialize the database
        traMediaPlanRepository.saveAndFlush(traMediaPlan);

        // Get all the traMediaPlanList
        restTraMediaPlanMockMvc.perform(get("/api/tra-media-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traMediaPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTraMediaPlan() throws Exception {
        // Initialize the database
        traMediaPlanRepository.saveAndFlush(traMediaPlan);

        // Get the traMediaPlan
        restTraMediaPlanMockMvc.perform(get("/api/tra-media-plans/{id}", traMediaPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traMediaPlan.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraMediaPlan() throws Exception {
        // Get the traMediaPlan
        restTraMediaPlanMockMvc.perform(get("/api/tra-media-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraMediaPlan() throws Exception {
        // Initialize the database
        traMediaPlanRepository.saveAndFlush(traMediaPlan);
        int databaseSizeBeforeUpdate = traMediaPlanRepository.findAll().size();

        // Update the traMediaPlan
        TraMediaPlan updatedTraMediaPlan = traMediaPlanRepository.findOne(traMediaPlan.getId());
        updatedTraMediaPlan
            .name(UPDATED_NAME);
        TraMediaPlanDTO traMediaPlanDTO = traMediaPlanMapper.DB2DTO(updatedTraMediaPlan);

        restTraMediaPlanMockMvc.perform(put("/api/tra-media-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traMediaPlanDTO)))
            .andExpect(status().isOk());

        // Validate the TraMediaPlan in the database
        List<TraMediaPlan> traMediaPlanList = traMediaPlanRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeUpdate);
        TraMediaPlan testTraMediaPlan = traMediaPlanList.get(traMediaPlanList.size() - 1);
        assertThat(testTraMediaPlan.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTraMediaPlan() throws Exception {
        int databaseSizeBeforeUpdate = traMediaPlanRepository.findAll().size();

        // Create the TraMediaPlan
        TraMediaPlanDTO traMediaPlanDTO = traMediaPlanMapper.DB2DTO(traMediaPlan);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraMediaPlanMockMvc.perform(put("/api/tra-media-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traMediaPlanDTO)))
            .andExpect(status().isCreated());

        // Validate the TraMediaPlan in the database
        List<TraMediaPlan> traMediaPlanList = traMediaPlanRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraMediaPlan() throws Exception {
        // Initialize the database
        traMediaPlanRepository.saveAndFlush(traMediaPlan);
        int databaseSizeBeforeDelete = traMediaPlanRepository.findAll().size();

        // Get the traMediaPlan
        restTraMediaPlanMockMvc.perform(delete("/api/tra-media-plans/{id}", traMediaPlan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TraMediaPlan> traMediaPlanList = traMediaPlanRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraMediaPlan.class);
    }
}
