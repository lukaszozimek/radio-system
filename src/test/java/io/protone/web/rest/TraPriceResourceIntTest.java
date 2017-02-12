package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.TraPrice;
import io.protone.repository.TraPriceRepository;
import io.protone.service.dto.TraPriceDTO;
import io.protone.service.mapper.TraPriceMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TraPriceResource REST controller.
 *
 * @see TraPriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraPriceResourceIntTest {

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

    @Autowired
    private TraPriceRepository traPriceRepository;

    @Autowired
    private TraPriceMapper traPriceMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTraPriceMockMvc;

    private TraPrice traPrice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TraPriceResource traPriceResource = new TraPriceResource(traPriceRepository, traPriceMapper);
        this.restTraPriceMockMvc = MockMvcBuilders.standaloneSetup(traPriceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraPrice createEntity(EntityManager em) {
        TraPrice traPrice = new TraPrice()
                .name(DEFAULT_NAME)
                .validFrom(DEFAULT_VALID_FROM)
                .validTo(DEFAULT_VALID_TO)
                .price(DEFAULT_PRICE)
                .baseLength(DEFAULT_BASE_LENGTH)
                .priceAlternative(DEFAULT_PRICE_ALTERNATIVE);
        return traPrice;
    }

    @Before
    public void initTest() {
        traPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraPrice() throws Exception {
        int databaseSizeBeforeCreate = traPriceRepository.findAll().size();

        // Create the TraPrice
        TraPriceDTO traPriceDTO = traPriceMapper.traPriceToTraPriceDTO(traPrice);

        restTraPriceMockMvc.perform(post("/api/tra-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the TraPrice in the database
        List<TraPrice> traPriceList = traPriceRepository.findAll();
        assertThat(traPriceList).hasSize(databaseSizeBeforeCreate + 1);
        TraPrice testTraPrice = traPriceList.get(traPriceList.size() - 1);
        assertThat(testTraPrice.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTraPrice.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testTraPrice.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testTraPrice.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTraPrice.getBaseLength()).isEqualTo(DEFAULT_BASE_LENGTH);
        assertThat(testTraPrice.getPriceAlternative()).isEqualTo(DEFAULT_PRICE_ALTERNATIVE);
    }

    @Test
    @Transactional
    public void createTraPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traPriceRepository.findAll().size();

        // Create the TraPrice with an existing ID
        TraPrice existingTraPrice = new TraPrice();
        existingTraPrice.setId(1L);
        TraPriceDTO existingTraPriceDTO = traPriceMapper.traPriceToTraPriceDTO(existingTraPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraPriceMockMvc.perform(post("/api/tra-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTraPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraPrice> traPriceList = traPriceRepository.findAll();
        assertThat(traPriceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = traPriceRepository.findAll().size();
        // set the field null
        traPrice.setName(null);

        // Create the TraPrice, which fails.
        TraPriceDTO traPriceDTO = traPriceMapper.traPriceToTraPriceDTO(traPrice);

        restTraPriceMockMvc.perform(post("/api/tra-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traPriceDTO)))
            .andExpect(status().isBadRequest());

        List<TraPrice> traPriceList = traPriceRepository.findAll();
        assertThat(traPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = traPriceRepository.findAll().size();
        // set the field null
        traPrice.setPrice(null);

        // Create the TraPrice, which fails.
        TraPriceDTO traPriceDTO = traPriceMapper.traPriceToTraPriceDTO(traPrice);

        restTraPriceMockMvc.perform(post("/api/tra-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traPriceDTO)))
            .andExpect(status().isBadRequest());

        List<TraPrice> traPriceList = traPriceRepository.findAll();
        assertThat(traPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceAlternativeIsRequired() throws Exception {
        int databaseSizeBeforeTest = traPriceRepository.findAll().size();
        // set the field null
        traPrice.setPriceAlternative(null);

        // Create the TraPrice, which fails.
        TraPriceDTO traPriceDTO = traPriceMapper.traPriceToTraPriceDTO(traPrice);

        restTraPriceMockMvc.perform(post("/api/tra-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traPriceDTO)))
            .andExpect(status().isBadRequest());

        List<TraPrice> traPriceList = traPriceRepository.findAll();
        assertThat(traPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTraPrices() throws Exception {
        // Initialize the database
        traPriceRepository.saveAndFlush(traPrice);

        // Get all the traPriceList
        restTraPriceMockMvc.perform(get("/api/tra-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].baseLength").value(hasItem(DEFAULT_BASE_LENGTH)))
            .andExpect(jsonPath("$.[*].priceAlternative").value(hasItem(DEFAULT_PRICE_ALTERNATIVE.intValue())));
    }

    @Test
    @Transactional
    public void getTraPrice() throws Exception {
        // Initialize the database
        traPriceRepository.saveAndFlush(traPrice);

        // Get the traPrice
        restTraPriceMockMvc.perform(get("/api/tra-prices/{id}", traPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traPrice.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.baseLength").value(DEFAULT_BASE_LENGTH))
            .andExpect(jsonPath("$.priceAlternative").value(DEFAULT_PRICE_ALTERNATIVE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTraPrice() throws Exception {
        // Get the traPrice
        restTraPriceMockMvc.perform(get("/api/tra-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraPrice() throws Exception {
        // Initialize the database
        traPriceRepository.saveAndFlush(traPrice);
        int databaseSizeBeforeUpdate = traPriceRepository.findAll().size();

        // Update the traPrice
        TraPrice updatedTraPrice = traPriceRepository.findOne(traPrice.getId());
        updatedTraPrice
                .name(UPDATED_NAME)
                .validFrom(UPDATED_VALID_FROM)
                .validTo(UPDATED_VALID_TO)
                .price(UPDATED_PRICE)
                .baseLength(UPDATED_BASE_LENGTH)
                .priceAlternative(UPDATED_PRICE_ALTERNATIVE);
        TraPriceDTO traPriceDTO = traPriceMapper.traPriceToTraPriceDTO(updatedTraPrice);

        restTraPriceMockMvc.perform(put("/api/tra-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traPriceDTO)))
            .andExpect(status().isOk());

        // Validate the TraPrice in the database
        List<TraPrice> traPriceList = traPriceRepository.findAll();
        assertThat(traPriceList).hasSize(databaseSizeBeforeUpdate);
        TraPrice testTraPrice = traPriceList.get(traPriceList.size() - 1);
        assertThat(testTraPrice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraPrice.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testTraPrice.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testTraPrice.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTraPrice.getBaseLength()).isEqualTo(UPDATED_BASE_LENGTH);
        assertThat(testTraPrice.getPriceAlternative()).isEqualTo(UPDATED_PRICE_ALTERNATIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingTraPrice() throws Exception {
        int databaseSizeBeforeUpdate = traPriceRepository.findAll().size();

        // Create the TraPrice
        TraPriceDTO traPriceDTO = traPriceMapper.traPriceToTraPriceDTO(traPrice);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraPriceMockMvc.perform(put("/api/tra-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the TraPrice in the database
        List<TraPrice> traPriceList = traPriceRepository.findAll();
        assertThat(traPriceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraPrice() throws Exception {
        // Initialize the database
        traPriceRepository.saveAndFlush(traPrice);
        int databaseSizeBeforeDelete = traPriceRepository.findAll().size();

        // Get the traPrice
        restTraPriceMockMvc.perform(delete("/api/tra-prices/{id}", traPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TraPrice> traPriceList = traPriceRepository.findAll();
        assertThat(traPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraPrice.class);
    }
}
