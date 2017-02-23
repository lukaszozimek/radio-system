package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CorSize;
import io.protone.repository.CorSizeRepository;
import io.protone.service.dto.CorSizeDTO;
import io.protone.service.mapper.CorSizeMapper;

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
 * Test class for the CorSizeResource REST controller.
 *
 * @see CorSizeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorSizeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CorSizeRepository corSizeRepository;

    @Autowired
    private CorSizeMapper corSizeMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCorSizeMockMvc;

    private CorSize corSize;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CorSizeResource corSizeResource = new CorSizeResource(corSizeRepository, corSizeMapper);
        this.restCorSizeMockMvc = MockMvcBuilders.standaloneSetup(corSizeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorSize createEntity(EntityManager em) {
        CorSize corSize = new CorSize()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return corSize;
    }

    @Before
    public void initTest() {
        corSize = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorSize() throws Exception {
        int databaseSizeBeforeCreate = corSizeRepository.findAll().size();

        // Create the CorSize
        CorSizeDTO corSizeDTO = corSizeMapper.corSizeToCorSizeDTO(corSize);

        restCorSizeMockMvc.perform(post("/api/cor-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corSizeDTO)))
            .andExpect(status().isCreated());

        // Validate the CorSize in the database
        List<CorSize> corSizeList = corSizeRepository.findAll();
        assertThat(corSizeList).hasSize(databaseSizeBeforeCreate + 1);
        CorSize testCorSize = corSizeList.get(corSizeList.size() - 1);
        assertThat(testCorSize.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCorSize.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCorSizeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corSizeRepository.findAll().size();

        // Create the CorSize with an existing ID
        CorSize existingCorSize = new CorSize();
        existingCorSize.setId(1L);
        CorSizeDTO existingCorSizeDTO = corSizeMapper.corSizeToCorSizeDTO(existingCorSize);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorSizeMockMvc.perform(post("/api/cor-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorSizeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorSize> corSizeList = corSizeRepository.findAll();
        assertThat(corSizeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorSizes() throws Exception {
        // Initialize the database
        corSizeRepository.saveAndFlush(corSize);

        // Get all the corSizeList
        restCorSizeMockMvc.perform(get("/api/cor-sizes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corSize.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCorSize() throws Exception {
        // Initialize the database
        corSizeRepository.saveAndFlush(corSize);

        // Get the corSize
        restCorSizeMockMvc.perform(get("/api/cor-sizes/{id}", corSize.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corSize.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorSize() throws Exception {
        // Get the corSize
        restCorSizeMockMvc.perform(get("/api/cor-sizes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorSize() throws Exception {
        // Initialize the database
        corSizeRepository.saveAndFlush(corSize);
        int databaseSizeBeforeUpdate = corSizeRepository.findAll().size();

        // Update the corSize
        CorSize updatedCorSize = corSizeRepository.findOne(corSize.getId());
        updatedCorSize
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        CorSizeDTO corSizeDTO = corSizeMapper.corSizeToCorSizeDTO(updatedCorSize);

        restCorSizeMockMvc.perform(put("/api/cor-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corSizeDTO)))
            .andExpect(status().isOk());

        // Validate the CorSize in the database
        List<CorSize> corSizeList = corSizeRepository.findAll();
        assertThat(corSizeList).hasSize(databaseSizeBeforeUpdate);
        CorSize testCorSize = corSizeList.get(corSizeList.size() - 1);
        assertThat(testCorSize.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCorSize.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCorSize() throws Exception {
        int databaseSizeBeforeUpdate = corSizeRepository.findAll().size();

        // Create the CorSize
        CorSizeDTO corSizeDTO = corSizeMapper.corSizeToCorSizeDTO(corSize);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorSizeMockMvc.perform(put("/api/cor-sizes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corSizeDTO)))
            .andExpect(status().isCreated());

        // Validate the CorSize in the database
        List<CorSize> corSizeList = corSizeRepository.findAll();
        assertThat(corSizeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorSize() throws Exception {
        // Initialize the database
        corSizeRepository.saveAndFlush(corSize);
        int databaseSizeBeforeDelete = corSizeRepository.findAll().size();

        // Get the corSize
        restCorSizeMockMvc.perform(delete("/api/cor-sizes/{id}", corSize.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorSize> corSizeList = corSizeRepository.findAll();
        assertThat(corSizeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorSize.class);
    }
}
