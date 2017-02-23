package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.TraDiscount;
import io.protone.repository.TraDiscountRepository;
import io.protone.service.dto.TraDiscountDTO;
import io.protone.service.mapper.TraDiscountMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TraDiscountResource REST controller.
 *
 * @see TraDiscountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraDiscountResourceIntTest {

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_DISCOUNT = 1L;
    private static final Long UPDATED_DISCOUNT = 2L;

    @Autowired
    private TraDiscountRepository traDiscountRepository;

    @Autowired
    private TraDiscountMapper traDiscountMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTraDiscountMockMvc;

    private TraDiscount traDiscount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TraDiscountResource traDiscountResource = new TraDiscountResource(traDiscountRepository, traDiscountMapper);
        this.restTraDiscountMockMvc = MockMvcBuilders.standaloneSetup(traDiscountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraDiscount createEntity(EntityManager em) {
        TraDiscount traDiscount = new TraDiscount()
                .validFrom(DEFAULT_VALID_FROM)
                .validTo(DEFAULT_VALID_TO)
                .discount(DEFAULT_DISCOUNT);
        return traDiscount;
    }

    @Before
    public void initTest() {
        traDiscount = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraDiscount() throws Exception {
        int databaseSizeBeforeCreate = traDiscountRepository.findAll().size();

        // Create the TraDiscount
        TraDiscountDTO traDiscountDTO = traDiscountMapper.traDiscountToTraDiscountDTO(traDiscount);

        restTraDiscountMockMvc.perform(post("/api/tra-discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traDiscountDTO)))
            .andExpect(status().isCreated());

        // Validate the TraDiscount in the database
        List<TraDiscount> traDiscountList = traDiscountRepository.findAll();
        assertThat(traDiscountList).hasSize(databaseSizeBeforeCreate + 1);
        TraDiscount testTraDiscount = traDiscountList.get(traDiscountList.size() - 1);
        assertThat(testTraDiscount.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testTraDiscount.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testTraDiscount.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    public void createTraDiscountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traDiscountRepository.findAll().size();

        // Create the TraDiscount with an existing ID
        TraDiscount existingTraDiscount = new TraDiscount();
        existingTraDiscount.setId(1L);
        TraDiscountDTO existingTraDiscountDTO = traDiscountMapper.traDiscountToTraDiscountDTO(existingTraDiscount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraDiscountMockMvc.perform(post("/api/tra-discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTraDiscountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraDiscount> traDiscountList = traDiscountRepository.findAll();
        assertThat(traDiscountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTraDiscounts() throws Exception {
        // Initialize the database
        traDiscountRepository.saveAndFlush(traDiscount);

        // Get all the traDiscountList
        restTraDiscountMockMvc.perform(get("/api/tra-discounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traDiscount.getId().intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getTraDiscount() throws Exception {
        // Initialize the database
        traDiscountRepository.saveAndFlush(traDiscount);

        // Get the traDiscount
        restTraDiscountMockMvc.perform(get("/api/tra-discounts/{id}", traDiscount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traDiscount.getId().intValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTraDiscount() throws Exception {
        // Get the traDiscount
        restTraDiscountMockMvc.perform(get("/api/tra-discounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraDiscount() throws Exception {
        // Initialize the database
        traDiscountRepository.saveAndFlush(traDiscount);
        int databaseSizeBeforeUpdate = traDiscountRepository.findAll().size();

        // Update the traDiscount
        TraDiscount updatedTraDiscount = traDiscountRepository.findOne(traDiscount.getId());
        updatedTraDiscount
                .validFrom(UPDATED_VALID_FROM)
                .validTo(UPDATED_VALID_TO)
                .discount(UPDATED_DISCOUNT);
        TraDiscountDTO traDiscountDTO = traDiscountMapper.traDiscountToTraDiscountDTO(updatedTraDiscount);

        restTraDiscountMockMvc.perform(put("/api/tra-discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traDiscountDTO)))
            .andExpect(status().isOk());

        // Validate the TraDiscount in the database
        List<TraDiscount> traDiscountList = traDiscountRepository.findAll();
        assertThat(traDiscountList).hasSize(databaseSizeBeforeUpdate);
        TraDiscount testTraDiscount = traDiscountList.get(traDiscountList.size() - 1);
        assertThat(testTraDiscount.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testTraDiscount.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testTraDiscount.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingTraDiscount() throws Exception {
        int databaseSizeBeforeUpdate = traDiscountRepository.findAll().size();

        // Create the TraDiscount
        TraDiscountDTO traDiscountDTO = traDiscountMapper.traDiscountToTraDiscountDTO(traDiscount);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraDiscountMockMvc.perform(put("/api/tra-discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traDiscountDTO)))
            .andExpect(status().isCreated());

        // Validate the TraDiscount in the database
        List<TraDiscount> traDiscountList = traDiscountRepository.findAll();
        assertThat(traDiscountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraDiscount() throws Exception {
        // Initialize the database
        traDiscountRepository.saveAndFlush(traDiscount);
        int databaseSizeBeforeDelete = traDiscountRepository.findAll().size();

        // Get the traDiscount
        restTraDiscountMockMvc.perform(delete("/api/tra-discounts/{id}", traDiscount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TraDiscount> traDiscountList = traDiscountRepository.findAll();
        assertThat(traDiscountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraDiscount.class);
    }
}
