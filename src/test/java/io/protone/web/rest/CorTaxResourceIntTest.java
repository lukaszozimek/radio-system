package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CorTax;
import io.protone.repository.CorTaxRepository;
import io.protone.service.dto.CorTaxDTO;
import io.protone.service.mapper.CorTaxMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CorTaxResource REST controller.
 *
 * @see CorTaxResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorTaxResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CorTaxRepository corTaxRepository;

    @Autowired
    private CorTaxMapper corTaxMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCorTaxMockMvc;

    private CorTax corTax;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CorTaxResource corTaxResource = new CorTaxResource(corTaxRepository, corTaxMapper);
        this.restCorTaxMockMvc = MockMvcBuilders.standaloneSetup(corTaxResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorTax createEntity(EntityManager em) {
        CorTax corTax = new CorTax()
                .name(DEFAULT_NAME)
                .value(DEFAULT_VALUE)
                .validFrom(DEFAULT_VALID_FROM)
                .validTo(DEFAULT_VALID_TO)
                .active(DEFAULT_ACTIVE);
        return corTax;
    }

    @Before
    public void initTest() {
        corTax = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorTax() throws Exception {
        int databaseSizeBeforeCreate = corTaxRepository.findAll().size();

        // Create the CorTax
        CorTaxDTO corTaxDTO = corTaxMapper.corTaxToCorTaxDTO(corTax);

        restCorTaxMockMvc.perform(post("/api/cor-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corTaxDTO)))
            .andExpect(status().isCreated());

        // Validate the CorTax in the database
        List<CorTax> corTaxList = corTaxRepository.findAll();
        assertThat(corTaxList).hasSize(databaseSizeBeforeCreate + 1);
        CorTax testCorTax = corTaxList.get(corTaxList.size() - 1);
        assertThat(testCorTax.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCorTax.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCorTax.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCorTax.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testCorTax.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCorTaxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corTaxRepository.findAll().size();

        // Create the CorTax with an existing ID
        CorTax existingCorTax = new CorTax();
        existingCorTax.setId(1L);
        CorTaxDTO existingCorTaxDTO = corTaxMapper.corTaxToCorTaxDTO(existingCorTax);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorTaxMockMvc.perform(post("/api/cor-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorTaxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorTax> corTaxList = corTaxRepository.findAll();
        assertThat(corTaxList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorTaxes() throws Exception {
        // Initialize the database
        corTaxRepository.saveAndFlush(corTax);

        // Get all the corTaxList
        restCorTaxMockMvc.perform(get("/api/cor-taxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corTax.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getCorTax() throws Exception {
        // Initialize the database
        corTaxRepository.saveAndFlush(corTax);

        // Get the corTax
        restCorTaxMockMvc.perform(get("/api/cor-taxes/{id}", corTax.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corTax.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCorTax() throws Exception {
        // Get the corTax
        restCorTaxMockMvc.perform(get("/api/cor-taxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorTax() throws Exception {
        // Initialize the database
        corTaxRepository.saveAndFlush(corTax);
        int databaseSizeBeforeUpdate = corTaxRepository.findAll().size();

        // Update the corTax
        CorTax updatedCorTax = corTaxRepository.findOne(corTax.getId());
        updatedCorTax
                .name(UPDATED_NAME)
                .value(UPDATED_VALUE)
                .validFrom(UPDATED_VALID_FROM)
                .validTo(UPDATED_VALID_TO)
                .active(UPDATED_ACTIVE);
        CorTaxDTO corTaxDTO = corTaxMapper.corTaxToCorTaxDTO(updatedCorTax);

        restCorTaxMockMvc.perform(put("/api/cor-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corTaxDTO)))
            .andExpect(status().isOk());

        // Validate the CorTax in the database
        List<CorTax> corTaxList = corTaxRepository.findAll();
        assertThat(corTaxList).hasSize(databaseSizeBeforeUpdate);
        CorTax testCorTax = corTaxList.get(corTaxList.size() - 1);
        assertThat(testCorTax.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCorTax.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCorTax.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCorTax.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testCorTax.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCorTax() throws Exception {
        int databaseSizeBeforeUpdate = corTaxRepository.findAll().size();

        // Create the CorTax
        CorTaxDTO corTaxDTO = corTaxMapper.corTaxToCorTaxDTO(corTax);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorTaxMockMvc.perform(put("/api/cor-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corTaxDTO)))
            .andExpect(status().isCreated());

        // Validate the CorTax in the database
        List<CorTax> corTaxList = corTaxRepository.findAll();
        assertThat(corTaxList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorTax() throws Exception {
        // Initialize the database
        corTaxRepository.saveAndFlush(corTax);
        int databaseSizeBeforeDelete = corTaxRepository.findAll().size();

        // Get the corTax
        restCorTaxMockMvc.perform(delete("/api/cor-taxes/{id}", corTax.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorTax> corTaxList = corTaxRepository.findAll();
        assertThat(corTaxList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorTax.class);
    }
}
