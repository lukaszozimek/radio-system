package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CorCountry;
import io.protone.repository.CorCountryRepository;
import io.protone.service.dto.CorCountryDTO;
import io.protone.service.mapper.CorCountryMapper;

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
 * Test class for the CorCountryResource REST controller.
 *
 * @see CorCountryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorCountryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    @Autowired
    private CorCountryRepository corCountryRepository;

    @Autowired
    private CorCountryMapper corCountryMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCorCountryMockMvc;

    private CorCountry corCountry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CorCountryResource corCountryResource = new CorCountryResource(corCountryRepository, corCountryMapper);
        this.restCorCountryMockMvc = MockMvcBuilders.standaloneSetup(corCountryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorCountry createEntity(EntityManager em) {
        CorCountry corCountry = new CorCountry()
                .name(DEFAULT_NAME)
                .shortName(DEFAULT_SHORT_NAME);
        return corCountry;
    }

    @Before
    public void initTest() {
        corCountry = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorCountry() throws Exception {
        int databaseSizeBeforeCreate = corCountryRepository.findAll().size();

        // Create the CorCountry
        CorCountryDTO corCountryDTO = corCountryMapper.corCountryToCorCountryDTO(corCountry);

        restCorCountryMockMvc.perform(post("/api/cor-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corCountryDTO)))
            .andExpect(status().isCreated());

        // Validate the CorCountry in the database
        List<CorCountry> corCountryList = corCountryRepository.findAll();
        assertThat(corCountryList).hasSize(databaseSizeBeforeCreate + 1);
        CorCountry testCorCountry = corCountryList.get(corCountryList.size() - 1);
        assertThat(testCorCountry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCorCountry.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
    }

    @Test
    @Transactional
    public void createCorCountryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corCountryRepository.findAll().size();

        // Create the CorCountry with an existing ID
        CorCountry existingCorCountry = new CorCountry();
        existingCorCountry.setId(1L);
        CorCountryDTO existingCorCountryDTO = corCountryMapper.corCountryToCorCountryDTO(existingCorCountry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorCountryMockMvc.perform(post("/api/cor-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorCountryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorCountry> corCountryList = corCountryRepository.findAll();
        assertThat(corCountryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorCountries() throws Exception {
        // Initialize the database
        corCountryRepository.saveAndFlush(corCountry);

        // Get all the corCountryList
        restCorCountryMockMvc.perform(get("/api/cor-countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corCountry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCorCountry() throws Exception {
        // Initialize the database
        corCountryRepository.saveAndFlush(corCountry);

        // Get the corCountry
        restCorCountryMockMvc.perform(get("/api/cor-countries/{id}", corCountry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corCountry.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorCountry() throws Exception {
        // Get the corCountry
        restCorCountryMockMvc.perform(get("/api/cor-countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorCountry() throws Exception {
        // Initialize the database
        corCountryRepository.saveAndFlush(corCountry);
        int databaseSizeBeforeUpdate = corCountryRepository.findAll().size();

        // Update the corCountry
        CorCountry updatedCorCountry = corCountryRepository.findOne(corCountry.getId());
        updatedCorCountry
                .name(UPDATED_NAME)
                .shortName(UPDATED_SHORT_NAME);
        CorCountryDTO corCountryDTO = corCountryMapper.corCountryToCorCountryDTO(updatedCorCountry);

        restCorCountryMockMvc.perform(put("/api/cor-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corCountryDTO)))
            .andExpect(status().isOk());

        // Validate the CorCountry in the database
        List<CorCountry> corCountryList = corCountryRepository.findAll();
        assertThat(corCountryList).hasSize(databaseSizeBeforeUpdate);
        CorCountry testCorCountry = corCountryList.get(corCountryList.size() - 1);
        assertThat(testCorCountry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCorCountry.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCorCountry() throws Exception {
        int databaseSizeBeforeUpdate = corCountryRepository.findAll().size();

        // Create the CorCountry
        CorCountryDTO corCountryDTO = corCountryMapper.corCountryToCorCountryDTO(corCountry);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorCountryMockMvc.perform(put("/api/cor-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corCountryDTO)))
            .andExpect(status().isCreated());

        // Validate the CorCountry in the database
        List<CorCountry> corCountryList = corCountryRepository.findAll();
        assertThat(corCountryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorCountry() throws Exception {
        // Initialize the database
        corCountryRepository.saveAndFlush(corCountry);
        int databaseSizeBeforeDelete = corCountryRepository.findAll().size();

        // Get the corCountry
        restCorCountryMockMvc.perform(delete("/api/cor-countries/{id}", corCountry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorCountry> corCountryList = corCountryRepository.findAll();
        assertThat(corCountryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorCountry.class);
    }
}
