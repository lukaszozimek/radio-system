package io.protone.web.rest.api.cor;

import io.protone.ProtoneApp;
import io.protone.web.rest.dto.cor.CorKeyDTO;
import io.protone.custom.web.rest.network.TestUtil;
import io.protone.web.rest.api.cor.impl.CorPropertyKeyResourceImpl;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorPropertyKey;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.cor.CorPropertyKeyRepository;
import io.protone.service.cor.CorNetworkService;
import io.protone.web.rest.mapper.CorPropertyKeyMapper;
import org.junit.*;
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

import static io.protone.web.rest.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 01.05.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorPropertyKeyResourceTest {
    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";
    private static final String NETWORK_TEST = "bb";
    @Inject
    private CorPropertyKeyRepository corPropertyKeyRepository;

    @Inject
    private CorPropertyKeyMapper corPropertyKeyMapper;
    @Inject
    private CorNetworkRepository corNetworkRepository;
    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCorPropertyKeyMockMvc;

    private CorPropertyKey corPropertyKey;

    private CorNetwork corNetwork;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorPropertyKey createEntity(EntityManager em) {
        CorPropertyKey corPropertyKey = new CorPropertyKey()
            .key(DEFAULT_KEY);
        return corPropertyKey;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);
        CorPropertyKeyResourceImpl corPropertyKeyResource = new CorPropertyKeyResourceImpl();
        ReflectionTestUtils.setField(corPropertyKeyResource, "corPropertyKeyRepository", corPropertyKeyRepository);
        ReflectionTestUtils.setField(corPropertyKeyResource, "corPropertyKeyMapper", corPropertyKeyMapper);
        ReflectionTestUtils.setField(corPropertyKeyResource, "corNetworkService", corNetworkService);
        this.restCorPropertyKeyMockMvc = MockMvcBuilders.standaloneSetup(corPropertyKeyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        corPropertyKey = createEntity(em).network(corNetwork);
    }


    @Test
    @Transactional
    public void createCorPropertyKey() throws Exception {
        int databaseSizeBeforeCreate = corPropertyKeyRepository.findAll().size();

        // Create the CorPropertyKey
        CorKeyDTO corPropertyKeyDTO = corPropertyKeyMapper.DB2DTO(corPropertyKey);

        restCorPropertyKeyMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/key/", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corPropertyKeyDTO)))
            .andExpect(status().isCreated());

        // Validate the CorPropertyKey in the database
        List<CorPropertyKey> corPropertyKeyList = corPropertyKeyRepository.findAll();
        assertThat(corPropertyKeyList).hasSize(databaseSizeBeforeCreate + 1);
        CorPropertyKey testCorPropertyKey = corPropertyKeyList.get(corPropertyKeyList.size() - 1);
        assertThat(testCorPropertyKey.getKey()).isEqualTo(DEFAULT_KEY);
    }

    @Test
    @Transactional
    public void createCorPropertyKeyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corPropertyKeyRepository.findAll().size();

        // Create the CorPropertyKey with an existing ID
        CorPropertyKey existingCorPropertyKey = new CorPropertyKey();
        existingCorPropertyKey.setId(1L);
        CorKeyDTO existingCorPropertyKeyDTO = corPropertyKeyMapper.DB2DTO(existingCorPropertyKey);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorPropertyKeyMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/key/", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorPropertyKeyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorPropertyKey> corPropertyKeyList = corPropertyKeyRepository.findAll();
        assertThat(corPropertyKeyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = corPropertyKeyRepository.findAll().size();
        // set the field null
        corPropertyKey.setKey(null);

        // Create the CorPropertyKey, which fails.
        CorKeyDTO corPropertyKeyDTO = corPropertyKeyMapper.DB2DTO(corPropertyKey);

        restCorPropertyKeyMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/key/", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corPropertyKeyDTO)))
            .andExpect(status().isBadRequest());

        List<CorPropertyKey> corPropertyKeyList = corPropertyKeyRepository.findAll();
        assertThat(corPropertyKeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorPropertyKeys() throws Exception {
        // Initialize the database
        CorPropertyKey propertyKey =   corPropertyKeyRepository.saveAndFlush(corPropertyKey.network(corNetwork));

        // Get all the corPropertyKeyList
        restCorPropertyKeyMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/key?sort=id,desc", corNetwork.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propertyKey.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())));
    }

    @Test
    @Transactional
    public void getCorPropertyKey() throws Exception {
        // Initialize the database
        corPropertyKeyRepository.saveAndFlush(corPropertyKey.network(corNetwork));

        // Get the corPropertyKey
        restCorPropertyKeyMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/key/{id}", corNetwork.getShortcut(), corPropertyKey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corPropertyKey.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorPropertyKey() throws Exception {
        // Get the corPropertyKey
        restCorPropertyKeyMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/key/{id}", corNetwork.getShortcut(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorPropertyKey() throws Exception {
        // Initialize the database
        corPropertyKeyRepository.saveAndFlush(corPropertyKey);
        int databaseSizeBeforeUpdate = corPropertyKeyRepository.findAll().size();

        // Update the corPropertyKey
        CorPropertyKey updatedCorPropertyKey = corPropertyKeyRepository.findOne(corPropertyKey.getId());
        updatedCorPropertyKey
            .key(UPDATED_KEY);
        CorKeyDTO corPropertyKeyDTO = corPropertyKeyMapper.DB2DTO(updatedCorPropertyKey);

        restCorPropertyKeyMockMvc.perform(put("/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/key", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corPropertyKeyDTO)))
            .andExpect(status().isOk());

        // Validate the CorPropertyKey in the database
        List<CorPropertyKey> corPropertyKeyList = corPropertyKeyRepository.findAll();
        assertThat(corPropertyKeyList).hasSize(databaseSizeBeforeUpdate);
        CorPropertyKey testCorPropertyKey = corPropertyKeyList.get(corPropertyKeyList.size() - 1);
        assertThat(testCorPropertyKey.getKey()).isEqualTo(UPDATED_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingCorPropertyKey() throws Exception {
        int databaseSizeBeforeUpdate = corPropertyKeyRepository.findAll().size();

        // Create the CorPropertyKey
        CorKeyDTO corPropertyKeyDTO = corPropertyKeyMapper.DB2DTO(corPropertyKey);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorPropertyKeyMockMvc.perform(put("/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/key", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corPropertyKeyDTO)))
            .andExpect(status().isCreated());

        // Validate the CorPropertyKey in the database
        List<CorPropertyKey> corPropertyKeyList = corPropertyKeyRepository.findAll();
        assertThat(corPropertyKeyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorPropertyKey() throws Exception {
        // Initialize the database
        corPropertyKeyRepository.saveAndFlush(corPropertyKey.network(corNetwork));
        int databaseSizeBeforeDelete = corPropertyKeyRepository.findAll().size();

        // Get the corPropertyKey
        restCorPropertyKeyMockMvc.perform(delete("/api/v1/network/{networkShortcut}/configuration/network/dictionary/property/key/{id}", corNetwork.getShortcut(), corPropertyKey.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorPropertyKey> corPropertyKeyList = corPropertyKeyRepository.findAll();
        assertThat(corPropertyKeyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
