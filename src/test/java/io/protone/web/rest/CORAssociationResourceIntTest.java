package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CORAssociation;
import io.protone.repository.CORAssociationRepository;
import io.protone.service.dto.CORAssociationDTO;
import io.protone.service.mapper.CORAssociationMapper;

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
 * Test class for the CORAssociationResource REST controller.
 *
 * @see CORAssociationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CORAssociationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_CLASS = "BBBBBBBBBB";

    private static final Long DEFAULT_SOURCE_ID = 1L;
    private static final Long UPDATED_SOURCE_ID = 2L;

    private static final String DEFAULT_TARGET_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_CLASS = "BBBBBBBBBB";

    private static final Long DEFAULT_TARGET_ID = 1L;
    private static final Long UPDATED_TARGET_ID = 2L;

    @Inject
    private CORAssociationRepository cORAssociationRepository;

    @Inject
    private CORAssociationMapper cORAssociationMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCORAssociationMockMvc;

    private CORAssociation cORAssociation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CORAssociationResource cORAssociationResource = new CORAssociationResource();
        ReflectionTestUtils.setField(cORAssociationResource, "cORAssociationRepository", cORAssociationRepository);
        ReflectionTestUtils.setField(cORAssociationResource, "cORAssociationMapper", cORAssociationMapper);
        this.restCORAssociationMockMvc = MockMvcBuilders.standaloneSetup(cORAssociationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CORAssociation createEntity(EntityManager em) {
        CORAssociation cORAssociation = new CORAssociation()
                .name(DEFAULT_NAME)
                .sourceClass(DEFAULT_SOURCE_CLASS)
                .sourceId(DEFAULT_SOURCE_ID)
                .targetClass(DEFAULT_TARGET_CLASS)
                .targetId(DEFAULT_TARGET_ID);
        return cORAssociation;
    }

    @Before
    public void initTest() {
        cORAssociation = createEntity(em);
    }

    @Test
    @Transactional
    public void createCORAssociation() throws Exception {
        int databaseSizeBeforeCreate = cORAssociationRepository.findAll().size();

        // Create the CORAssociation
        CORAssociationDTO cORAssociationDTO = cORAssociationMapper.cORAssociationToCORAssociationDTO(cORAssociation);

        restCORAssociationMockMvc.perform(post("/api/c-or-associations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORAssociationDTO)))
            .andExpect(status().isCreated());

        // Validate the CORAssociation in the database
        List<CORAssociation> cORAssociationList = cORAssociationRepository.findAll();
        assertThat(cORAssociationList).hasSize(databaseSizeBeforeCreate + 1);
        CORAssociation testCORAssociation = cORAssociationList.get(cORAssociationList.size() - 1);
        assertThat(testCORAssociation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCORAssociation.getSourceClass()).isEqualTo(DEFAULT_SOURCE_CLASS);
        assertThat(testCORAssociation.getSourceId()).isEqualTo(DEFAULT_SOURCE_ID);
        assertThat(testCORAssociation.getTargetClass()).isEqualTo(DEFAULT_TARGET_CLASS);
        assertThat(testCORAssociation.getTargetId()).isEqualTo(DEFAULT_TARGET_ID);
    }

    @Test
    @Transactional
    public void createCORAssociationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cORAssociationRepository.findAll().size();

        // Create the CORAssociation with an existing ID
        CORAssociation existingCORAssociation = new CORAssociation();
        existingCORAssociation.setId(1L);
        CORAssociationDTO existingCORAssociationDTO = cORAssociationMapper.cORAssociationToCORAssociationDTO(existingCORAssociation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCORAssociationMockMvc.perform(post("/api/c-or-associations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCORAssociationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CORAssociation> cORAssociationList = cORAssociationRepository.findAll();
        assertThat(cORAssociationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSourceClassIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORAssociationRepository.findAll().size();
        // set the field null
        cORAssociation.setSourceClass(null);

        // Create the CORAssociation, which fails.
        CORAssociationDTO cORAssociationDTO = cORAssociationMapper.cORAssociationToCORAssociationDTO(cORAssociation);

        restCORAssociationMockMvc.perform(post("/api/c-or-associations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORAssociationDTO)))
            .andExpect(status().isBadRequest());

        List<CORAssociation> cORAssociationList = cORAssociationRepository.findAll();
        assertThat(cORAssociationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSourceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORAssociationRepository.findAll().size();
        // set the field null
        cORAssociation.setSourceId(null);

        // Create the CORAssociation, which fails.
        CORAssociationDTO cORAssociationDTO = cORAssociationMapper.cORAssociationToCORAssociationDTO(cORAssociation);

        restCORAssociationMockMvc.perform(post("/api/c-or-associations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORAssociationDTO)))
            .andExpect(status().isBadRequest());

        List<CORAssociation> cORAssociationList = cORAssociationRepository.findAll();
        assertThat(cORAssociationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTargetClassIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORAssociationRepository.findAll().size();
        // set the field null
        cORAssociation.setTargetClass(null);

        // Create the CORAssociation, which fails.
        CORAssociationDTO cORAssociationDTO = cORAssociationMapper.cORAssociationToCORAssociationDTO(cORAssociation);

        restCORAssociationMockMvc.perform(post("/api/c-or-associations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORAssociationDTO)))
            .andExpect(status().isBadRequest());

        List<CORAssociation> cORAssociationList = cORAssociationRepository.findAll();
        assertThat(cORAssociationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTargetIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORAssociationRepository.findAll().size();
        // set the field null
        cORAssociation.setTargetId(null);

        // Create the CORAssociation, which fails.
        CORAssociationDTO cORAssociationDTO = cORAssociationMapper.cORAssociationToCORAssociationDTO(cORAssociation);

        restCORAssociationMockMvc.perform(post("/api/c-or-associations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORAssociationDTO)))
            .andExpect(status().isBadRequest());

        List<CORAssociation> cORAssociationList = cORAssociationRepository.findAll();
        assertThat(cORAssociationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCORAssociations() throws Exception {
        // Initialize the database
        cORAssociationRepository.saveAndFlush(cORAssociation);

        // Get all the cORAssociationList
        restCORAssociationMockMvc.perform(get("/api/c-or-associations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cORAssociation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].sourceClass").value(hasItem(DEFAULT_SOURCE_CLASS.toString())))
            .andExpect(jsonPath("$.[*].sourceId").value(hasItem(DEFAULT_SOURCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].targetClass").value(hasItem(DEFAULT_TARGET_CLASS.toString())))
            .andExpect(jsonPath("$.[*].targetId").value(hasItem(DEFAULT_TARGET_ID.intValue())));
    }

    @Test
    @Transactional
    public void getCORAssociation() throws Exception {
        // Initialize the database
        cORAssociationRepository.saveAndFlush(cORAssociation);

        // Get the cORAssociation
        restCORAssociationMockMvc.perform(get("/api/c-or-associations/{id}", cORAssociation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cORAssociation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.sourceClass").value(DEFAULT_SOURCE_CLASS.toString()))
            .andExpect(jsonPath("$.sourceId").value(DEFAULT_SOURCE_ID.intValue()))
            .andExpect(jsonPath("$.targetClass").value(DEFAULT_TARGET_CLASS.toString()))
            .andExpect(jsonPath("$.targetId").value(DEFAULT_TARGET_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCORAssociation() throws Exception {
        // Get the cORAssociation
        restCORAssociationMockMvc.perform(get("/api/c-or-associations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCORAssociation() throws Exception {
        // Initialize the database
        cORAssociationRepository.saveAndFlush(cORAssociation);
        int databaseSizeBeforeUpdate = cORAssociationRepository.findAll().size();

        // Update the cORAssociation
        CORAssociation updatedCORAssociation = cORAssociationRepository.findOne(cORAssociation.getId());
        updatedCORAssociation
                .name(UPDATED_NAME)
                .sourceClass(UPDATED_SOURCE_CLASS)
                .sourceId(UPDATED_SOURCE_ID)
                .targetClass(UPDATED_TARGET_CLASS)
                .targetId(UPDATED_TARGET_ID);
        CORAssociationDTO cORAssociationDTO = cORAssociationMapper.cORAssociationToCORAssociationDTO(updatedCORAssociation);

        restCORAssociationMockMvc.perform(put("/api/c-or-associations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORAssociationDTO)))
            .andExpect(status().isOk());

        // Validate the CORAssociation in the database
        List<CORAssociation> cORAssociationList = cORAssociationRepository.findAll();
        assertThat(cORAssociationList).hasSize(databaseSizeBeforeUpdate);
        CORAssociation testCORAssociation = cORAssociationList.get(cORAssociationList.size() - 1);
        assertThat(testCORAssociation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCORAssociation.getSourceClass()).isEqualTo(UPDATED_SOURCE_CLASS);
        assertThat(testCORAssociation.getSourceId()).isEqualTo(UPDATED_SOURCE_ID);
        assertThat(testCORAssociation.getTargetClass()).isEqualTo(UPDATED_TARGET_CLASS);
        assertThat(testCORAssociation.getTargetId()).isEqualTo(UPDATED_TARGET_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingCORAssociation() throws Exception {
        int databaseSizeBeforeUpdate = cORAssociationRepository.findAll().size();

        // Create the CORAssociation
        CORAssociationDTO cORAssociationDTO = cORAssociationMapper.cORAssociationToCORAssociationDTO(cORAssociation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCORAssociationMockMvc.perform(put("/api/c-or-associations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORAssociationDTO)))
            .andExpect(status().isCreated());

        // Validate the CORAssociation in the database
        List<CORAssociation> cORAssociationList = cORAssociationRepository.findAll();
        assertThat(cORAssociationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCORAssociation() throws Exception {
        // Initialize the database
        cORAssociationRepository.saveAndFlush(cORAssociation);
        int databaseSizeBeforeDelete = cORAssociationRepository.findAll().size();

        // Get the cORAssociation
        restCORAssociationMockMvc.perform(delete("/api/c-or-associations/{id}", cORAssociation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CORAssociation> cORAssociationList = cORAssociationRepository.findAll();
        assertThat(cORAssociationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
