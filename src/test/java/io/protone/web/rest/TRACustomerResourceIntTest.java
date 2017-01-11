package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.TRACustomer;
import io.protone.repository.TRACustomerRepository;
import io.protone.service.dto.TRACustomerDTO;
import io.protone.service.mapper.TRACustomerMapper;

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
 * Test class for the TRACustomerResource REST controller.
 *
 * @see TRACustomerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TRACustomerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_SIZE = 1L;
    private static final Long UPDATED_SIZE = 2L;

    private static final Long DEFAULT_RANGE = 1L;
    private static final Long UPDATED_RANGE = 2L;

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_VAT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VAT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ID_NUMBER_1 = "AAAAAAAAAA";
    private static final String UPDATED_ID_NUMBER_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ID_NUMBER_2 = "AAAAAAAAAA";
    private static final String UPDATED_ID_NUMBER_2 = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAYMENT_DATE = 1;
    private static final Integer UPDATED_PAYMENT_DATE = 2;

    @Inject
    private TRACustomerRepository tRACustomerRepository;

    @Inject
    private TRACustomerMapper tRACustomerMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTRACustomerMockMvc;

    private TRACustomer tRACustomer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TRACustomerResource tRACustomerResource = new TRACustomerResource();
        ReflectionTestUtils.setField(tRACustomerResource, "tRACustomerRepository", tRACustomerRepository);
        ReflectionTestUtils.setField(tRACustomerResource, "tRACustomerMapper", tRACustomerMapper);
        this.restTRACustomerMockMvc = MockMvcBuilders.standaloneSetup(tRACustomerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TRACustomer createEntity(EntityManager em) {
        TRACustomer tRACustomer = new TRACustomer()
                .name(DEFAULT_NAME)
                .size(DEFAULT_SIZE)
                .range(DEFAULT_RANGE)
                .area(DEFAULT_AREA)
                .vatNumber(DEFAULT_VAT_NUMBER)
                .idNumber1(DEFAULT_ID_NUMBER_1)
                .idNumber2(DEFAULT_ID_NUMBER_2)
                .paymentDate(DEFAULT_PAYMENT_DATE);
        return tRACustomer;
    }

    @Before
    public void initTest() {
        tRACustomer = createEntity(em);
    }

    @Test
    @Transactional
    public void createTRACustomer() throws Exception {
        int databaseSizeBeforeCreate = tRACustomerRepository.findAll().size();

        // Create the TRACustomer
        TRACustomerDTO tRACustomerDTO = tRACustomerMapper.tRACustomerToTRACustomerDTO(tRACustomer);

        restTRACustomerMockMvc.perform(post("/api/t-ra-customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRACustomerDTO)))
            .andExpect(status().isCreated());

        // Validate the TRACustomer in the database
        List<TRACustomer> tRACustomerList = tRACustomerRepository.findAll();
        assertThat(tRACustomerList).hasSize(databaseSizeBeforeCreate + 1);
        TRACustomer testTRACustomer = tRACustomerList.get(tRACustomerList.size() - 1);
        assertThat(testTRACustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTRACustomer.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testTRACustomer.getRange()).isEqualTo(DEFAULT_RANGE);
        assertThat(testTRACustomer.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testTRACustomer.getVatNumber()).isEqualTo(DEFAULT_VAT_NUMBER);
        assertThat(testTRACustomer.getIdNumber1()).isEqualTo(DEFAULT_ID_NUMBER_1);
        assertThat(testTRACustomer.getIdNumber2()).isEqualTo(DEFAULT_ID_NUMBER_2);
        assertThat(testTRACustomer.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void createTRACustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tRACustomerRepository.findAll().size();

        // Create the TRACustomer with an existing ID
        TRACustomer existingTRACustomer = new TRACustomer();
        existingTRACustomer.setId(1L);
        TRACustomerDTO existingTRACustomerDTO = tRACustomerMapper.tRACustomerToTRACustomerDTO(existingTRACustomer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTRACustomerMockMvc.perform(post("/api/t-ra-customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTRACustomerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TRACustomer> tRACustomerList = tRACustomerRepository.findAll();
        assertThat(tRACustomerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tRACustomerRepository.findAll().size();
        // set the field null
        tRACustomer.setName(null);

        // Create the TRACustomer, which fails.
        TRACustomerDTO tRACustomerDTO = tRACustomerMapper.tRACustomerToTRACustomerDTO(tRACustomer);

        restTRACustomerMockMvc.perform(post("/api/t-ra-customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRACustomerDTO)))
            .andExpect(status().isBadRequest());

        List<TRACustomer> tRACustomerList = tRACustomerRepository.findAll();
        assertThat(tRACustomerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTRACustomers() throws Exception {
        // Initialize the database
        tRACustomerRepository.saveAndFlush(tRACustomer);

        // Get all the tRACustomerList
        restTRACustomerMockMvc.perform(get("/api/t-ra-customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tRACustomer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].range").value(hasItem(DEFAULT_RANGE.intValue())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.toString())))
            .andExpect(jsonPath("$.[*].vatNumber").value(hasItem(DEFAULT_VAT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].idNumber1").value(hasItem(DEFAULT_ID_NUMBER_1.toString())))
            .andExpect(jsonPath("$.[*].idNumber2").value(hasItem(DEFAULT_ID_NUMBER_2.toString())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE)));
    }

    @Test
    @Transactional
    public void getTRACustomer() throws Exception {
        // Initialize the database
        tRACustomerRepository.saveAndFlush(tRACustomer);

        // Get the tRACustomer
        restTRACustomerMockMvc.perform(get("/api/t-ra-customers/{id}", tRACustomer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tRACustomer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.intValue()))
            .andExpect(jsonPath("$.range").value(DEFAULT_RANGE.intValue()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.toString()))
            .andExpect(jsonPath("$.vatNumber").value(DEFAULT_VAT_NUMBER.toString()))
            .andExpect(jsonPath("$.idNumber1").value(DEFAULT_ID_NUMBER_1.toString()))
            .andExpect(jsonPath("$.idNumber2").value(DEFAULT_ID_NUMBER_2.toString()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE));
    }

    @Test
    @Transactional
    public void getNonExistingTRACustomer() throws Exception {
        // Get the tRACustomer
        restTRACustomerMockMvc.perform(get("/api/t-ra-customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTRACustomer() throws Exception {
        // Initialize the database
        tRACustomerRepository.saveAndFlush(tRACustomer);
        int databaseSizeBeforeUpdate = tRACustomerRepository.findAll().size();

        // Update the tRACustomer
        TRACustomer updatedTRACustomer = tRACustomerRepository.findOne(tRACustomer.getId());
        updatedTRACustomer
                .name(UPDATED_NAME)
                .size(UPDATED_SIZE)
                .range(UPDATED_RANGE)
                .area(UPDATED_AREA)
                .vatNumber(UPDATED_VAT_NUMBER)
                .idNumber1(UPDATED_ID_NUMBER_1)
                .idNumber2(UPDATED_ID_NUMBER_2)
                .paymentDate(UPDATED_PAYMENT_DATE);
        TRACustomerDTO tRACustomerDTO = tRACustomerMapper.tRACustomerToTRACustomerDTO(updatedTRACustomer);

        restTRACustomerMockMvc.perform(put("/api/t-ra-customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRACustomerDTO)))
            .andExpect(status().isOk());

        // Validate the TRACustomer in the database
        List<TRACustomer> tRACustomerList = tRACustomerRepository.findAll();
        assertThat(tRACustomerList).hasSize(databaseSizeBeforeUpdate);
        TRACustomer testTRACustomer = tRACustomerList.get(tRACustomerList.size() - 1);
        assertThat(testTRACustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTRACustomer.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testTRACustomer.getRange()).isEqualTo(UPDATED_RANGE);
        assertThat(testTRACustomer.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testTRACustomer.getVatNumber()).isEqualTo(UPDATED_VAT_NUMBER);
        assertThat(testTRACustomer.getIdNumber1()).isEqualTo(UPDATED_ID_NUMBER_1);
        assertThat(testTRACustomer.getIdNumber2()).isEqualTo(UPDATED_ID_NUMBER_2);
        assertThat(testTRACustomer.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTRACustomer() throws Exception {
        int databaseSizeBeforeUpdate = tRACustomerRepository.findAll().size();

        // Create the TRACustomer
        TRACustomerDTO tRACustomerDTO = tRACustomerMapper.tRACustomerToTRACustomerDTO(tRACustomer);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTRACustomerMockMvc.perform(put("/api/t-ra-customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRACustomerDTO)))
            .andExpect(status().isCreated());

        // Validate the TRACustomer in the database
        List<TRACustomer> tRACustomerList = tRACustomerRepository.findAll();
        assertThat(tRACustomerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTRACustomer() throws Exception {
        // Initialize the database
        tRACustomerRepository.saveAndFlush(tRACustomer);
        int databaseSizeBeforeDelete = tRACustomerRepository.findAll().size();

        // Get the tRACustomer
        restTRACustomerMockMvc.perform(delete("/api/t-ra-customers/{id}", tRACustomer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TRACustomer> tRACustomerList = tRACustomerRepository.findAll();
        assertThat(tRACustomerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
