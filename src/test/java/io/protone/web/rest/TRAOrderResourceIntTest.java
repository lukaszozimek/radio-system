package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.TRAOrder;
import io.protone.repository.TRAOrderRepository;
import io.protone.service.dto.TRAOrderDTO;
import io.protone.service.mapper.TRAOrderMapper;

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
 * Test class for the TRAOrderResource REST controller.
 *
 * @see TRAOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TRAOrderResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CALCULATED_PRIZE = 1L;
    private static final Long UPDATED_CALCULATED_PRIZE = 2L;

    @Inject
    private TRAOrderRepository tRAOrderRepository;

    @Inject
    private TRAOrderMapper tRAOrderMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTRAOrderMockMvc;

    private TRAOrder tRAOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TRAOrderResource tRAOrderResource = new TRAOrderResource();
        ReflectionTestUtils.setField(tRAOrderResource, "tRAOrderRepository", tRAOrderRepository);
        ReflectionTestUtils.setField(tRAOrderResource, "tRAOrderMapper", tRAOrderMapper);
        this.restTRAOrderMockMvc = MockMvcBuilders.standaloneSetup(tRAOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TRAOrder createEntity(EntityManager em) {
        TRAOrder tRAOrder = new TRAOrder()
                .name(DEFAULT_NAME)
                .startDate(DEFAULT_START_DATE)
                .endDate(DEFAULT_END_DATE)
                .calculatedPrize(DEFAULT_CALCULATED_PRIZE);
        return tRAOrder;
    }

    @Before
    public void initTest() {
        tRAOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createTRAOrder() throws Exception {
        int databaseSizeBeforeCreate = tRAOrderRepository.findAll().size();

        // Create the TRAOrder
        TRAOrderDTO tRAOrderDTO = tRAOrderMapper.tRAOrderToTRAOrderDTO(tRAOrder);

        restTRAOrderMockMvc.perform(post("/api/t-ra-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the TRAOrder in the database
        List<TRAOrder> tRAOrderList = tRAOrderRepository.findAll();
        assertThat(tRAOrderList).hasSize(databaseSizeBeforeCreate + 1);
        TRAOrder testTRAOrder = tRAOrderList.get(tRAOrderList.size() - 1);
        assertThat(testTRAOrder.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTRAOrder.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTRAOrder.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTRAOrder.getCalculatedPrize()).isEqualTo(DEFAULT_CALCULATED_PRIZE);
    }

    @Test
    @Transactional
    public void createTRAOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tRAOrderRepository.findAll().size();

        // Create the TRAOrder with an existing ID
        TRAOrder existingTRAOrder = new TRAOrder();
        existingTRAOrder.setId(1L);
        TRAOrderDTO existingTRAOrderDTO = tRAOrderMapper.tRAOrderToTRAOrderDTO(existingTRAOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTRAOrderMockMvc.perform(post("/api/t-ra-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTRAOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TRAOrder> tRAOrderList = tRAOrderRepository.findAll();
        assertThat(tRAOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tRAOrderRepository.findAll().size();
        // set the field null
        tRAOrder.setName(null);

        // Create the TRAOrder, which fails.
        TRAOrderDTO tRAOrderDTO = tRAOrderMapper.tRAOrderToTRAOrderDTO(tRAOrder);

        restTRAOrderMockMvc.perform(post("/api/t-ra-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAOrderDTO)))
            .andExpect(status().isBadRequest());

        List<TRAOrder> tRAOrderList = tRAOrderRepository.findAll();
        assertThat(tRAOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTRAOrders() throws Exception {
        // Initialize the database
        tRAOrderRepository.saveAndFlush(tRAOrder);

        // Get all the tRAOrderList
        restTRAOrderMockMvc.perform(get("/api/t-ra-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tRAOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].calculatedPrize").value(hasItem(DEFAULT_CALCULATED_PRIZE.intValue())));
    }

    @Test
    @Transactional
    public void getTRAOrder() throws Exception {
        // Initialize the database
        tRAOrderRepository.saveAndFlush(tRAOrder);

        // Get the tRAOrder
        restTRAOrderMockMvc.perform(get("/api/t-ra-orders/{id}", tRAOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tRAOrder.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.calculatedPrize").value(DEFAULT_CALCULATED_PRIZE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTRAOrder() throws Exception {
        // Get the tRAOrder
        restTRAOrderMockMvc.perform(get("/api/t-ra-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTRAOrder() throws Exception {
        // Initialize the database
        tRAOrderRepository.saveAndFlush(tRAOrder);
        int databaseSizeBeforeUpdate = tRAOrderRepository.findAll().size();

        // Update the tRAOrder
        TRAOrder updatedTRAOrder = tRAOrderRepository.findOne(tRAOrder.getId());
        updatedTRAOrder
                .name(UPDATED_NAME)
                .startDate(UPDATED_START_DATE)
                .endDate(UPDATED_END_DATE)
                .calculatedPrize(UPDATED_CALCULATED_PRIZE);
        TRAOrderDTO tRAOrderDTO = tRAOrderMapper.tRAOrderToTRAOrderDTO(updatedTRAOrder);

        restTRAOrderMockMvc.perform(put("/api/t-ra-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAOrderDTO)))
            .andExpect(status().isOk());

        // Validate the TRAOrder in the database
        List<TRAOrder> tRAOrderList = tRAOrderRepository.findAll();
        assertThat(tRAOrderList).hasSize(databaseSizeBeforeUpdate);
        TRAOrder testTRAOrder = tRAOrderList.get(tRAOrderList.size() - 1);
        assertThat(testTRAOrder.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTRAOrder.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTRAOrder.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTRAOrder.getCalculatedPrize()).isEqualTo(UPDATED_CALCULATED_PRIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingTRAOrder() throws Exception {
        int databaseSizeBeforeUpdate = tRAOrderRepository.findAll().size();

        // Create the TRAOrder
        TRAOrderDTO tRAOrderDTO = tRAOrderMapper.tRAOrderToTRAOrderDTO(tRAOrder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTRAOrderMockMvc.perform(put("/api/t-ra-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the TRAOrder in the database
        List<TRAOrder> tRAOrderList = tRAOrderRepository.findAll();
        assertThat(tRAOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTRAOrder() throws Exception {
        // Initialize the database
        tRAOrderRepository.saveAndFlush(tRAOrder);
        int databaseSizeBeforeDelete = tRAOrderRepository.findAll().size();

        // Get the tRAOrder
        restTRAOrderMockMvc.perform(delete("/api/t-ra-orders/{id}", tRAOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TRAOrder> tRAOrderList = tRAOrderRepository.findAll();
        assertThat(tRAOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
