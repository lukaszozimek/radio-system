package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.TRAAdvertisement;
import io.protone.repository.TRAAdvertisementRepository;
import io.protone.service.dto.TRAAdvertisementDTO;
import io.protone.service.mapper.TRAAdvertisementMapper;

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
 * Test class for the TRAAdvertisementResource REST controller.
 *
 * @see TRAAdvertisementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TRAAdvertisementResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private TRAAdvertisementRepository tRAAdvertisementRepository;

    @Inject
    private TRAAdvertisementMapper tRAAdvertisementMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTRAAdvertisementMockMvc;

    private TRAAdvertisement tRAAdvertisement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TRAAdvertisementResource tRAAdvertisementResource = new TRAAdvertisementResource();
        ReflectionTestUtils.setField(tRAAdvertisementResource, "tRAAdvertisementRepository", tRAAdvertisementRepository);
        ReflectionTestUtils.setField(tRAAdvertisementResource, "tRAAdvertisementMapper", tRAAdvertisementMapper);
        this.restTRAAdvertisementMockMvc = MockMvcBuilders.standaloneSetup(tRAAdvertisementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TRAAdvertisement createEntity(EntityManager em) {
        TRAAdvertisement tRAAdvertisement = new TRAAdvertisement()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return tRAAdvertisement;
    }

    @Before
    public void initTest() {
        tRAAdvertisement = createEntity(em);
    }

    @Test
    @Transactional
    public void createTRAAdvertisement() throws Exception {
        int databaseSizeBeforeCreate = tRAAdvertisementRepository.findAll().size();

        // Create the TRAAdvertisement
        TRAAdvertisementDTO tRAAdvertisementDTO = tRAAdvertisementMapper.tRAAdvertisementToTRAAdvertisementDTO(tRAAdvertisement);

        restTRAAdvertisementMockMvc.perform(post("/api/t-ra-advertisements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAAdvertisementDTO)))
            .andExpect(status().isCreated());

        // Validate the TRAAdvertisement in the database
        List<TRAAdvertisement> tRAAdvertisementList = tRAAdvertisementRepository.findAll();
        assertThat(tRAAdvertisementList).hasSize(databaseSizeBeforeCreate + 1);
        TRAAdvertisement testTRAAdvertisement = tRAAdvertisementList.get(tRAAdvertisementList.size() - 1);
        assertThat(testTRAAdvertisement.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTRAAdvertisement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTRAAdvertisementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tRAAdvertisementRepository.findAll().size();

        // Create the TRAAdvertisement with an existing ID
        TRAAdvertisement existingTRAAdvertisement = new TRAAdvertisement();
        existingTRAAdvertisement.setId(1L);
        TRAAdvertisementDTO existingTRAAdvertisementDTO = tRAAdvertisementMapper.tRAAdvertisementToTRAAdvertisementDTO(existingTRAAdvertisement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTRAAdvertisementMockMvc.perform(post("/api/t-ra-advertisements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTRAAdvertisementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TRAAdvertisement> tRAAdvertisementList = tRAAdvertisementRepository.findAll();
        assertThat(tRAAdvertisementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tRAAdvertisementRepository.findAll().size();
        // set the field null
        tRAAdvertisement.setName(null);

        // Create the TRAAdvertisement, which fails.
        TRAAdvertisementDTO tRAAdvertisementDTO = tRAAdvertisementMapper.tRAAdvertisementToTRAAdvertisementDTO(tRAAdvertisement);

        restTRAAdvertisementMockMvc.perform(post("/api/t-ra-advertisements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAAdvertisementDTO)))
            .andExpect(status().isBadRequest());

        List<TRAAdvertisement> tRAAdvertisementList = tRAAdvertisementRepository.findAll();
        assertThat(tRAAdvertisementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTRAAdvertisements() throws Exception {
        // Initialize the database
        tRAAdvertisementRepository.saveAndFlush(tRAAdvertisement);

        // Get all the tRAAdvertisementList
        restTRAAdvertisementMockMvc.perform(get("/api/t-ra-advertisements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tRAAdvertisement.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTRAAdvertisement() throws Exception {
        // Initialize the database
        tRAAdvertisementRepository.saveAndFlush(tRAAdvertisement);

        // Get the tRAAdvertisement
        restTRAAdvertisementMockMvc.perform(get("/api/t-ra-advertisements/{id}", tRAAdvertisement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tRAAdvertisement.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTRAAdvertisement() throws Exception {
        // Get the tRAAdvertisement
        restTRAAdvertisementMockMvc.perform(get("/api/t-ra-advertisements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTRAAdvertisement() throws Exception {
        // Initialize the database
        tRAAdvertisementRepository.saveAndFlush(tRAAdvertisement);
        int databaseSizeBeforeUpdate = tRAAdvertisementRepository.findAll().size();

        // Update the tRAAdvertisement
        TRAAdvertisement updatedTRAAdvertisement = tRAAdvertisementRepository.findOne(tRAAdvertisement.getId());
        updatedTRAAdvertisement
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        TRAAdvertisementDTO tRAAdvertisementDTO = tRAAdvertisementMapper.tRAAdvertisementToTRAAdvertisementDTO(updatedTRAAdvertisement);

        restTRAAdvertisementMockMvc.perform(put("/api/t-ra-advertisements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAAdvertisementDTO)))
            .andExpect(status().isOk());

        // Validate the TRAAdvertisement in the database
        List<TRAAdvertisement> tRAAdvertisementList = tRAAdvertisementRepository.findAll();
        assertThat(tRAAdvertisementList).hasSize(databaseSizeBeforeUpdate);
        TRAAdvertisement testTRAAdvertisement = tRAAdvertisementList.get(tRAAdvertisementList.size() - 1);
        assertThat(testTRAAdvertisement.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTRAAdvertisement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTRAAdvertisement() throws Exception {
        int databaseSizeBeforeUpdate = tRAAdvertisementRepository.findAll().size();

        // Create the TRAAdvertisement
        TRAAdvertisementDTO tRAAdvertisementDTO = tRAAdvertisementMapper.tRAAdvertisementToTRAAdvertisementDTO(tRAAdvertisement);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTRAAdvertisementMockMvc.perform(put("/api/t-ra-advertisements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAAdvertisementDTO)))
            .andExpect(status().isCreated());

        // Validate the TRAAdvertisement in the database
        List<TRAAdvertisement> tRAAdvertisementList = tRAAdvertisementRepository.findAll();
        assertThat(tRAAdvertisementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTRAAdvertisement() throws Exception {
        // Initialize the database
        tRAAdvertisementRepository.saveAndFlush(tRAAdvertisement);
        int databaseSizeBeforeDelete = tRAAdvertisementRepository.findAll().size();

        // Get the tRAAdvertisement
        restTRAAdvertisementMockMvc.perform(delete("/api/t-ra-advertisements/{id}", tRAAdvertisement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TRAAdvertisement> tRAAdvertisementList = tRAAdvertisementRepository.findAll();
        assertThat(tRAAdvertisementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
