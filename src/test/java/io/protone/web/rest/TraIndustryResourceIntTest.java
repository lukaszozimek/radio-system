package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.TraIndustry;
import io.protone.repository.TraIndustryRepository;
import io.protone.service.dto.TraIndustryDTO;
import io.protone.service.mapper.TraIndustryMapper;

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
 * Test class for the TraIndustryResource REST controller.
 *
 * @see TraIndustryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraIndustryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TraIndustryRepository traIndustryRepository;

    @Autowired
    private TraIndustryMapper traIndustryMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTraIndustryMockMvc;

    private TraIndustry traIndustry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TraIndustryResource traIndustryResource = new TraIndustryResource(traIndustryRepository, traIndustryMapper);
        this.restTraIndustryMockMvc = MockMvcBuilders.standaloneSetup(traIndustryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraIndustry createEntity(EntityManager em) {
        TraIndustry traIndustry = new TraIndustry()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return traIndustry;
    }

    @Before
    public void initTest() {
        traIndustry = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraIndustry() throws Exception {
        int databaseSizeBeforeCreate = traIndustryRepository.findAll().size();

        // Create the TraIndustry
        TraIndustryDTO traIndustryDTO = traIndustryMapper.traIndustryToTraIndustryDTO(traIndustry);

        restTraIndustryMockMvc.perform(post("/api/tra-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traIndustryDTO)))
            .andExpect(status().isCreated());

        // Validate the TraIndustry in the database
        List<TraIndustry> traIndustryList = traIndustryRepository.findAll();
        assertThat(traIndustryList).hasSize(databaseSizeBeforeCreate + 1);
        TraIndustry testTraIndustry = traIndustryList.get(traIndustryList.size() - 1);
        assertThat(testTraIndustry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTraIndustry.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTraIndustryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traIndustryRepository.findAll().size();

        // Create the TraIndustry with an existing ID
        TraIndustry existingTraIndustry = new TraIndustry();
        existingTraIndustry.setId(1L);
        TraIndustryDTO existingTraIndustryDTO = traIndustryMapper.traIndustryToTraIndustryDTO(existingTraIndustry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraIndustryMockMvc.perform(post("/api/tra-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTraIndustryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraIndustry> traIndustryList = traIndustryRepository.findAll();
        assertThat(traIndustryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = traIndustryRepository.findAll().size();
        // set the field null
        traIndustry.setName(null);

        // Create the TraIndustry, which fails.
        TraIndustryDTO traIndustryDTO = traIndustryMapper.traIndustryToTraIndustryDTO(traIndustry);

        restTraIndustryMockMvc.perform(post("/api/tra-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traIndustryDTO)))
            .andExpect(status().isBadRequest());

        List<TraIndustry> traIndustryList = traIndustryRepository.findAll();
        assertThat(traIndustryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTraIndustries() throws Exception {
        // Initialize the database
        traIndustryRepository.saveAndFlush(traIndustry);

        // Get all the traIndustryList
        restTraIndustryMockMvc.perform(get("/api/tra-industries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traIndustry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTraIndustry() throws Exception {
        // Initialize the database
        traIndustryRepository.saveAndFlush(traIndustry);

        // Get the traIndustry
        restTraIndustryMockMvc.perform(get("/api/tra-industries/{id}", traIndustry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traIndustry.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraIndustry() throws Exception {
        // Get the traIndustry
        restTraIndustryMockMvc.perform(get("/api/tra-industries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraIndustry() throws Exception {
        // Initialize the database
        traIndustryRepository.saveAndFlush(traIndustry);
        int databaseSizeBeforeUpdate = traIndustryRepository.findAll().size();

        // Update the traIndustry
        TraIndustry updatedTraIndustry = traIndustryRepository.findOne(traIndustry.getId());
        updatedTraIndustry
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        TraIndustryDTO traIndustryDTO = traIndustryMapper.traIndustryToTraIndustryDTO(updatedTraIndustry);

        restTraIndustryMockMvc.perform(put("/api/tra-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traIndustryDTO)))
            .andExpect(status().isOk());

        // Validate the TraIndustry in the database
        List<TraIndustry> traIndustryList = traIndustryRepository.findAll();
        assertThat(traIndustryList).hasSize(databaseSizeBeforeUpdate);
        TraIndustry testTraIndustry = traIndustryList.get(traIndustryList.size() - 1);
        assertThat(testTraIndustry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraIndustry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTraIndustry() throws Exception {
        int databaseSizeBeforeUpdate = traIndustryRepository.findAll().size();

        // Create the TraIndustry
        TraIndustryDTO traIndustryDTO = traIndustryMapper.traIndustryToTraIndustryDTO(traIndustry);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraIndustryMockMvc.perform(put("/api/tra-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traIndustryDTO)))
            .andExpect(status().isCreated());

        // Validate the TraIndustry in the database
        List<TraIndustry> traIndustryList = traIndustryRepository.findAll();
        assertThat(traIndustryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraIndustry() throws Exception {
        // Initialize the database
        traIndustryRepository.saveAndFlush(traIndustry);
        int databaseSizeBeforeDelete = traIndustryRepository.findAll().size();

        // Get the traIndustry
        restTraIndustryMockMvc.perform(delete("/api/tra-industries/{id}", traIndustry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TraIndustry> traIndustryList = traIndustryRepository.findAll();
        assertThat(traIndustryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraIndustry.class);
    }
}
