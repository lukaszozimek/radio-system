package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CORAddress;
import io.protone.repository.CORAddressRepository;
import io.protone.service.dto.CORAddressDTO;
import io.protone.service.mapper.CORAddressMapper;

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
 * Test class for the CORAddressResource REST controller.
 *
 * @see CORAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CORAddressResourceIntTest {

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

    @Inject
    private CORAddressRepository cORAddressRepository;

    @Inject
    private CORAddressMapper cORAddressMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCORAddressMockMvc;

    private CORAddress cORAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CORAddressResource cORAddressResource = new CORAddressResource();
        ReflectionTestUtils.setField(cORAddressResource, "cORAddressRepository", cORAddressRepository);
        ReflectionTestUtils.setField(cORAddressResource, "cORAddressMapper", cORAddressMapper);
        this.restCORAddressMockMvc = MockMvcBuilders.standaloneSetup(cORAddressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CORAddress createEntity(EntityManager em) {
        CORAddress cORAddress = new CORAddress()
                .street(DEFAULT_STREET)
                .number(DEFAULT_NUMBER)
                .postalCode(DEFAULT_POSTAL_CODE)
                .city(DEFAULT_CITY)
                .country(DEFAULT_COUNTRY);
        return cORAddress;
    }

    @Before
    public void initTest() {
        cORAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createCORAddress() throws Exception {
        int databaseSizeBeforeCreate = cORAddressRepository.findAll().size();

        // Create the CORAddress
        CORAddressDTO cORAddressDTO = cORAddressMapper.cORAddressToCORAddressDTO(cORAddress);

        restCORAddressMockMvc.perform(post("/api/c-or-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the CORAddress in the database
        List<CORAddress> cORAddressList = cORAddressRepository.findAll();
        assertThat(cORAddressList).hasSize(databaseSizeBeforeCreate + 1);
        CORAddress testCORAddress = cORAddressList.get(cORAddressList.size() - 1);
        assertThat(testCORAddress.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testCORAddress.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCORAddress.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testCORAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCORAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void createCORAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cORAddressRepository.findAll().size();

        // Create the CORAddress with an existing ID
        CORAddress existingCORAddress = new CORAddress();
        existingCORAddress.setId(1L);
        CORAddressDTO existingCORAddressDTO = cORAddressMapper.cORAddressToCORAddressDTO(existingCORAddress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCORAddressMockMvc.perform(post("/api/c-or-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCORAddressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CORAddress> cORAddressList = cORAddressRepository.findAll();
        assertThat(cORAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORAddressRepository.findAll().size();
        // set the field null
        cORAddress.setStreet(null);

        // Create the CORAddress, which fails.
        CORAddressDTO cORAddressDTO = cORAddressMapper.cORAddressToCORAddressDTO(cORAddress);

        restCORAddressMockMvc.perform(post("/api/c-or-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CORAddress> cORAddressList = cORAddressRepository.findAll();
        assertThat(cORAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORAddressRepository.findAll().size();
        // set the field null
        cORAddress.setNumber(null);

        // Create the CORAddress, which fails.
        CORAddressDTO cORAddressDTO = cORAddressMapper.cORAddressToCORAddressDTO(cORAddress);

        restCORAddressMockMvc.perform(post("/api/c-or-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CORAddress> cORAddressList = cORAddressRepository.findAll();
        assertThat(cORAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORAddressRepository.findAll().size();
        // set the field null
        cORAddress.setPostalCode(null);

        // Create the CORAddress, which fails.
        CORAddressDTO cORAddressDTO = cORAddressMapper.cORAddressToCORAddressDTO(cORAddress);

        restCORAddressMockMvc.perform(post("/api/c-or-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CORAddress> cORAddressList = cORAddressRepository.findAll();
        assertThat(cORAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORAddressRepository.findAll().size();
        // set the field null
        cORAddress.setCity(null);

        // Create the CORAddress, which fails.
        CORAddressDTO cORAddressDTO = cORAddressMapper.cORAddressToCORAddressDTO(cORAddress);

        restCORAddressMockMvc.perform(post("/api/c-or-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CORAddress> cORAddressList = cORAddressRepository.findAll();
        assertThat(cORAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORAddressRepository.findAll().size();
        // set the field null
        cORAddress.setCountry(null);

        // Create the CORAddress, which fails.
        CORAddressDTO cORAddressDTO = cORAddressMapper.cORAddressToCORAddressDTO(cORAddress);

        restCORAddressMockMvc.perform(post("/api/c-or-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORAddressDTO)))
            .andExpect(status().isBadRequest());

        List<CORAddress> cORAddressList = cORAddressRepository.findAll();
        assertThat(cORAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCORAddresses() throws Exception {
        // Initialize the database
        cORAddressRepository.saveAndFlush(cORAddress);

        // Get all the cORAddressList
        restCORAddressMockMvc.perform(get("/api/c-or-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cORAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())));
    }

    @Test
    @Transactional
    public void getCORAddress() throws Exception {
        // Initialize the database
        cORAddressRepository.saveAndFlush(cORAddress);

        // Get the cORAddress
        restCORAddressMockMvc.perform(get("/api/c-or-addresses/{id}", cORAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cORAddress.getId().intValue()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCORAddress() throws Exception {
        // Get the cORAddress
        restCORAddressMockMvc.perform(get("/api/c-or-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCORAddress() throws Exception {
        // Initialize the database
        cORAddressRepository.saveAndFlush(cORAddress);
        int databaseSizeBeforeUpdate = cORAddressRepository.findAll().size();

        // Update the cORAddress
        CORAddress updatedCORAddress = cORAddressRepository.findOne(cORAddress.getId());
        updatedCORAddress
                .street(UPDATED_STREET)
                .number(UPDATED_NUMBER)
                .postalCode(UPDATED_POSTAL_CODE)
                .city(UPDATED_CITY)
                .country(UPDATED_COUNTRY);
        CORAddressDTO cORAddressDTO = cORAddressMapper.cORAddressToCORAddressDTO(updatedCORAddress);

        restCORAddressMockMvc.perform(put("/api/c-or-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORAddressDTO)))
            .andExpect(status().isOk());

        // Validate the CORAddress in the database
        List<CORAddress> cORAddressList = cORAddressRepository.findAll();
        assertThat(cORAddressList).hasSize(databaseSizeBeforeUpdate);
        CORAddress testCORAddress = cORAddressList.get(cORAddressList.size() - 1);
        assertThat(testCORAddress.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testCORAddress.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCORAddress.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testCORAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCORAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void updateNonExistingCORAddress() throws Exception {
        int databaseSizeBeforeUpdate = cORAddressRepository.findAll().size();

        // Create the CORAddress
        CORAddressDTO cORAddressDTO = cORAddressMapper.cORAddressToCORAddressDTO(cORAddress);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCORAddressMockMvc.perform(put("/api/c-or-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORAddressDTO)))
            .andExpect(status().isCreated());

        // Validate the CORAddress in the database
        List<CORAddress> cORAddressList = cORAddressRepository.findAll();
        assertThat(cORAddressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCORAddress() throws Exception {
        // Initialize the database
        cORAddressRepository.saveAndFlush(cORAddress);
        int databaseSizeBeforeDelete = cORAddressRepository.findAll().size();

        // Get the cORAddress
        restCORAddressMockMvc.perform(delete("/api/c-or-addresses/{id}", cORAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CORAddress> cORAddressList = cORAddressRepository.findAll();
        assertThat(cORAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
