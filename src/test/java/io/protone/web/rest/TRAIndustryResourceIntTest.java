package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.TRAIndustry;
import io.protone.repository.TRAIndustryRepository;
import io.protone.service.dto.TRAIndustryDTO;
import io.protone.service.mapper.TRAIndustryMapper;

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
 * Test class for the TRAIndustryResource REST controller.
 *
 * @see TRAIndustryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TRAIndustryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private TRAIndustryRepository tRAIndustryRepository;

    @Inject
    private TRAIndustryMapper tRAIndustryMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTRAIndustryMockMvc;

    private TRAIndustry tRAIndustry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TRAIndustryResource tRAIndustryResource = new TRAIndustryResource();
        ReflectionTestUtils.setField(tRAIndustryResource, "tRAIndustryRepository", tRAIndustryRepository);
        ReflectionTestUtils.setField(tRAIndustryResource, "tRAIndustryMapper", tRAIndustryMapper);
        this.restTRAIndustryMockMvc = MockMvcBuilders.standaloneSetup(tRAIndustryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TRAIndustry createEntity(EntityManager em) {
        TRAIndustry tRAIndustry = new TRAIndustry()
                .name(DEFAULT_NAME);
        return tRAIndustry;
    }

    @Before
    public void initTest() {
        tRAIndustry = createEntity(em);
    }

    @Test
    @Transactional
    public void createTRAIndustry() throws Exception {
        int databaseSizeBeforeCreate = tRAIndustryRepository.findAll().size();

        // Create the TRAIndustry
        TRAIndustryDTO tRAIndustryDTO = tRAIndustryMapper.tRAIndustryToTRAIndustryDTO(tRAIndustry);

        restTRAIndustryMockMvc.perform(post("/api/t-ra-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAIndustryDTO)))
            .andExpect(status().isCreated());

        // Validate the TRAIndustry in the database
        List<TRAIndustry> tRAIndustryList = tRAIndustryRepository.findAll();
        assertThat(tRAIndustryList).hasSize(databaseSizeBeforeCreate + 1);
        TRAIndustry testTRAIndustry = tRAIndustryList.get(tRAIndustryList.size() - 1);
        assertThat(testTRAIndustry.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTRAIndustryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tRAIndustryRepository.findAll().size();

        // Create the TRAIndustry with an existing ID
        TRAIndustry existingTRAIndustry = new TRAIndustry();
        existingTRAIndustry.setId(1L);
        TRAIndustryDTO existingTRAIndustryDTO = tRAIndustryMapper.tRAIndustryToTRAIndustryDTO(existingTRAIndustry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTRAIndustryMockMvc.perform(post("/api/t-ra-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTRAIndustryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TRAIndustry> tRAIndustryList = tRAIndustryRepository.findAll();
        assertThat(tRAIndustryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tRAIndustryRepository.findAll().size();
        // set the field null
        tRAIndustry.setName(null);

        // Create the TRAIndustry, which fails.
        TRAIndustryDTO tRAIndustryDTO = tRAIndustryMapper.tRAIndustryToTRAIndustryDTO(tRAIndustry);

        restTRAIndustryMockMvc.perform(post("/api/t-ra-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAIndustryDTO)))
            .andExpect(status().isBadRequest());

        List<TRAIndustry> tRAIndustryList = tRAIndustryRepository.findAll();
        assertThat(tRAIndustryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTRAIndustries() throws Exception {
        // Initialize the database
        tRAIndustryRepository.saveAndFlush(tRAIndustry);

        // Get all the tRAIndustryList
        restTRAIndustryMockMvc.perform(get("/api/t-ra-industries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tRAIndustry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTRAIndustry() throws Exception {
        // Initialize the database
        tRAIndustryRepository.saveAndFlush(tRAIndustry);

        // Get the tRAIndustry
        restTRAIndustryMockMvc.perform(get("/api/t-ra-industries/{id}", tRAIndustry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tRAIndustry.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTRAIndustry() throws Exception {
        // Get the tRAIndustry
        restTRAIndustryMockMvc.perform(get("/api/t-ra-industries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTRAIndustry() throws Exception {
        // Initialize the database
        tRAIndustryRepository.saveAndFlush(tRAIndustry);
        int databaseSizeBeforeUpdate = tRAIndustryRepository.findAll().size();

        // Update the tRAIndustry
        TRAIndustry updatedTRAIndustry = tRAIndustryRepository.findOne(tRAIndustry.getId());
        updatedTRAIndustry
                .name(UPDATED_NAME);
        TRAIndustryDTO tRAIndustryDTO = tRAIndustryMapper.tRAIndustryToTRAIndustryDTO(updatedTRAIndustry);

        restTRAIndustryMockMvc.perform(put("/api/t-ra-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAIndustryDTO)))
            .andExpect(status().isOk());

        // Validate the TRAIndustry in the database
        List<TRAIndustry> tRAIndustryList = tRAIndustryRepository.findAll();
        assertThat(tRAIndustryList).hasSize(databaseSizeBeforeUpdate);
        TRAIndustry testTRAIndustry = tRAIndustryList.get(tRAIndustryList.size() - 1);
        assertThat(testTRAIndustry.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTRAIndustry() throws Exception {
        int databaseSizeBeforeUpdate = tRAIndustryRepository.findAll().size();

        // Create the TRAIndustry
        TRAIndustryDTO tRAIndustryDTO = tRAIndustryMapper.tRAIndustryToTRAIndustryDTO(tRAIndustry);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTRAIndustryMockMvc.perform(put("/api/t-ra-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAIndustryDTO)))
            .andExpect(status().isCreated());

        // Validate the TRAIndustry in the database
        List<TRAIndustry> tRAIndustryList = tRAIndustryRepository.findAll();
        assertThat(tRAIndustryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTRAIndustry() throws Exception {
        // Initialize the database
        tRAIndustryRepository.saveAndFlush(tRAIndustry);
        int databaseSizeBeforeDelete = tRAIndustryRepository.findAll().size();

        // Get the tRAIndustry
        restTRAIndustryMockMvc.perform(delete("/api/t-ra-industries/{id}", tRAIndustry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TRAIndustry> tRAIndustryList = tRAIndustryRepository.findAll();
        assertThat(tRAIndustryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
