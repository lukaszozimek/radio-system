package io.protone.application.web.api.cor;

import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.impl.CorCurrencyResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.api.dto.CorCurrencyDTO;
import io.protone.core.domain.CorCurrency;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorCurrencyMapper;
import io.protone.core.repository.CorCurrencyRepository;
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

import static io.protone.application.web.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 01/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorCurrencyResourceImplTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_SYMBOL = "BBBBBBBBBB";

    private static final String DEFAULT_DELIMITER = "AAAAAAAAAA";
    private static final String UPDATED_DELIMITER = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    @Autowired
    private CorCurrencyRepository corCurrencyRepository;

    @Autowired
    private CorCurrencyMapper corCurrencyMapper;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCorCurrencyMockMvc;

    private CorCurrency corCurrency;

    private CorNetwork corNetwork;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorCurrency createEntity(EntityManager em) {
        CorCurrency corCurrency = new CorCurrency()
            .name(DEFAULT_NAME)
            .symbol(DEFAULT_SYMBOL)
            .delimiter(DEFAULT_DELIMITER)
            .shortName(DEFAULT_SHORT_NAME);
        return corCurrency;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CorCurrencyResourceImpl corCurrencyResource = new CorCurrencyResourceImpl();

        ReflectionTestUtils.setField(corCurrencyResource, "corCurrencyRepository", corCurrencyRepository);
        ReflectionTestUtils.setField(corCurrencyResource, "corCurrencyMapper", corCurrencyMapper);
        ReflectionTestUtils.setField(corCurrencyResource, "corNetworkService", corNetworkService);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);

        this.restCorCurrencyMockMvc = MockMvcBuilders.standaloneSetup(corCurrencyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        corCurrency = createEntity(em).network(corNetwork);
    }

    @Test
    @Transactional
    public void createCorCurrency() throws Exception {
        int databaseSizeBeforeCreate = corCurrencyRepository.findAll().size();

        // Create the CorCurrency
        CorCurrencyDTO corCurrencyDTO = corCurrencyMapper.DB2DTO(corCurrency);

        restCorCurrencyMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/currency", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corCurrencyDTO)))
            .andExpect(status().isCreated());


        // Validate the CorCurrency in the database
        List<CorCurrency> corCurrencyList = corCurrencyRepository.findAll();
        assertThat(corCurrencyList).hasSize(databaseSizeBeforeCreate + 1);
        CorCurrency testCorCurrency = corCurrencyList.get(corCurrencyList.size() - 1);
        assertThat(testCorCurrency.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCorCurrency.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testCorCurrency.getDelimiter()).isEqualTo(DEFAULT_DELIMITER);
        assertThat(testCorCurrency.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
    }

    @Test
    @Transactional
    public void createCorCurrencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corCurrencyRepository.findAll().size();

        // Create the CorCurrency with an existing ID
        CorCurrency existingCorCurrency = new CorCurrency();
        existingCorCurrency.setId(1L);
        CorCurrencyDTO existingCorCurrencyDTO = corCurrencyMapper.DB2DTO(existingCorCurrency);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorCurrencyMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/currency", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorCurrencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorCurrency> corCurrencyList = corCurrencyRepository.findAll();
        assertThat(corCurrencyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = corCurrencyRepository.findAll().size();
        // set the field null
        corCurrency.setName(null);

        // Create the CfgMarkerConfiguration, which fails.
        CorCurrencyDTO cfgMarkerConfigurationDTO = corCurrencyMapper.DB2DTO(corCurrency);

        restCorCurrencyMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/currency", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfgMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<CorCurrency> corCurrencies = corCurrencyRepository.findAll();
        assertThat(corCurrencies).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShortNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = corCurrencyRepository.findAll().size();
        // set the field null
        corCurrency.setShortName(null);

        // Create the CfgMarkerConfiguration, which fails.
        CorCurrencyDTO cfgMarkerConfigurationDTO = corCurrencyMapper.DB2DTO(corCurrency);

        restCorCurrencyMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/currency", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfgMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<CorCurrency> corCurrencies = corCurrencyRepository.findAll();
        assertThat(corCurrencies).hasSize(databaseSizeBeforeTest);
    }


    @Test
    @Transactional
    public void getAllCorCurrencies() throws Exception {
        // Initialize the database
        corCurrencyRepository.saveAndFlush(corCurrency.network(corNetwork));

        // Get all the corCurrencyList
        restCorCurrencyMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/currency?sort=id,desc", corNetwork.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corCurrency.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL.toString())))
            .andExpect(jsonPath("$.[*].delimiter").value(hasItem(DEFAULT_DELIMITER.toString())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCorCurrency() throws Exception {
        // Initialize the database
        corCurrencyRepository.saveAndFlush(corCurrency.network(corNetwork));

        // Get the corCurrency
        restCorCurrencyMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/currency/{id}", corNetwork.getShortcut(), corCurrency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corCurrency.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL.toString()))
            .andExpect(jsonPath("$.delimiter").value(DEFAULT_DELIMITER.toString()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorCurrency() throws Exception {
        // Get the corCurrency
        restCorCurrencyMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/currency/{id}", corNetwork.getShortcut(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorCurrency() throws Exception {
        // Initialize the database
        corCurrencyRepository.saveAndFlush(corCurrency.network(corNetwork));
        int databaseSizeBeforeUpdate = corCurrencyRepository.findAll().size();

        // Update the corCurrency
        CorCurrency updatedCorCurrency = corCurrencyRepository.findOne(corCurrency.getId());
        updatedCorCurrency
            .name(UPDATED_NAME)
            .symbol(UPDATED_SYMBOL)
            .delimiter(UPDATED_DELIMITER)
            .shortName(UPDATED_SHORT_NAME);
        CorCurrencyDTO corCurrencyDTO = corCurrencyMapper.DB2DTO(updatedCorCurrency);
        restCorCurrencyMockMvc.perform(put("/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/currency", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corCurrencyDTO)))
            .andExpect(status().isOk());

        // Validate the CorCurrency in the database
        List<CorCurrency> corCurrencyList = corCurrencyRepository.findAll();
        assertThat(corCurrencyList).hasSize(databaseSizeBeforeUpdate);
        CorCurrency testCorCurrency = corCurrencyList.get(corCurrencyList.size() - 1);
        assertThat(testCorCurrency.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCorCurrency.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testCorCurrency.getDelimiter()).isEqualTo(UPDATED_DELIMITER);
        assertThat(testCorCurrency.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCorCurrency() throws Exception {
        int databaseSizeBeforeUpdate = corCurrencyRepository.findAll().size();

        // Create the CorCurrency
        CorCurrencyDTO corCurrencyDTO = corCurrencyMapper.DB2DTO(corCurrency);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorCurrencyMockMvc.perform(put("/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/currency", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corCurrencyDTO)))
            .andExpect(status().isCreated());

        // Validate the CorCurrency in the database
        List<CorCurrency> corCurrencyList = corCurrencyRepository.findAll();
        assertThat(corCurrencyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorCurrency() throws Exception {
        // Initialize the database
        corCurrencyRepository.saveAndFlush(corCurrency.network(corNetwork));
        int databaseSizeBeforeDelete = corCurrencyRepository.findAll().size();

        // Get the corCurrency
        restCorCurrencyMockMvc.perform(delete("/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/currency/{id}", corNetwork.getShortcut(), corCurrency.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorCurrency> corCurrencyList = corCurrencyRepository.findAll();
        assertThat(corCurrencyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorCurrency.class);
    }
}
