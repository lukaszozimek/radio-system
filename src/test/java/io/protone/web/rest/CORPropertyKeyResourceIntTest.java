package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CORPropertyKey;
import io.protone.repository.CORPropertyKeyRepository;
import io.protone.service.dto.CORPropertyKeyDTO;
import io.protone.service.mapper.CORPropertyKeyMapper;

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
 * Test class for the CORPropertyKeyResource REST controller.
 *
 * @see CORPropertyKeyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CORPropertyKeyResourceIntTest {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    @Inject
    private CORPropertyKeyRepository cORPropertyKeyRepository;

    @Inject
    private CORPropertyKeyMapper cORPropertyKeyMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCORPropertyKeyMockMvc;

    private CORPropertyKey cORPropertyKey;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CORPropertyKeyResource cORPropertyKeyResource = new CORPropertyKeyResource();
        ReflectionTestUtils.setField(cORPropertyKeyResource, "cORPropertyKeyRepository", cORPropertyKeyRepository);
        ReflectionTestUtils.setField(cORPropertyKeyResource, "cORPropertyKeyMapper", cORPropertyKeyMapper);
        this.restCORPropertyKeyMockMvc = MockMvcBuilders.standaloneSetup(cORPropertyKeyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CORPropertyKey createEntity(EntityManager em) {
        CORPropertyKey cORPropertyKey = new CORPropertyKey()
                .key(DEFAULT_KEY);
        return cORPropertyKey;
    }

    @Before
    public void initTest() {
        cORPropertyKey = createEntity(em);
    }

    @Test
    @Transactional
    public void createCORPropertyKey() throws Exception {
        int databaseSizeBeforeCreate = cORPropertyKeyRepository.findAll().size();

        // Create the CORPropertyKey
        CORPropertyKeyDTO cORPropertyKeyDTO = cORPropertyKeyMapper.cORPropertyKeyToCORPropertyKeyDTO(cORPropertyKey);

        restCORPropertyKeyMockMvc.perform(post("/api/c-or-property-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORPropertyKeyDTO)))
            .andExpect(status().isCreated());

        // Validate the CORPropertyKey in the database
        List<CORPropertyKey> cORPropertyKeyList = cORPropertyKeyRepository.findAll();
        assertThat(cORPropertyKeyList).hasSize(databaseSizeBeforeCreate + 1);
        CORPropertyKey testCORPropertyKey = cORPropertyKeyList.get(cORPropertyKeyList.size() - 1);
        assertThat(testCORPropertyKey.getKey()).isEqualTo(DEFAULT_KEY);
    }

    @Test
    @Transactional
    public void createCORPropertyKeyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cORPropertyKeyRepository.findAll().size();

        // Create the CORPropertyKey with an existing ID
        CORPropertyKey existingCORPropertyKey = new CORPropertyKey();
        existingCORPropertyKey.setId(1L);
        CORPropertyKeyDTO existingCORPropertyKeyDTO = cORPropertyKeyMapper.cORPropertyKeyToCORPropertyKeyDTO(existingCORPropertyKey);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCORPropertyKeyMockMvc.perform(post("/api/c-or-property-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCORPropertyKeyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CORPropertyKey> cORPropertyKeyList = cORPropertyKeyRepository.findAll();
        assertThat(cORPropertyKeyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORPropertyKeyRepository.findAll().size();
        // set the field null
        cORPropertyKey.setKey(null);

        // Create the CORPropertyKey, which fails.
        CORPropertyKeyDTO cORPropertyKeyDTO = cORPropertyKeyMapper.cORPropertyKeyToCORPropertyKeyDTO(cORPropertyKey);

        restCORPropertyKeyMockMvc.perform(post("/api/c-or-property-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORPropertyKeyDTO)))
            .andExpect(status().isBadRequest());

        List<CORPropertyKey> cORPropertyKeyList = cORPropertyKeyRepository.findAll();
        assertThat(cORPropertyKeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCORPropertyKeys() throws Exception {
        // Initialize the database
        cORPropertyKeyRepository.saveAndFlush(cORPropertyKey);

        // Get all the cORPropertyKeyList
        restCORPropertyKeyMockMvc.perform(get("/api/c-or-property-keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cORPropertyKey.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())));
    }

    @Test
    @Transactional
    public void getCORPropertyKey() throws Exception {
        // Initialize the database
        cORPropertyKeyRepository.saveAndFlush(cORPropertyKey);

        // Get the cORPropertyKey
        restCORPropertyKeyMockMvc.perform(get("/api/c-or-property-keys/{id}", cORPropertyKey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cORPropertyKey.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCORPropertyKey() throws Exception {
        // Get the cORPropertyKey
        restCORPropertyKeyMockMvc.perform(get("/api/c-or-property-keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCORPropertyKey() throws Exception {
        // Initialize the database
        cORPropertyKeyRepository.saveAndFlush(cORPropertyKey);
        int databaseSizeBeforeUpdate = cORPropertyKeyRepository.findAll().size();

        // Update the cORPropertyKey
        CORPropertyKey updatedCORPropertyKey = cORPropertyKeyRepository.findOne(cORPropertyKey.getId());
        updatedCORPropertyKey
                .key(UPDATED_KEY);
        CORPropertyKeyDTO cORPropertyKeyDTO = cORPropertyKeyMapper.cORPropertyKeyToCORPropertyKeyDTO(updatedCORPropertyKey);

        restCORPropertyKeyMockMvc.perform(put("/api/c-or-property-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORPropertyKeyDTO)))
            .andExpect(status().isOk());

        // Validate the CORPropertyKey in the database
        List<CORPropertyKey> cORPropertyKeyList = cORPropertyKeyRepository.findAll();
        assertThat(cORPropertyKeyList).hasSize(databaseSizeBeforeUpdate);
        CORPropertyKey testCORPropertyKey = cORPropertyKeyList.get(cORPropertyKeyList.size() - 1);
        assertThat(testCORPropertyKey.getKey()).isEqualTo(UPDATED_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingCORPropertyKey() throws Exception {
        int databaseSizeBeforeUpdate = cORPropertyKeyRepository.findAll().size();

        // Create the CORPropertyKey
        CORPropertyKeyDTO cORPropertyKeyDTO = cORPropertyKeyMapper.cORPropertyKeyToCORPropertyKeyDTO(cORPropertyKey);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCORPropertyKeyMockMvc.perform(put("/api/c-or-property-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORPropertyKeyDTO)))
            .andExpect(status().isCreated());

        // Validate the CORPropertyKey in the database
        List<CORPropertyKey> cORPropertyKeyList = cORPropertyKeyRepository.findAll();
        assertThat(cORPropertyKeyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCORPropertyKey() throws Exception {
        // Initialize the database
        cORPropertyKeyRepository.saveAndFlush(cORPropertyKey);
        int databaseSizeBeforeDelete = cORPropertyKeyRepository.findAll().size();

        // Get the cORPropertyKey
        restCORPropertyKeyMockMvc.perform(delete("/api/c-or-property-keys/{id}", cORPropertyKey.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CORPropertyKey> cORPropertyKeyList = cORPropertyKeyRepository.findAll();
        assertThat(cORPropertyKeyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
