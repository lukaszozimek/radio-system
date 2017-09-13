package io.protone.application.web.api.cor;


import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.impl.CorDictionaryResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.core.repository.CorDictionaryRepository;
import io.protone.core.service.CorNetworkService;
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
import org.springframework.test.util.ReflectionTestUtils;
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
 * Created by lukaszozimek on 01.05.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorDictionaryResourceTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_SEQ_NUMBER = 1L;
    private static final Long UPDATED_SEQ_NUMBER = 2L;

    private static final String TEST_MODULE = "crm";

    private static final String TEST_TYPE = "crmStage";
    @Autowired
    private CorDictionaryRepository corDictionaryRepository;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private CorDictionaryMapper corDictionaryMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCorDictionaryMockMvc;

    private CorDictionary corDictionary;

    private CorNetwork corNetwork;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorDictionary createEntity(EntityManager em) {
        CorDictionary corDictionary = new CorDictionary()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .dictionaryType(TEST_TYPE)
            .seqNumber(DEFAULT_SEQ_NUMBER)
            .corModule(TEST_MODULE);
        return corDictionary;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CorDictionaryResourceImpl corDictionaryResource = new CorDictionaryResourceImpl();

        ReflectionTestUtils.setField(corDictionaryResource, "corDictionaryRepository", corDictionaryRepository);
        ReflectionTestUtils.setField(corDictionaryResource, "corDictionaryMapper", corDictionaryMapper);
        ReflectionTestUtils.setField(corDictionaryResource, "corNetworkService", corNetworkService);

        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);

        this.restCorDictionaryMockMvc = MockMvcBuilders.standaloneSetup(corDictionaryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        corDictionary = createEntity(em).network(corNetwork);
    }

    @Test
    @Transactional
    public void createCorDictionary() throws Exception {
        int databaseSizeBeforeCreate = corDictionaryRepository.findAll().size();

        // Create the CorDictionary
        CorDictionaryDTO corDictionaryDTO = corDictionaryMapper.DB2DTO(corDictionary);

        restCorDictionaryMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}", corNetwork.getShortcut(), TEST_MODULE, TEST_TYPE)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corDictionaryDTO)))
            .andExpect(status().isCreated());

        // Validate the CorDictionary in the database
        List<CorDictionary> corDictionaryList = corDictionaryRepository.findAll();
        assertThat(corDictionaryList).hasSize(databaseSizeBeforeCreate + 1);
        CorDictionary testCorDictionary = corDictionaryList.get(corDictionaryList.size() - 1);
        assertThat(testCorDictionary.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCorDictionary.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCorDictionary.getCorDictionaryType()).isEqualTo(TEST_TYPE);
        assertThat(testCorDictionary.getSeqNumber()).isEqualTo(DEFAULT_SEQ_NUMBER);
        assertThat(testCorDictionary.getCorModule()).isEqualTo(TEST_MODULE);
    }

    @Test
    @Transactional
    public void createCorDictionaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corDictionaryRepository.findAll().size();

        // Create the CorDictionary with an existing ID
        CorDictionary existingCorDictionary = new CorDictionary();
        existingCorDictionary.setId(1L);
        CorDictionaryDTO existingCorDictionaryDTO = corDictionaryMapper.DB2DTO(existingCorDictionary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorDictionaryMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}", corNetwork.getShortcut(), TEST_MODULE, TEST_TYPE)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorDictionaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorDictionary> corDictionaryList = corDictionaryRepository.findAll();
        assertThat(corDictionaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorDictionaries() throws Exception {
        // Initialize the database
        corDictionaryRepository.saveAndFlush(corDictionary.network(corNetwork));

        // Get all the corDictionaryList
        restCorDictionaryMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}?sort=id,desc", corNetwork.getShortcut(), TEST_MODULE, TEST_TYPE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corDictionary.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].seqNumber").value(hasItem(DEFAULT_SEQ_NUMBER.intValue())));
    }

    @Test
    @Transactional
    public void getCorDictionary() throws Exception {
        // Initialize the database
        corDictionaryRepository.saveAndFlush(corDictionary.network(corNetwork));

        // Get the corDictionary
        restCorDictionaryMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}/{id}", corNetwork.getShortcut(), TEST_MODULE, TEST_TYPE, corDictionary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corDictionary.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.seqNumber").value(DEFAULT_SEQ_NUMBER.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCorDictionary() throws Exception {
        // Get the corDictionary
        restCorDictionaryMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}/{id}", corNetwork.getShortcut(), TEST_MODULE, TEST_TYPE, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorDictionary() throws Exception {
        // Initialize the database
        corDictionaryRepository.saveAndFlush(corDictionary.network(corNetwork));
        int databaseSizeBeforeUpdate = corDictionaryRepository.findAll().size();

        // Update the corDictionary
        CorDictionary updatedCorDictionary = corDictionaryRepository.findOne(corDictionary.getId());
        updatedCorDictionary
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .seqNumber(UPDATED_SEQ_NUMBER);
        CorDictionaryDTO corDictionaryDTO = corDictionaryMapper.DB2DTO(updatedCorDictionary);

        restCorDictionaryMockMvc.perform(put("/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}", corNetwork.getShortcut(), TEST_MODULE, TEST_TYPE)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corDictionaryDTO)))
            .andExpect(status().isOk());

        // Validate the CorDictionary in the database
        List<CorDictionary> corDictionaryList = corDictionaryRepository.findAll();
        assertThat(corDictionaryList).hasSize(databaseSizeBeforeUpdate);
        CorDictionary testCorDictionary = corDictionaryList.get(corDictionaryList.size() - 1);
        assertThat(testCorDictionary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCorDictionary.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCorDictionary.getCorDictionaryType()).isEqualTo(TEST_TYPE);
        assertThat(testCorDictionary.getSeqNumber()).isEqualTo(UPDATED_SEQ_NUMBER);
        assertThat(testCorDictionary.getCorModule()).isEqualTo(TEST_MODULE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = corDictionaryRepository.findAll().size();
        // set the field null
        corDictionary.setName(null);

        // Create the LibMediaLibrary, which fails.
        CorDictionaryDTO corDictionaryDTO = corDictionaryMapper.DB2DTO(corDictionary);

        restCorDictionaryMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}", corNetwork.getShortcut(), TEST_MODULE, TEST_TYPE)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corDictionaryDTO)))
            .andExpect(status().isBadRequest());

        List<CorDictionary> libLibraryList = corDictionaryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void updateNonExistingCorDictionary() throws Exception {
        int databaseSizeBeforeUpdate = corDictionaryRepository.findAll().size();

        // Create the CorDictionary
        CorDictionaryDTO corDictionaryDTO = corDictionaryMapper.DB2DTO(corDictionary);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorDictionaryMockMvc.perform(put("/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}", corNetwork.getShortcut(), TEST_MODULE, TEST_TYPE)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corDictionaryDTO)))
            .andExpect(status().isCreated());

        // Validate the CorDictionary in the database
        List<CorDictionary> corDictionaryList = corDictionaryRepository.findAll();
        assertThat(corDictionaryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorDictionary() throws Exception {
        // Initialize the database
        corDictionaryRepository.saveAndFlush(corDictionary.network(corNetwork));
        int databaseSizeBeforeDelete = corDictionaryRepository.findAll().size();

        // Get the corDictionary
        restCorDictionaryMockMvc.perform(delete("/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}/{id}", corNetwork.getShortcut(), TEST_MODULE, TEST_TYPE, corDictionary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorDictionary> corDictionaryList = corDictionaryRepository.findAll();
        assertThat(corDictionaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorDictionary.class);
    }
}
