package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.TRAPrice;
import io.protone.repository.TRAPriceRepository;
import io.protone.service.dto.TRAPriceDTO;
import io.protone.service.mapper.TRAPriceMapper;

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
 * Test class for the TRAPriceResource REST controller.
 *
 * @see TRAPriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TRAPriceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final Integer DEFAULT_BASE_LENGTH = 1;
    private static final Integer UPDATED_BASE_LENGTH = 2;

    private static final BigDecimal DEFAULT_PRICE_ALTERNATIVE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_ALTERNATIVE = new BigDecimal(2);

    @Inject
    private TRAPriceRepository tRAPriceRepository;

    @Inject
    private TRAPriceMapper tRAPriceMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTRAPriceMockMvc;

    private TRAPrice tRAPrice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TRAPriceResource tRAPriceResource = new TRAPriceResource();
        ReflectionTestUtils.setField(tRAPriceResource, "tRAPriceRepository", tRAPriceRepository);
        ReflectionTestUtils.setField(tRAPriceResource, "tRAPriceMapper", tRAPriceMapper);
        this.restTRAPriceMockMvc = MockMvcBuilders.standaloneSetup(tRAPriceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TRAPrice createEntity(EntityManager em) {
        TRAPrice tRAPrice = new TRAPrice()
                .name(DEFAULT_NAME)
                .validFrom(DEFAULT_VALID_FROM)
                .validTo(DEFAULT_VALID_TO)
                .price(DEFAULT_PRICE)
                .baseLength(DEFAULT_BASE_LENGTH)
                .priceAlternative(DEFAULT_PRICE_ALTERNATIVE);
        return tRAPrice;
    }

    @Before
    public void initTest() {
        tRAPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createTRAPrice() throws Exception {
        int databaseSizeBeforeCreate = tRAPriceRepository.findAll().size();

        // Create the TRAPrice
        TRAPriceDTO tRAPriceDTO = tRAPriceMapper.tRAPriceToTRAPriceDTO(tRAPrice);

        restTRAPriceMockMvc.perform(post("/api/t-ra-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the TRAPrice in the database
        List<TRAPrice> tRAPriceList = tRAPriceRepository.findAll();
        assertThat(tRAPriceList).hasSize(databaseSizeBeforeCreate + 1);
        TRAPrice testTRAPrice = tRAPriceList.get(tRAPriceList.size() - 1);
        assertThat(testTRAPrice.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTRAPrice.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testTRAPrice.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testTRAPrice.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTRAPrice.getBaseLength()).isEqualTo(DEFAULT_BASE_LENGTH);
        assertThat(testTRAPrice.getPriceAlternative()).isEqualTo(DEFAULT_PRICE_ALTERNATIVE);
    }

    @Test
    @Transactional
    public void createTRAPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tRAPriceRepository.findAll().size();

        // Create the TRAPrice with an existing ID
        TRAPrice existingTRAPrice = new TRAPrice();
        existingTRAPrice.setId(1L);
        TRAPriceDTO existingTRAPriceDTO = tRAPriceMapper.tRAPriceToTRAPriceDTO(existingTRAPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTRAPriceMockMvc.perform(post("/api/t-ra-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTRAPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TRAPrice> tRAPriceList = tRAPriceRepository.findAll();
        assertThat(tRAPriceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tRAPriceRepository.findAll().size();
        // set the field null
        tRAPrice.setName(null);

        // Create the TRAPrice, which fails.
        TRAPriceDTO tRAPriceDTO = tRAPriceMapper.tRAPriceToTRAPriceDTO(tRAPrice);

        restTRAPriceMockMvc.perform(post("/api/t-ra-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAPriceDTO)))
            .andExpect(status().isBadRequest());

        List<TRAPrice> tRAPriceList = tRAPriceRepository.findAll();
        assertThat(tRAPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = tRAPriceRepository.findAll().size();
        // set the field null
        tRAPrice.setPrice(null);

        // Create the TRAPrice, which fails.
        TRAPriceDTO tRAPriceDTO = tRAPriceMapper.tRAPriceToTRAPriceDTO(tRAPrice);

        restTRAPriceMockMvc.perform(post("/api/t-ra-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAPriceDTO)))
            .andExpect(status().isBadRequest());

        List<TRAPrice> tRAPriceList = tRAPriceRepository.findAll();
        assertThat(tRAPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceAlternativeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tRAPriceRepository.findAll().size();
        // set the field null
        tRAPrice.setPriceAlternative(null);

        // Create the TRAPrice, which fails.
        TRAPriceDTO tRAPriceDTO = tRAPriceMapper.tRAPriceToTRAPriceDTO(tRAPrice);

        restTRAPriceMockMvc.perform(post("/api/t-ra-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAPriceDTO)))
            .andExpect(status().isBadRequest());

        List<TRAPrice> tRAPriceList = tRAPriceRepository.findAll();
        assertThat(tRAPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTRAPrices() throws Exception {
        // Initialize the database
        tRAPriceRepository.saveAndFlush(tRAPrice);

        // Get all the tRAPriceList
        restTRAPriceMockMvc.perform(get("/api/t-ra-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tRAPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].baseLength").value(hasItem(DEFAULT_BASE_LENGTH)))
            .andExpect(jsonPath("$.[*].priceAlternative").value(hasItem(DEFAULT_PRICE_ALTERNATIVE.intValue())));
    }

    @Test
    @Transactional
    public void getTRAPrice() throws Exception {
        // Initialize the database
        tRAPriceRepository.saveAndFlush(tRAPrice);

        // Get the tRAPrice
        restTRAPriceMockMvc.perform(get("/api/t-ra-prices/{id}", tRAPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tRAPrice.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.baseLength").value(DEFAULT_BASE_LENGTH))
            .andExpect(jsonPath("$.priceAlternative").value(DEFAULT_PRICE_ALTERNATIVE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTRAPrice() throws Exception {
        // Get the tRAPrice
        restTRAPriceMockMvc.perform(get("/api/t-ra-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTRAPrice() throws Exception {
        // Initialize the database
        tRAPriceRepository.saveAndFlush(tRAPrice);
        int databaseSizeBeforeUpdate = tRAPriceRepository.findAll().size();

        // Update the tRAPrice
        TRAPrice updatedTRAPrice = tRAPriceRepository.findOne(tRAPrice.getId());
        updatedTRAPrice
                .name(UPDATED_NAME)
                .validFrom(UPDATED_VALID_FROM)
                .validTo(UPDATED_VALID_TO)
                .price(UPDATED_PRICE)
                .baseLength(UPDATED_BASE_LENGTH)
                .priceAlternative(UPDATED_PRICE_ALTERNATIVE);
        TRAPriceDTO tRAPriceDTO = tRAPriceMapper.tRAPriceToTRAPriceDTO(updatedTRAPrice);

        restTRAPriceMockMvc.perform(put("/api/t-ra-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAPriceDTO)))
            .andExpect(status().isOk());

        // Validate the TRAPrice in the database
        List<TRAPrice> tRAPriceList = tRAPriceRepository.findAll();
        assertThat(tRAPriceList).hasSize(databaseSizeBeforeUpdate);
        TRAPrice testTRAPrice = tRAPriceList.get(tRAPriceList.size() - 1);
        assertThat(testTRAPrice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTRAPrice.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testTRAPrice.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testTRAPrice.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTRAPrice.getBaseLength()).isEqualTo(UPDATED_BASE_LENGTH);
        assertThat(testTRAPrice.getPriceAlternative()).isEqualTo(UPDATED_PRICE_ALTERNATIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingTRAPrice() throws Exception {
        int databaseSizeBeforeUpdate = tRAPriceRepository.findAll().size();

        // Create the TRAPrice
        TRAPriceDTO tRAPriceDTO = tRAPriceMapper.tRAPriceToTRAPriceDTO(tRAPrice);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTRAPriceMockMvc.perform(put("/api/t-ra-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the TRAPrice in the database
        List<TRAPrice> tRAPriceList = tRAPriceRepository.findAll();
        assertThat(tRAPriceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTRAPrice() throws Exception {
        // Initialize the database
        tRAPriceRepository.saveAndFlush(tRAPrice);
        int databaseSizeBeforeDelete = tRAPriceRepository.findAll().size();

        // Get the tRAPrice
        restTRAPriceMockMvc.perform(delete("/api/t-ra-prices/{id}", tRAPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TRAPrice> tRAPriceList = tRAPriceRepository.findAll();
        assertThat(tRAPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
