package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LibLabel;
import io.protone.repository.LibLabelRepository;
import io.protone.service.dto.LibLabelDTO;
import io.protone.service.mapper.LibLabelMapper;

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
 * Test class for the LibLabelResource REST controller.
 *
 * @see LibLabelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibLabelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private LibLabelRepository libLabelRepository;

    @Autowired
    private LibLabelMapper libLabelMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restLibLabelMockMvc;

    private LibLabel libLabel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            LibLabelResource libLabelResource = new LibLabelResource(libLabelRepository, libLabelMapper);
        this.restLibLabelMockMvc = MockMvcBuilders.standaloneSetup(libLabelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibLabel createEntity(EntityManager em) {
        LibLabel libLabel = new LibLabel()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return libLabel;
    }

    @Before
    public void initTest() {
        libLabel = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibLabel() throws Exception {
        int databaseSizeBeforeCreate = libLabelRepository.findAll().size();

        // Create the LibLabel
        LibLabelDTO libLabelDTO = libLabelMapper.libLabelToLibLabelDTO(libLabel);

        restLibLabelMockMvc.perform(post("/api/lib-labels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libLabelDTO)))
            .andExpect(status().isCreated());

        // Validate the LibLabel in the database
        List<LibLabel> libLabelList = libLabelRepository.findAll();
        assertThat(libLabelList).hasSize(databaseSizeBeforeCreate + 1);
        LibLabel testLibLabel = libLabelList.get(libLabelList.size() - 1);
        assertThat(testLibLabel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLibLabel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLibLabelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libLabelRepository.findAll().size();

        // Create the LibLabel with an existing ID
        LibLabel existingLibLabel = new LibLabel();
        existingLibLabel.setId(1L);
        LibLabelDTO existingLibLabelDTO = libLabelMapper.libLabelToLibLabelDTO(existingLibLabel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibLabelMockMvc.perform(post("/api/lib-labels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLibLabelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LibLabel> libLabelList = libLabelRepository.findAll();
        assertThat(libLabelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = libLabelRepository.findAll().size();
        // set the field null
        libLabel.setName(null);

        // Create the LibLabel, which fails.
        LibLabelDTO libLabelDTO = libLabelMapper.libLabelToLibLabelDTO(libLabel);

        restLibLabelMockMvc.perform(post("/api/lib-labels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libLabelDTO)))
            .andExpect(status().isBadRequest());

        List<LibLabel> libLabelList = libLabelRepository.findAll();
        assertThat(libLabelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibLabels() throws Exception {
        // Initialize the database
        libLabelRepository.saveAndFlush(libLabel);

        // Get all the libLabelList
        restLibLabelMockMvc.perform(get("/api/lib-labels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libLabel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLibLabel() throws Exception {
        // Initialize the database
        libLabelRepository.saveAndFlush(libLabel);

        // Get the libLabel
        restLibLabelMockMvc.perform(get("/api/lib-labels/{id}", libLabel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libLabel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLibLabel() throws Exception {
        // Get the libLabel
        restLibLabelMockMvc.perform(get("/api/lib-labels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibLabel() throws Exception {
        // Initialize the database
        libLabelRepository.saveAndFlush(libLabel);
        int databaseSizeBeforeUpdate = libLabelRepository.findAll().size();

        // Update the libLabel
        LibLabel updatedLibLabel = libLabelRepository.findOne(libLabel.getId());
        updatedLibLabel
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        LibLabelDTO libLabelDTO = libLabelMapper.libLabelToLibLabelDTO(updatedLibLabel);

        restLibLabelMockMvc.perform(put("/api/lib-labels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libLabelDTO)))
            .andExpect(status().isOk());

        // Validate the LibLabel in the database
        List<LibLabel> libLabelList = libLabelRepository.findAll();
        assertThat(libLabelList).hasSize(databaseSizeBeforeUpdate);
        LibLabel testLibLabel = libLabelList.get(libLabelList.size() - 1);
        assertThat(testLibLabel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibLabel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLibLabel() throws Exception {
        int databaseSizeBeforeUpdate = libLabelRepository.findAll().size();

        // Create the LibLabel
        LibLabelDTO libLabelDTO = libLabelMapper.libLabelToLibLabelDTO(libLabel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibLabelMockMvc.perform(put("/api/lib-labels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libLabelDTO)))
            .andExpect(status().isCreated());

        // Validate the LibLabel in the database
        List<LibLabel> libLabelList = libLabelRepository.findAll();
        assertThat(libLabelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibLabel() throws Exception {
        // Initialize the database
        libLabelRepository.saveAndFlush(libLabel);
        int databaseSizeBeforeDelete = libLabelRepository.findAll().size();

        // Get the libLabel
        restLibLabelMockMvc.perform(delete("/api/lib-labels/{id}", libLabel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LibLabel> libLabelList = libLabelRepository.findAll();
        assertThat(libLabelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibLabel.class);
    }
}
