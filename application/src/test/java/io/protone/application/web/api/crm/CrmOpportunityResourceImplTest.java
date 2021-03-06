package io.protone.application.web.api.crm;


import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.crm.impl.CrmOpportunityResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.crm.api.dto.CrmOpportunityDTO;
import io.protone.crm.domain.CrmOpportunity;
import io.protone.crm.mapper.CrmOpportunityMapper;
import io.protone.crm.repostiory.CrmOpportunityRepository;
import io.protone.crm.service.CrmOpportunityService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static io.protone.application.web.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 02/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmOpportunityResourceImplTest {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String DEFAULT_SHORT_NAME = "AAAAAAAXAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_TRY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_TRY = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CLOSE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CLOSE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PROBABILITY = 1;
    private static final Integer UPDATED_PROBABILITY = 2;

    @Autowired
    private CrmOpportunityRepository crmOpportunityRepository;

    @Autowired
    private CrmOpportunityMapper crmOpportunityMapper;

    @Autowired
    private CrmOpportunityService crmOpportunityService;

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

    private MockMvc restCrmOpportunityMockMvc;

    private CrmOpportunity crmOpportunity;

    private CorNetwork corNetwork;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrmOpportunity createEntity(EntityManager em) {
        CrmOpportunity crmOpportunity = new CrmOpportunity()
            .name(DEFAULT_NAME)
            .shortName(DEFAULT_SHORT_NAME)
            .lastTry(DEFAULT_LAST_TRY)
            .closeDate(DEFAULT_CLOSE_DATE)
            .probability(DEFAULT_PROBABILITY);
        return crmOpportunity;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CrmOpportunityResourceImpl crmOpportunityResource = new CrmOpportunityResourceImpl();

        ReflectionTestUtils.setField(crmOpportunityResource, "crmOpportunityService", crmOpportunityService);
        ReflectionTestUtils.setField(crmOpportunityResource, "crmOpportunityMapper", crmOpportunityMapper);
        ReflectionTestUtils.setField(crmOpportunityResource, "corNetworkService", corNetworkService);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);

        this.restCrmOpportunityMockMvc = MockMvcBuilders.standaloneSetup(crmOpportunityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        crmOpportunity = createEntity(em).network(corNetwork);
    }

    @Test
    @Transactional
    public void createCrmOpportunity() throws Exception {
        int databaseSizeBeforeCreate = crmOpportunityRepository.findAll().size();

        // Create the CrmOpportunity
        CrmOpportunityDTO crmOpportunityDTO = crmOpportunityMapper.DB2DTO(crmOpportunity);

        restCrmOpportunityMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/opportunity", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmOpportunityDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmOpportunity in the database
        List<CrmOpportunity> crmOpportunityList = crmOpportunityRepository.findAll();
        assertThat(crmOpportunityList).hasSize(databaseSizeBeforeCreate + 1);
        CrmOpportunity testCrmOpportunity = crmOpportunityList.get(crmOpportunityList.size() - 1);
        assertThat(testCrmOpportunity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCrmOpportunity.getLastTry()).isEqualTo(DEFAULT_LAST_TRY);
        assertThat(testCrmOpportunity.getCloseDate()).isEqualTo(DEFAULT_CLOSE_DATE);
        assertThat(testCrmOpportunity.getProbability()).isEqualTo(DEFAULT_PROBABILITY);
    }

    @Test
    @Transactional
    public void createCrmOpportunityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = crmOpportunityRepository.findAll().size();

        // Create the CrmOpportunity with an existing ID
        CrmOpportunity existingCrmOpportunity = new CrmOpportunity();
        existingCrmOpportunity.setId(1L);
        CrmOpportunityDTO existingCrmOpportunityDTO = crmOpportunityMapper.DB2DTO(existingCrmOpportunity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrmOpportunityMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/opportunity", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCrmOpportunityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CrmOpportunity> crmOpportunityList = crmOpportunityRepository.findAll();
        assertThat(crmOpportunityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCrmOpportunities() throws Exception {
        // Initialize the database
        crmOpportunityRepository.saveAndFlush(crmOpportunity.network(corNetwork));

        // Get all the crmOpportunityList
        restCrmOpportunityMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/opportunity?sort=id,desc", corNetwork.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmOpportunity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastTry").value(hasItem(DEFAULT_LAST_TRY.toString())))
            .andExpect(jsonPath("$.[*].closeDate").value(hasItem(DEFAULT_CLOSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].probability").value(hasItem(DEFAULT_PROBABILITY)));
    }

    @Test
    @Transactional
    public void getCrmOpportunity() throws Exception {
        // Initialize the database
        crmOpportunityRepository.saveAndFlush(crmOpportunity.network(corNetwork));

        // Get the crmOpportunity
        restCrmOpportunityMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}", corNetwork.getShortcut(), crmOpportunity.getShortName()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(crmOpportunity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.lastTry").value(DEFAULT_LAST_TRY.toString()))
            .andExpect(jsonPath("$.closeDate").value(DEFAULT_CLOSE_DATE.toString()))
            .andExpect(jsonPath("$.probability").value(DEFAULT_PROBABILITY));
    }

    @Test
    @Transactional
    public void getNonExistingCrmOpportunity() throws Exception {
        // Get the crmOpportunity
        restCrmOpportunityMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}", corNetwork.getShortcut(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrmOpportunity() throws Exception {
        // Initialize the database
        crmOpportunityRepository.saveAndFlush(crmOpportunity.network(corNetwork));
        int databaseSizeBeforeUpdate = crmOpportunityRepository.findAll().size();

        // Update the crmOpportunity
        CrmOpportunity updatedCrmOpportunity = crmOpportunityRepository.findOne(crmOpportunity.getId());
        updatedCrmOpportunity
            .name(UPDATED_NAME)
            .lastTry(UPDATED_LAST_TRY)
            .closeDate(UPDATED_CLOSE_DATE)
            .probability(UPDATED_PROBABILITY);
        CrmOpportunityDTO crmOpportunityDTO = crmOpportunityMapper.DB2DTO(updatedCrmOpportunity);

        restCrmOpportunityMockMvc.perform(put("/api/v1/network/{networkShortcut}/crm/opportunity", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmOpportunityDTO)))
            .andExpect(status().isOk());

        // Validate the CrmOpportunity in the database
        List<CrmOpportunity> crmOpportunityList = crmOpportunityRepository.findAll();
        assertThat(crmOpportunityList).hasSize(databaseSizeBeforeUpdate);
        CrmOpportunity testCrmOpportunity = crmOpportunityList.get(crmOpportunityList.size() - 1);
        assertThat(testCrmOpportunity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCrmOpportunity.getLastTry()).isEqualTo(UPDATED_LAST_TRY);
        assertThat(testCrmOpportunity.getCloseDate()).isEqualTo(UPDATED_CLOSE_DATE);
        assertThat(testCrmOpportunity.getProbability()).isEqualTo(UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = crmOpportunityRepository.findAll().size();
        // set the field null
        crmOpportunity.setName(null);

        // Create the CfgMarkerConfiguration, which fails.
        CrmOpportunityDTO cfgMarkerConfigurationDTO = crmOpportunityMapper.DB2DTO(crmOpportunity);

        restCrmOpportunityMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/opportunity", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfgMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<CrmOpportunity> crmOpportunities = crmOpportunityRepository.findAll();
        assertThat(crmOpportunities).hasSize(databaseSizeBeforeTest);
    }


    @Test
    @Transactional
    public void checkShortNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = crmOpportunityRepository.findAll().size();
        // set the field null
        crmOpportunity.setShortName(null);

        // Create the CfgMarkerConfiguration, which fails.
        CrmOpportunityDTO cfgMarkerConfigurationDTO = crmOpportunityMapper.DB2DTO(crmOpportunity);

        restCrmOpportunityMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/opportunity", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfgMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<CrmOpportunity> crmOpportunities = crmOpportunityRepository.findAll();
        assertThat(crmOpportunities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void updateNonExistingCrmOpportunity() throws Exception {
        int databaseSizeBeforeUpdate = crmOpportunityRepository.findAll().size();

        // Create the CrmOpportunity
        CrmOpportunityDTO crmOpportunityDTO = crmOpportunityMapper.DB2DTO(crmOpportunity);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCrmOpportunityMockMvc.perform(put("/api/v1/network/{networkShortcut}/crm/opportunity", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmOpportunityDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmOpportunity in the database
        List<CrmOpportunity> crmOpportunityList = crmOpportunityRepository.findAll();
        assertThat(crmOpportunityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCrmOpportunity() throws Exception {
        crmOpportunityRepository.deleteAll();
        // Initialize the database
        crmOpportunityRepository.saveAndFlush(crmOpportunity.network(corNetwork));
        int databaseSizeBeforeDelete = crmOpportunityRepository.findAll().size();

        // Get the crmOpportunity
        restCrmOpportunityMockMvc.perform(delete("/api/v1/network/{networkShortcut}/crm/opportunity/{shortName}", corNetwork.getShortcut(), crmOpportunity.getShortName())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CrmOpportunity> crmOpportunityList = crmOpportunityRepository.findAll();
        assertThat(crmOpportunityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrmOpportunity.class);
    }
}
