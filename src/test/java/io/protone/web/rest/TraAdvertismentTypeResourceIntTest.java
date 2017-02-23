package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.TraAdvertismentType;
import io.protone.repository.TraAdvertismentTypeRepository;
import io.protone.service.dto.TraAdvertismentTypeDTO;
import io.protone.service.mapper.TraAdvertismentTypeMapper;

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
 * Test class for the TraAdvertismentTypeResource REST controller.
 *
 * @see TraAdvertismentTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraAdvertismentTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TraAdvertismentTypeRepository traAdvertismentTypeRepository;

    @Autowired
    private TraAdvertismentTypeMapper traAdvertismentTypeMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTraAdvertismentTypeMockMvc;

    private TraAdvertismentType traAdvertismentType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TraAdvertismentTypeResource traAdvertismentTypeResource = new TraAdvertismentTypeResource(traAdvertismentTypeRepository, traAdvertismentTypeMapper);
        this.restTraAdvertismentTypeMockMvc = MockMvcBuilders.standaloneSetup(traAdvertismentTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraAdvertismentType createEntity(EntityManager em) {
        TraAdvertismentType traAdvertismentType = new TraAdvertismentType()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return traAdvertismentType;
    }

    @Before
    public void initTest() {
        traAdvertismentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraAdvertismentType() throws Exception {
        int databaseSizeBeforeCreate = traAdvertismentTypeRepository.findAll().size();

        // Create the TraAdvertismentType
        TraAdvertismentTypeDTO traAdvertismentTypeDTO = traAdvertismentTypeMapper.traAdvertismentTypeToTraAdvertismentTypeDTO(traAdvertismentType);

        restTraAdvertismentTypeMockMvc.perform(post("/api/tra-advertisment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traAdvertismentTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the TraAdvertismentType in the database
        List<TraAdvertismentType> traAdvertismentTypeList = traAdvertismentTypeRepository.findAll();
        assertThat(traAdvertismentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        TraAdvertismentType testTraAdvertismentType = traAdvertismentTypeList.get(traAdvertismentTypeList.size() - 1);
        assertThat(testTraAdvertismentType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTraAdvertismentType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTraAdvertismentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traAdvertismentTypeRepository.findAll().size();

        // Create the TraAdvertismentType with an existing ID
        TraAdvertismentType existingTraAdvertismentType = new TraAdvertismentType();
        existingTraAdvertismentType.setId(1L);
        TraAdvertismentTypeDTO existingTraAdvertismentTypeDTO = traAdvertismentTypeMapper.traAdvertismentTypeToTraAdvertismentTypeDTO(existingTraAdvertismentType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraAdvertismentTypeMockMvc.perform(post("/api/tra-advertisment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTraAdvertismentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraAdvertismentType> traAdvertismentTypeList = traAdvertismentTypeRepository.findAll();
        assertThat(traAdvertismentTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTraAdvertismentTypes() throws Exception {
        // Initialize the database
        traAdvertismentTypeRepository.saveAndFlush(traAdvertismentType);

        // Get all the traAdvertismentTypeList
        restTraAdvertismentTypeMockMvc.perform(get("/api/tra-advertisment-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traAdvertismentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTraAdvertismentType() throws Exception {
        // Initialize the database
        traAdvertismentTypeRepository.saveAndFlush(traAdvertismentType);

        // Get the traAdvertismentType
        restTraAdvertismentTypeMockMvc.perform(get("/api/tra-advertisment-types/{id}", traAdvertismentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traAdvertismentType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraAdvertismentType() throws Exception {
        // Get the traAdvertismentType
        restTraAdvertismentTypeMockMvc.perform(get("/api/tra-advertisment-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraAdvertismentType() throws Exception {
        // Initialize the database
        traAdvertismentTypeRepository.saveAndFlush(traAdvertismentType);
        int databaseSizeBeforeUpdate = traAdvertismentTypeRepository.findAll().size();

        // Update the traAdvertismentType
        TraAdvertismentType updatedTraAdvertismentType = traAdvertismentTypeRepository.findOne(traAdvertismentType.getId());
        updatedTraAdvertismentType
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        TraAdvertismentTypeDTO traAdvertismentTypeDTO = traAdvertismentTypeMapper.traAdvertismentTypeToTraAdvertismentTypeDTO(updatedTraAdvertismentType);

        restTraAdvertismentTypeMockMvc.perform(put("/api/tra-advertisment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traAdvertismentTypeDTO)))
            .andExpect(status().isOk());

        // Validate the TraAdvertismentType in the database
        List<TraAdvertismentType> traAdvertismentTypeList = traAdvertismentTypeRepository.findAll();
        assertThat(traAdvertismentTypeList).hasSize(databaseSizeBeforeUpdate);
        TraAdvertismentType testTraAdvertismentType = traAdvertismentTypeList.get(traAdvertismentTypeList.size() - 1);
        assertThat(testTraAdvertismentType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraAdvertismentType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTraAdvertismentType() throws Exception {
        int databaseSizeBeforeUpdate = traAdvertismentTypeRepository.findAll().size();

        // Create the TraAdvertismentType
        TraAdvertismentTypeDTO traAdvertismentTypeDTO = traAdvertismentTypeMapper.traAdvertismentTypeToTraAdvertismentTypeDTO(traAdvertismentType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraAdvertismentTypeMockMvc.perform(put("/api/tra-advertisment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traAdvertismentTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the TraAdvertismentType in the database
        List<TraAdvertismentType> traAdvertismentTypeList = traAdvertismentTypeRepository.findAll();
        assertThat(traAdvertismentTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraAdvertismentType() throws Exception {
        // Initialize the database
        traAdvertismentTypeRepository.saveAndFlush(traAdvertismentType);
        int databaseSizeBeforeDelete = traAdvertismentTypeRepository.findAll().size();

        // Get the traAdvertismentType
        restTraAdvertismentTypeMockMvc.perform(delete("/api/tra-advertisment-types/{id}", traAdvertismentType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TraAdvertismentType> traAdvertismentTypeList = traAdvertismentTypeRepository.findAll();
        assertThat(traAdvertismentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraAdvertismentType.class);
    }
}
