package io.protone.web.api.cor;

import io.protone.ProtoneApp;
import io.protone.web.api.cor.impl.CorNetworkResourceImpl;
import io.protone.web.rest.dto.cor.CorNetworkDTO;
import io.protone.custom.web.rest.network.TestUtil;
import io.protone.domain.CorNetwork;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.service.cor.CorNetworkService;
import io.protone.web.rest.mapper.CorNetworkMapper;
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
 * Created by lukaszozimek on 01/03/2017.
 */

/**
 * Test class for the CorNetworkResource REST controller.
 *
 * @see CorNetworkResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorNetworkResourceIntTest {
    public static final String TEST_NETWORK="test";
    private static final String DEFAULT_SHORTCUT = "AAAAAAAAAA";
    private static final String UPDATED_SHORTCUT = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private CorNetworkRepository corNetworkRepository;
    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorNetworkMapper corNetworkMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCorNetworkMockMvc;

    private CorNetwork corNetwork;

    /**
     * Create an entity for this test.
     * <p>
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
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CorNetworkResourceImpl corNetworkResource = new CorNetworkResourceImpl();
        ReflectionTestUtils.setField(corNetworkResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(corNetworkResource, "corNetworkMapper", corNetworkMapper);
        this.restCorNetworkMockMvc = MockMvcBuilders.standaloneSetup(corNetworkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
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
        CorNetworkDTO corNetworkDTO = corNetworkMapper.DB2DTO(corNetwork);

        restCorNetworkMockMvc.perform(post("/api/v1/network")
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
        // Create the CorNetwork
        CorNetworkDTO existingCorNetworkDTO = corNetworkMapper.DB2DTO(existingCorNetwork);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorNetworkMockMvc.perform(post("/api/v1/network")
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

        // Create the CorNetwork
        CorNetworkDTO corNetworkDTO = corNetworkMapper.DB2DTO(corNetwork);

        restCorNetworkMockMvc.perform(post("/api/v1/network")
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

        // Create the CorNetwork
        CorNetworkDTO corNetworkDTO = corNetworkMapper.DB2DTO(corNetwork);

        restCorNetworkMockMvc.perform(post("/api/v1/network")
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
        restCorNetworkMockMvc.perform(get("/api/v1/network?sort=id,desc"))
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
        restCorNetworkMockMvc.perform(get("/api/v1/network/{networkShortcut}", corNetwork.getShortcut()))
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
        restCorNetworkMockMvc.perform(get("/api/v1/network/{networkShortcut}", Long.MAX_VALUE))
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
        // Create the CorNetwork
        CorNetworkDTO corNetworkDTO = corNetworkMapper.DB2DTO(corNetwork);

        restCorNetworkMockMvc.perform(put("/api/v1/network")
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
        CorNetworkDTO corNetworkDTO = corNetworkMapper.DB2DTO(corNetwork);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorNetworkMockMvc.perform(put("/api/v1/network")
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
        restCorNetworkMockMvc.perform(delete("/api/v1/network/{networkShortcut}", corNetwork.getShortcut())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorNetwork> corNetworkList = corNetworkRepository.findAll();
        assertThat(corNetworkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
