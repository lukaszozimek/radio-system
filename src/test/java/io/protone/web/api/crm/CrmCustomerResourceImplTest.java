package io.protone.web.api.crm;

import io.protone.ProtoneApp;
import io.protone.web.api.crm.impl.CrmCustomerResourceImpl;
import io.protone.web.rest.dto.crm.CrmAccountDTO;
import io.protone.custom.web.rest.network.TestUtil;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.crm.CrmCustomerService;
import io.protone.web.rest.dto.traffic.TraCustomerDTO;
import io.protone.web.rest.errors.ExceptionTranslator;
import io.protone.web.rest.mapper.CrmAccountMapper;
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

import static io.protone.web.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 02/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmCustomerResourceImplTest {
    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_ID_1 = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_ID_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_ID_2 = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_ID_2 = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAYMENT_DELAY = 1;
    private static final Integer UPDATED_PAYMENT_DELAY = 2;

    private static final String DEFAULT_VAT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VAT_NUMBER = "BBBBBBBBBB";
    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private CrmAccountRepository crmAccountRepository;

    @Autowired
    private CrmCustomerService crmCustomerService;

    @Autowired
    private CrmAccountMapper crmAccountMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCrmAccountMockMvc;

    private CrmAccount crmAccount;

    private CorNetwork corNetwork;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrmAccount createEntity(EntityManager em) {
        CrmAccount crmAccount = new CrmAccount()
            .shortName(DEFAULT_SHORT_NAME)
            .externalId1(DEFAULT_EXTERNAL_ID_1)
            .externalId2(DEFAULT_EXTERNAL_ID_2)
            .name(DEFAULT_NAME)
            .paymentDelay(DEFAULT_PAYMENT_DELAY)
            .vatNumber(DEFAULT_VAT_NUMBER);
        return crmAccount;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CrmCustomerResourceImpl crmAccountResource = new CrmCustomerResourceImpl();

        ReflectionTestUtils.setField(crmAccountResource, "crmCustomerService", crmCustomerService);
        ReflectionTestUtils.setField(crmAccountResource, "crmAccountMapper", crmAccountMapper);
        ReflectionTestUtils.setField(crmAccountResource, "corNetworkService", corNetworkService);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);

        this.restCrmAccountMockMvc = MockMvcBuilders.standaloneSetup(crmAccountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        crmAccount = createEntity(em).network(corNetwork);
    }

    @Test
    @Transactional
    public void createCrmAccount() throws Exception {
        int databaseSizeBeforeCreate = crmAccountRepository.findAll().size();

        // Create the CrmAccount
        CrmAccountDTO crmAccountDTO = crmAccountMapper.DB2DTO(crmAccount);

        restCrmAccountMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/customer", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmAccount in the database
        List<CrmAccount> crmAccountList = crmAccountRepository.findAll();
        assertThat(crmAccountList).hasSize(databaseSizeBeforeCreate + 1);
        CrmAccount testCrmAccount = crmAccountList.get(crmAccountList.size() - 1);
        assertThat(testCrmAccount.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testCrmAccount.getExternalId1()).isEqualTo(DEFAULT_EXTERNAL_ID_1);
        assertThat(testCrmAccount.getExternalId2()).isEqualTo(DEFAULT_EXTERNAL_ID_2);
        assertThat(testCrmAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCrmAccount.getPaymentDelay()).isEqualTo(DEFAULT_PAYMENT_DELAY);
        assertThat(testCrmAccount.getVatNumber()).isEqualTo(DEFAULT_VAT_NUMBER);
    }

    @Test
    @Transactional
    public void createCrmAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = crmAccountRepository.findAll().size();

        // Create the CrmAccount with an existing ID
        CrmAccount existingCrmAccount = new CrmAccount();
        existingCrmAccount.setId(1L);
        CrmAccountDTO existingCrmAccountDTO = crmAccountMapper.DB2DTO(existingCrmAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrmAccountMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/customer", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCrmAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CrmAccount> crmAccountList = crmAccountRepository.findAll();
        assertThat(crmAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCrmAccounts() throws Exception {
        // Initialize the database
        crmAccountRepository.saveAndFlush(crmAccount.network(corNetwork));

        // Get all the crmAccountList
        restCrmAccountMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/customer?sort=id,desc", corNetwork.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].externalId1").value(hasItem(DEFAULT_EXTERNAL_ID_1.toString())))
            .andExpect(jsonPath("$.[*].externalId2").value(hasItem(DEFAULT_EXTERNAL_ID_2.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].paymentDelay").value(hasItem(DEFAULT_PAYMENT_DELAY)))
            .andExpect(jsonPath("$.[*].vatNumber").value(hasItem(DEFAULT_VAT_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getCrmAccount() throws Exception {
        // Initialize the database
        crmAccountRepository.saveAndFlush(crmAccount.network(corNetwork));

        // Get the crmAccount
        restCrmAccountMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/customer/{shortName}", corNetwork.getShortcut(), crmAccount.getShortName()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(crmAccount.getId().intValue()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()))
            .andExpect(jsonPath("$.externalId1").value(DEFAULT_EXTERNAL_ID_1.toString()))
            .andExpect(jsonPath("$.externalId2").value(DEFAULT_EXTERNAL_ID_2.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.paymentDelay").value(DEFAULT_PAYMENT_DELAY))
            .andExpect(jsonPath("$.vatNumber").value(DEFAULT_VAT_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = crmAccountRepository.findAll().size();
        // set the field null
        crmAccount.setName(null);

        // Create the CfgMarkerConfiguration, which fails.
        TraCustomerDTO cfgMarkerConfigurationDTO = crmAccountMapper.traDB2DTO(crmAccount);

        restCrmAccountMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/customer", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfgMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<CrmAccount> crmAccounts = crmAccountRepository.findAll();
        assertThat(crmAccounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShortNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = crmAccountRepository.findAll().size();
        // set the field null
        crmAccount.setShortName(null);

        // Create the CfgMarkerConfiguration, which fails.
        TraCustomerDTO cfgMarkerConfigurationDTO = crmAccountMapper.traDB2DTO(crmAccount);

        restCrmAccountMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/customer", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfgMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<CrmAccount> crmAccounts = crmAccountRepository.findAll();
        assertThat(crmAccounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getNonExistingCrmAccount() throws Exception {
        // Get the crmAccount
        restCrmAccountMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/customer/{shortName}", corNetwork.getShortcut(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrmAccount() throws Exception {
        // Initialize the database
        crmAccountRepository.deleteAll();
        crmAccountRepository.saveAndFlush(crmAccount.network(corNetwork).shortName("YYYYY"));
        int databaseSizeBeforeUpdate = crmAccountRepository.findAll().size();

        // Update the crmAccount
        CrmAccount updatedCrmAccount = crmAccountRepository.findOne(crmAccount.getId());
        updatedCrmAccount
            .shortName(UPDATED_SHORT_NAME)
            .externalId1(UPDATED_EXTERNAL_ID_1)
            .externalId2(UPDATED_EXTERNAL_ID_2)
            .name(UPDATED_NAME)
            .paymentDelay(UPDATED_PAYMENT_DELAY)
            .vatNumber(UPDATED_VAT_NUMBER);
        CrmAccountDTO crmAccountDTO = crmAccountMapper.DB2DTO(updatedCrmAccount);

        restCrmAccountMockMvc.perform(put("/api/v1/network/{networkShortcut}/crm/customer", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmAccountDTO)))
            .andExpect(status().isOk());

        // Validate the CrmAccount in the database
        List<CrmAccount> crmAccountList = crmAccountRepository.findAll();
        assertThat(crmAccountList).hasSize(databaseSizeBeforeUpdate);
        CrmAccount testCrmAccount = crmAccountList.get(crmAccountList.size() - 1);
        assertThat(testCrmAccount.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testCrmAccount.getExternalId1()).isEqualTo(UPDATED_EXTERNAL_ID_1);
        assertThat(testCrmAccount.getExternalId2()).isEqualTo(UPDATED_EXTERNAL_ID_2);
        assertThat(testCrmAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCrmAccount.getPaymentDelay()).isEqualTo(UPDATED_PAYMENT_DELAY);
        assertThat(testCrmAccount.getVatNumber()).isEqualTo(UPDATED_VAT_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingCrmAccount() throws Exception {
        int databaseSizeBeforeUpdate = crmAccountRepository.findAll().size();

        // Create the CrmAccount
        CrmAccountDTO crmAccountDTO = crmAccountMapper.DB2DTO(crmAccount);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCrmAccountMockMvc.perform(put("/api/v1/network/{networkShortcut}/crm/customer", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmAccount in the database
        List<CrmAccount> crmAccountList = crmAccountRepository.findAll();
        assertThat(crmAccountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCrmAccount() throws Exception {
        // Initialize the database
        crmAccountRepository.deleteAll();
        crmAccountRepository.saveAndFlush(crmAccount.network(corNetwork));
        int databaseSizeBeforeDelete = crmAccountRepository.findAll().size();

        // Get the crmAccount
        restCrmAccountMockMvc.perform(delete("/api/v1/network/{networkShortcut}/crm/customer/{shortName}", corNetwork.getShortcut(), crmAccount.getShortName())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CrmAccount> crmAccountList = crmAccountRepository.findAll();
        assertThat(crmAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrmAccount.class);
    }
}
