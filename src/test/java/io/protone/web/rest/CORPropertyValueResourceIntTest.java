package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CORPropertyValue;
import io.protone.repository.CORPropertyValueRepository;
import io.protone.service.dto.CORPropertyValueDTO;
import io.protone.service.mapper.CORPropertyValueMapper;

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
 * Test class for the CORPropertyValueResource REST controller.
 *
 * @see CORPropertyValueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CORPropertyValueResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Inject
    private CORPropertyValueRepository cORPropertyValueRepository;

    @Inject
    private CORPropertyValueMapper cORPropertyValueMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCORPropertyValueMockMvc;

    private CORPropertyValue cORPropertyValue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CORPropertyValueResource cORPropertyValueResource = new CORPropertyValueResource();
        ReflectionTestUtils.setField(cORPropertyValueResource, "cORPropertyValueRepository", cORPropertyValueRepository);
        ReflectionTestUtils.setField(cORPropertyValueResource, "cORPropertyValueMapper", cORPropertyValueMapper);
        this.restCORPropertyValueMockMvc = MockMvcBuilders.standaloneSetup(cORPropertyValueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CORPropertyValue createEntity(EntityManager em) {
        CORPropertyValue cORPropertyValue = new CORPropertyValue()
                .value(DEFAULT_VALUE);
        return cORPropertyValue;
    }

    @Before
    public void initTest() {
        cORPropertyValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createCORPropertyValue() throws Exception {
        int databaseSizeBeforeCreate = cORPropertyValueRepository.findAll().size();

        // Create the CORPropertyValue
        CORPropertyValueDTO cORPropertyValueDTO = cORPropertyValueMapper.cORPropertyValueToCORPropertyValueDTO(cORPropertyValue);

        restCORPropertyValueMockMvc.perform(post("/api/c-or-property-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORPropertyValueDTO)))
            .andExpect(status().isCreated());

        // Validate the CORPropertyValue in the database
        List<CORPropertyValue> cORPropertyValueList = cORPropertyValueRepository.findAll();
        assertThat(cORPropertyValueList).hasSize(databaseSizeBeforeCreate + 1);
        CORPropertyValue testCORPropertyValue = cORPropertyValueList.get(cORPropertyValueList.size() - 1);
        assertThat(testCORPropertyValue.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createCORPropertyValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cORPropertyValueRepository.findAll().size();

        // Create the CORPropertyValue with an existing ID
        CORPropertyValue existingCORPropertyValue = new CORPropertyValue();
        existingCORPropertyValue.setId(1L);
        CORPropertyValueDTO existingCORPropertyValueDTO = cORPropertyValueMapper.cORPropertyValueToCORPropertyValueDTO(existingCORPropertyValue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCORPropertyValueMockMvc.perform(post("/api/c-or-property-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCORPropertyValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CORPropertyValue> cORPropertyValueList = cORPropertyValueRepository.findAll();
        assertThat(cORPropertyValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORPropertyValueRepository.findAll().size();
        // set the field null
        cORPropertyValue.setValue(null);

        // Create the CORPropertyValue, which fails.
        CORPropertyValueDTO cORPropertyValueDTO = cORPropertyValueMapper.cORPropertyValueToCORPropertyValueDTO(cORPropertyValue);

        restCORPropertyValueMockMvc.perform(post("/api/c-or-property-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORPropertyValueDTO)))
            .andExpect(status().isBadRequest());

        List<CORPropertyValue> cORPropertyValueList = cORPropertyValueRepository.findAll();
        assertThat(cORPropertyValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCORPropertyValues() throws Exception {
        // Initialize the database
        cORPropertyValueRepository.saveAndFlush(cORPropertyValue);

        // Get all the cORPropertyValueList
        restCORPropertyValueMockMvc.perform(get("/api/c-or-property-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cORPropertyValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getCORPropertyValue() throws Exception {
        // Initialize the database
        cORPropertyValueRepository.saveAndFlush(cORPropertyValue);

        // Get the cORPropertyValue
        restCORPropertyValueMockMvc.perform(get("/api/c-or-property-values/{id}", cORPropertyValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cORPropertyValue.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCORPropertyValue() throws Exception {
        // Get the cORPropertyValue
        restCORPropertyValueMockMvc.perform(get("/api/c-or-property-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCORPropertyValue() throws Exception {
        // Initialize the database
        cORPropertyValueRepository.saveAndFlush(cORPropertyValue);
        int databaseSizeBeforeUpdate = cORPropertyValueRepository.findAll().size();

        // Update the cORPropertyValue
        CORPropertyValue updatedCORPropertyValue = cORPropertyValueRepository.findOne(cORPropertyValue.getId());
        updatedCORPropertyValue
                .value(UPDATED_VALUE);
        CORPropertyValueDTO cORPropertyValueDTO = cORPropertyValueMapper.cORPropertyValueToCORPropertyValueDTO(updatedCORPropertyValue);

        restCORPropertyValueMockMvc.perform(put("/api/c-or-property-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORPropertyValueDTO)))
            .andExpect(status().isOk());

        // Validate the CORPropertyValue in the database
        List<CORPropertyValue> cORPropertyValueList = cORPropertyValueRepository.findAll();
        assertThat(cORPropertyValueList).hasSize(databaseSizeBeforeUpdate);
        CORPropertyValue testCORPropertyValue = cORPropertyValueList.get(cORPropertyValueList.size() - 1);
        assertThat(testCORPropertyValue.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingCORPropertyValue() throws Exception {
        int databaseSizeBeforeUpdate = cORPropertyValueRepository.findAll().size();

        // Create the CORPropertyValue
        CORPropertyValueDTO cORPropertyValueDTO = cORPropertyValueMapper.cORPropertyValueToCORPropertyValueDTO(cORPropertyValue);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCORPropertyValueMockMvc.perform(put("/api/c-or-property-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORPropertyValueDTO)))
            .andExpect(status().isCreated());

        // Validate the CORPropertyValue in the database
        List<CORPropertyValue> cORPropertyValueList = cORPropertyValueRepository.findAll();
        assertThat(cORPropertyValueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCORPropertyValue() throws Exception {
        // Initialize the database
        cORPropertyValueRepository.saveAndFlush(cORPropertyValue);
        int databaseSizeBeforeDelete = cORPropertyValueRepository.findAll().size();

        // Get the cORPropertyValue
        restCORPropertyValueMockMvc.perform(delete("/api/c-or-property-values/{id}", cORPropertyValue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CORPropertyValue> cORPropertyValueList = cORPropertyValueRepository.findAll();
        assertThat(cORPropertyValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
