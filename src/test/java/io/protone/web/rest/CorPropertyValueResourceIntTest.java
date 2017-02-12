package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CorPropertyValue;
import io.protone.repository.CorPropertyValueRepository;
import io.protone.service.dto.CorPropertyValueDTO;
import io.protone.service.mapper.CorPropertyValueMapper;

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
 * Test class for the CorPropertyValueResource REST controller.
 *
 * @see CorPropertyValueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorPropertyValueResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private CorPropertyValueRepository corPropertyValueRepository;

    @Autowired
    private CorPropertyValueMapper corPropertyValueMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCorPropertyValueMockMvc;

    private CorPropertyValue corPropertyValue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CorPropertyValueResource corPropertyValueResource = new CorPropertyValueResource(corPropertyValueRepository, corPropertyValueMapper);
        this.restCorPropertyValueMockMvc = MockMvcBuilders.standaloneSetup(corPropertyValueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorPropertyValue createEntity(EntityManager em) {
        CorPropertyValue corPropertyValue = new CorPropertyValue()
                .value(DEFAULT_VALUE);
        return corPropertyValue;
    }

    @Before
    public void initTest() {
        corPropertyValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorPropertyValue() throws Exception {
        int databaseSizeBeforeCreate = corPropertyValueRepository.findAll().size();

        // Create the CorPropertyValue
        CorPropertyValueDTO corPropertyValueDTO = corPropertyValueMapper.corPropertyValueToCorPropertyValueDTO(corPropertyValue);

        restCorPropertyValueMockMvc.perform(post("/api/cor-property-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corPropertyValueDTO)))
            .andExpect(status().isCreated());

        // Validate the CorPropertyValue in the database
        List<CorPropertyValue> corPropertyValueList = corPropertyValueRepository.findAll();
        assertThat(corPropertyValueList).hasSize(databaseSizeBeforeCreate + 1);
        CorPropertyValue testCorPropertyValue = corPropertyValueList.get(corPropertyValueList.size() - 1);
        assertThat(testCorPropertyValue.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createCorPropertyValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corPropertyValueRepository.findAll().size();

        // Create the CorPropertyValue with an existing ID
        CorPropertyValue existingCorPropertyValue = new CorPropertyValue();
        existingCorPropertyValue.setId(1L);
        CorPropertyValueDTO existingCorPropertyValueDTO = corPropertyValueMapper.corPropertyValueToCorPropertyValueDTO(existingCorPropertyValue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorPropertyValueMockMvc.perform(post("/api/cor-property-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorPropertyValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorPropertyValue> corPropertyValueList = corPropertyValueRepository.findAll();
        assertThat(corPropertyValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = corPropertyValueRepository.findAll().size();
        // set the field null
        corPropertyValue.setValue(null);

        // Create the CorPropertyValue, which fails.
        CorPropertyValueDTO corPropertyValueDTO = corPropertyValueMapper.corPropertyValueToCorPropertyValueDTO(corPropertyValue);

        restCorPropertyValueMockMvc.perform(post("/api/cor-property-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corPropertyValueDTO)))
            .andExpect(status().isBadRequest());

        List<CorPropertyValue> corPropertyValueList = corPropertyValueRepository.findAll();
        assertThat(corPropertyValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorPropertyValues() throws Exception {
        // Initialize the database
        corPropertyValueRepository.saveAndFlush(corPropertyValue);

        // Get all the corPropertyValueList
        restCorPropertyValueMockMvc.perform(get("/api/cor-property-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corPropertyValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getCorPropertyValue() throws Exception {
        // Initialize the database
        corPropertyValueRepository.saveAndFlush(corPropertyValue);

        // Get the corPropertyValue
        restCorPropertyValueMockMvc.perform(get("/api/cor-property-values/{id}", corPropertyValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corPropertyValue.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorPropertyValue() throws Exception {
        // Get the corPropertyValue
        restCorPropertyValueMockMvc.perform(get("/api/cor-property-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorPropertyValue() throws Exception {
        // Initialize the database
        corPropertyValueRepository.saveAndFlush(corPropertyValue);
        int databaseSizeBeforeUpdate = corPropertyValueRepository.findAll().size();

        // Update the corPropertyValue
        CorPropertyValue updatedCorPropertyValue = corPropertyValueRepository.findOne(corPropertyValue.getId());
        updatedCorPropertyValue
                .value(UPDATED_VALUE);
        CorPropertyValueDTO corPropertyValueDTO = corPropertyValueMapper.corPropertyValueToCorPropertyValueDTO(updatedCorPropertyValue);

        restCorPropertyValueMockMvc.perform(put("/api/cor-property-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corPropertyValueDTO)))
            .andExpect(status().isOk());

        // Validate the CorPropertyValue in the database
        List<CorPropertyValue> corPropertyValueList = corPropertyValueRepository.findAll();
        assertThat(corPropertyValueList).hasSize(databaseSizeBeforeUpdate);
        CorPropertyValue testCorPropertyValue = corPropertyValueList.get(corPropertyValueList.size() - 1);
        assertThat(testCorPropertyValue.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingCorPropertyValue() throws Exception {
        int databaseSizeBeforeUpdate = corPropertyValueRepository.findAll().size();

        // Create the CorPropertyValue
        CorPropertyValueDTO corPropertyValueDTO = corPropertyValueMapper.corPropertyValueToCorPropertyValueDTO(corPropertyValue);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorPropertyValueMockMvc.perform(put("/api/cor-property-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corPropertyValueDTO)))
            .andExpect(status().isCreated());

        // Validate the CorPropertyValue in the database
        List<CorPropertyValue> corPropertyValueList = corPropertyValueRepository.findAll();
        assertThat(corPropertyValueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorPropertyValue() throws Exception {
        // Initialize the database
        corPropertyValueRepository.saveAndFlush(corPropertyValue);
        int databaseSizeBeforeDelete = corPropertyValueRepository.findAll().size();

        // Get the corPropertyValue
        restCorPropertyValueMockMvc.perform(delete("/api/cor-property-values/{id}", corPropertyValue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorPropertyValue> corPropertyValueList = corPropertyValueRepository.findAll();
        assertThat(corPropertyValueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorPropertyValue.class);
    }
}
