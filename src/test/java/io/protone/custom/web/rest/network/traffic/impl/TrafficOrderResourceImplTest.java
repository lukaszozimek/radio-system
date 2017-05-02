package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.custom.web.rest.network.TestUtil;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraOrder;
import io.protone.repository.traffic.TraOrderRepository;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.traffic.TraOrderService;
import io.protone.web.rest.errors.ExceptionTranslator;
import io.protone.web.rest.mapper.TraOrderMapper;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static io.protone.web.rest.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by lukaszozimek on 02/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TrafficOrderResourceImplTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CALCULATED_PRIZE = 1L;
    private static final Long UPDATED_CALCULATED_PRIZE = 2L;

    @Autowired
    private TraOrderRepository traOrderRepository;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private TraOrderMapper traOrderMapper;

    @Autowired
    private TraOrderService traOrderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTraOrderMockMvc;

    private TraOrder traOrder;

    private CorNetwork corNetwork;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ApiNetworkTrafficOrderImpl traOrderResource = new ApiNetworkTrafficOrderImpl();

        ReflectionTestUtils.setField(traOrderResource, "traOrderService", traOrderService);
        ReflectionTestUtils.setField(traOrderResource, "traOrderMapper", traOrderMapper);
        ReflectionTestUtils.setField(traOrderResource, "corNetworkService", corNetworkService);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);

        this.restTraOrderMockMvc = MockMvcBuilders.standaloneSetup(traOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraOrder createEntity(EntityManager em) {
        TraOrder traOrder = new TraOrder()
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .calculatedPrize(DEFAULT_CALCULATED_PRIZE);
        return traOrder;
    }

    @Before
    public void initTest() {
        traOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraOrder() throws Exception {
        int databaseSizeBeforeCreate = traOrderRepository.findAll().size();

        // Create the TraOrder
        TraOrderPT traOrderDTO = traOrderMapper.DB2DTO(traOrder);

        restTraOrderMockMvc.perform(post("/api/v1/network/{networkShortcut}/traffic/order",corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the TraOrder in the database
        List<TraOrder> traOrderList = traOrderRepository.findAll();
        assertThat(traOrderList).hasSize(databaseSizeBeforeCreate + 1);
        TraOrder testTraOrder = traOrderList.get(traOrderList.size() - 1);
        assertThat(testTraOrder.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTraOrder.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTraOrder.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTraOrder.getCalculatedPrize()).isEqualTo(DEFAULT_CALCULATED_PRIZE);
    }

    @Test
    @Transactional
    public void createTraOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traOrderRepository.findAll().size();

        // Create the TraOrder with an existing ID
        TraOrder existingTraOrder = new TraOrder();
        existingTraOrder.setId(1L);
        TraOrderPT existingTraOrderDTO = traOrderMapper.DB2DTO(existingTraOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraOrderMockMvc.perform(post("/api/v1/network/{networkShortcut}/traffic/order",corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTraOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraOrder> traOrderList = traOrderRepository.findAll();
        assertThat(traOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = traOrderRepository.findAll().size();
        // set the field null
        traOrder.setName(null);

        // Create the TraOrder, which fails.
        TraOrderPT traOrderDTO = traOrderMapper.DB2DTO(traOrder);

        restTraOrderMockMvc.perform(post("/api/v1/network/{networkShortcut}/traffic/order",corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traOrderDTO)))
            .andExpect(status().isBadRequest());

        List<TraOrder> traOrderList = traOrderRepository.findAll();
        assertThat(traOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTraOrders() throws Exception {
        // Initialize the database
        traOrderRepository.saveAndFlush(traOrder);

        // Get all the traOrderList
        restTraOrderMockMvc.perform(get("/api/v1/network/{networkShortcut}/traffic/order?sort=id,desc",corNetwork.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].calculatedPrize").value(hasItem(DEFAULT_CALCULATED_PRIZE.intValue())));
    }

    @Test
    @Transactional
    public void getTraOrder() throws Exception {
        // Initialize the database
        traOrderRepository.saveAndFlush(traOrder);

        // Get the traOrder
        restTraOrderMockMvc.perform(get("/api/v1/network/{networkShortcut}/traffic/order/{id}",corNetwork.getShortcut(), traOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traOrder.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.calculatedPrize").value(DEFAULT_CALCULATED_PRIZE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTraOrder() throws Exception {
        // Get the traOrder
        restTraOrderMockMvc.perform(get("/api/v1/network/{networkShortcut}/traffic/order/{id}",corNetwork.getShortcut(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraOrder() throws Exception {
        // Initialize the database
        traOrderRepository.saveAndFlush(traOrder);
        int databaseSizeBeforeUpdate = traOrderRepository.findAll().size();

        // Update the traOrder
        TraOrder updatedTraOrder = traOrderRepository.findOne(traOrder.getId());
        updatedTraOrder
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .calculatedPrize(UPDATED_CALCULATED_PRIZE);
        TraOrderPT traOrderDTO = traOrderMapper.DB2DTO(updatedTraOrder);

        restTraOrderMockMvc.perform(put("/api/v1/network/{networkShortcut}/traffic/order",corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traOrderDTO)))
            .andExpect(status().isOk());

        // Validate the TraOrder in the database
        List<TraOrder> traOrderList = traOrderRepository.findAll();
        assertThat(traOrderList).hasSize(databaseSizeBeforeUpdate);
        TraOrder testTraOrder = traOrderList.get(traOrderList.size() - 1);
        assertThat(testTraOrder.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraOrder.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTraOrder.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTraOrder.getCalculatedPrize()).isEqualTo(UPDATED_CALCULATED_PRIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingTraOrder() throws Exception {
        int databaseSizeBeforeUpdate = traOrderRepository.findAll().size();

        // Create the TraOrder
        TraOrderPT traOrderDTO = traOrderMapper.DB2DTO(traOrder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraOrderMockMvc.perform(put("/api/v1/network/{networkShortcut}/traffic/order",corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the TraOrder in the database
        List<TraOrder> traOrderList = traOrderRepository.findAll();
        assertThat(traOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraOrder() throws Exception {
        // Initialize the database
        traOrderRepository.saveAndFlush(traOrder);
        int databaseSizeBeforeDelete = traOrderRepository.findAll().size();

        // Get the traOrder
        restTraOrderMockMvc.perform(delete("/api/v1/network/{networkShortcut}/traffic/order/{id}",corNetwork.getShortcut(), traOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TraOrder> traOrderList = traOrderRepository.findAll();
        assertThat(traOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraOrder.class);
    }
}
