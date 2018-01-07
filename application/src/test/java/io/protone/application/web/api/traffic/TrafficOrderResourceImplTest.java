package io.protone.application.web.api.traffic;


import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.crm.CrmCustomerResourceImplTest;
import io.protone.application.web.api.library.LibMediaItemResourceTest;
import io.protone.application.web.api.traffic.impl.TraOrderResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.service.CorChannelService;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.repostiory.CrmAccountRepository;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.repository.LibLibraryRepository;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.traffic.api.dto.TraOrderDTO;
import io.protone.traffic.domain.TraAdvertisement;
import io.protone.traffic.domain.TraOrder;
import io.protone.traffic.mapper.TraOrderMapper;
import io.protone.traffic.repository.TraAdvertisementRepository;
import io.protone.traffic.repository.TraOrderRepository;
import io.protone.traffic.service.TraOrderService;
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
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static io.protone.application.util.TestConstans.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    private static final BigDecimal DEFAULT_CALCULATED_PRIZE = new BigDecimal(1L);
    private static final BigDecimal UPDATED_CALCULATED_PRIZE = new BigDecimal(2L);

    @Autowired
    private TraOrderRepository traOrderRepository;

    @Autowired
    private CorChannelService corChannelService;

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

    @Autowired
    private TraAdvertisementRepository traAdvertisementRepository;

    @Autowired
    private CrmAccountRepository crmAccountRepository;

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    private MockMvc restTraOrderMockMvc;

    private TraOrder traOrder;

    private TraAdvertisement traAdvertisement;

    private LibMediaItem libMediaItem;

    private LibMediaLibrary libMediaLibrary;

    private CrmAccount crmAccount;

    private PodamFactoryImpl factory;

    private CorOrganization corOrganization;

    private CorChannel corChannel;

    @Autowired
    private CorChannelRepository corChannelRepository;
    @Autowired
    private LibLibraryRepository libLibraryRepository;

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
    public void setup() {
        MockitoAnnotations.initMocks(this);
        factory = new PodamFactoryImpl();
        TraOrderResourceImpl traOrderResource = new TraOrderResourceImpl();


        traOrderRepository.deleteAllInBatch();
        traAdvertisementRepository.deleteAllInBatch();
        libMediaItemRepository.deleteAllInBatch();
        crmAccountRepository.deleteAllInBatch();

        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);

        libMediaLibrary = new LibMediaLibrary();
        libMediaLibrary.setId(1L);
        libMediaLibrary.setShortcut("tes");

        libMediaItem = LibMediaItemResourceTest.createEntity(em);
        libMediaItem.setChannel(corChannel);
        libMediaItem.setLibrary(libMediaLibrary);
        libMediaItem = libMediaItemRepository.saveAndFlush(libMediaItem);

        crmAccount = crmAccountRepository.saveAndFlush(CrmCustomerResourceImplTest.createEntity(em).channel(corChannel));
        traAdvertisement = traAdvertisementRepository.saveAndFlush(TraAdvertisementResourceImplTest.createEntity(em).mediaItem(Sets.newHashSet(libMediaItem)).customer(crmAccount).channel(corChannel));

        ReflectionTestUtils.setField(traOrderResource, "traOrderService", traOrderService);
        ReflectionTestUtils.setField(traOrderResource, "traOrderMapper", traOrderMapper);
        ReflectionTestUtils.setField(traOrderResource, "corChannelService", corChannelService);


        this.restTraOrderMockMvc = MockMvcBuilders.standaloneSetup(traOrderResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Test
    @Transactional
    public void createTraOrder() throws Exception {
        traOrder = createEntity(em).channel(corChannel).advertisment(traAdvertisement).customer(crmAccount);

        int databaseSizeBeforeCreate = traOrderRepository.findAll().size();

        // Create the TraOrder
        TraOrderDTO traOrderDTO = traOrderMapper.DB2DTO(traOrder);

        restTraOrderMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//traffic/order", corOrganization.getShortcut(), corChannel.getShortcut())
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
        traOrder = createEntity(em).channel(corChannel).advertisment(traAdvertisement).customer(crmAccount);

        int databaseSizeBeforeCreate = traOrderRepository.findAll().size();

        // Create the TraOrder with an existing ID
        TraOrder existingTraOrder = new TraOrder();
        existingTraOrder.setId(1L);
        TraOrderDTO existingTraOrderDTO = traOrderMapper.DB2DTO(existingTraOrder.channel(corChannel).advertisment(traAdvertisement).customer(crmAccount));

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraOrderMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//traffic/order", corOrganization.getShortcut(), corChannel.getShortcut())
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
        traOrder = createEntity(em).channel(corChannel).advertisment(traAdvertisement).customer(crmAccount);

        int databaseSizeBeforeTest = traOrderRepository.findAll().size();
        // set the field null
        traOrder.setName(null);

        // Create the TraOrder, which fails.
        TraOrderDTO traOrderDTO = traOrderMapper.DB2DTO(traOrder.channel(corChannel).advertisment(traAdvertisement).customer(crmAccount));

        restTraOrderMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//traffic/order", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traOrderDTO)))
                .andExpect(status().isBadRequest());

        List<TraOrder> traOrderList = traOrderRepository.findAll();
        assertThat(traOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustomerIsRequired() throws Exception {
        traOrder = createEntity(em).channel(corChannel).advertisment(traAdvertisement).customer(crmAccount);

        int databaseSizeBeforeTest = traOrderRepository.findAll().size();
        // set the field null
        traOrder.setCustomer(null);

        // Create the TraOrder, which fails.
        TraOrderDTO traOrderDTO = traOrderMapper.DB2DTO(traOrder);

        restTraOrderMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//traffic/order", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traOrderDTO)))
                .andExpect(status().isBadRequest());

        List<TraOrder> traOrderList = traOrderRepository.findAll();
        assertThat(traOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommercialIsRequired() throws Exception {
        traOrder = createEntity(em).channel(corChannel).advertisment(traAdvertisement).customer(crmAccount);

        int databaseSizeBeforeTest = traOrderRepository.findAll().size();
        // set the field null
        traOrder.setAdvertisment(null);

        // Create the TraOrder, which fails.
        TraOrderDTO traOrderDTO = traOrderMapper.DB2DTO(traOrder);

        restTraOrderMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//traffic/order", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traOrderDTO)))
                .andExpect(status().isBadRequest());

        List<TraOrder> traOrderList = traOrderRepository.findAll();
        assertThat(traOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTraOrders() throws Exception {
        traOrder = createEntity(em).channel(corChannel).advertisment(traAdvertisement).customer(crmAccount);

        // Initialize the database
        traOrderRepository.saveAndFlush(traOrder.channel(corChannel));

        // Get all the traOrderList
        restTraOrderMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//traffic/order?sort=id,desc", corOrganization.getShortcut(), corChannel.getShortcut()))
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
        traOrder = createEntity(em).channel(corChannel).advertisment(traAdvertisement).customer(crmAccount);

        // Initialize the database
        traOrderRepository.saveAndFlush(traOrder);

        // Get the traOrder
        restTraOrderMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//traffic/order/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), traOrder.getId()))
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
        traOrder = createEntity(em).channel(corChannel).advertisment(traAdvertisement).customer(crmAccount);

        // Get the traOrder
        restTraOrderMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//traffic/order/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraOrder() throws Exception {
        traOrder = createEntity(em).channel(corChannel).advertisment(traAdvertisement).customer(crmAccount);

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
        TraOrderDTO traOrderDTO = traOrderMapper.DB2DTO(updatedTraOrder);

        restTraOrderMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//traffic/order", corOrganization.getShortcut(), corChannel.getShortcut())
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
        traOrder = createEntity(em).channel(corChannel).advertisment(traAdvertisement).customer(crmAccount);

        int databaseSizeBeforeUpdate = traOrderRepository.findAll().size();

        // Create the TraOrder
        TraOrderDTO traOrderDTO = traOrderMapper.DB2DTO(traOrder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraOrderMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//traffic/order", corOrganization.getShortcut(), corChannel.getShortcut())
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
        traOrder = createEntity(em).channel(corChannel).advertisment(traAdvertisement).customer(crmAccount);

        // Initialize the database
        traOrderRepository.saveAndFlush(traOrder);
        int databaseSizeBeforeDelete = traOrderRepository.findAll().size();

        // Get the traOrder
        restTraOrderMockMvc.perform(delete("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//traffic/order/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), traOrder.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TraOrder> traOrderList = traOrderRepository.findAll();
        assertThat(traOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void getAllTraOrdersForCustomer() throws Exception {
        traOrder = createEntity(em).channel(corChannel).advertisment(traAdvertisement).customer(crmAccount);

        // Initialize the database
        traOrderRepository.saveAndFlush(traOrder);

        // Get all the traOrderList
        restTraOrderMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//traffic/order/customer/{customerShortcut}?sort=id,desc", corOrganization.getShortcut(), corChannel.getShortcut(), crmAccount.getShortName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(traOrder.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].calculatedPrize").value(hasItem(DEFAULT_CALCULATED_PRIZE.intValue())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraOrder.class);
    }
}
