package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.TRAInvoice;
import io.protone.repository.TRAInvoiceRepository;
import io.protone.service.dto.TRAInvoiceDTO;
import io.protone.service.mapper.TRAInvoiceMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TRAInvoiceResource REST controller.
 *
 * @see TRAInvoiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TRAInvoiceResourceIntTest {

    private static final Boolean DEFAULT_PAID = false;
    private static final Boolean UPDATED_PAID = true;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final LocalDate DEFAULT_PAYMENT_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_DAY = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private TRAInvoiceRepository tRAInvoiceRepository;

    @Inject
    private TRAInvoiceMapper tRAInvoiceMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTRAInvoiceMockMvc;

    private TRAInvoice tRAInvoice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TRAInvoiceResource tRAInvoiceResource = new TRAInvoiceResource();
        ReflectionTestUtils.setField(tRAInvoiceResource, "tRAInvoiceRepository", tRAInvoiceRepository);
        ReflectionTestUtils.setField(tRAInvoiceResource, "tRAInvoiceMapper", tRAInvoiceMapper);
        this.restTRAInvoiceMockMvc = MockMvcBuilders.standaloneSetup(tRAInvoiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TRAInvoice createEntity(EntityManager em) {
        TRAInvoice tRAInvoice = new TRAInvoice()
                .paid(DEFAULT_PAID)
                .price(DEFAULT_PRICE)
                .paymentDay(DEFAULT_PAYMENT_DAY);
        return tRAInvoice;
    }

    @Before
    public void initTest() {
        tRAInvoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createTRAInvoice() throws Exception {
        int databaseSizeBeforeCreate = tRAInvoiceRepository.findAll().size();

        // Create the TRAInvoice
        TRAInvoiceDTO tRAInvoiceDTO = tRAInvoiceMapper.tRAInvoiceToTRAInvoiceDTO(tRAInvoice);

        restTRAInvoiceMockMvc.perform(post("/api/t-ra-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAInvoiceDTO)))
            .andExpect(status().isCreated());

        // Validate the TRAInvoice in the database
        List<TRAInvoice> tRAInvoiceList = tRAInvoiceRepository.findAll();
        assertThat(tRAInvoiceList).hasSize(databaseSizeBeforeCreate + 1);
        TRAInvoice testTRAInvoice = tRAInvoiceList.get(tRAInvoiceList.size() - 1);
        assertThat(testTRAInvoice.isPaid()).isEqualTo(DEFAULT_PAID);
        assertThat(testTRAInvoice.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTRAInvoice.getPaymentDay()).isEqualTo(DEFAULT_PAYMENT_DAY);
    }

    @Test
    @Transactional
    public void createTRAInvoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tRAInvoiceRepository.findAll().size();

        // Create the TRAInvoice with an existing ID
        TRAInvoice existingTRAInvoice = new TRAInvoice();
        existingTRAInvoice.setId(1L);
        TRAInvoiceDTO existingTRAInvoiceDTO = tRAInvoiceMapper.tRAInvoiceToTRAInvoiceDTO(existingTRAInvoice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTRAInvoiceMockMvc.perform(post("/api/t-ra-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTRAInvoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TRAInvoice> tRAInvoiceList = tRAInvoiceRepository.findAll();
        assertThat(tRAInvoiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTRAInvoices() throws Exception {
        // Initialize the database
        tRAInvoiceRepository.saveAndFlush(tRAInvoice);

        // Get all the tRAInvoiceList
        restTRAInvoiceMockMvc.perform(get("/api/t-ra-invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tRAInvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].paid").value(hasItem(DEFAULT_PAID.booleanValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].paymentDay").value(hasItem(DEFAULT_PAYMENT_DAY.toString())));
    }

    @Test
    @Transactional
    public void getTRAInvoice() throws Exception {
        // Initialize the database
        tRAInvoiceRepository.saveAndFlush(tRAInvoice);

        // Get the tRAInvoice
        restTRAInvoiceMockMvc.perform(get("/api/t-ra-invoices/{id}", tRAInvoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tRAInvoice.getId().intValue()))
            .andExpect(jsonPath("$.paid").value(DEFAULT_PAID.booleanValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.paymentDay").value(DEFAULT_PAYMENT_DAY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTRAInvoice() throws Exception {
        // Get the tRAInvoice
        restTRAInvoiceMockMvc.perform(get("/api/t-ra-invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTRAInvoice() throws Exception {
        // Initialize the database
        tRAInvoiceRepository.saveAndFlush(tRAInvoice);
        int databaseSizeBeforeUpdate = tRAInvoiceRepository.findAll().size();

        // Update the tRAInvoice
        TRAInvoice updatedTRAInvoice = tRAInvoiceRepository.findOne(tRAInvoice.getId());
        updatedTRAInvoice
                .paid(UPDATED_PAID)
                .price(UPDATED_PRICE)
                .paymentDay(UPDATED_PAYMENT_DAY);
        TRAInvoiceDTO tRAInvoiceDTO = tRAInvoiceMapper.tRAInvoiceToTRAInvoiceDTO(updatedTRAInvoice);

        restTRAInvoiceMockMvc.perform(put("/api/t-ra-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAInvoiceDTO)))
            .andExpect(status().isOk());

        // Validate the TRAInvoice in the database
        List<TRAInvoice> tRAInvoiceList = tRAInvoiceRepository.findAll();
        assertThat(tRAInvoiceList).hasSize(databaseSizeBeforeUpdate);
        TRAInvoice testTRAInvoice = tRAInvoiceList.get(tRAInvoiceList.size() - 1);
        assertThat(testTRAInvoice.isPaid()).isEqualTo(UPDATED_PAID);
        assertThat(testTRAInvoice.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTRAInvoice.getPaymentDay()).isEqualTo(UPDATED_PAYMENT_DAY);
    }

    @Test
    @Transactional
    public void updateNonExistingTRAInvoice() throws Exception {
        int databaseSizeBeforeUpdate = tRAInvoiceRepository.findAll().size();

        // Create the TRAInvoice
        TRAInvoiceDTO tRAInvoiceDTO = tRAInvoiceMapper.tRAInvoiceToTRAInvoiceDTO(tRAInvoice);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTRAInvoiceMockMvc.perform(put("/api/t-ra-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAInvoiceDTO)))
            .andExpect(status().isCreated());

        // Validate the TRAInvoice in the database
        List<TRAInvoice> tRAInvoiceList = tRAInvoiceRepository.findAll();
        assertThat(tRAInvoiceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTRAInvoice() throws Exception {
        // Initialize the database
        tRAInvoiceRepository.saveAndFlush(tRAInvoice);
        int databaseSizeBeforeDelete = tRAInvoiceRepository.findAll().size();

        // Get the tRAInvoice
        restTRAInvoiceMockMvc.perform(delete("/api/t-ra-invoices/{id}", tRAInvoice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TRAInvoice> tRAInvoiceList = tRAInvoiceRepository.findAll();
        assertThat(tRAInvoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
