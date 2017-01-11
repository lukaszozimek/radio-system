package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CRMAccount;
import io.protone.repository.CRMAccountRepository;
import io.protone.service.dto.CRMAccountDTO;
import io.protone.service.mapper.CRMAccountMapper;

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
 * Test class for the CRMAccountResource REST controller.
 *
 * @see CRMAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CRMAccountResourceIntTest {

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

    @Inject
    private CRMAccountRepository cRMAccountRepository;

    @Inject
    private CRMAccountMapper cRMAccountMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCRMAccountMockMvc;

    private CRMAccount cRMAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CRMAccountResource cRMAccountResource = new CRMAccountResource();
        ReflectionTestUtils.setField(cRMAccountResource, "cRMAccountRepository", cRMAccountRepository);
        ReflectionTestUtils.setField(cRMAccountResource, "cRMAccountMapper", cRMAccountMapper);
        this.restCRMAccountMockMvc = MockMvcBuilders.standaloneSetup(cRMAccountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRMAccount createEntity(EntityManager em) {
        CRMAccount cRMAccount = new CRMAccount()
                .shortName(DEFAULT_SHORT_NAME)
                .externalId1(DEFAULT_EXTERNAL_ID_1)
                .externalId2(DEFAULT_EXTERNAL_ID_2)
                .name(DEFAULT_NAME)
                .paymentDelay(DEFAULT_PAYMENT_DELAY)
                .vatNumber(DEFAULT_VAT_NUMBER);
        return cRMAccount;
    }

    @Before
    public void initTest() {
        cRMAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createCRMAccount() throws Exception {
        int databaseSizeBeforeCreate = cRMAccountRepository.findAll().size();

        // Create the CRMAccount
        CRMAccountDTO cRMAccountDTO = cRMAccountMapper.cRMAccountToCRMAccountDTO(cRMAccount);

        restCRMAccountMockMvc.perform(post("/api/c-rm-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the CRMAccount in the database
        List<CRMAccount> cRMAccountList = cRMAccountRepository.findAll();
        assertThat(cRMAccountList).hasSize(databaseSizeBeforeCreate + 1);
        CRMAccount testCRMAccount = cRMAccountList.get(cRMAccountList.size() - 1);
        assertThat(testCRMAccount.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testCRMAccount.getExternalId1()).isEqualTo(DEFAULT_EXTERNAL_ID_1);
        assertThat(testCRMAccount.getExternalId2()).isEqualTo(DEFAULT_EXTERNAL_ID_2);
        assertThat(testCRMAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCRMAccount.getPaymentDelay()).isEqualTo(DEFAULT_PAYMENT_DELAY);
        assertThat(testCRMAccount.getVatNumber()).isEqualTo(DEFAULT_VAT_NUMBER);
    }

    @Test
    @Transactional
    public void createCRMAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cRMAccountRepository.findAll().size();

        // Create the CRMAccount with an existing ID
        CRMAccount existingCRMAccount = new CRMAccount();
        existingCRMAccount.setId(1L);
        CRMAccountDTO existingCRMAccountDTO = cRMAccountMapper.cRMAccountToCRMAccountDTO(existingCRMAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCRMAccountMockMvc.perform(post("/api/c-rm-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCRMAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CRMAccount> cRMAccountList = cRMAccountRepository.findAll();
        assertThat(cRMAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCRMAccounts() throws Exception {
        // Initialize the database
        cRMAccountRepository.saveAndFlush(cRMAccount);

        // Get all the cRMAccountList
        restCRMAccountMockMvc.perform(get("/api/c-rm-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRMAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].externalId1").value(hasItem(DEFAULT_EXTERNAL_ID_1.toString())))
            .andExpect(jsonPath("$.[*].externalId2").value(hasItem(DEFAULT_EXTERNAL_ID_2.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].paymentDelay").value(hasItem(DEFAULT_PAYMENT_DELAY)))
            .andExpect(jsonPath("$.[*].vatNumber").value(hasItem(DEFAULT_VAT_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getCRMAccount() throws Exception {
        // Initialize the database
        cRMAccountRepository.saveAndFlush(cRMAccount);

        // Get the cRMAccount
        restCRMAccountMockMvc.perform(get("/api/c-rm-accounts/{id}", cRMAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cRMAccount.getId().intValue()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()))
            .andExpect(jsonPath("$.externalId1").value(DEFAULT_EXTERNAL_ID_1.toString()))
            .andExpect(jsonPath("$.externalId2").value(DEFAULT_EXTERNAL_ID_2.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.paymentDelay").value(DEFAULT_PAYMENT_DELAY))
            .andExpect(jsonPath("$.vatNumber").value(DEFAULT_VAT_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCRMAccount() throws Exception {
        // Get the cRMAccount
        restCRMAccountMockMvc.perform(get("/api/c-rm-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCRMAccount() throws Exception {
        // Initialize the database
        cRMAccountRepository.saveAndFlush(cRMAccount);
        int databaseSizeBeforeUpdate = cRMAccountRepository.findAll().size();

        // Update the cRMAccount
        CRMAccount updatedCRMAccount = cRMAccountRepository.findOne(cRMAccount.getId());
        updatedCRMAccount
                .shortName(UPDATED_SHORT_NAME)
                .externalId1(UPDATED_EXTERNAL_ID_1)
                .externalId2(UPDATED_EXTERNAL_ID_2)
                .name(UPDATED_NAME)
                .paymentDelay(UPDATED_PAYMENT_DELAY)
                .vatNumber(UPDATED_VAT_NUMBER);
        CRMAccountDTO cRMAccountDTO = cRMAccountMapper.cRMAccountToCRMAccountDTO(updatedCRMAccount);

        restCRMAccountMockMvc.perform(put("/api/c-rm-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMAccountDTO)))
            .andExpect(status().isOk());

        // Validate the CRMAccount in the database
        List<CRMAccount> cRMAccountList = cRMAccountRepository.findAll();
        assertThat(cRMAccountList).hasSize(databaseSizeBeforeUpdate);
        CRMAccount testCRMAccount = cRMAccountList.get(cRMAccountList.size() - 1);
        assertThat(testCRMAccount.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testCRMAccount.getExternalId1()).isEqualTo(UPDATED_EXTERNAL_ID_1);
        assertThat(testCRMAccount.getExternalId2()).isEqualTo(UPDATED_EXTERNAL_ID_2);
        assertThat(testCRMAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCRMAccount.getPaymentDelay()).isEqualTo(UPDATED_PAYMENT_DELAY);
        assertThat(testCRMAccount.getVatNumber()).isEqualTo(UPDATED_VAT_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingCRMAccount() throws Exception {
        int databaseSizeBeforeUpdate = cRMAccountRepository.findAll().size();

        // Create the CRMAccount
        CRMAccountDTO cRMAccountDTO = cRMAccountMapper.cRMAccountToCRMAccountDTO(cRMAccount);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCRMAccountMockMvc.perform(put("/api/c-rm-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the CRMAccount in the database
        List<CRMAccount> cRMAccountList = cRMAccountRepository.findAll();
        assertThat(cRMAccountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCRMAccount() throws Exception {
        // Initialize the database
        cRMAccountRepository.saveAndFlush(cRMAccount);
        int databaseSizeBeforeDelete = cRMAccountRepository.findAll().size();

        // Get the cRMAccount
        restCRMAccountMockMvc.perform(delete("/api/c-rm-accounts/{id}", cRMAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CRMAccount> cRMAccountList = cRMAccountRepository.findAll();
        assertThat(cRMAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
