package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CorNetwork;
import io.protone.repository.CorNetworkRepository;
import io.protone.service.dto.CorNetworkDTO;
import io.protone.service.mapper.CorNetworkMapper;

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
 * Test class for the CorNetworkResource REST controller.
 *
 * @see CorNetworkResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorNetworkResourceIntTest {

    private static final String DEFAULT_SHORTCUT = "AAAAAAAAAA";
    private static final String UPDATED_SHORTCUT = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private CorNetworkMapper corNetworkMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCorNetworkMockMvc;

    private CorNetwork corNetwork;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CorNetworkResource corNetworkResource = new CorNetworkResource(corNetworkRepository, corNetworkMapper);
        this.restCorNetworkMockMvc = MockMvcBuilders.standaloneSetup(corNetworkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorNetwork createEntity(EntityManager em) {
        CorNetwork corNetwork = new CorNetwork()
                .shortcut(DEFAULT_SHORTCUT)
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return corNetwork;
    }

    @Before
    public void initTest() {
        corNetwork = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorNetwork() throws Exception {
        int databaseSizeBeforeCreate = corNetworkRepository.findAll().size();

        // Create the CorNetwork
        CorNetworkDTO corNetworkDTO = corNetworkMapper.corNetworkToCorNetworkDTO(corNetwork);

        restCorNetworkMockMvc.perform(post("/api/cor-networks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corNetworkDTO)))
            .andExpect(status().isCreated());

        // Validate the CorNetwork in the database
        List<CorNetwork> corNetworkList = corNetworkRepository.findAll();
        assertThat(corNetworkList).hasSize(databaseSizeBeforeCreate + 1);
        CorNetwork testCorNetwork = corNetworkList.get(corNetworkList.size() - 1);
        assertThat(testCorNetwork.getShortcut()).isEqualTo(DEFAULT_SHORTCUT);
        assertThat(testCorNetwork.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCorNetwork.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCorNetworkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corNetworkRepository.findAll().size();

        // Create the CorNetwork with an existing ID
        CorNetwork existingCorNetwork = new CorNetwork();
        existingCorNetwork.setId(1L);
        CorNetworkDTO existingCorNetworkDTO = corNetworkMapper.corNetworkToCorNetworkDTO(existingCorNetwork);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorNetworkMockMvc.perform(post("/api/cor-networks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorNetworkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorNetwork> corNetworkList = corNetworkRepository.findAll();
        assertThat(corNetworkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkShortcutIsRequired() throws Exception {
        int databaseSizeBeforeTest = corNetworkRepository.findAll().size();
        // set the field null
        corNetwork.setShortcut(null);

        // Create the CorNetwork, which fails.
        CorNetworkDTO corNetworkDTO = corNetworkMapper.corNetworkToCorNetworkDTO(corNetwork);

        restCorNetworkMockMvc.perform(post("/api/cor-networks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corNetworkDTO)))
            .andExpect(status().isBadRequest());

        List<CorNetwork> corNetworkList = corNetworkRepository.findAll();
        assertThat(corNetworkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = corNetworkRepository.findAll().size();
        // set the field null
        corNetwork.setName(null);

        // Create the CorNetwork, which fails.
        CorNetworkDTO corNetworkDTO = corNetworkMapper.corNetworkToCorNetworkDTO(corNetwork);

        restCorNetworkMockMvc.perform(post("/api/cor-networks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corNetworkDTO)))
            .andExpect(status().isBadRequest());

        List<CorNetwork> corNetworkList = corNetworkRepository.findAll();
        assertThat(corNetworkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorNetworks() throws Exception {
        // Initialize the database
        corNetworkRepository.saveAndFlush(corNetwork);

        // Get all the corNetworkList
        restCorNetworkMockMvc.perform(get("/api/cor-networks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corNetwork.getId().intValue())))
            .andExpect(jsonPath("$.[*].shortcut").value(hasItem(DEFAULT_SHORTCUT.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCorNetwork() throws Exception {
        // Initialize the database
        corNetworkRepository.saveAndFlush(corNetwork);

        // Get the corNetwork
        restCorNetworkMockMvc.perform(get("/api/cor-networks/{id}", corNetwork.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corNetwork.getId().intValue()))
            .andExpect(jsonPath("$.shortcut").value(DEFAULT_SHORTCUT.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorNetwork() throws Exception {
        // Get the corNetwork
        restCorNetworkMockMvc.perform(get("/api/cor-networks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorNetwork() throws Exception {
        // Initialize the database
        corNetworkRepository.saveAndFlush(corNetwork);
        int databaseSizeBeforeUpdate = corNetworkRepository.findAll().size();

        // Update the corNetwork
        CorNetwork updatedCorNetwork = corNetworkRepository.findOne(corNetwork.getId());
        updatedCorNetwork
                .shortcut(UPDATED_SHORTCUT)
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        CorNetworkDTO corNetworkDTO = corNetworkMapper.corNetworkToCorNetworkDTO(updatedCorNetwork);

        restCorNetworkMockMvc.perform(put("/api/cor-networks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corNetworkDTO)))
            .andExpect(status().isOk());

        // Validate the CorNetwork in the database
        List<CorNetwork> corNetworkList = corNetworkRepository.findAll();
        assertThat(corNetworkList).hasSize(databaseSizeBeforeUpdate);
        CorNetwork testCorNetwork = corNetworkList.get(corNetworkList.size() - 1);
        assertThat(testCorNetwork.getShortcut()).isEqualTo(UPDATED_SHORTCUT);
        assertThat(testCorNetwork.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCorNetwork.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCorNetwork() throws Exception {
        int databaseSizeBeforeUpdate = corNetworkRepository.findAll().size();

        // Create the CorNetwork
        CorNetworkDTO corNetworkDTO = corNetworkMapper.corNetworkToCorNetworkDTO(corNetwork);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorNetworkMockMvc.perform(put("/api/cor-networks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corNetworkDTO)))
            .andExpect(status().isCreated());

        // Validate the CorNetwork in the database
        List<CorNetwork> corNetworkList = corNetworkRepository.findAll();
        assertThat(corNetworkList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorNetwork() throws Exception {
        // Initialize the database
        corNetworkRepository.saveAndFlush(corNetwork);
        int databaseSizeBeforeDelete = corNetworkRepository.findAll().size();

        // Get the corNetwork
        restCorNetworkMockMvc.perform(delete("/api/cor-networks/{id}", corNetwork.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorNetwork> corNetworkList = corNetworkRepository.findAll();
        assertThat(corNetworkList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorNetwork.class);
    }
}
