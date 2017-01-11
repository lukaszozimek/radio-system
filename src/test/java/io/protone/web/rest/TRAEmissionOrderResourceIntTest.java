package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.TRAEmissionOrder;
import io.protone.repository.TRAEmissionOrderRepository;
import io.protone.service.dto.TRAEmissionOrderDTO;
import io.protone.service.mapper.TRAEmissionOrderMapper;

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
 * Test class for the TRAEmissionOrderResource REST controller.
 *
 * @see TRAEmissionOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TRAEmissionOrderResourceIntTest {

    private static final BigDecimal DEFAULT_CALCULATED_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CALCULATED_PRICE = new BigDecimal(2);

    @Inject
    private TRAEmissionOrderRepository tRAEmissionOrderRepository;

    @Inject
    private TRAEmissionOrderMapper tRAEmissionOrderMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTRAEmissionOrderMockMvc;

    private TRAEmissionOrder tRAEmissionOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TRAEmissionOrderResource tRAEmissionOrderResource = new TRAEmissionOrderResource();
        ReflectionTestUtils.setField(tRAEmissionOrderResource, "tRAEmissionOrderRepository", tRAEmissionOrderRepository);
        ReflectionTestUtils.setField(tRAEmissionOrderResource, "tRAEmissionOrderMapper", tRAEmissionOrderMapper);
        this.restTRAEmissionOrderMockMvc = MockMvcBuilders.standaloneSetup(tRAEmissionOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TRAEmissionOrder createEntity(EntityManager em) {
        TRAEmissionOrder tRAEmissionOrder = new TRAEmissionOrder()
                .calculatedPrice(DEFAULT_CALCULATED_PRICE);
        return tRAEmissionOrder;
    }

    @Before
    public void initTest() {
        tRAEmissionOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createTRAEmissionOrder() throws Exception {
        int databaseSizeBeforeCreate = tRAEmissionOrderRepository.findAll().size();

        // Create the TRAEmissionOrder
        TRAEmissionOrderDTO tRAEmissionOrderDTO = tRAEmissionOrderMapper.tRAEmissionOrderToTRAEmissionOrderDTO(tRAEmissionOrder);

        restTRAEmissionOrderMockMvc.perform(post("/api/t-ra-emission-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAEmissionOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the TRAEmissionOrder in the database
        List<TRAEmissionOrder> tRAEmissionOrderList = tRAEmissionOrderRepository.findAll();
        assertThat(tRAEmissionOrderList).hasSize(databaseSizeBeforeCreate + 1);
        TRAEmissionOrder testTRAEmissionOrder = tRAEmissionOrderList.get(tRAEmissionOrderList.size() - 1);
        assertThat(testTRAEmissionOrder.getCalculatedPrice()).isEqualTo(DEFAULT_CALCULATED_PRICE);
    }

    @Test
    @Transactional
    public void createTRAEmissionOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tRAEmissionOrderRepository.findAll().size();

        // Create the TRAEmissionOrder with an existing ID
        TRAEmissionOrder existingTRAEmissionOrder = new TRAEmissionOrder();
        existingTRAEmissionOrder.setId(1L);
        TRAEmissionOrderDTO existingTRAEmissionOrderDTO = tRAEmissionOrderMapper.tRAEmissionOrderToTRAEmissionOrderDTO(existingTRAEmissionOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTRAEmissionOrderMockMvc.perform(post("/api/t-ra-emission-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTRAEmissionOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TRAEmissionOrder> tRAEmissionOrderList = tRAEmissionOrderRepository.findAll();
        assertThat(tRAEmissionOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTRAEmissionOrders() throws Exception {
        // Initialize the database
        tRAEmissionOrderRepository.saveAndFlush(tRAEmissionOrder);

        // Get all the tRAEmissionOrderList
        restTRAEmissionOrderMockMvc.perform(get("/api/t-ra-emission-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tRAEmissionOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].calculatedPrice").value(hasItem(DEFAULT_CALCULATED_PRICE.intValue())));
    }

    @Test
    @Transactional
    public void getTRAEmissionOrder() throws Exception {
        // Initialize the database
        tRAEmissionOrderRepository.saveAndFlush(tRAEmissionOrder);

        // Get the tRAEmissionOrder
        restTRAEmissionOrderMockMvc.perform(get("/api/t-ra-emission-orders/{id}", tRAEmissionOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tRAEmissionOrder.getId().intValue()))
            .andExpect(jsonPath("$.calculatedPrice").value(DEFAULT_CALCULATED_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTRAEmissionOrder() throws Exception {
        // Get the tRAEmissionOrder
        restTRAEmissionOrderMockMvc.perform(get("/api/t-ra-emission-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTRAEmissionOrder() throws Exception {
        // Initialize the database
        tRAEmissionOrderRepository.saveAndFlush(tRAEmissionOrder);
        int databaseSizeBeforeUpdate = tRAEmissionOrderRepository.findAll().size();

        // Update the tRAEmissionOrder
        TRAEmissionOrder updatedTRAEmissionOrder = tRAEmissionOrderRepository.findOne(tRAEmissionOrder.getId());
        updatedTRAEmissionOrder
                .calculatedPrice(UPDATED_CALCULATED_PRICE);
        TRAEmissionOrderDTO tRAEmissionOrderDTO = tRAEmissionOrderMapper.tRAEmissionOrderToTRAEmissionOrderDTO(updatedTRAEmissionOrder);

        restTRAEmissionOrderMockMvc.perform(put("/api/t-ra-emission-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAEmissionOrderDTO)))
            .andExpect(status().isOk());

        // Validate the TRAEmissionOrder in the database
        List<TRAEmissionOrder> tRAEmissionOrderList = tRAEmissionOrderRepository.findAll();
        assertThat(tRAEmissionOrderList).hasSize(databaseSizeBeforeUpdate);
        TRAEmissionOrder testTRAEmissionOrder = tRAEmissionOrderList.get(tRAEmissionOrderList.size() - 1);
        assertThat(testTRAEmissionOrder.getCalculatedPrice()).isEqualTo(UPDATED_CALCULATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingTRAEmissionOrder() throws Exception {
        int databaseSizeBeforeUpdate = tRAEmissionOrderRepository.findAll().size();

        // Create the TRAEmissionOrder
        TRAEmissionOrderDTO tRAEmissionOrderDTO = tRAEmissionOrderMapper.tRAEmissionOrderToTRAEmissionOrderDTO(tRAEmissionOrder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTRAEmissionOrderMockMvc.perform(put("/api/t-ra-emission-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRAEmissionOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the TRAEmissionOrder in the database
        List<TRAEmissionOrder> tRAEmissionOrderList = tRAEmissionOrderRepository.findAll();
        assertThat(tRAEmissionOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTRAEmissionOrder() throws Exception {
        // Initialize the database
        tRAEmissionOrderRepository.saveAndFlush(tRAEmissionOrder);
        int databaseSizeBeforeDelete = tRAEmissionOrderRepository.findAll().size();

        // Get the tRAEmissionOrder
        restTRAEmissionOrderMockMvc.perform(delete("/api/t-ra-emission-orders/{id}", tRAEmissionOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TRAEmissionOrder> tRAEmissionOrderList = tRAEmissionOrderRepository.findAll();
        assertThat(tRAEmissionOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
