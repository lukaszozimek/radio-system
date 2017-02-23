package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CorContact;
import io.protone.repository.CorContactRepository;
import io.protone.service.dto.CorContactDTO;
import io.protone.service.mapper.CorContactMapper;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.protone.domain.enumeration.CorContactTypeEnum;
/**
 * Test class for the CorContactResource REST controller.
 *
 * @see CorContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorContactResourceIntTest {

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final CorContactTypeEnum DEFAULT_CONTACT_TYPE = CorContactTypeEnum.CT_EMAIL;
    private static final CorContactTypeEnum UPDATED_CONTACT_TYPE = CorContactTypeEnum.CT_PHONE;

    @Autowired
    private CorContactRepository corContactRepository;

    @Autowired
    private CorContactMapper corContactMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCorContactMockMvc;

    private CorContact corContact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CorContactResource corContactResource = new CorContactResource(corContactRepository, corContactMapper);
        this.restCorContactMockMvc = MockMvcBuilders.standaloneSetup(corContactResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorContact createEntity(EntityManager em) {
        CorContact corContact = new CorContact()
                .contact(DEFAULT_CONTACT)
                .contactType(DEFAULT_CONTACT_TYPE);
        return corContact;
    }

    @Before
    public void initTest() {
        corContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorContact() throws Exception {
        int databaseSizeBeforeCreate = corContactRepository.findAll().size();

        // Create the CorContact
        CorContactDTO corContactDTO = corContactMapper.corContactToCorContactDTO(corContact);

        restCorContactMockMvc.perform(post("/api/cor-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corContactDTO)))
            .andExpect(status().isCreated());

        // Validate the CorContact in the database
        List<CorContact> corContactList = corContactRepository.findAll();
        assertThat(corContactList).hasSize(databaseSizeBeforeCreate + 1);
        CorContact testCorContact = corContactList.get(corContactList.size() - 1);
        assertThat(testCorContact.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testCorContact.getContactType()).isEqualTo(DEFAULT_CONTACT_TYPE);
    }

    @Test
    @Transactional
    public void createCorContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corContactRepository.findAll().size();

        // Create the CorContact with an existing ID
        CorContact existingCorContact = new CorContact();
        existingCorContact.setId(1L);
        CorContactDTO existingCorContactDTO = corContactMapper.corContactToCorContactDTO(existingCorContact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorContactMockMvc.perform(post("/api/cor-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorContact> corContactList = corContactRepository.findAll();
        assertThat(corContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = corContactRepository.findAll().size();
        // set the field null
        corContact.setContact(null);

        // Create the CorContact, which fails.
        CorContactDTO corContactDTO = corContactMapper.corContactToCorContactDTO(corContact);

        restCorContactMockMvc.perform(post("/api/cor-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corContactDTO)))
            .andExpect(status().isBadRequest());

        List<CorContact> corContactList = corContactRepository.findAll();
        assertThat(corContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = corContactRepository.findAll().size();
        // set the field null
        corContact.setContactType(null);

        // Create the CorContact, which fails.
        CorContactDTO corContactDTO = corContactMapper.corContactToCorContactDTO(corContact);

        restCorContactMockMvc.perform(post("/api/cor-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corContactDTO)))
            .andExpect(status().isBadRequest());

        List<CorContact> corContactList = corContactRepository.findAll();
        assertThat(corContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorContacts() throws Exception {
        // Initialize the database
        corContactRepository.saveAndFlush(corContact);

        // Get all the corContactList
        restCorContactMockMvc.perform(get("/api/cor-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].contactType").value(hasItem(DEFAULT_CONTACT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getCorContact() throws Exception {
        // Initialize the database
        corContactRepository.saveAndFlush(corContact);

        // Get the corContact
        restCorContactMockMvc.perform(get("/api/cor-contacts/{id}", corContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corContact.getId().intValue()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()))
            .andExpect(jsonPath("$.contactType").value(DEFAULT_CONTACT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorContact() throws Exception {
        // Get the corContact
        restCorContactMockMvc.perform(get("/api/cor-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorContact() throws Exception {
        // Initialize the database
        corContactRepository.saveAndFlush(corContact);
        int databaseSizeBeforeUpdate = corContactRepository.findAll().size();

        // Update the corContact
        CorContact updatedCorContact = corContactRepository.findOne(corContact.getId());
        updatedCorContact
                .contact(UPDATED_CONTACT)
                .contactType(UPDATED_CONTACT_TYPE);
        CorContactDTO corContactDTO = corContactMapper.corContactToCorContactDTO(updatedCorContact);

        restCorContactMockMvc.perform(put("/api/cor-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corContactDTO)))
            .andExpect(status().isOk());

        // Validate the CorContact in the database
        List<CorContact> corContactList = corContactRepository.findAll();
        assertThat(corContactList).hasSize(databaseSizeBeforeUpdate);
        CorContact testCorContact = corContactList.get(corContactList.size() - 1);
        assertThat(testCorContact.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testCorContact.getContactType()).isEqualTo(UPDATED_CONTACT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCorContact() throws Exception {
        int databaseSizeBeforeUpdate = corContactRepository.findAll().size();

        // Create the CorContact
        CorContactDTO corContactDTO = corContactMapper.corContactToCorContactDTO(corContact);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorContactMockMvc.perform(put("/api/cor-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corContactDTO)))
            .andExpect(status().isCreated());

        // Validate the CorContact in the database
        List<CorContact> corContactList = corContactRepository.findAll();
        assertThat(corContactList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorContact() throws Exception {
        // Initialize the database
        corContactRepository.saveAndFlush(corContact);
        int databaseSizeBeforeDelete = corContactRepository.findAll().size();

        // Get the corContact
        restCorContactMockMvc.perform(delete("/api/cor-contacts/{id}", corContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorContact> corContactList = corContactRepository.findAll();
        assertThat(corContactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorContact.class);
    }
}
