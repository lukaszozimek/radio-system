package io.protone.custom.web.rest.network.configuration.core.dictionary.impl;

import io.protone.ProtoneApp;
import io.protone.custom.web.rest.network.TestUtil;
import io.protone.domain.CorDictionary;
import io.protone.domain.CorNetwork;
import io.protone.repository.cor.CorDictionaryRepository;
import io.protone.service.cor.CorNetworkService;
import io.protone.web.rest.api.cor.CorDictionaryResource;
import io.protone.web.rest.api.cor.impl.CorDictionaryResourceImpl;
import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.web.rest.errors.ExceptionTranslator;
import io.protone.web.rest.mapper.CorDictionaryMapper;
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

import static io.protone.web.rest.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private static final String DEFAULT_DICTIONARY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DICTIONARY_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_SEQ_NUMBER = 1L;
    private static final Long UPDATED_SEQ_NUMBER = 2L;

    private static final String DEFAULT_MODULE = "AAAAAAAAAA";
    private static final String UPDATED_MODULE = "BBBBBBBBBB";

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

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CorDictionaryResourceImpl corDictionaryResource = new CorDictionaryResourceImpl();

        ReflectionTestUtils.setField(corDictionaryResource, "corDictionaryRepository", corDictionaryRepository);
        ReflectionTestUtils.setField(corDictionaryResource, "corDictionaryMapper", corDictionaryMapper);
        ReflectionTestUtils.setField(corDictionaryResource, "corNetworkService", corNetworkService);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);

        this.restCorDictionaryMockMvc = MockMvcBuilders.standaloneSetup(corDictionaryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

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
            .dictionaryType(DEFAULT_DICTIONARY_TYPE)
            .seqNumber(DEFAULT_SEQ_NUMBER)
            .corModule(DEFAULT_MODULE);
        return corDictionary;
    }

    @Before
    public void initTest() {
        corDictionary = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorDictionary() throws Exception {
        int databaseSizeBeforeCreate = corDictionaryRepository.findAll().size();

        // Create the CorDictionary
        CorDictionaryDTO corDictionaryDTO = corDictionaryMapper.DB2DTO(corDictionary);

        restCorDictionaryMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corDictionaryDTO)))
            .andExpect(status().isCreated());

        // Validate the CorDictionary in the database
        List<CorDictionary> corDictionaryList = corDictionaryRepository.findAll();
        assertThat(corDictionaryList).hasSize(databaseSizeBeforeCreate + 1);
        CorDictionary testCorDictionary = corDictionaryList.get(corDictionaryList.size() - 1);
        assertThat(testCorDictionary.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCorDictionary.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCorDictionary.getCorDictionaryType()).isEqualTo(DEFAULT_DICTIONARY_TYPE);
        assertThat(testCorDictionary.getSeqNumber()).isEqualTo(DEFAULT_SEQ_NUMBER);
        assertThat(testCorDictionary.getCorModule()).isEqualTo(DEFAULT_MODULE);
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
        restCorDictionaryMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}", corNetwork.getShortcut())
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
        corDictionaryRepository.saveAndFlush(corDictionary);

        // Get all the corDictionaryList
        restCorDictionaryMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}?sort=id,desc", corNetwork.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corDictionary.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].dictionaryType").value(hasItem(DEFAULT_DICTIONARY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].seqNumber").value(hasItem(DEFAULT_SEQ_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].module").value(hasItem(DEFAULT_MODULE.toString())));
    }

    @Test
    @Transactional
    public void getCorDictionary() throws Exception {
        // Initialize the database
        corDictionaryRepository.saveAndFlush(corDictionary);

        // Get the corDictionary
        restCorDictionaryMockMvc.perform(get("/api/cor-dictionaries/{id}", corDictionary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corDictionary.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.dictionaryType").value(DEFAULT_DICTIONARY_TYPE.toString()))
            .andExpect(jsonPath("$.seqNumber").value(DEFAULT_SEQ_NUMBER.intValue()))
            .andExpect(jsonPath("$.module").value(DEFAULT_MODULE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorDictionary() throws Exception {
        // Get the corDictionary
        restCorDictionaryMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}/{id}", corNetwork.getShortcut(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorDictionary() throws Exception {
        // Initialize the database
        corDictionaryRepository.saveAndFlush(corDictionary);
        int databaseSizeBeforeUpdate = corDictionaryRepository.findAll().size();

        // Update the corDictionary
        CorDictionary updatedCorDictionary = corDictionaryRepository.findOne(corDictionary.getId());
        updatedCorDictionary
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .dictionaryType(UPDATED_DICTIONARY_TYPE)
            .seqNumber(UPDATED_SEQ_NUMBER)
            .corModule(UPDATED_MODULE);
        CorDictionaryDTO corDictionaryDTO = corDictionaryMapper.DB2DTO(updatedCorDictionary);

        restCorDictionaryMockMvc.perform(put("/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corDictionaryDTO)))
            .andExpect(status().isOk());

        // Validate the CorDictionary in the database
        List<CorDictionary> corDictionaryList = corDictionaryRepository.findAll();
        assertThat(corDictionaryList).hasSize(databaseSizeBeforeUpdate);
        CorDictionary testCorDictionary = corDictionaryList.get(corDictionaryList.size() - 1);
        assertThat(testCorDictionary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCorDictionary.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCorDictionary.getCorModule()).isEqualTo(UPDATED_DICTIONARY_TYPE);
        assertThat(testCorDictionary.getSeqNumber()).isEqualTo(UPDATED_SEQ_NUMBER);
        assertThat(testCorDictionary.getCorModule()).isEqualTo(UPDATED_MODULE);
    }

    @Test
    @Transactional
    public void updateNonExistingCorDictionary() throws Exception {
        int databaseSizeBeforeUpdate = corDictionaryRepository.findAll().size();

        // Create the CorDictionary
        CorDictionaryDTO corDictionaryDTO = corDictionaryMapper.DB2DTO(corDictionary);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorDictionaryMockMvc.perform(put("/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}", corNetwork.getShortcut())
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
        corDictionaryRepository.saveAndFlush(corDictionary);
        int databaseSizeBeforeDelete = corDictionaryRepository.findAll().size();

        // Get the corDictionary
        restCorDictionaryMockMvc.perform(delete("/api/v1/network/{networkShortcut}/configuration/network/dictionary/{module}/{type}/{id}", corDictionary.getId())
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
