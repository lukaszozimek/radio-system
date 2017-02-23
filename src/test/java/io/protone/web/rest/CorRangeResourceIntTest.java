package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CorRange;
import io.protone.repository.CorRangeRepository;
import io.protone.service.dto.CorRangeDTO;
import io.protone.service.mapper.CorRangeMapper;

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
 * Test class for the CorRangeResource REST controller.
 *
 * @see CorRangeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorRangeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CorRangeRepository corRangeRepository;

    @Autowired
    private CorRangeMapper corRangeMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCorRangeMockMvc;

    private CorRange corRange;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CorRangeResource corRangeResource = new CorRangeResource(corRangeRepository, corRangeMapper);
        this.restCorRangeMockMvc = MockMvcBuilders.standaloneSetup(corRangeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorRange createEntity(EntityManager em) {
        CorRange corRange = new CorRange()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return corRange;
    }

    @Before
    public void initTest() {
        corRange = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorRange() throws Exception {
        int databaseSizeBeforeCreate = corRangeRepository.findAll().size();

        // Create the CorRange
        CorRangeDTO corRangeDTO = corRangeMapper.corRangeToCorRangeDTO(corRange);

        restCorRangeMockMvc.perform(post("/api/cor-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corRangeDTO)))
            .andExpect(status().isCreated());

        // Validate the CorRange in the database
        List<CorRange> corRangeList = corRangeRepository.findAll();
        assertThat(corRangeList).hasSize(databaseSizeBeforeCreate + 1);
        CorRange testCorRange = corRangeList.get(corRangeList.size() - 1);
        assertThat(testCorRange.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCorRange.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCorRangeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corRangeRepository.findAll().size();

        // Create the CorRange with an existing ID
        CorRange existingCorRange = new CorRange();
        existingCorRange.setId(1L);
        CorRangeDTO existingCorRangeDTO = corRangeMapper.corRangeToCorRangeDTO(existingCorRange);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorRangeMockMvc.perform(post("/api/cor-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorRangeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorRange> corRangeList = corRangeRepository.findAll();
        assertThat(corRangeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorRanges() throws Exception {
        // Initialize the database
        corRangeRepository.saveAndFlush(corRange);

        // Get all the corRangeList
        restCorRangeMockMvc.perform(get("/api/cor-ranges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corRange.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCorRange() throws Exception {
        // Initialize the database
        corRangeRepository.saveAndFlush(corRange);

        // Get the corRange
        restCorRangeMockMvc.perform(get("/api/cor-ranges/{id}", corRange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corRange.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorRange() throws Exception {
        // Get the corRange
        restCorRangeMockMvc.perform(get("/api/cor-ranges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorRange() throws Exception {
        // Initialize the database
        corRangeRepository.saveAndFlush(corRange);
        int databaseSizeBeforeUpdate = corRangeRepository.findAll().size();

        // Update the corRange
        CorRange updatedCorRange = corRangeRepository.findOne(corRange.getId());
        updatedCorRange
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        CorRangeDTO corRangeDTO = corRangeMapper.corRangeToCorRangeDTO(updatedCorRange);

        restCorRangeMockMvc.perform(put("/api/cor-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corRangeDTO)))
            .andExpect(status().isOk());

        // Validate the CorRange in the database
        List<CorRange> corRangeList = corRangeRepository.findAll();
        assertThat(corRangeList).hasSize(databaseSizeBeforeUpdate);
        CorRange testCorRange = corRangeList.get(corRangeList.size() - 1);
        assertThat(testCorRange.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCorRange.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCorRange() throws Exception {
        int databaseSizeBeforeUpdate = corRangeRepository.findAll().size();

        // Create the CorRange
        CorRangeDTO corRangeDTO = corRangeMapper.corRangeToCorRangeDTO(corRange);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorRangeMockMvc.perform(put("/api/cor-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corRangeDTO)))
            .andExpect(status().isCreated());

        // Validate the CorRange in the database
        List<CorRange> corRangeList = corRangeRepository.findAll();
        assertThat(corRangeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorRange() throws Exception {
        // Initialize the database
        corRangeRepository.saveAndFlush(corRange);
        int databaseSizeBeforeDelete = corRangeRepository.findAll().size();

        // Get the corRange
        restCorRangeMockMvc.perform(delete("/api/cor-ranges/{id}", corRange.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorRange> corRangeList = corRangeRepository.findAll();
        assertThat(corRangeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorRange.class);
    }
}
