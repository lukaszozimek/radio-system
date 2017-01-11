package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CORNetwork;
import io.protone.repository.CORNetworkRepository;
import io.protone.service.dto.CORNetworkDTO;
import io.protone.service.mapper.CORNetworkMapper;

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
 * Test class for the CORNetworkResource REST controller.
 *
 * @see CORNetworkResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CORNetworkResourceIntTest {

    private static final String DEFAULT_SHORTCUT = "AAAAAAAAAA";
    private static final String UPDATED_SHORTCUT = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private CORNetworkRepository cORNetworkRepository;

    @Inject
    private CORNetworkMapper cORNetworkMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCORNetworkMockMvc;

    private CORNetwork cORNetwork;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CORNetworkResource cORNetworkResource = new CORNetworkResource();
        ReflectionTestUtils.setField(cORNetworkResource, "cORNetworkRepository", cORNetworkRepository);
        ReflectionTestUtils.setField(cORNetworkResource, "cORNetworkMapper", cORNetworkMapper);
        this.restCORNetworkMockMvc = MockMvcBuilders.standaloneSetup(cORNetworkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CORNetwork createEntity(EntityManager em) {
        CORNetwork cORNetwork = new CORNetwork()
                .shortcut(DEFAULT_SHORTCUT)
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return cORNetwork;
    }

    @Before
    public void initTest() {
        cORNetwork = createEntity(em);
    }

    @Test
    @Transactional
    public void createCORNetwork() throws Exception {
        int databaseSizeBeforeCreate = cORNetworkRepository.findAll().size();

        // Create the CORNetwork
        CORNetworkDTO cORNetworkDTO = cORNetworkMapper.cORNetworkToCORNetworkDTO(cORNetwork);

        restCORNetworkMockMvc.perform(post("/api/c-or-networks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORNetworkDTO)))
            .andExpect(status().isCreated());

        // Validate the CORNetwork in the database
        List<CORNetwork> cORNetworkList = cORNetworkRepository.findAll();
        assertThat(cORNetworkList).hasSize(databaseSizeBeforeCreate + 1);
        CORNetwork testCORNetwork = cORNetworkList.get(cORNetworkList.size() - 1);
        assertThat(testCORNetwork.getShortcut()).isEqualTo(DEFAULT_SHORTCUT);
        assertThat(testCORNetwork.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCORNetwork.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCORNetworkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cORNetworkRepository.findAll().size();

        // Create the CORNetwork with an existing ID
        CORNetwork existingCORNetwork = new CORNetwork();
        existingCORNetwork.setId(1L);
        CORNetworkDTO existingCORNetworkDTO = cORNetworkMapper.cORNetworkToCORNetworkDTO(existingCORNetwork);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCORNetworkMockMvc.perform(post("/api/c-or-networks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCORNetworkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CORNetwork> cORNetworkList = cORNetworkRepository.findAll();
        assertThat(cORNetworkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkShortcutIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORNetworkRepository.findAll().size();
        // set the field null
        cORNetwork.setShortcut(null);

        // Create the CORNetwork, which fails.
        CORNetworkDTO cORNetworkDTO = cORNetworkMapper.cORNetworkToCORNetworkDTO(cORNetwork);

        restCORNetworkMockMvc.perform(post("/api/c-or-networks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORNetworkDTO)))
            .andExpect(status().isBadRequest());

        List<CORNetwork> cORNetworkList = cORNetworkRepository.findAll();
        assertThat(cORNetworkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORNetworkRepository.findAll().size();
        // set the field null
        cORNetwork.setName(null);

        // Create the CORNetwork, which fails.
        CORNetworkDTO cORNetworkDTO = cORNetworkMapper.cORNetworkToCORNetworkDTO(cORNetwork);

        restCORNetworkMockMvc.perform(post("/api/c-or-networks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORNetworkDTO)))
            .andExpect(status().isBadRequest());

        List<CORNetwork> cORNetworkList = cORNetworkRepository.findAll();
        assertThat(cORNetworkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCORNetworks() throws Exception {
        // Initialize the database
        cORNetworkRepository.saveAndFlush(cORNetwork);

        // Get all the cORNetworkList
        restCORNetworkMockMvc.perform(get("/api/c-or-networks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cORNetwork.getId().intValue())))
            .andExpect(jsonPath("$.[*].shortcut").value(hasItem(DEFAULT_SHORTCUT.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCORNetwork() throws Exception {
        // Initialize the database
        cORNetworkRepository.saveAndFlush(cORNetwork);

        // Get the cORNetwork
        restCORNetworkMockMvc.perform(get("/api/c-or-networks/{id}", cORNetwork.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cORNetwork.getId().intValue()))
            .andExpect(jsonPath("$.shortcut").value(DEFAULT_SHORTCUT.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCORNetwork() throws Exception {
        // Get the cORNetwork
        restCORNetworkMockMvc.perform(get("/api/c-or-networks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCORNetwork() throws Exception {
        // Initialize the database
        cORNetworkRepository.saveAndFlush(cORNetwork);
        int databaseSizeBeforeUpdate = cORNetworkRepository.findAll().size();

        // Update the cORNetwork
        CORNetwork updatedCORNetwork = cORNetworkRepository.findOne(cORNetwork.getId());
        updatedCORNetwork
                .shortcut(UPDATED_SHORTCUT)
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        CORNetworkDTO cORNetworkDTO = cORNetworkMapper.cORNetworkToCORNetworkDTO(updatedCORNetwork);

        restCORNetworkMockMvc.perform(put("/api/c-or-networks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORNetworkDTO)))
            .andExpect(status().isOk());

        // Validate the CORNetwork in the database
        List<CORNetwork> cORNetworkList = cORNetworkRepository.findAll();
        assertThat(cORNetworkList).hasSize(databaseSizeBeforeUpdate);
        CORNetwork testCORNetwork = cORNetworkList.get(cORNetworkList.size() - 1);
        assertThat(testCORNetwork.getShortcut()).isEqualTo(UPDATED_SHORTCUT);
        assertThat(testCORNetwork.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCORNetwork.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCORNetwork() throws Exception {
        int databaseSizeBeforeUpdate = cORNetworkRepository.findAll().size();

        // Create the CORNetwork
        CORNetworkDTO cORNetworkDTO = cORNetworkMapper.cORNetworkToCORNetworkDTO(cORNetwork);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCORNetworkMockMvc.perform(put("/api/c-or-networks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORNetworkDTO)))
            .andExpect(status().isCreated());

        // Validate the CORNetwork in the database
        List<CORNetwork> cORNetworkList = cORNetworkRepository.findAll();
        assertThat(cORNetworkList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCORNetwork() throws Exception {
        // Initialize the database
        cORNetworkRepository.saveAndFlush(cORNetwork);
        int databaseSizeBeforeDelete = cORNetworkRepository.findAll().size();

        // Get the cORNetwork
        restCORNetworkMockMvc.perform(delete("/api/c-or-networks/{id}", cORNetwork.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CORNetwork> cORNetworkList = cORNetworkRepository.findAll();
        assertThat(cORNetworkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
