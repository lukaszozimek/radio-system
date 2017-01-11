package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CORArea;
import io.protone.repository.CORAreaRepository;
import io.protone.service.dto.CORAreaDTO;
import io.protone.service.mapper.CORAreaMapper;

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
 * Test class for the CORAreaResource REST controller.
 *
 * @see CORAreaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CORAreaResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private CORAreaRepository cORAreaRepository;

    @Inject
    private CORAreaMapper cORAreaMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCORAreaMockMvc;

    private CORArea cORArea;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CORAreaResource cORAreaResource = new CORAreaResource();
        ReflectionTestUtils.setField(cORAreaResource, "cORAreaRepository", cORAreaRepository);
        ReflectionTestUtils.setField(cORAreaResource, "cORAreaMapper", cORAreaMapper);
        this.restCORAreaMockMvc = MockMvcBuilders.standaloneSetup(cORAreaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CORArea createEntity(EntityManager em) {
        CORArea cORArea = new CORArea()
                .name(DEFAULT_NAME);
        return cORArea;
    }

    @Before
    public void initTest() {
        cORArea = createEntity(em);
    }

    @Test
    @Transactional
    public void createCORArea() throws Exception {
        int databaseSizeBeforeCreate = cORAreaRepository.findAll().size();

        // Create the CORArea
        CORAreaDTO cORAreaDTO = cORAreaMapper.cORAreaToCORAreaDTO(cORArea);

        restCORAreaMockMvc.perform(post("/api/c-or-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORAreaDTO)))
            .andExpect(status().isCreated());

        // Validate the CORArea in the database
        List<CORArea> cORAreaList = cORAreaRepository.findAll();
        assertThat(cORAreaList).hasSize(databaseSizeBeforeCreate + 1);
        CORArea testCORArea = cORAreaList.get(cORAreaList.size() - 1);
        assertThat(testCORArea.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCORAreaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cORAreaRepository.findAll().size();

        // Create the CORArea with an existing ID
        CORArea existingCORArea = new CORArea();
        existingCORArea.setId(1L);
        CORAreaDTO existingCORAreaDTO = cORAreaMapper.cORAreaToCORAreaDTO(existingCORArea);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCORAreaMockMvc.perform(post("/api/c-or-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCORAreaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CORArea> cORAreaList = cORAreaRepository.findAll();
        assertThat(cORAreaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCORAreas() throws Exception {
        // Initialize the database
        cORAreaRepository.saveAndFlush(cORArea);

        // Get all the cORAreaList
        restCORAreaMockMvc.perform(get("/api/c-or-areas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cORArea.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCORArea() throws Exception {
        // Initialize the database
        cORAreaRepository.saveAndFlush(cORArea);

        // Get the cORArea
        restCORAreaMockMvc.perform(get("/api/c-or-areas/{id}", cORArea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cORArea.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCORArea() throws Exception {
        // Get the cORArea
        restCORAreaMockMvc.perform(get("/api/c-or-areas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCORArea() throws Exception {
        // Initialize the database
        cORAreaRepository.saveAndFlush(cORArea);
        int databaseSizeBeforeUpdate = cORAreaRepository.findAll().size();

        // Update the cORArea
        CORArea updatedCORArea = cORAreaRepository.findOne(cORArea.getId());
        updatedCORArea
                .name(UPDATED_NAME);
        CORAreaDTO cORAreaDTO = cORAreaMapper.cORAreaToCORAreaDTO(updatedCORArea);

        restCORAreaMockMvc.perform(put("/api/c-or-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORAreaDTO)))
            .andExpect(status().isOk());

        // Validate the CORArea in the database
        List<CORArea> cORAreaList = cORAreaRepository.findAll();
        assertThat(cORAreaList).hasSize(databaseSizeBeforeUpdate);
        CORArea testCORArea = cORAreaList.get(cORAreaList.size() - 1);
        assertThat(testCORArea.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCORArea() throws Exception {
        int databaseSizeBeforeUpdate = cORAreaRepository.findAll().size();

        // Create the CORArea
        CORAreaDTO cORAreaDTO = cORAreaMapper.cORAreaToCORAreaDTO(cORArea);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCORAreaMockMvc.perform(put("/api/c-or-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORAreaDTO)))
            .andExpect(status().isCreated());

        // Validate the CORArea in the database
        List<CORArea> cORAreaList = cORAreaRepository.findAll();
        assertThat(cORAreaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCORArea() throws Exception {
        // Initialize the database
        cORAreaRepository.saveAndFlush(cORArea);
        int databaseSizeBeforeDelete = cORAreaRepository.findAll().size();

        // Get the cORArea
        restCORAreaMockMvc.perform(delete("/api/c-or-areas/{id}", cORArea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CORArea> cORAreaList = cORAreaRepository.findAll();
        assertThat(cORAreaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
