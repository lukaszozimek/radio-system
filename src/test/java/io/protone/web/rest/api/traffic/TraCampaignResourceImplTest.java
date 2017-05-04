package io.protone.web.rest.api.traffic;

import io.protone.ProtoneApp;
import io.protone.web.rest.dto.traffic.TraCampaignDTO;
import io.protone.custom.web.rest.network.TestUtil;
import io.protone.web.rest.api.traffic.impl.TraCampaignResourceImpl;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.domain.TraCampaign;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.repository.traffic.TraCampaignRepository;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.traffic.TraCampaignService;
import io.protone.web.rest.api.crm.CrmCustomerResourceImplTest;
import io.protone.web.rest.errors.ExceptionTranslator;
import io.protone.web.rest.mapper.TraCampaignMapper;
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
///TODO: ShortName to campaign
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraCampaignResourceImplTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_PRIZE = 1L;
    private static final Long UPDATED_PRIZE = 2L;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private TraCampaignRepository traCampaignRepository;

    @Autowired
    private TraCampaignService traCampaignService;

    @Autowired
    private TraCampaignMapper traCampaignMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTraCampaignMockMvc;

    private TraCampaign traCampaign;

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
    public static TraCampaign createEntity(EntityManager em) {
        TraCampaign traCampaign = new TraCampaign()
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .prize(DEFAULT_PRIZE);
        return traCampaign;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TraCampaignResourceImpl traCampaignResource = new TraCampaignResourceImpl();

        ReflectionTestUtils.setField(traCampaignResource, "traCampaignService", traCampaignService);
        ReflectionTestUtils.setField(traCampaignResource, "traCampaignMapper", traCampaignMapper);
        ReflectionTestUtils.setField(traCampaignResource, "corNetworkService", corNetworkService);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);

        this.restTraCampaignMockMvc = MockMvcBuilders.standaloneSetup(traCampaignResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        traCampaign = createEntity(em).network(corNetwork);
    }

    @Test
    @Transactional
    public void createTraCampaign() throws Exception {

        crmAccount = crmAccountRepository.save(CrmCustomerResourceImplTest.createEntity(em).network(corNetwork));
        int databaseSizeBeforeCreate = traCampaignRepository.findAll().size();

        // Create the TraCampaign
        TraCampaignDTO traCampaignDTO = traCampaignMapper.DB2DTO(traCampaign.customer(crmAccount));

        restTraCampaignMockMvc.perform(post("/api/v1/network/{networkShortcut}/traffic/campaign", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traCampaignDTO)))
            .andExpect(status().isCreated());

        // Validate the TraCampaign in the database
        List<TraCampaign> traCampaignList = traCampaignRepository.findAll();
        assertThat(traCampaignList).hasSize(databaseSizeBeforeCreate + 1);
        TraCampaign testTraCampaign = traCampaignList.get(traCampaignList.size() - 1);
        assertThat(testTraCampaign.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTraCampaign.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTraCampaign.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTraCampaign.getPrize()).isEqualTo(DEFAULT_PRIZE);
    }

    @Test
    @Transactional
    public void createTraCampaignWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traCampaignRepository.findAll().size();

        crmAccount = crmAccountRepository.save(CrmCustomerResourceImplTest.createEntity(em).network(corNetwork));
        // Create the TraCampaign with an existing ID
        TraCampaign existingTraCampaign = new TraCampaign();
        existingTraCampaign.setId(1L);
        TraCampaignDTO existingTraCampaignDTO = traCampaignMapper.DB2DTO(existingTraCampaign.customer(crmAccount));

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraCampaignMockMvc.perform(post("/api/v1/network/{networkShortcut}/traffic/campaign", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTraCampaignDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraCampaign> traCampaignList = traCampaignRepository.findAll();
        assertThat(traCampaignList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = traCampaignRepository.findAll().size();
        // set the field null
        traCampaign.setName(null);

        // Create the TraCampaign, which fails.
        TraCampaignDTO traCampaignDTO = traCampaignMapper.DB2DTO(traCampaign);

        restTraCampaignMockMvc.perform(post("/api/v1/network/{networkShortcut}/traffic/campaign", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traCampaignDTO)))
            .andExpect(status().isBadRequest());

        List<TraCampaign> traCampaignList = traCampaignRepository.findAll();
        assertThat(traCampaignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTraCampaigns() throws Exception {
        // Initialize the database
        traCampaignRepository.saveAndFlush(traCampaign.network(corNetwork));

        // Get all the traCampaignList
        restTraCampaignMockMvc.perform(get("/api/v1/network/{networkShortcut}/traffic/campaign?sort=id,desc", corNetwork.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traCampaign.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].prize").value(hasItem(DEFAULT_PRIZE.intValue())));
    }

    @Test
    @Transactional
    public void getTraCampaign() throws Exception {
        // Initialize the database
        traCampaignRepository.saveAndFlush(traCampaign.network(corNetwork));

        // Get the traCampaign
        restTraCampaignMockMvc.perform(get("/api/v1/network/{networkShortcut}/traffic/campaign/{shortName}", corNetwork.getShortcut(), traCampaign.getName()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traCampaign.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.prize").value(DEFAULT_PRIZE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTraCampaign() throws Exception {
        // Get the traCampaign
        restTraCampaignMockMvc.perform(get("/api/v1/network/{networkShortcut}/traffic/campaign/{shortName}", corNetwork.getShortcut(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraCampaign() throws Exception {
        // Initialize the database

        crmAccount = crmAccountRepository.save(CrmCustomerResourceImplTest.createEntity(em).network(corNetwork));
        traCampaignRepository.saveAndFlush(traCampaign.network(corNetwork));
        int databaseSizeBeforeUpdate = traCampaignRepository.findAll().size();

        // Update the traCampaign
        TraCampaign updatedTraCampaign = traCampaignRepository.findOne(traCampaign.getId());
        updatedTraCampaign
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .prize(UPDATED_PRIZE);
        TraCampaignDTO traCampaignDTO = traCampaignMapper.DB2DTO(updatedTraCampaign.customer(crmAccount));

        restTraCampaignMockMvc.perform(put("/api/v1/network/{networkShortcut}/traffic/campaign", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traCampaignDTO)))
            .andExpect(status().isOk());

        // Validate the TraCampaign in the database
        List<TraCampaign> traCampaignList = traCampaignRepository.findAll();
        assertThat(traCampaignList).hasSize(databaseSizeBeforeUpdate);
        TraCampaign testTraCampaign = traCampaignList.get(traCampaignList.size() - 1);
        assertThat(testTraCampaign.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraCampaign.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTraCampaign.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTraCampaign.getPrize()).isEqualTo(UPDATED_PRIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingTraCampaign() throws Exception {

        crmAccount = crmAccountRepository.save(CrmCustomerResourceImplTest.createEntity(em).network(corNetwork));
        int databaseSizeBeforeUpdate = traCampaignRepository.findAll().size();

        // Create the TraCampaign
        TraCampaignDTO traCampaignDTO = traCampaignMapper.DB2DTO(traCampaign.customer(crmAccount));

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraCampaignMockMvc.perform(put("/api/v1/network/{networkShortcut}/traffic/campaign", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traCampaignDTO)))
            .andExpect(status().isCreated());

        // Validate the TraCampaign in the database
        List<TraCampaign> traCampaignList = traCampaignRepository.findAll();
        assertThat(traCampaignList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraCampaign() throws Exception {
        // Initialize the database
        traCampaignRepository.saveAndFlush(traCampaign.network(corNetwork).name("xxx"));
        int databaseSizeBeforeDelete = traCampaignRepository.findAll().size();

        // Get the traCampaign
        restTraCampaignMockMvc.perform(delete("/api/v1/network/{networkShortcut}/traffic/campaign/{shortName}", corNetwork.getShortcut(), "xxx")
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TraCampaign> traCampaignList = traCampaignRepository.findAll();
        assertThat(traCampaignList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraCampaign.class);
    }
}
