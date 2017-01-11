package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CRMContact;
import io.protone.repository.CRMContactRepository;
import io.protone.service.dto.CRMContactDTO;
import io.protone.service.mapper.CRMContactMapper;

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
 * Test class for the CRMContactResource REST controller.
 *
 * @see CRMContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CRMContactResourceIntTest {

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
    private CRMContactRepository cRMContactRepository;

    @Inject
    private CRMContactMapper cRMContactMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCRMContactMockMvc;

    private CRMContact cRMContact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CRMContactResource cRMContactResource = new CRMContactResource();
        ReflectionTestUtils.setField(cRMContactResource, "cRMContactRepository", cRMContactRepository);
        ReflectionTestUtils.setField(cRMContactResource, "cRMContactMapper", cRMContactMapper);
        this.restCRMContactMockMvc = MockMvcBuilders.standaloneSetup(cRMContactResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRMContact createEntity(EntityManager em) {
        CRMContact cRMContact = new CRMContact()
                .shortName(DEFAULT_SHORT_NAME)
                .externalId1(DEFAULT_EXTERNAL_ID_1)
                .externalId2(DEFAULT_EXTERNAL_ID_2)
                .name(DEFAULT_NAME)
                .paymentDelay(DEFAULT_PAYMENT_DELAY)
                .vatNumber(DEFAULT_VAT_NUMBER);
        return cRMContact;
    }

    @Before
    public void initTest() {
        cRMContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createCRMContact() throws Exception {
        int databaseSizeBeforeCreate = cRMContactRepository.findAll().size();

        // Create the CRMContact
        CRMContactDTO cRMContactDTO = cRMContactMapper.cRMContactToCRMContactDTO(cRMContact);

        restCRMContactMockMvc.perform(post("/api/c-rm-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMContactDTO)))
            .andExpect(status().isCreated());

        // Validate the CRMContact in the database
        List<CRMContact> cRMContactList = cRMContactRepository.findAll();
        assertThat(cRMContactList).hasSize(databaseSizeBeforeCreate + 1);
        CRMContact testCRMContact = cRMContactList.get(cRMContactList.size() - 1);
        assertThat(testCRMContact.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testCRMContact.getExternalId1()).isEqualTo(DEFAULT_EXTERNAL_ID_1);
        assertThat(testCRMContact.getExternalId2()).isEqualTo(DEFAULT_EXTERNAL_ID_2);
        assertThat(testCRMContact.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCRMContact.getPaymentDelay()).isEqualTo(DEFAULT_PAYMENT_DELAY);
        assertThat(testCRMContact.getVatNumber()).isEqualTo(DEFAULT_VAT_NUMBER);
    }

    @Test
    @Transactional
    public void createCRMContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cRMContactRepository.findAll().size();

        // Create the CRMContact with an existing ID
        CRMContact existingCRMContact = new CRMContact();
        existingCRMContact.setId(1L);
        CRMContactDTO existingCRMContactDTO = cRMContactMapper.cRMContactToCRMContactDTO(existingCRMContact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCRMContactMockMvc.perform(post("/api/c-rm-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCRMContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CRMContact> cRMContactList = cRMContactRepository.findAll();
        assertThat(cRMContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCRMContacts() throws Exception {
        // Initialize the database
        cRMContactRepository.saveAndFlush(cRMContact);

        // Get all the cRMContactList
        restCRMContactMockMvc.perform(get("/api/c-rm-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRMContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].externalId1").value(hasItem(DEFAULT_EXTERNAL_ID_1.toString())))
            .andExpect(jsonPath("$.[*].externalId2").value(hasItem(DEFAULT_EXTERNAL_ID_2.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].paymentDelay").value(hasItem(DEFAULT_PAYMENT_DELAY)))
            .andExpect(jsonPath("$.[*].vatNumber").value(hasItem(DEFAULT_VAT_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getCRMContact() throws Exception {
        // Initialize the database
        cRMContactRepository.saveAndFlush(cRMContact);

        // Get the cRMContact
        restCRMContactMockMvc.perform(get("/api/c-rm-contacts/{id}", cRMContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cRMContact.getId().intValue()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()))
            .andExpect(jsonPath("$.externalId1").value(DEFAULT_EXTERNAL_ID_1.toString()))
            .andExpect(jsonPath("$.externalId2").value(DEFAULT_EXTERNAL_ID_2.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.paymentDelay").value(DEFAULT_PAYMENT_DELAY))
            .andExpect(jsonPath("$.vatNumber").value(DEFAULT_VAT_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCRMContact() throws Exception {
        // Get the cRMContact
        restCRMContactMockMvc.perform(get("/api/c-rm-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCRMContact() throws Exception {
        // Initialize the database
        cRMContactRepository.saveAndFlush(cRMContact);
        int databaseSizeBeforeUpdate = cRMContactRepository.findAll().size();

        // Update the cRMContact
        CRMContact updatedCRMContact = cRMContactRepository.findOne(cRMContact.getId());
        updatedCRMContact
                .shortName(UPDATED_SHORT_NAME)
                .externalId1(UPDATED_EXTERNAL_ID_1)
                .externalId2(UPDATED_EXTERNAL_ID_2)
                .name(UPDATED_NAME)
                .paymentDelay(UPDATED_PAYMENT_DELAY)
                .vatNumber(UPDATED_VAT_NUMBER);
        CRMContactDTO cRMContactDTO = cRMContactMapper.cRMContactToCRMContactDTO(updatedCRMContact);

        restCRMContactMockMvc.perform(put("/api/c-rm-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMContactDTO)))
            .andExpect(status().isOk());

        // Validate the CRMContact in the database
        List<CRMContact> cRMContactList = cRMContactRepository.findAll();
        assertThat(cRMContactList).hasSize(databaseSizeBeforeUpdate);
        CRMContact testCRMContact = cRMContactList.get(cRMContactList.size() - 1);
        assertThat(testCRMContact.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testCRMContact.getExternalId1()).isEqualTo(UPDATED_EXTERNAL_ID_1);
        assertThat(testCRMContact.getExternalId2()).isEqualTo(UPDATED_EXTERNAL_ID_2);
        assertThat(testCRMContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCRMContact.getPaymentDelay()).isEqualTo(UPDATED_PAYMENT_DELAY);
        assertThat(testCRMContact.getVatNumber()).isEqualTo(UPDATED_VAT_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingCRMContact() throws Exception {
        int databaseSizeBeforeUpdate = cRMContactRepository.findAll().size();

        // Create the CRMContact
        CRMContactDTO cRMContactDTO = cRMContactMapper.cRMContactToCRMContactDTO(cRMContact);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCRMContactMockMvc.perform(put("/api/c-rm-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMContactDTO)))
            .andExpect(status().isCreated());

        // Validate the CRMContact in the database
        List<CRMContact> cRMContactList = cRMContactRepository.findAll();
        assertThat(cRMContactList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCRMContact() throws Exception {
        // Initialize the database
        cRMContactRepository.saveAndFlush(cRMContact);
        int databaseSizeBeforeDelete = cRMContactRepository.findAll().size();

        // Get the cRMContact
        restCRMContactMockMvc.perform(delete("/api/c-rm-contacts/{id}", cRMContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CRMContact> cRMContactList = cRMContactRepository.findAll();
        assertThat(cRMContactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
