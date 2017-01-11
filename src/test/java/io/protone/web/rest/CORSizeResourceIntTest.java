package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CORSize;
import io.protone.repository.CORSizeRepository;
import io.protone.service.dto.CORSizeDTO;
import io.protone.service.mapper.CORSizeMapper;

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
 * Test class for the CORSizeResource REST controller.
 *
 * @see CORSizeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CORSizeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private CORSizeRepository cORSizeRepository;

    @Inject
    private CORSizeMapper cORSizeMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCORSizeMockMvc;

    private CORSize cORSize;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CORSizeResource cORSizeResource = new CORSizeResource();
        ReflectionTestUtils.setField(cORSizeResource, "cORSizeRepository", cORSizeRepository);
        ReflectionTestUtils.setField(cORSizeResource, "cORSizeMapper", cORSizeMapper);
        this.restCORSizeMockMvc = MockMvcBuilders.standaloneSetup(cORSizeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CORSize createEntity(EntityManager em) {
        CORSize cORSize = new CORSize()
                .name(DEFAULT_NAME);
        return cORSize;
    }

    @Before
    public void initTest() {
        cORSize = createEntity(em);
    }

    @Test
    @Transactional
    public void createCORSize() throws Exception {
        int databaseSizeBeforeCreate = cORSizeRepository.findAll().size();

        // Create the CORSize
        CORSizeDTO cORSizeDTO = cORSizeMapper.cORSizeToCORSizeDTO(cORSize);

        restCORSizeMockMvc.perform(post("/api/c-or-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORSizeDTO)))
            .andExpect(status().isCreated());

        // Validate the CORSize in the database
        List<CORSize> cORSizeList = cORSizeRepository.findAll();
        assertThat(cORSizeList).hasSize(databaseSizeBeforeCreate + 1);
        CORSize testCORSize = cORSizeList.get(cORSizeList.size() - 1);
        assertThat(testCORSize.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCORSizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cORSizeRepository.findAll().size();

        // Create the CORSize with an existing ID
        CORSize existingCORSize = new CORSize();
        existingCORSize.setId(1L);
        CORSizeDTO existingCORSizeDTO = cORSizeMapper.cORSizeToCORSizeDTO(existingCORSize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCORSizeMockMvc.perform(post("/api/c-or-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCORSizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CORSize> cORSizeList = cORSizeRepository.findAll();
        assertThat(cORSizeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCORSizes() throws Exception {
        // Initialize the database
        cORSizeRepository.saveAndFlush(cORSize);

        // Get all the cORSizeList
        restCORSizeMockMvc.perform(get("/api/c-or-sizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cORSize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCORSize() throws Exception {
        // Initialize the database
        cORSizeRepository.saveAndFlush(cORSize);

        // Get the cORSize
        restCORSizeMockMvc.perform(get("/api/c-or-sizes/{id}", cORSize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cORSize.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCORSize() throws Exception {
        // Get the cORSize
        restCORSizeMockMvc.perform(get("/api/c-or-sizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCORSize() throws Exception {
        // Initialize the database
        cORSizeRepository.saveAndFlush(cORSize);
        int databaseSizeBeforeUpdate = cORSizeRepository.findAll().size();

        // Update the cORSize
        CORSize updatedCORSize = cORSizeRepository.findOne(cORSize.getId());
        updatedCORSize
                .name(UPDATED_NAME);
        CORSizeDTO cORSizeDTO = cORSizeMapper.cORSizeToCORSizeDTO(updatedCORSize);

        restCORSizeMockMvc.perform(put("/api/c-or-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORSizeDTO)))
            .andExpect(status().isOk());

        // Validate the CORSize in the database
        List<CORSize> cORSizeList = cORSizeRepository.findAll();
        assertThat(cORSizeList).hasSize(databaseSizeBeforeUpdate);
        CORSize testCORSize = cORSizeList.get(cORSizeList.size() - 1);
        assertThat(testCORSize.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCORSize() throws Exception {
        int databaseSizeBeforeUpdate = cORSizeRepository.findAll().size();

        // Create the CORSize
        CORSizeDTO cORSizeDTO = cORSizeMapper.cORSizeToCORSizeDTO(cORSize);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCORSizeMockMvc.perform(put("/api/c-or-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORSizeDTO)))
            .andExpect(status().isCreated());

        // Validate the CORSize in the database
        List<CORSize> cORSizeList = cORSizeRepository.findAll();
        assertThat(cORSizeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCORSize() throws Exception {
        // Initialize the database
        cORSizeRepository.saveAndFlush(cORSize);
        int databaseSizeBeforeDelete = cORSizeRepository.findAll().size();

        // Get the cORSize
        restCORSizeMockMvc.perform(delete("/api/c-or-sizes/{id}", cORSize.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CORSize> cORSizeList = cORSizeRepository.findAll();
        assertThat(cORSizeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
