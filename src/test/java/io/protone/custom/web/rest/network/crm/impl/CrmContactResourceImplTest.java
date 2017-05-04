package io.protone.custom.web.rest.network.crm.impl;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.web.rest.network.TestUtil;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmContact;
import io.protone.repository.crm.CrmContactRepository;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.crm.CrmContactService;
import io.protone.web.rest.errors.ExceptionTranslator;
import io.protone.web.rest.mapper.CrmContactMapper;
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

import static io.protone.web.rest.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by lukaszozimek on 02/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmContactResourceImplTest {
    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_ID_1 = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_ID_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_ID_2 = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_ID_2 = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAYMENT_DELAY = 1;
    private static final Integer UPDATED_PAYMENT_DELAY = 2;

    private static final String DEFAULT_VAT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VAT_NUMBER = "BBBBBBBBBB";
    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private CrmContactRepository crmContactRepository;

    @Autowired
    private CrmContactService crmContactService;

    @Autowired
    private CrmContactMapper crmContactMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCrmContactMockMvc;

    private CrmContact crmContact;

    private CorNetwork corNetwork;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ApiNetworkCrmContactImpl crmContactResource = new ApiNetworkCrmContactImpl();

        ReflectionTestUtils.setField(crmContactResource, "crmContactService", crmContactService);
        ReflectionTestUtils.setField(crmContactResource, "crmContactMapper", crmContactMapper);
        ReflectionTestUtils.setField(crmContactResource, "corNetworkService", corNetworkService);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);

        this.restCrmContactMockMvc = MockMvcBuilders.standaloneSetup(crmContactResource)
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
    public static CrmContact createEntity(EntityManager em) {
        CrmContact crmContact = new CrmContact()
            .shortName(DEFAULT_SHORT_NAME)
            .externalId1(DEFAULT_EXTERNAL_ID_1)
            .externalId2(DEFAULT_EXTERNAL_ID_2)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .name(DEFAULT_NAME)
            .paymentDelay(DEFAULT_PAYMENT_DELAY)
            .vatNumber(DEFAULT_VAT_NUMBER);
        return crmContact;
    }

    @Before
    public void initTest() {
        crmContact = createEntity(em).network(corNetwork);
    }

    @Test
    @Transactional
    public void createCrmContact() throws Exception {
        int databaseSizeBeforeCreate = crmContactRepository.findAll().size();

        // Create the CrmContact
        CrmContactPT crmContactDTO = crmContactMapper.DB2DTO(crmContact);

        restCrmContactMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/contact", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmContactDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmContact in the database
        List<CrmContact> crmContactList = crmContactRepository.findAll();
        assertThat(crmContactList).hasSize(databaseSizeBeforeCreate + 1);
        CrmContact testCrmContact = crmContactList.get(crmContactList.size() - 1);
        assertThat(testCrmContact.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testCrmContact.getExternalId1()).isEqualTo(DEFAULT_EXTERNAL_ID_1);
        assertThat(testCrmContact.getExternalId2()).isEqualTo(DEFAULT_EXTERNAL_ID_2);
        assertThat(testCrmContact.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testCrmContact.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCrmContact.getPaymentDelay()).isEqualTo(DEFAULT_PAYMENT_DELAY);
        assertThat(testCrmContact.getVatNumber()).isEqualTo(DEFAULT_VAT_NUMBER);
    }

    @Test
    @Transactional
    public void createCrmContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = crmContactRepository.findAll().size();

        // Create the CrmContact with an existing ID
        CrmContact existingCrmContact = new CrmContact();
        existingCrmContact.setId(1L);
        CrmContactPT existingCrmContactDTO = crmContactMapper.DB2DTO(existingCrmContact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrmContactMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/contact", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCrmContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CrmContact> crmContactList = crmContactRepository.findAll();
        assertThat(crmContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCrmContacts() throws Exception {
        // Initialize the database
        crmContactRepository.saveAndFlush(crmContact.network(corNetwork));

        // Get all the crmContactList
        restCrmContactMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/contact?sort=id,desc", corNetwork.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].externalId1").value(hasItem(DEFAULT_EXTERNAL_ID_1.toString())))
            .andExpect(jsonPath("$.[*].externalId2").value(hasItem(DEFAULT_EXTERNAL_ID_2.toString())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].paymentDelay").value(hasItem(DEFAULT_PAYMENT_DELAY)))
            .andExpect(jsonPath("$.[*].vatNumber").value(hasItem(DEFAULT_VAT_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getCrmContact() throws Exception {
        // Initialize the database
        crmContactRepository.saveAndFlush(crmContact.network(corNetwork));

        // Get the crmContact
        restCrmContactMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/contact/{shortName}", corNetwork.getShortcut(), crmContact.getShortName()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(crmContact.getId().intValue()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()))
            .andExpect(jsonPath("$.externalId1").value(DEFAULT_EXTERNAL_ID_1.toString()))
            .andExpect(jsonPath("$.externalId2").value(DEFAULT_EXTERNAL_ID_2.toString()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.paymentDelay").value(DEFAULT_PAYMENT_DELAY))
            .andExpect(jsonPath("$.vatNumber").value(DEFAULT_VAT_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCrmContact() throws Exception {
        // Get the crmContact
        restCrmContactMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/contact/{shortName}", corNetwork.getShortcut(), crmContact.getShortName(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrmContact() throws Exception {
        // Initialize the database
        crmContactRepository.saveAndFlush(crmContact.network(corNetwork));
        int databaseSizeBeforeUpdate = crmContactRepository.findAll().size();

        // Update the crmContact
        CrmContact updatedCrmContact = crmContactRepository.findOne(crmContact.getId());
        updatedCrmContact
            .shortName(UPDATED_SHORT_NAME)
            .externalId1(UPDATED_EXTERNAL_ID_1)
            .externalId2(UPDATED_EXTERNAL_ID_2)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .name(UPDATED_NAME)
            .paymentDelay(UPDATED_PAYMENT_DELAY)
            .vatNumber(UPDATED_VAT_NUMBER);
        CrmContactPT crmContactDTO = crmContactMapper.DB2DTO(updatedCrmContact);

        restCrmContactMockMvc.perform(put("/api/v1/network/{networkShortcut}/crm/contact", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmContactDTO)))
            .andExpect(status().isOk());

        // Validate the CrmContact in the database
        List<CrmContact> crmContactList = crmContactRepository.findAll();
        assertThat(crmContactList).hasSize(databaseSizeBeforeUpdate);
        CrmContact testCrmContact = crmContactList.get(crmContactList.size() - 1);
        assertThat(testCrmContact.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testCrmContact.getExternalId1()).isEqualTo(UPDATED_EXTERNAL_ID_1);
        assertThat(testCrmContact.getExternalId2()).isEqualTo(UPDATED_EXTERNAL_ID_2);
        assertThat(testCrmContact.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testCrmContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCrmContact.getPaymentDelay()).isEqualTo(UPDATED_PAYMENT_DELAY);
        assertThat(testCrmContact.getVatNumber()).isEqualTo(UPDATED_VAT_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingCrmContact() throws Exception {
        int databaseSizeBeforeUpdate = crmContactRepository.findAll().size();

        // Create the CrmContact
        CrmContactPT crmContactDTO = crmContactMapper.DB2DTO(crmContact);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCrmContactMockMvc.perform(put("/api/v1/network/{networkShortcut}/crm/contact", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmContactDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmContact in the database
        List<CrmContact> crmContactList = crmContactRepository.findAll();
        assertThat(crmContactList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCrmContact() throws Exception {
        // Initialize the database
        crmContactRepository.saveAndFlush(crmContact.network(corNetwork));
        int databaseSizeBeforeDelete = crmContactRepository.findAll().size();

        // Get the crmContact
        restCrmContactMockMvc.perform(delete("/api/v1/network/{networkShortcut}/crm/contact/{shortName}", corNetwork.getShortcut(), crmContact.getShortName())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CrmContact> crmContactList = crmContactRepository.findAll();
        assertThat(crmContactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrmContact.class);
    }
}