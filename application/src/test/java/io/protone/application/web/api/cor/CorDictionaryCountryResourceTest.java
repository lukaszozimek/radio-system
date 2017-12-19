package io.protone.application.web.api.cor;


import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.impl.CorDictionaryCountryResourceImpl;
import io.protone.core.api.dto.CorCountryDTO;
import io.protone.core.domain.CorCountry;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorCountryMapper;
import io.protone.core.repository.CorCountryRepository;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.service.CorNetworkService;
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

import static io.protone.application.web.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 01.05.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorDictionaryCountryResourceTest {

    private static final String NETWORK_TEST = "ee";
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    @Inject
    private CorCountryRepository corCountryRepository;

    @Inject
    private CorCountryMapper corCountryMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorNetworkRepository corNetworkRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCorCountryMockMvc;

    private CorCountry corCountry;

    private CorNetwork corNetwork;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorCountry createEntity(EntityManager em) {
        CorCountry corCountry = new CorCountry()
            .name(DEFAULT_NAME)
            .shortName(DEFAULT_SHORT_NAME);
        return corCountry;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);
        CorDictionaryCountryResourceImpl corCountryResource = new CorDictionaryCountryResourceImpl();
        ReflectionTestUtils.setField(corCountryResource, "corCountryRepository", corCountryRepository);
        ReflectionTestUtils.setField(corCountryResource, "corCountryMapper", corCountryMapper);
        ReflectionTestUtils.setField(corCountryResource, "corNetworkService", corNetworkService);

        this.restCorCountryMockMvc = MockMvcBuilders.standaloneSetup(corCountryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        corCountry = createEntity(em).network(corNetwork);
    }

    @Test
    @Transactional
    public void createCorCountry() throws Exception {
        int databaseSizeBeforeCreate = corCountryRepository.findAll().size();

        // Create the CorCountry
        CorCountryDTO corCountryDTO = corCountryMapper.DB2DTO(corCountry);

        restCorCountryMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/configuration/organization/dictionary/country", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corCountryDTO)))
            .andExpect(status().isCreated());

        // Validate the CorCountry in the database
        List<CorCountry> corCountryList = corCountryRepository.findAll();
        assertThat(corCountryList).hasSize(databaseSizeBeforeCreate + 1);
        CorCountry testCorCountry = corCountryList.get(corCountryList.size() - 1);
        assertThat(testCorCountry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCorCountry.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
    }

    @Test
    @Transactional
    public void createCorCountryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corCountryRepository.findAll().size();

        // Create the CorCountry with an existing ID
        CorCountry existingCorCountry = new CorCountry();
        existingCorCountry.setId(1L);
        CorCountryDTO existingCorCountryDTO = corCountryMapper.DB2DTO(existingCorCountry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorCountryMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/configuration/organization/dictionary/country", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorCountryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorCountry> corCountryList = corCountryRepository.findAll();
        assertThat(corCountryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorCountries() throws Exception {
        // Initialize the database
        corCountryRepository.saveAndFlush(corCountry.network(corNetwork));

        // Get all the corCountryList
        restCorCountryMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/configuration/organization/dictionary/country?sort=id,desc", corNetwork.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corCountry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCorCountry() throws Exception {
        // Initialize the database
        corCountryRepository.saveAndFlush(corCountry.network(corNetwork));

        // Get the corCountry
        restCorCountryMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/configuration/organization/dictionary/country/{id}", corNetwork.getShortcut(), corCountry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corCountry.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorCountry() throws Exception {
        // Get the corCountry
        restCorCountryMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/configuration/organization/dictionary/country/{id}", corNetwork.getShortcut(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorCountry() throws Exception {
        // Initialize the database
        corCountryRepository.saveAndFlush(corCountry.network(corNetwork));
        int databaseSizeBeforeUpdate = corCountryRepository.findAll().size();

        // Update the corCountry
        CorCountry updatedCorCountry = corCountryRepository.findOne(corCountry.getId());
        updatedCorCountry
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME);
        CorCountryDTO corCountryDTO = corCountryMapper.DB2DTO(updatedCorCountry);

        restCorCountryMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/configuration/organization/dictionary/country", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corCountryDTO)))
            .andExpect(status().isOk());

        // Validate the CorCountry in the database
        List<CorCountry> corCountryList = corCountryRepository.findAll();
        assertThat(corCountryList).hasSize(databaseSizeBeforeUpdate);
        CorCountry testCorCountry = corCountryList.get(corCountryList.size() - 1);
        assertThat(testCorCountry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCorCountry.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
    }

    @Test
    public void checkShortcutIsRequired() throws Exception {
        int databaseSizeBeforeTest = corCountryRepository.findAll().size();
        // set the field null
        corCountry.setShortName(null);

        // Create the CorChannel, which fails.
        CorCountryDTO corChannelDTO = corCountryMapper.DB2DTO(corCountry);

        restCorCountryMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/configuration/organization/dictionary/country", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corChannelDTO)))
            .andExpect(status().isBadRequest());

        List<CorCountry> corChannelList = corCountryRepository.findAll();
        assertThat(corChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = corCountryRepository.findAll().size();
        // set the field null
        corCountry.setName(null);

        // Create the CorChannel, which fails.
        CorCountryDTO corChannelDTO = corCountryMapper.DB2DTO(corCountry);

        restCorCountryMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/configuration/organization/dictionary/country", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corChannelDTO)))
            .andExpect(status().isBadRequest());

        List<CorCountry> corChannelList = corCountryRepository.findAll();
        assertThat(corChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void updateNonExistingCorCountry() throws Exception {
        int databaseSizeBeforeUpdate = corCountryRepository.findAll().size();

        // Create the CorCountry
        CorCountryDTO corCountryDTO = corCountryMapper.DB2DTO(corCountry);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorCountryMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/configuration/organization/dictionary/country", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corCountryDTO)))
            .andExpect(status().isCreated());

        // Validate the CorCountry in the database
        List<CorCountry> corCountryList = corCountryRepository.findAll();
        assertThat(corCountryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorCountry() throws Exception {
        // Initialize the database
        corCountryRepository.saveAndFlush(corCountry.network(corNetwork));
        int databaseSizeBeforeDelete = corCountryRepository.findAll().size();

        // Get the corCountry
        restCorCountryMockMvc.perform(delete("/api/v1/organization/{organizationShortcut}/configuration/organization/dictionary/country/{id}", corNetwork.getShortcut(), corCountry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorCountry> corCountryList = corCountryRepository.findAll();
        assertThat(corCountryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
