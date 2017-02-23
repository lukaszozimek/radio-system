package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CorAddress;
import io.protone.repository.CorAddressRepository;
import io.protone.service.dto.CorAddressDTO;
import io.protone.service.mapper.CorAddressMapper;

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

/**
 * Test class for the CorAddressResource REST controller.
 *
 * @see CorAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorAddressResourceIntTest {

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private CorAddressRepository corAddressRepository;

    @Autowired
    private CorAddressMapper corAddressMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCorAddressMockMvc;

    private CorAddress corAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CorAddressResource corAddressResource = new CorAddressResource(corAddressRepository, corAddressMapper);
        this.restCorAddressMockMvc = MockMvcBuilders.standaloneSetup(corAddressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorAddress createEntity(EntityManager em) {
        CorAddress corAddress = new CorAddress()
                .street(DEFAULT_STREET)
                .number(DEFAULT_NUMBER)
                .postalCode(DEFAULT_POSTAL_CODE)
                .city(DEFAULT_CITY)
                .country(DEFAULT_COUNTRY);
        return corAddress;
    }

    @Before
    public void initTest() {
        corAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorAddress() throws Exception {
        int databaseSizeBeforeCreate = corAddressRepository.findAll().size();

        // Create the CorAddress
        CorAddressDTO corAddressDTO = corAddressMapper.corAddressToCorAddressDTO(corAddress);

        restCorAddressMockMvc.perform(post("/api/cor-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the CorAddress in the database
        List<CorAddress> corAddressList = corAddressRepository.findAll();
        assertThat(corAddressList).hasSize(databaseSizeBeforeCreate + 1);
        CorAddress testCorAddress = corAddressList.get(corAddressList.size() - 1);
        assertThat(testCorAddress.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testCorAddress.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCorAddress.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testCorAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCorAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void createCorAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corAddressRepository.findAll().size();

        // Create the CorAddress with an existing ID
        CorAddress existingCorAddress = new CorAddress();
        existingCorAddress.setId(1L);
        CorAddressDTO existingCorAddressDTO = corAddressMapper.corAddressToCorAddressDTO(existingCorAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorAddressMockMvc.perform(post("/api/cor-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorAddress> corAddressList = corAddressRepository.findAll();
        assertThat(corAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = corAddressRepository.findAll().size();
        // set the field null
        corAddress.setStreet(null);

        // Create the CorAddress, which fails.
        CorAddressDTO corAddressDTO = corAddressMapper.corAddressToCorAddressDTO(corAddress);

        restCorAddressMockMvc.perform(post("/api/cor-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CorAddress> corAddressList = corAddressRepository.findAll();
        assertThat(corAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = corAddressRepository.findAll().size();
        // set the field null
        corAddress.setNumber(null);

        // Create the CorAddress, which fails.
        CorAddressDTO corAddressDTO = corAddressMapper.corAddressToCorAddressDTO(corAddress);

        restCorAddressMockMvc.perform(post("/api/cor-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CorAddress> corAddressList = corAddressRepository.findAll();
        assertThat(corAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = corAddressRepository.findAll().size();
        // set the field null
        corAddress.setPostalCode(null);

        // Create the CorAddress, which fails.
        CorAddressDTO corAddressDTO = corAddressMapper.corAddressToCorAddressDTO(corAddress);

        restCorAddressMockMvc.perform(post("/api/cor-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CorAddress> corAddressList = corAddressRepository.findAll();
        assertThat(corAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = corAddressRepository.findAll().size();
        // set the field null
        corAddress.setCity(null);

        // Create the CorAddress, which fails.
        CorAddressDTO corAddressDTO = corAddressMapper.corAddressToCorAddressDTO(corAddress);

        restCorAddressMockMvc.perform(post("/api/cor-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CorAddress> corAddressList = corAddressRepository.findAll();
        assertThat(corAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = corAddressRepository.findAll().size();
        // set the field null
        corAddress.setCountry(null);

        // Create the CorAddress, which fails.
        CorAddressDTO corAddressDTO = corAddressMapper.corAddressToCorAddressDTO(corAddress);

        restCorAddressMockMvc.perform(post("/api/cor-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CorAddress> corAddressList = corAddressRepository.findAll();
        assertThat(corAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorAddresses() throws Exception {
        // Initialize the database
        corAddressRepository.saveAndFlush(corAddress);

        // Get all the corAddressList
        restCorAddressMockMvc.perform(get("/api/cor-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())));
    }

    @Test
    @Transactional
    public void getCorAddress() throws Exception {
        // Initialize the database
        corAddressRepository.saveAndFlush(corAddress);

        // Get the corAddress
        restCorAddressMockMvc.perform(get("/api/cor-addresses/{id}", corAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corAddress.getId().intValue()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorAddress() throws Exception {
        // Get the corAddress
        restCorAddressMockMvc.perform(get("/api/cor-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorAddress() throws Exception {
        // Initialize the database
        corAddressRepository.saveAndFlush(corAddress);
        int databaseSizeBeforeUpdate = corAddressRepository.findAll().size();

        // Update the corAddress
        CorAddress updatedCorAddress = corAddressRepository.findOne(corAddress.getId());
        updatedCorAddress
                .street(UPDATED_STREET)
                .number(UPDATED_NUMBER)
                .postalCode(UPDATED_POSTAL_CODE)
                .city(UPDATED_CITY)
                .country(UPDATED_COUNTRY);
        CorAddressDTO corAddressDTO = corAddressMapper.corAddressToCorAddressDTO(updatedCorAddress);

        restCorAddressMockMvc.perform(put("/api/cor-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corAddressDTO)))
            .andExpect(status().isOk());

        // Validate the CorAddress in the database
        List<CorAddress> corAddressList = corAddressRepository.findAll();
        assertThat(corAddressList).hasSize(databaseSizeBeforeUpdate);
        CorAddress testCorAddress = corAddressList.get(corAddressList.size() - 1);
        assertThat(testCorAddress.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testCorAddress.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCorAddress.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testCorAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCorAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void updateNonExistingCorAddress() throws Exception {
        int databaseSizeBeforeUpdate = corAddressRepository.findAll().size();

        // Create the CorAddress
        CorAddressDTO corAddressDTO = corAddressMapper.corAddressToCorAddressDTO(corAddress);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorAddressMockMvc.perform(put("/api/cor-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the CorAddress in the database
        List<CorAddress> corAddressList = corAddressRepository.findAll();
        assertThat(corAddressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorAddress() throws Exception {
        // Initialize the database
        corAddressRepository.saveAndFlush(corAddress);
        int databaseSizeBeforeDelete = corAddressRepository.findAll().size();

        // Get the corAddress
        restCorAddressMockMvc.perform(delete("/api/cor-addresses/{id}", corAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorAddress> corAddressList = corAddressRepository.findAll();
        assertThat(corAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorAddress.class);
    }
}
