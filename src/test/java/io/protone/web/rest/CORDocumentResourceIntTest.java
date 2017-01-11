package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CORDocument;
import io.protone.repository.CORDocumentRepository;
import io.protone.service.dto.CORDocumentDTO;
import io.protone.service.mapper.CORDocumentMapper;

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
import org.springframework.util.Base64Utils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CORDocumentResource REST controller.
 *
 * @see CORDocumentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CORDocumentResourceIntTest {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Inject
    private CORDocumentRepository cORDocumentRepository;

    @Inject
    private CORDocumentMapper cORDocumentMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCORDocumentMockMvc;

    private CORDocument cORDocument;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CORDocumentResource cORDocumentResource = new CORDocumentResource();
        ReflectionTestUtils.setField(cORDocumentResource, "cORDocumentRepository", cORDocumentRepository);
        ReflectionTestUtils.setField(cORDocumentResource, "cORDocumentMapper", cORDocumentMapper);
        this.restCORDocumentMockMvc = MockMvcBuilders.standaloneSetup(cORDocumentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CORDocument createEntity(EntityManager em) {
        CORDocument cORDocument = new CORDocument()
                .key(DEFAULT_KEY)
                .description(DEFAULT_DESCRIPTION)
                .value(DEFAULT_VALUE);
        return cORDocument;
    }

    @Before
    public void initTest() {
        cORDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createCORDocument() throws Exception {
        int databaseSizeBeforeCreate = cORDocumentRepository.findAll().size();

        // Create the CORDocument
        CORDocumentDTO cORDocumentDTO = cORDocumentMapper.cORDocumentToCORDocumentDTO(cORDocument);

        restCORDocumentMockMvc.perform(post("/api/c-or-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the CORDocument in the database
        List<CORDocument> cORDocumentList = cORDocumentRepository.findAll();
        assertThat(cORDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        CORDocument testCORDocument = cORDocumentList.get(cORDocumentList.size() - 1);
        assertThat(testCORDocument.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testCORDocument.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCORDocument.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createCORDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cORDocumentRepository.findAll().size();

        // Create the CORDocument with an existing ID
        CORDocument existingCORDocument = new CORDocument();
        existingCORDocument.setId(1L);
        CORDocumentDTO existingCORDocumentDTO = cORDocumentMapper.cORDocumentToCORDocumentDTO(existingCORDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCORDocumentMockMvc.perform(post("/api/c-or-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCORDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CORDocument> cORDocumentList = cORDocumentRepository.findAll();
        assertThat(cORDocumentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORDocumentRepository.findAll().size();
        // set the field null
        cORDocument.setKey(null);

        // Create the CORDocument, which fails.
        CORDocumentDTO cORDocumentDTO = cORDocumentMapper.cORDocumentToCORDocumentDTO(cORDocument);

        restCORDocumentMockMvc.perform(post("/api/c-or-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<CORDocument> cORDocumentList = cORDocumentRepository.findAll();
        assertThat(cORDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORDocumentRepository.findAll().size();
        // set the field null
        cORDocument.setValue(null);

        // Create the CORDocument, which fails.
        CORDocumentDTO cORDocumentDTO = cORDocumentMapper.cORDocumentToCORDocumentDTO(cORDocument);

        restCORDocumentMockMvc.perform(post("/api/c-or-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<CORDocument> cORDocumentList = cORDocumentRepository.findAll();
        assertThat(cORDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCORDocuments() throws Exception {
        // Initialize the database
        cORDocumentRepository.saveAndFlush(cORDocument);

        // Get all the cORDocumentList
        restCORDocumentMockMvc.perform(get("/api/c-or-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cORDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getCORDocument() throws Exception {
        // Initialize the database
        cORDocumentRepository.saveAndFlush(cORDocument);

        // Get the cORDocument
        restCORDocumentMockMvc.perform(get("/api/c-or-documents/{id}", cORDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cORDocument.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCORDocument() throws Exception {
        // Get the cORDocument
        restCORDocumentMockMvc.perform(get("/api/c-or-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCORDocument() throws Exception {
        // Initialize the database
        cORDocumentRepository.saveAndFlush(cORDocument);
        int databaseSizeBeforeUpdate = cORDocumentRepository.findAll().size();

        // Update the cORDocument
        CORDocument updatedCORDocument = cORDocumentRepository.findOne(cORDocument.getId());
        updatedCORDocument
                .key(UPDATED_KEY)
                .description(UPDATED_DESCRIPTION)
                .value(UPDATED_VALUE);
        CORDocumentDTO cORDocumentDTO = cORDocumentMapper.cORDocumentToCORDocumentDTO(updatedCORDocument);

        restCORDocumentMockMvc.perform(put("/api/c-or-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the CORDocument in the database
        List<CORDocument> cORDocumentList = cORDocumentRepository.findAll();
        assertThat(cORDocumentList).hasSize(databaseSizeBeforeUpdate);
        CORDocument testCORDocument = cORDocumentList.get(cORDocumentList.size() - 1);
        assertThat(testCORDocument.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testCORDocument.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCORDocument.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingCORDocument() throws Exception {
        int databaseSizeBeforeUpdate = cORDocumentRepository.findAll().size();

        // Create the CORDocument
        CORDocumentDTO cORDocumentDTO = cORDocumentMapper.cORDocumentToCORDocumentDTO(cORDocument);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCORDocumentMockMvc.perform(put("/api/c-or-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the CORDocument in the database
        List<CORDocument> cORDocumentList = cORDocumentRepository.findAll();
        assertThat(cORDocumentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCORDocument() throws Exception {
        // Initialize the database
        cORDocumentRepository.saveAndFlush(cORDocument);
        int databaseSizeBeforeDelete = cORDocumentRepository.findAll().size();

        // Get the cORDocument
        restCORDocumentMockMvc.perform(delete("/api/c-or-documents/{id}", cORDocument.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CORDocument> cORDocumentList = cORDocumentRepository.findAll();
        assertThat(cORDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
