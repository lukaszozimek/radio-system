package io.protone.application.web.api.traffic;

import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.traffic.impl.TraPriceResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.service.CorChannelService;
import io.protone.traffic.api.dto.TraPriceDTO;
import io.protone.traffic.domain.TraPrice;
import io.protone.traffic.mapper.TraPriceMapper;
import io.protone.traffic.repository.TraPriceRepository;
import io.protone.traffic.service.TraPriceService;
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
import java.util.List;

import static io.protone.application.util.TestConstans.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 11/08/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraPriceResourceImplTest {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.now();
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now().plusDays(1);

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.now();
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now().plusDays(1);

    private static final BigDecimal DEFAULT_BASE_PRICE = new BigDecimal(1L);
    private static final BigDecimal UPDATED_BASE_PRICE = new BigDecimal(2L);

    private static final BigDecimal DEFAULT_PRICE_LAST_POSTION = new BigDecimal(1L);
    private static final BigDecimal UPDATED_PRICE_LAST_POSTION = new BigDecimal(2L);

    private static final BigDecimal DEFAULT_PRICE_FIRST_POSTION = new BigDecimal(1L);
    private static final BigDecimal UPDATED_PRICE_FIRST_POSTION = new BigDecimal(2L);


    private static final Long DEFAULT_BASE_LENGHT = 1L;
    private static final Long UPDATED_BASE_LENGHT = 2L;

    @Autowired
    private TraPriceRepository traPriceRepository;

    @Autowired
    private TraPriceMapper traPriceMapper;

    @Autowired
    private CorChannelService corChannelService;

    @Autowired
    private TraPriceService traPriceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTraMediaPlantMockMvc;

    private CorOrganization corOrganization;

    private CorChannel corChannel;

    private TraPrice traPrice;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraPrice createEntity(EntityManager em) {

        return new TraPrice().name(DEFAULT_NAME).validFrom(DEFAULT_VALID_FROM)
                .validTo(DEFAULT_VALID_TO)
                .basePrice(DEFAULT_BASE_PRICE)
                .priceLastPostion(DEFAULT_PRICE_LAST_POSTION)
                .priceFirstPostion(DEFAULT_PRICE_FIRST_POSTION)
                .baseLength(DEFAULT_BASE_LENGHT);

    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TraPriceResourceImpl traMediaPlanTemplateResource = new TraPriceResourceImpl();

        ReflectionTestUtils.setField(traMediaPlanTemplateResource, "corChannelService", corChannelService);
        ReflectionTestUtils.setField(traMediaPlanTemplateResource, "traPriceService", traPriceService);
        ReflectionTestUtils.setField(traMediaPlanTemplateResource, "traPriceMapper", traPriceMapper);

        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);

        this.restTraMediaPlantMockMvc = MockMvcBuilders.standaloneSetup(traMediaPlanTemplateResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        traPrice = createEntity(em).channel(corChannel);
    }

    @Test
    @Transactional
    public void createTraPrice() throws Exception {
        int databaseSizeBeforeCreate = traPriceRepository.findAll().size();

        // Create the TraDiscount
        TraPriceDTO traMediaPlanTemplateDTO = traPriceMapper.DB2DTO(traPrice);

        restTraMediaPlantMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//configuration/traffic/price", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traMediaPlanTemplateDTO)))
                .andExpect(status().isCreated());

        // Validate the TraDiscount in the database
        List<TraPrice> traMediaPlanList = traPriceRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeCreate + 1);
        TraPrice testTraDiscount = traMediaPlanList.get(traMediaPlanList.size() - 1);

    }

    @Test
    @Transactional
    public void createTraPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traPriceRepository.findAll().size();

        // Create the TraDiscount with an existing ID
        TraPrice existingTraDiscount = new TraPrice();
        existingTraDiscount.setId(1L);
        TraPriceDTO existingTraPriceDTO = traPriceMapper.DB2DTO(existingTraDiscount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraMediaPlantMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//configuration/traffic/price", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingTraPriceDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraPrice> traMediaPlanList = traPriceRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTraPrice() throws Exception {
        // Initialize the database
        traPriceRepository.saveAndFlush(traPrice.channel(corChannel));

        // Get all the traMediaPlanList
        restTraMediaPlantMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//configuration/traffic/price?sort=id,desc", corOrganization.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(traPrice.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
                .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

    }

    @Test
    @Transactional
    public void getTraPrice() throws Exception {
        // Initialize the database
        traPriceRepository.saveAndFlush(traPrice.channel(corChannel));

        // Get the traPrice
        restTraMediaPlantMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//configuration/traffic/price/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), traPrice.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(traPrice.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.priceLastPostion").value(DEFAULT_PRICE_LAST_POSTION.toString()))
                .andExpect(jsonPath("$.basePrice").value(DEFAULT_BASE_PRICE.toString()))
                .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
                .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()))
                .andExpect(jsonPath("$.priceFirstPostion").value(DEFAULT_PRICE_FIRST_POSTION.toString()))
                .andExpect(jsonPath("$.baseLength").value(DEFAULT_BASE_LENGHT.toString()));


    }

    @Test
    @Transactional
    public void getNonExistingTraPrice() throws Exception {
        // Get the traPrice
        restTraMediaPlantMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//configuration/traffic/price/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraPrice() throws Exception {
        // Initialize the database
        traPriceRepository.saveAndFlush(traPrice.channel(corChannel));
        int databaseSizeBeforeUpdate = traPriceRepository.findAll().size();

        // Update the traPrice
        TraPrice traMediaPlanTemplate = traPriceRepository.findOne(this.traPrice.getId());
        traMediaPlanTemplate.name(UPDATED_NAME)
                .validFrom(UPDATED_VALID_FROM).validTo(UPDATED_VALID_TO)
                .basePrice(UPDATED_BASE_PRICE)
                .priceLastPostion(UPDATED_PRICE_LAST_POSTION)
                .priceFirstPostion(UPDATED_PRICE_FIRST_POSTION)
                .baseLength(UPDATED_BASE_LENGHT);
        TraPriceDTO traMediaPlanTemplateDTO = traPriceMapper.DB2DTO(this.traPrice);

        restTraMediaPlantMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//configuration/traffic/price", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traMediaPlanTemplateDTO)))
                .andExpect(status().isOk());

        // Validate the TraDiscount in the database
        List<TraPrice> traCompanies = traPriceRepository.findAll();
        assertThat(traCompanies).hasSize(databaseSizeBeforeUpdate);
        TraPrice traPrice = traCompanies.get(traCompanies.size() - 1);
        assertEquals(traPrice.getName(), UPDATED_NAME);
        assertEquals(traPrice.getValidFrom(), UPDATED_VALID_FROM);
        assertEquals(traPrice.getValidTo(), UPDATED_VALID_TO);
        assertEquals(traPrice.getBasePrice(), UPDATED_BASE_PRICE);
        assertEquals(traPrice.getPriceLastPostion(), UPDATED_PRICE_LAST_POSTION);
        assertEquals(traPrice.getPriceFirstPostion(), UPDATED_PRICE_FIRST_POSTION);
        assertEquals(traPrice.getBaseLength(), UPDATED_BASE_LENGHT);

    }

    @Test
    @Transactional
    public void updateNonExistingTraPrice() throws Exception {
        int databaseSizeBeforeUpdate = traPriceRepository.findAll().size();

        // Create the TraDiscount
        TraPriceDTO traMediaPlanTemplateDTO = traPriceMapper.DB2DTO(traPrice);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraMediaPlantMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//configuration/traffic/price", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traMediaPlanTemplateDTO)))
                .andExpect(status().isCreated());

        // Validate the TraDiscount in the database
        List<TraPrice> traCompanies = traPriceRepository.findAll();
        assertThat(traCompanies).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraPrice() throws Exception {
        // Initialize the database
        traPriceRepository.saveAndFlush(traPrice.channel(corChannel));
        int databaseSizeBeforeDelete = traPriceRepository.findAll().size();

        // Get the traPrice
        restTraMediaPlantMockMvc.perform(delete("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}//configuration/traffic/price/{id}", corOrganization.getShortcut(), corChannel.getShortcut(), traPrice.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TraPrice> traMediaPlanList = traPriceRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }


}