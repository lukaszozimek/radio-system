package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CORContact;
import io.protone.repository.CORContactRepository;
import io.protone.service.dto.CORContactDTO;
import io.protone.service.mapper.CORContactMapper;

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

import io.protone.domain.enumeration.CORContactTypeEnum;
/**
 * Test class for the CORContactResource REST controller.
 *
 * @see CORContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CORContactResourceIntTest {

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final CORContactTypeEnum DEFAULT_CONTACT_TYPE = CORContactTypeEnum.CT_EMAIL;
    private static final CORContactTypeEnum UPDATED_CONTACT_TYPE = CORContactTypeEnum.CT_PHONE;

    @Inject
    private CORContactRepository cORContactRepository;

    @Inject
    private CORContactMapper cORContactMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCORContactMockMvc;

    private CORContact cORContact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CORContactResource cORContactResource = new CORContactResource();
        ReflectionTestUtils.setField(cORContactResource, "cORContactRepository", cORContactRepository);
        ReflectionTestUtils.setField(cORContactResource, "cORContactMapper", cORContactMapper);
        this.restCORContactMockMvc = MockMvcBuilders.standaloneSetup(cORContactResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CORContact createEntity(EntityManager em) {
        CORContact cORContact = new CORContact()
                .contact(DEFAULT_CONTACT)
                .contactType(DEFAULT_CONTACT_TYPE);
        return cORContact;
    }

    @Before
    public void initTest() {
        cORContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createCORContact() throws Exception {
        int databaseSizeBeforeCreate = cORContactRepository.findAll().size();

        // Create the CORContact
        CORContactDTO cORContactDTO = cORContactMapper.cORContactToCORContactDTO(cORContact);

        restCORContactMockMvc.perform(post("/api/c-or-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORContactDTO)))
            .andExpect(status().isCreated());

        // Validate the CORContact in the database
        List<CORContact> cORContactList = cORContactRepository.findAll();
        assertThat(cORContactList).hasSize(databaseSizeBeforeCreate + 1);
        CORContact testCORContact = cORContactList.get(cORContactList.size() - 1);
        assertThat(testCORContact.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testCORContact.getContactType()).isEqualTo(DEFAULT_CONTACT_TYPE);
    }

    @Test
    @Transactional
    public void createCORContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cORContactRepository.findAll().size();

        // Create the CORContact with an existing ID
        CORContact existingCORContact = new CORContact();
        existingCORContact.setId(1L);
        CORContactDTO existingCORContactDTO = cORContactMapper.cORContactToCORContactDTO(existingCORContact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCORContactMockMvc.perform(post("/api/c-or-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCORContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CORContact> cORContactList = cORContactRepository.findAll();
        assertThat(cORContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORContactRepository.findAll().size();
        // set the field null
        cORContact.setContact(null);

        // Create the CORContact, which fails.
        CORContactDTO cORContactDTO = cORContactMapper.cORContactToCORContactDTO(cORContact);

        restCORContactMockMvc.perform(post("/api/c-or-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORContactDTO)))
            .andExpect(status().isBadRequest());

        List<CORContact> cORContactList = cORContactRepository.findAll();
        assertThat(cORContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORContactRepository.findAll().size();
        // set the field null
        cORContact.setContactType(null);

        // Create the CORContact, which fails.
        CORContactDTO cORContactDTO = cORContactMapper.cORContactToCORContactDTO(cORContact);

        restCORContactMockMvc.perform(post("/api/c-or-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORContactDTO)))
            .andExpect(status().isBadRequest());

        List<CORContact> cORContactList = cORContactRepository.findAll();
        assertThat(cORContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCORContacts() throws Exception {
        // Initialize the database
        cORContactRepository.saveAndFlush(cORContact);

        // Get all the cORContactList
        restCORContactMockMvc.perform(get("/api/c-or-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cORContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].contactType").value(hasItem(DEFAULT_CONTACT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getCORContact() throws Exception {
        // Initialize the database
        cORContactRepository.saveAndFlush(cORContact);

        // Get the cORContact
        restCORContactMockMvc.perform(get("/api/c-or-contacts/{id}", cORContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cORContact.getId().intValue()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()))
            .andExpect(jsonPath("$.contactType").value(DEFAULT_CONTACT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCORContact() throws Exception {
        // Get the cORContact
        restCORContactMockMvc.perform(get("/api/c-or-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCORContact() throws Exception {
        // Initialize the database
        cORContactRepository.saveAndFlush(cORContact);
        int databaseSizeBeforeUpdate = cORContactRepository.findAll().size();

        // Update the cORContact
        CORContact updatedCORContact = cORContactRepository.findOne(cORContact.getId());
        updatedCORContact
                .contact(UPDATED_CONTACT)
                .contactType(UPDATED_CONTACT_TYPE);
        CORContactDTO cORContactDTO = cORContactMapper.cORContactToCORContactDTO(updatedCORContact);

        restCORContactMockMvc.perform(put("/api/c-or-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORContactDTO)))
            .andExpect(status().isOk());

        // Validate the CORContact in the database
        List<CORContact> cORContactList = cORContactRepository.findAll();
        assertThat(cORContactList).hasSize(databaseSizeBeforeUpdate);
        CORContact testCORContact = cORContactList.get(cORContactList.size() - 1);
        assertThat(testCORContact.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testCORContact.getContactType()).isEqualTo(UPDATED_CONTACT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCORContact() throws Exception {
        int databaseSizeBeforeUpdate = cORContactRepository.findAll().size();

        // Create the CORContact
        CORContactDTO cORContactDTO = cORContactMapper.cORContactToCORContactDTO(cORContact);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCORContactMockMvc.perform(put("/api/c-or-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORContactDTO)))
            .andExpect(status().isCreated());

        // Validate the CORContact in the database
        List<CORContact> cORContactList = cORContactRepository.findAll();
        assertThat(cORContactList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCORContact() throws Exception {
        // Initialize the database
        cORContactRepository.saveAndFlush(cORContact);
        int databaseSizeBeforeDelete = cORContactRepository.findAll().size();

        // Get the cORContact
        restCORContactMockMvc.perform(delete("/api/c-or-contacts/{id}", cORContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CORContact> cORContactList = cORContactRepository.findAll();
        assertThat(cORContactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
