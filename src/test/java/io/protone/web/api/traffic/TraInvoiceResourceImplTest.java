package io.protone.web.api.traffic;

import io.protone.ProtoneApp;
import io.protone.web.rest.dto.traffic.TraInvoiceDTO;
import io.protone.custom.web.rest.network.TestUtil;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.domain.TraInvoice;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.repository.traffic.TraInvoiceRepository;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.traffic.TraInvoiceService;
import io.protone.web.api.crm.CrmCustomerResourceImplTest;
import io.protone.web.api.traffic.impl.TraInvoiceResourceImpl;
import io.protone.web.rest.errors.ExceptionTranslator;
import io.protone.web.rest.mapper.TraInvoiceMapper;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static io.protone.web.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
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
public class TraInvoiceResourceImplTest {

    private static final Boolean DEFAULT_PAID = false;
    private static final Boolean UPDATED_PAID = true;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final LocalDate DEFAULT_PAYMENT_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_DAY = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TraInvoiceRepository traInvoiceRepository;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private TraInvoiceService traInvoiceService;

    @Autowired
    private TraInvoiceMapper traInvoiceMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTraInvoiceMockMvc;

    private TraInvoice traInvoice;

    private CorNetwork corNetwork;

    private CrmAccount crmAccount;

    @Autowired
    private CrmAccountRepository crmAccountRepository;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraInvoice createEntity(EntityManager em) {
        TraInvoice traInvoice = new TraInvoice()
            .paid(DEFAULT_PAID)
            .price(DEFAULT_PRICE)
            .paymentDay(DEFAULT_PAYMENT_DAY);
        return traInvoice;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TraInvoiceResourceImpl traInvoiceResource = new TraInvoiceResourceImpl();

        ReflectionTestUtils.setField(traInvoiceResource, "traInvoiceService", traInvoiceService);
        ReflectionTestUtils.setField(traInvoiceResource, "traInvoiceMapper", traInvoiceMapper);
        ReflectionTestUtils.setField(traInvoiceResource, "corNetworkService", corNetworkService);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);

        this.restTraInvoiceMockMvc = MockMvcBuilders.standaloneSetup(traInvoiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        traInvoice = createEntity(em).network(corNetwork);
    }

    @Test
    @Transactional
    public void createTraInvoice() throws Exception {
        crmAccount = crmAccountRepository.save(CrmCustomerResourceImplTest.createEntity(em).network(corNetwork));
        int databaseSizeBeforeCreate = traInvoiceRepository.findAll().size();

        // Create the TraInvoice
        TraInvoiceDTO traInvoiceDTO = traInvoiceMapper.DB2DTO(traInvoice.customer(crmAccount));

        restTraInvoiceMockMvc.perform(post("/api/v1/network/{networkShortcut}/traffic/invoice", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traInvoiceDTO)))
            .andExpect(status().isCreated());

        // Validate the TraInvoice in the database
        List<TraInvoice> traInvoiceList = traInvoiceRepository.findAll();
        assertThat(traInvoiceList).hasSize(databaseSizeBeforeCreate + 1);
        TraInvoice testTraInvoice = traInvoiceList.get(traInvoiceList.size() - 1);
        assertThat(testTraInvoice.isPaid()).isEqualTo(DEFAULT_PAID);
        assertThat(testTraInvoice.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTraInvoice.getPaymentDay()).isEqualTo(DEFAULT_PAYMENT_DAY);
    }

    @Test
    @Transactional
    public void createTraInvoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traInvoiceRepository.findAll().size();

        // Create the TraInvoice with an existing ID
        TraInvoice existingTraInvoice = new TraInvoice();
        existingTraInvoice.setId(1L);
        TraInvoiceDTO existingTraInvoiceDTO = traInvoiceMapper.DB2DTO(existingTraInvoice.customer(crmAccount));

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraInvoiceMockMvc.perform(post("/api/v1/network/{networkShortcut}/traffic/invoice", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTraInvoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraInvoice> traInvoiceList = traInvoiceRepository.findAll();
        assertThat(traInvoiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTraInvoices() throws Exception {
        // Initialize the database
        traInvoiceRepository.saveAndFlush(traInvoice.network(corNetwork));

        // Get all the traInvoiceList
        restTraInvoiceMockMvc.perform(get("/api/v1/network/{networkShortcut}/traffic/invoice?sort=id,desc", corNetwork.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traInvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].paid").value(hasItem(DEFAULT_PAID.booleanValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].paymentDay").value(hasItem(DEFAULT_PAYMENT_DAY.toString())));
    }

    @Test
    @Transactional
    public void getTraInvoice() throws Exception {
        // Initialize the database
        traInvoiceRepository.saveAndFlush(traInvoice.network(corNetwork));

        // Get the traInvoice
        restTraInvoiceMockMvc.perform(get("/api/v1/network/{networkShortcut}/traffic/invoice/{id}", corNetwork.getShortcut(), traInvoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traInvoice.getId().intValue()))
            .andExpect(jsonPath("$.paid").value(DEFAULT_PAID.booleanValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.paymentDay").value(DEFAULT_PAYMENT_DAY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraInvoice() throws Exception {
        // Get the traInvoice
        restTraInvoiceMockMvc.perform(get("/api/v1/network/{networkShortcut}/traffic/invoice/{id}", corNetwork.getShortcut(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraInvoice() throws Exception {
        crmAccount = crmAccountRepository.save(CrmCustomerResourceImplTest.createEntity(em).network(corNetwork));
        // Initialize the database
        traInvoiceRepository.saveAndFlush(traInvoice.network(corNetwork).customer(crmAccount));
        int databaseSizeBeforeUpdate = traInvoiceRepository.findAll().size();

        // Update the traInvoice
        TraInvoice updatedTraInvoice = traInvoiceRepository.findOne(traInvoice.getId());
        updatedTraInvoice
            .paid(UPDATED_PAID)
            .price(UPDATED_PRICE)
            .paymentDay(UPDATED_PAYMENT_DAY);
        TraInvoiceDTO traInvoiceDTO = traInvoiceMapper.DB2DTO(updatedTraInvoice);

        restTraInvoiceMockMvc.perform(put("/api/v1/network/{networkShortcut}/traffic/invoice", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traInvoiceDTO)))
            .andExpect(status().isOk());

        // Validate the TraInvoice in the database
        List<TraInvoice> traInvoiceList = traInvoiceRepository.findAll();
        assertThat(traInvoiceList).hasSize(databaseSizeBeforeUpdate);
        TraInvoice testTraInvoice = traInvoiceList.get(traInvoiceList.size() - 1);
        assertThat(testTraInvoice.isPaid()).isEqualTo(UPDATED_PAID);
        assertThat(testTraInvoice.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTraInvoice.getPaymentDay()).isEqualTo(UPDATED_PAYMENT_DAY);
    }

    @Test
    @Transactional
    public void updateNonExistingTraInvoice() throws Exception {
        crmAccount = crmAccountRepository.save(CrmCustomerResourceImplTest.createEntity(em).network(corNetwork));

        int databaseSizeBeforeUpdate = traInvoiceRepository.findAll().size();

        // Create the TraInvoice
        TraInvoiceDTO traInvoiceDTO = traInvoiceMapper.DB2DTO(traInvoice.customer(crmAccount));

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraInvoiceMockMvc.perform(put("/api/v1/network/{networkShortcut}/traffic/invoice", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traInvoiceDTO)))
            .andExpect(status().isCreated());

        // Validate the TraInvoice in the database
        List<TraInvoice> traInvoiceList = traInvoiceRepository.findAll();
        assertThat(traInvoiceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraInvoice() throws Exception {

        // Initialize the database
        traInvoiceRepository.saveAndFlush(traInvoice.network(corNetwork));
        int databaseSizeBeforeDelete = traInvoiceRepository.findAll().size();

        // Get the traInvoice
        restTraInvoiceMockMvc.perform(delete("/api/v1/network/{networkShortcut}/traffic/invoice/{id}", corNetwork.getShortcut(), traInvoice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TraInvoice> traInvoiceList = traInvoiceRepository.findAll();
        assertThat(traInvoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void getAllTraInvoicesForCustomer() throws Exception {
        // Initialize the database
        crmAccount = crmAccountRepository.save(CrmCustomerResourceImplTest.createEntity(em).network(corNetwork));
        traInvoiceRepository.saveAndFlush(traInvoice.customer(crmAccount).network(corNetwork));

        // Get all the traInvoiceList
        restTraInvoiceMockMvc.perform(get("/api/v1/network/{networkShortcut}/traffic/invoice/customer/{customerShortcut}?sort=id,desc", corNetwork.getShortcut(),crmAccount.getShortName()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traInvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].paid").value(hasItem(DEFAULT_PAID.booleanValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].paymentDay").value(hasItem(DEFAULT_PAYMENT_DAY.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraInvoice.class);
    }
}
