package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CORRange;
import io.protone.repository.CORRangeRepository;
import io.protone.service.dto.CORRangeDTO;
import io.protone.service.mapper.CORRangeMapper;

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
 * Test class for the CORRangeResource REST controller.
 *
 * @see CORRangeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CORRangeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private CORRangeRepository cORRangeRepository;

    @Inject
    private CORRangeMapper cORRangeMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCORRangeMockMvc;

    private CORRange cORRange;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CORRangeResource cORRangeResource = new CORRangeResource();
        ReflectionTestUtils.setField(cORRangeResource, "cORRangeRepository", cORRangeRepository);
        ReflectionTestUtils.setField(cORRangeResource, "cORRangeMapper", cORRangeMapper);
        this.restCORRangeMockMvc = MockMvcBuilders.standaloneSetup(cORRangeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CORRange createEntity(EntityManager em) {
        CORRange cORRange = new CORRange()
                .name(DEFAULT_NAME);
        return cORRange;
    }

    @Before
    public void initTest() {
        cORRange = createEntity(em);
    }

    @Test
    @Transactional
    public void createCORRange() throws Exception {
        int databaseSizeBeforeCreate = cORRangeRepository.findAll().size();

        // Create the CORRange
        CORRangeDTO cORRangeDTO = cORRangeMapper.cORRangeToCORRangeDTO(cORRange);

        restCORRangeMockMvc.perform(post("/api/c-or-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORRangeDTO)))
            .andExpect(status().isCreated());

        // Validate the CORRange in the database
        List<CORRange> cORRangeList = cORRangeRepository.findAll();
        assertThat(cORRangeList).hasSize(databaseSizeBeforeCreate + 1);
        CORRange testCORRange = cORRangeList.get(cORRangeList.size() - 1);
        assertThat(testCORRange.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCORRangeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cORRangeRepository.findAll().size();

        // Create the CORRange with an existing ID
        CORRange existingCORRange = new CORRange();
        existingCORRange.setId(1L);
        CORRangeDTO existingCORRangeDTO = cORRangeMapper.cORRangeToCORRangeDTO(existingCORRange);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCORRangeMockMvc.perform(post("/api/c-or-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCORRangeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CORRange> cORRangeList = cORRangeRepository.findAll();
        assertThat(cORRangeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCORRanges() throws Exception {
        // Initialize the database
        cORRangeRepository.saveAndFlush(cORRange);

        // Get all the cORRangeList
        restCORRangeMockMvc.perform(get("/api/c-or-ranges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cORRange.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCORRange() throws Exception {
        // Initialize the database
        cORRangeRepository.saveAndFlush(cORRange);

        // Get the cORRange
        restCORRangeMockMvc.perform(get("/api/c-or-ranges/{id}", cORRange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cORRange.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCORRange() throws Exception {
        // Get the cORRange
        restCORRangeMockMvc.perform(get("/api/c-or-ranges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCORRange() throws Exception {
        // Initialize the database
        cORRangeRepository.saveAndFlush(cORRange);
        int databaseSizeBeforeUpdate = cORRangeRepository.findAll().size();

        // Update the cORRange
        CORRange updatedCORRange = cORRangeRepository.findOne(cORRange.getId());
        updatedCORRange
                .name(UPDATED_NAME);
        CORRangeDTO cORRangeDTO = cORRangeMapper.cORRangeToCORRangeDTO(updatedCORRange);

        restCORRangeMockMvc.perform(put("/api/c-or-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORRangeDTO)))
            .andExpect(status().isOk());

        // Validate the CORRange in the database
        List<CORRange> cORRangeList = cORRangeRepository.findAll();
        assertThat(cORRangeList).hasSize(databaseSizeBeforeUpdate);
        CORRange testCORRange = cORRangeList.get(cORRangeList.size() - 1);
        assertThat(testCORRange.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCORRange() throws Exception {
        int databaseSizeBeforeUpdate = cORRangeRepository.findAll().size();

        // Create the CORRange
        CORRangeDTO cORRangeDTO = cORRangeMapper.cORRangeToCORRangeDTO(cORRange);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCORRangeMockMvc.perform(put("/api/c-or-ranges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORRangeDTO)))
            .andExpect(status().isCreated());

        // Validate the CORRange in the database
        List<CORRange> cORRangeList = cORRangeRepository.findAll();
        assertThat(cORRangeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCORRange() throws Exception {
        // Initialize the database
        cORRangeRepository.saveAndFlush(cORRange);
        int databaseSizeBeforeDelete = cORRangeRepository.findAll().size();

        // Get the cORRange
        restCORRangeMockMvc.perform(delete("/api/c-or-ranges/{id}", cORRange.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CORRange> cORRangeList = cORRangeRepository.findAll();
        assertThat(cORRangeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
