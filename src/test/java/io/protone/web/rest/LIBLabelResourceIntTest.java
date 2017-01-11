package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LIBLabel;
import io.protone.repository.LIBLabelRepository;
import io.protone.service.dto.LIBLabelDTO;
import io.protone.service.mapper.LIBLabelMapper;

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
 * Test class for the LIBLabelResource REST controller.
 *
 * @see LIBLabelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LIBLabelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private LIBLabelRepository lIBLabelRepository;

    @Inject
    private LIBLabelMapper lIBLabelMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLIBLabelMockMvc;

    private LIBLabel lIBLabel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LIBLabelResource lIBLabelResource = new LIBLabelResource();
        ReflectionTestUtils.setField(lIBLabelResource, "lIBLabelRepository", lIBLabelRepository);
        ReflectionTestUtils.setField(lIBLabelResource, "lIBLabelMapper", lIBLabelMapper);
        this.restLIBLabelMockMvc = MockMvcBuilders.standaloneSetup(lIBLabelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LIBLabel createEntity(EntityManager em) {
        LIBLabel lIBLabel = new LIBLabel()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return lIBLabel;
    }

    @Before
    public void initTest() {
        lIBLabel = createEntity(em);
    }

    @Test
    @Transactional
    public void createLIBLabel() throws Exception {
        int databaseSizeBeforeCreate = lIBLabelRepository.findAll().size();

        // Create the LIBLabel
        LIBLabelDTO lIBLabelDTO = lIBLabelMapper.lIBLabelToLIBLabelDTO(lIBLabel);

        restLIBLabelMockMvc.perform(post("/api/l-ib-labels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBLabelDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBLabel in the database
        List<LIBLabel> lIBLabelList = lIBLabelRepository.findAll();
        assertThat(lIBLabelList).hasSize(databaseSizeBeforeCreate + 1);
        LIBLabel testLIBLabel = lIBLabelList.get(lIBLabelList.size() - 1);
        assertThat(testLIBLabel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLIBLabel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLIBLabelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lIBLabelRepository.findAll().size();

        // Create the LIBLabel with an existing ID
        LIBLabel existingLIBLabel = new LIBLabel();
        existingLIBLabel.setId(1L);
        LIBLabelDTO existingLIBLabelDTO = lIBLabelMapper.lIBLabelToLIBLabelDTO(existingLIBLabel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLIBLabelMockMvc.perform(post("/api/l-ib-labels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLIBLabelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LIBLabel> lIBLabelList = lIBLabelRepository.findAll();
        assertThat(lIBLabelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBLabelRepository.findAll().size();
        // set the field null
        lIBLabel.setName(null);

        // Create the LIBLabel, which fails.
        LIBLabelDTO lIBLabelDTO = lIBLabelMapper.lIBLabelToLIBLabelDTO(lIBLabel);

        restLIBLabelMockMvc.perform(post("/api/l-ib-labels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBLabelDTO)))
            .andExpect(status().isBadRequest());

        List<LIBLabel> lIBLabelList = lIBLabelRepository.findAll();
        assertThat(lIBLabelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLIBLabels() throws Exception {
        // Initialize the database
        lIBLabelRepository.saveAndFlush(lIBLabel);

        // Get all the lIBLabelList
        restLIBLabelMockMvc.perform(get("/api/l-ib-labels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lIBLabel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLIBLabel() throws Exception {
        // Initialize the database
        lIBLabelRepository.saveAndFlush(lIBLabel);

        // Get the lIBLabel
        restLIBLabelMockMvc.perform(get("/api/l-ib-labels/{id}", lIBLabel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lIBLabel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLIBLabel() throws Exception {
        // Get the lIBLabel
        restLIBLabelMockMvc.perform(get("/api/l-ib-labels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLIBLabel() throws Exception {
        // Initialize the database
        lIBLabelRepository.saveAndFlush(lIBLabel);
        int databaseSizeBeforeUpdate = lIBLabelRepository.findAll().size();

        // Update the lIBLabel
        LIBLabel updatedLIBLabel = lIBLabelRepository.findOne(lIBLabel.getId());
        updatedLIBLabel
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        LIBLabelDTO lIBLabelDTO = lIBLabelMapper.lIBLabelToLIBLabelDTO(updatedLIBLabel);

        restLIBLabelMockMvc.perform(put("/api/l-ib-labels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBLabelDTO)))
            .andExpect(status().isOk());

        // Validate the LIBLabel in the database
        List<LIBLabel> lIBLabelList = lIBLabelRepository.findAll();
        assertThat(lIBLabelList).hasSize(databaseSizeBeforeUpdate);
        LIBLabel testLIBLabel = lIBLabelList.get(lIBLabelList.size() - 1);
        assertThat(testLIBLabel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLIBLabel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLIBLabel() throws Exception {
        int databaseSizeBeforeUpdate = lIBLabelRepository.findAll().size();

        // Create the LIBLabel
        LIBLabelDTO lIBLabelDTO = lIBLabelMapper.lIBLabelToLIBLabelDTO(lIBLabel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLIBLabelMockMvc.perform(put("/api/l-ib-labels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBLabelDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBLabel in the database
        List<LIBLabel> lIBLabelList = lIBLabelRepository.findAll();
        assertThat(lIBLabelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLIBLabel() throws Exception {
        // Initialize the database
        lIBLabelRepository.saveAndFlush(lIBLabel);
        int databaseSizeBeforeDelete = lIBLabelRepository.findAll().size();

        // Get the lIBLabel
        restLIBLabelMockMvc.perform(delete("/api/l-ib-labels/{id}", lIBLabel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LIBLabel> lIBLabelList = lIBLabelRepository.findAll();
        assertThat(lIBLabelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
