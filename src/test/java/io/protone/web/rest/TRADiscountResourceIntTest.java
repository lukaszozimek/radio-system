package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.TRADiscount;
import io.protone.repository.TRADiscountRepository;
import io.protone.service.dto.TRADiscountDTO;
import io.protone.service.mapper.TRADiscountMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TRADiscountResource REST controller.
 *
 * @see TRADiscountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TRADiscountResourceIntTest {

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_DISCOUNT = 1L;
    private static final Long UPDATED_DISCOUNT = 2L;

    @Inject
    private TRADiscountRepository tRADiscountRepository;

    @Inject
    private TRADiscountMapper tRADiscountMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTRADiscountMockMvc;

    private TRADiscount tRADiscount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TRADiscountResource tRADiscountResource = new TRADiscountResource();
        ReflectionTestUtils.setField(tRADiscountResource, "tRADiscountRepository", tRADiscountRepository);
        ReflectionTestUtils.setField(tRADiscountResource, "tRADiscountMapper", tRADiscountMapper);
        this.restTRADiscountMockMvc = MockMvcBuilders.standaloneSetup(tRADiscountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TRADiscount createEntity(EntityManager em) {
        TRADiscount tRADiscount = new TRADiscount()
                .validFrom(DEFAULT_VALID_FROM)
                .validTo(DEFAULT_VALID_TO)
                .discount(DEFAULT_DISCOUNT);
        return tRADiscount;
    }

    @Before
    public void initTest() {
        tRADiscount = createEntity(em);
    }

    @Test
    @Transactional
    public void createTRADiscount() throws Exception {
        int databaseSizeBeforeCreate = tRADiscountRepository.findAll().size();

        // Create the TRADiscount
        TRADiscountDTO tRADiscountDTO = tRADiscountMapper.tRADiscountToTRADiscountDTO(tRADiscount);

        restTRADiscountMockMvc.perform(post("/api/t-ra-discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRADiscountDTO)))
            .andExpect(status().isCreated());

        // Validate the TRADiscount in the database
        List<TRADiscount> tRADiscountList = tRADiscountRepository.findAll();
        assertThat(tRADiscountList).hasSize(databaseSizeBeforeCreate + 1);
        TRADiscount testTRADiscount = tRADiscountList.get(tRADiscountList.size() - 1);
        assertThat(testTRADiscount.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testTRADiscount.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testTRADiscount.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    public void createTRADiscountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tRADiscountRepository.findAll().size();

        // Create the TRADiscount with an existing ID
        TRADiscount existingTRADiscount = new TRADiscount();
        existingTRADiscount.setId(1L);
        TRADiscountDTO existingTRADiscountDTO = tRADiscountMapper.tRADiscountToTRADiscountDTO(existingTRADiscount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTRADiscountMockMvc.perform(post("/api/t-ra-discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTRADiscountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TRADiscount> tRADiscountList = tRADiscountRepository.findAll();
        assertThat(tRADiscountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTRADiscounts() throws Exception {
        // Initialize the database
        tRADiscountRepository.saveAndFlush(tRADiscount);

        // Get all the tRADiscountList
        restTRADiscountMockMvc.perform(get("/api/t-ra-discounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tRADiscount.getId().intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getTRADiscount() throws Exception {
        // Initialize the database
        tRADiscountRepository.saveAndFlush(tRADiscount);

        // Get the tRADiscount
        restTRADiscountMockMvc.perform(get("/api/t-ra-discounts/{id}", tRADiscount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tRADiscount.getId().intValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTRADiscount() throws Exception {
        // Get the tRADiscount
        restTRADiscountMockMvc.perform(get("/api/t-ra-discounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTRADiscount() throws Exception {
        // Initialize the database
        tRADiscountRepository.saveAndFlush(tRADiscount);
        int databaseSizeBeforeUpdate = tRADiscountRepository.findAll().size();

        // Update the tRADiscount
        TRADiscount updatedTRADiscount = tRADiscountRepository.findOne(tRADiscount.getId());
        updatedTRADiscount
                .validFrom(UPDATED_VALID_FROM)
                .validTo(UPDATED_VALID_TO)
                .discount(UPDATED_DISCOUNT);
        TRADiscountDTO tRADiscountDTO = tRADiscountMapper.tRADiscountToTRADiscountDTO(updatedTRADiscount);

        restTRADiscountMockMvc.perform(put("/api/t-ra-discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRADiscountDTO)))
            .andExpect(status().isOk());

        // Validate the TRADiscount in the database
        List<TRADiscount> tRADiscountList = tRADiscountRepository.findAll();
        assertThat(tRADiscountList).hasSize(databaseSizeBeforeUpdate);
        TRADiscount testTRADiscount = tRADiscountList.get(tRADiscountList.size() - 1);
        assertThat(testTRADiscount.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testTRADiscount.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testTRADiscount.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingTRADiscount() throws Exception {
        int databaseSizeBeforeUpdate = tRADiscountRepository.findAll().size();

        // Create the TRADiscount
        TRADiscountDTO tRADiscountDTO = tRADiscountMapper.tRADiscountToTRADiscountDTO(tRADiscount);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTRADiscountMockMvc.perform(put("/api/t-ra-discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRADiscountDTO)))
            .andExpect(status().isCreated());

        // Validate the TRADiscount in the database
        List<TRADiscount> tRADiscountList = tRADiscountRepository.findAll();
        assertThat(tRADiscountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTRADiscount() throws Exception {
        // Initialize the database
        tRADiscountRepository.saveAndFlush(tRADiscount);
        int databaseSizeBeforeDelete = tRADiscountRepository.findAll().size();

        // Get the tRADiscount
        restTRADiscountMockMvc.perform(delete("/api/t-ra-discounts/{id}", tRADiscount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TRADiscount> tRADiscountList = tRADiscountRepository.findAll();
        assertThat(tRADiscountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
