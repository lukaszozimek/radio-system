package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CorCurrency;
import io.protone.repository.CorCurrencyRepository;
import io.protone.service.dto.CorCurrencyDTO;
import io.protone.service.mapper.CorCurrencyMapper;

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
 * Test class for the CorCurrencyResource REST controller.
 *
 * @see CorCurrencyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorCurrencyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_SYMBOL = "BBBBBBBBBB";

    private static final String DEFAULT_DELIMITER = "AAAAAAAAAA";
    private static final String UPDATED_DELIMITER = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    @Autowired
    private CorCurrencyRepository corCurrencyRepository;

    @Autowired
    private CorCurrencyMapper corCurrencyMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCorCurrencyMockMvc;

    private CorCurrency corCurrency;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CorCurrencyResource corCurrencyResource = new CorCurrencyResource(corCurrencyRepository, corCurrencyMapper);
        this.restCorCurrencyMockMvc = MockMvcBuilders.standaloneSetup(corCurrencyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorCurrency createEntity(EntityManager em) {
        CorCurrency corCurrency = new CorCurrency()
                .name(DEFAULT_NAME)
                .symbol(DEFAULT_SYMBOL)
                .delimiter(DEFAULT_DELIMITER)
                .shortName(DEFAULT_SHORT_NAME);
        return corCurrency;
    }

    @Before
    public void initTest() {
        corCurrency = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorCurrency() throws Exception {
        int databaseSizeBeforeCreate = corCurrencyRepository.findAll().size();

        // Create the CorCurrency
        CorCurrencyDTO corCurrencyDTO = corCurrencyMapper.corCurrencyToCorCurrencyDTO(corCurrency);

        restCorCurrencyMockMvc.perform(post("/api/cor-currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corCurrencyDTO)))
            .andExpect(status().isCreated());

        // Validate the CorCurrency in the database
        List<CorCurrency> corCurrencyList = corCurrencyRepository.findAll();
        assertThat(corCurrencyList).hasSize(databaseSizeBeforeCreate + 1);
        CorCurrency testCorCurrency = corCurrencyList.get(corCurrencyList.size() - 1);
        assertThat(testCorCurrency.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCorCurrency.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testCorCurrency.getDelimiter()).isEqualTo(DEFAULT_DELIMITER);
        assertThat(testCorCurrency.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
    }

    @Test
    @Transactional
    public void createCorCurrencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corCurrencyRepository.findAll().size();

        // Create the CorCurrency with an existing ID
        CorCurrency existingCorCurrency = new CorCurrency();
        existingCorCurrency.setId(1L);
        CorCurrencyDTO existingCorCurrencyDTO = corCurrencyMapper.corCurrencyToCorCurrencyDTO(existingCorCurrency);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorCurrencyMockMvc.perform(post("/api/cor-currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorCurrencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorCurrency> corCurrencyList = corCurrencyRepository.findAll();
        assertThat(corCurrencyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorCurrencies() throws Exception {
        // Initialize the database
        corCurrencyRepository.saveAndFlush(corCurrency);

        // Get all the corCurrencyList
        restCorCurrencyMockMvc.perform(get("/api/cor-currencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corCurrency.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL.toString())))
            .andExpect(jsonPath("$.[*].delimiter").value(hasItem(DEFAULT_DELIMITER.toString())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCorCurrency() throws Exception {
        // Initialize the database
        corCurrencyRepository.saveAndFlush(corCurrency);

        // Get the corCurrency
        restCorCurrencyMockMvc.perform(get("/api/cor-currencies/{id}", corCurrency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corCurrency.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL.toString()))
            .andExpect(jsonPath("$.delimiter").value(DEFAULT_DELIMITER.toString()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorCurrency() throws Exception {
        // Get the corCurrency
        restCorCurrencyMockMvc.perform(get("/api/cor-currencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorCurrency() throws Exception {
        // Initialize the database
        corCurrencyRepository.saveAndFlush(corCurrency);
        int databaseSizeBeforeUpdate = corCurrencyRepository.findAll().size();

        // Update the corCurrency
        CorCurrency updatedCorCurrency = corCurrencyRepository.findOne(corCurrency.getId());
        updatedCorCurrency
                .name(UPDATED_NAME)
                .symbol(UPDATED_SYMBOL)
                .delimiter(UPDATED_DELIMITER)
                .shortName(UPDATED_SHORT_NAME);
        CorCurrencyDTO corCurrencyDTO = corCurrencyMapper.corCurrencyToCorCurrencyDTO(updatedCorCurrency);

        restCorCurrencyMockMvc.perform(put("/api/cor-currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corCurrencyDTO)))
            .andExpect(status().isOk());

        // Validate the CorCurrency in the database
        List<CorCurrency> corCurrencyList = corCurrencyRepository.findAll();
        assertThat(corCurrencyList).hasSize(databaseSizeBeforeUpdate);
        CorCurrency testCorCurrency = corCurrencyList.get(corCurrencyList.size() - 1);
        assertThat(testCorCurrency.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCorCurrency.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testCorCurrency.getDelimiter()).isEqualTo(UPDATED_DELIMITER);
        assertThat(testCorCurrency.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCorCurrency() throws Exception {
        int databaseSizeBeforeUpdate = corCurrencyRepository.findAll().size();

        // Create the CorCurrency
        CorCurrencyDTO corCurrencyDTO = corCurrencyMapper.corCurrencyToCorCurrencyDTO(corCurrency);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorCurrencyMockMvc.perform(put("/api/cor-currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corCurrencyDTO)))
            .andExpect(status().isCreated());

        // Validate the CorCurrency in the database
        List<CorCurrency> corCurrencyList = corCurrencyRepository.findAll();
        assertThat(corCurrencyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorCurrency() throws Exception {
        // Initialize the database
        corCurrencyRepository.saveAndFlush(corCurrency);
        int databaseSizeBeforeDelete = corCurrencyRepository.findAll().size();

        // Get the corCurrency
        restCorCurrencyMockMvc.perform(delete("/api/cor-currencies/{id}", corCurrency.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorCurrency> corCurrencyList = corCurrencyRepository.findAll();
        assertThat(corCurrencyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorCurrency.class);
    }
}
