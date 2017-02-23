package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CorArea;
import io.protone.repository.CorAreaRepository;
import io.protone.service.dto.CorAreaDTO;
import io.protone.service.mapper.CorAreaMapper;

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
 * Test class for the CorAreaResource REST controller.
 *
 * @see CorAreaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorAreaResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CorAreaRepository corAreaRepository;

    @Autowired
    private CorAreaMapper corAreaMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCorAreaMockMvc;

    private CorArea corArea;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CorAreaResource corAreaResource = new CorAreaResource(corAreaRepository, corAreaMapper);
        this.restCorAreaMockMvc = MockMvcBuilders.standaloneSetup(corAreaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorArea createEntity(EntityManager em) {
        CorArea corArea = new CorArea()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return corArea;
    }

    @Before
    public void initTest() {
        corArea = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorArea() throws Exception {
        int databaseSizeBeforeCreate = corAreaRepository.findAll().size();

        // Create the CorArea
        CorAreaDTO corAreaDTO = corAreaMapper.corAreaToCorAreaDTO(corArea);

        restCorAreaMockMvc.perform(post("/api/cor-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corAreaDTO)))
            .andExpect(status().isCreated());

        // Validate the CorArea in the database
        List<CorArea> corAreaList = corAreaRepository.findAll();
        assertThat(corAreaList).hasSize(databaseSizeBeforeCreate + 1);
        CorArea testCorArea = corAreaList.get(corAreaList.size() - 1);
        assertThat(testCorArea.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCorArea.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCorAreaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corAreaRepository.findAll().size();

        // Create the CorArea with an existing ID
        CorArea existingCorArea = new CorArea();
        existingCorArea.setId(1L);
        CorAreaDTO existingCorAreaDTO = corAreaMapper.corAreaToCorAreaDTO(existingCorArea);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorAreaMockMvc.perform(post("/api/cor-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorAreaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorArea> corAreaList = corAreaRepository.findAll();
        assertThat(corAreaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorAreas() throws Exception {
        // Initialize the database
        corAreaRepository.saveAndFlush(corArea);

        // Get all the corAreaList
        restCorAreaMockMvc.perform(get("/api/cor-areas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corArea.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCorArea() throws Exception {
        // Initialize the database
        corAreaRepository.saveAndFlush(corArea);

        // Get the corArea
        restCorAreaMockMvc.perform(get("/api/cor-areas/{id}", corArea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corArea.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorArea() throws Exception {
        // Get the corArea
        restCorAreaMockMvc.perform(get("/api/cor-areas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorArea() throws Exception {
        // Initialize the database
        corAreaRepository.saveAndFlush(corArea);
        int databaseSizeBeforeUpdate = corAreaRepository.findAll().size();

        // Update the corArea
        CorArea updatedCorArea = corAreaRepository.findOne(corArea.getId());
        updatedCorArea
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        CorAreaDTO corAreaDTO = corAreaMapper.corAreaToCorAreaDTO(updatedCorArea);

        restCorAreaMockMvc.perform(put("/api/cor-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corAreaDTO)))
            .andExpect(status().isOk());

        // Validate the CorArea in the database
        List<CorArea> corAreaList = corAreaRepository.findAll();
        assertThat(corAreaList).hasSize(databaseSizeBeforeUpdate);
        CorArea testCorArea = corAreaList.get(corAreaList.size() - 1);
        assertThat(testCorArea.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCorArea.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCorArea() throws Exception {
        int databaseSizeBeforeUpdate = corAreaRepository.findAll().size();

        // Create the CorArea
        CorAreaDTO corAreaDTO = corAreaMapper.corAreaToCorAreaDTO(corArea);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorAreaMockMvc.perform(put("/api/cor-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corAreaDTO)))
            .andExpect(status().isCreated());

        // Validate the CorArea in the database
        List<CorArea> corAreaList = corAreaRepository.findAll();
        assertThat(corAreaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorArea() throws Exception {
        // Initialize the database
        corAreaRepository.saveAndFlush(corArea);
        int databaseSizeBeforeDelete = corAreaRepository.findAll().size();

        // Get the corArea
        restCorAreaMockMvc.perform(delete("/api/cor-areas/{id}", corArea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorArea> corAreaList = corAreaRepository.findAll();
        assertThat(corAreaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorArea.class);
    }
}
