package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.TRABlockPrice;
import io.protone.repository.TRABlockPriceRepository;
import io.protone.service.dto.TRABlockPriceDTO;
import io.protone.service.mapper.TRABlockPriceMapper;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TRABlockPriceResource REST controller.
 *
 * @see TRABlockPriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TRABlockPriceResourceIntTest {

    private static final BigDecimal DEFAULT_CALCULATED_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CALCULATED_PRICE = new BigDecimal(2);

    @Inject
    private TRABlockPriceRepository tRABlockPriceRepository;

    @Inject
    private TRABlockPriceMapper tRABlockPriceMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTRABlockPriceMockMvc;

    private TRABlockPrice tRABlockPrice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TRABlockPriceResource tRABlockPriceResource = new TRABlockPriceResource();
        ReflectionTestUtils.setField(tRABlockPriceResource, "tRABlockPriceRepository", tRABlockPriceRepository);
        ReflectionTestUtils.setField(tRABlockPriceResource, "tRABlockPriceMapper", tRABlockPriceMapper);
        this.restTRABlockPriceMockMvc = MockMvcBuilders.standaloneSetup(tRABlockPriceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TRABlockPrice createEntity(EntityManager em) {
        TRABlockPrice tRABlockPrice = new TRABlockPrice()
                .calculatedPrice(DEFAULT_CALCULATED_PRICE);
        return tRABlockPrice;
    }

    @Before
    public void initTest() {
        tRABlockPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createTRABlockPrice() throws Exception {
        int databaseSizeBeforeCreate = tRABlockPriceRepository.findAll().size();

        // Create the TRABlockPrice
        TRABlockPriceDTO tRABlockPriceDTO = tRABlockPriceMapper.tRABlockPriceToTRABlockPriceDTO(tRABlockPrice);

        restTRABlockPriceMockMvc.perform(post("/api/t-ra-block-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRABlockPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the TRABlockPrice in the database
        List<TRABlockPrice> tRABlockPriceList = tRABlockPriceRepository.findAll();
        assertThat(tRABlockPriceList).hasSize(databaseSizeBeforeCreate + 1);
        TRABlockPrice testTRABlockPrice = tRABlockPriceList.get(tRABlockPriceList.size() - 1);
        assertThat(testTRABlockPrice.getCalculatedPrice()).isEqualTo(DEFAULT_CALCULATED_PRICE);
    }

    @Test
    @Transactional
    public void createTRABlockPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tRABlockPriceRepository.findAll().size();

        // Create the TRABlockPrice with an existing ID
        TRABlockPrice existingTRABlockPrice = new TRABlockPrice();
        existingTRABlockPrice.setId(1L);
        TRABlockPriceDTO existingTRABlockPriceDTO = tRABlockPriceMapper.tRABlockPriceToTRABlockPriceDTO(existingTRABlockPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTRABlockPriceMockMvc.perform(post("/api/t-ra-block-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTRABlockPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TRABlockPrice> tRABlockPriceList = tRABlockPriceRepository.findAll();
        assertThat(tRABlockPriceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTRABlockPrices() throws Exception {
        // Initialize the database
        tRABlockPriceRepository.saveAndFlush(tRABlockPrice);

        // Get all the tRABlockPriceList
        restTRABlockPriceMockMvc.perform(get("/api/t-ra-block-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tRABlockPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].calculatedPrice").value(hasItem(DEFAULT_CALCULATED_PRICE.intValue())));
    }

    @Test
    @Transactional
    public void getTRABlockPrice() throws Exception {
        // Initialize the database
        tRABlockPriceRepository.saveAndFlush(tRABlockPrice);

        // Get the tRABlockPrice
        restTRABlockPriceMockMvc.perform(get("/api/t-ra-block-prices/{id}", tRABlockPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tRABlockPrice.getId().intValue()))
            .andExpect(jsonPath("$.calculatedPrice").value(DEFAULT_CALCULATED_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTRABlockPrice() throws Exception {
        // Get the tRABlockPrice
        restTRABlockPriceMockMvc.perform(get("/api/t-ra-block-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTRABlockPrice() throws Exception {
        // Initialize the database
        tRABlockPriceRepository.saveAndFlush(tRABlockPrice);
        int databaseSizeBeforeUpdate = tRABlockPriceRepository.findAll().size();

        // Update the tRABlockPrice
        TRABlockPrice updatedTRABlockPrice = tRABlockPriceRepository.findOne(tRABlockPrice.getId());
        updatedTRABlockPrice
                .calculatedPrice(UPDATED_CALCULATED_PRICE);
        TRABlockPriceDTO tRABlockPriceDTO = tRABlockPriceMapper.tRABlockPriceToTRABlockPriceDTO(updatedTRABlockPrice);

        restTRABlockPriceMockMvc.perform(put("/api/t-ra-block-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRABlockPriceDTO)))
            .andExpect(status().isOk());

        // Validate the TRABlockPrice in the database
        List<TRABlockPrice> tRABlockPriceList = tRABlockPriceRepository.findAll();
        assertThat(tRABlockPriceList).hasSize(databaseSizeBeforeUpdate);
        TRABlockPrice testTRABlockPrice = tRABlockPriceList.get(tRABlockPriceList.size() - 1);
        assertThat(testTRABlockPrice.getCalculatedPrice()).isEqualTo(UPDATED_CALCULATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingTRABlockPrice() throws Exception {
        int databaseSizeBeforeUpdate = tRABlockPriceRepository.findAll().size();

        // Create the TRABlockPrice
        TRABlockPriceDTO tRABlockPriceDTO = tRABlockPriceMapper.tRABlockPriceToTRABlockPriceDTO(tRABlockPrice);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTRABlockPriceMockMvc.perform(put("/api/t-ra-block-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRABlockPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the TRABlockPrice in the database
        List<TRABlockPrice> tRABlockPriceList = tRABlockPriceRepository.findAll();
        assertThat(tRABlockPriceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTRABlockPrice() throws Exception {
        // Initialize the database
        tRABlockPriceRepository.saveAndFlush(tRABlockPrice);
        int databaseSizeBeforeDelete = tRABlockPriceRepository.findAll().size();

        // Get the tRABlockPrice
        restTRABlockPriceMockMvc.perform(delete("/api/t-ra-block-prices/{id}", tRABlockPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TRABlockPrice> tRABlockPriceList = tRABlockPriceRepository.findAll();
        assertThat(tRABlockPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
