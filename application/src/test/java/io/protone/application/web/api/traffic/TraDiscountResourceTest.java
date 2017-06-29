package io.protone.application.web.api.traffic;


import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.traffic.impl.TraDiscountResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorDiscount;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorDiscountRepository;
import io.protone.core.service.CorNetworkService;
import io.protone.traffic.api.dto.TraDiscountDTO;
import io.protone.traffic.mapper.TraDiscountMapper;
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

import static io.protone.application.web.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 01/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraDiscountResourceTest {
    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_DISCOUNT = 1L;
    private static final Long UPDATED_DISCOUNT = 2L;

    @Autowired
    private CorDiscountRepository traDiscountRepository;

    @Autowired
    private TraDiscountMapper traDiscountMapper;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTraDiscountMockMvc;

    private CorDiscount traDiscount;
    private CorNetwork corNetwork;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorDiscount createEntity(EntityManager em) {
        CorDiscount traDiscount = new CorDiscount()
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO)
            .discount(DEFAULT_DISCOUNT);
        return traDiscount;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TraDiscountResourceImpl traDiscountResource = new TraDiscountResourceImpl();

        ReflectionTestUtils.setField(traDiscountResource, "traDiscountRepository", traDiscountRepository);
        ReflectionTestUtils.setField(traDiscountResource, "traDiscountMapper", traDiscountMapper);
        ReflectionTestUtils.setField(traDiscountResource, "corNetworkService", corNetworkService);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);

        this.restTraDiscountMockMvc = MockMvcBuilders.standaloneSetup(traDiscountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        traDiscount = createEntity(em).network(corNetwork);
    }

    @Test
    @Transactional
    public void createTraDiscount() throws Exception {
        int databaseSizeBeforeCreate = traDiscountRepository.findAll().size();

        // Create the TraDiscount
        TraDiscountDTO traDiscountDTO = traDiscountMapper.DB2DTO(traDiscount);

        restTraDiscountMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/discount", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traDiscountDTO)))
            .andExpect(status().isCreated());

        // Validate the TraDiscount in the database
        List<CorDiscount> traDiscountList = traDiscountRepository.findAll();
        assertThat(traDiscountList).hasSize(databaseSizeBeforeCreate + 1);
        CorDiscount testTraDiscount = traDiscountList.get(traDiscountList.size() - 1);
        assertThat(testTraDiscount.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testTraDiscount.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testTraDiscount.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    public void createTraDiscountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traDiscountRepository.findAll().size();

        // Create the TraDiscount with an existing ID
        CorDiscount existingTraDiscount = new CorDiscount();
        existingTraDiscount.setId(1L);
        TraDiscountDTO existingTraDiscountDTO = traDiscountMapper.DB2DTO(existingTraDiscount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraDiscountMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/discount", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTraDiscountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorDiscount> traDiscountList = traDiscountRepository.findAll();
        assertThat(traDiscountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTraDiscounts() throws Exception {
        // Initialize the database
        traDiscountRepository.saveAndFlush(traDiscount.network(corNetwork));

        // Get all the traDiscountList
        restTraDiscountMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/discount?sort=id,desc", corNetwork.getShortcut()))
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
        traDiscountRepository.saveAndFlush(traDiscount.network(corNetwork));

        // Get the traDiscount
        restTraDiscountMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/discount/{id}", corNetwork.getShortcut(), traDiscount.getId()))
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
        restTraDiscountMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/discount/{id}", corNetwork.getShortcut(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraDiscount() throws Exception {
        // Initialize the database
        traDiscountRepository.saveAndFlush(traDiscount.network(corNetwork));
        int databaseSizeBeforeUpdate = traDiscountRepository.findAll().size();

        // Update the traDiscount
        CorDiscount updatedTraDiscount = traDiscountRepository.findOne(traDiscount.getId());
        updatedTraDiscount
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .discount(UPDATED_DISCOUNT);
        TraDiscountDTO traDiscountDTO = traDiscountMapper.DB2DTO(traDiscount);

        restTraDiscountMockMvc.perform(put("/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/discount", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traDiscountDTO)))
            .andExpect(status().isOk());

        // Validate the TraDiscount in the database
        List<CorDiscount> traDiscountList = traDiscountRepository.findAll();
        assertThat(traDiscountList).hasSize(databaseSizeBeforeUpdate);
        CorDiscount testTraDiscount = traDiscountList.get(traDiscountList.size() - 1);
        assertThat(testTraDiscount.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testTraDiscount.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testTraDiscount.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingTraDiscount() throws Exception {
        int databaseSizeBeforeUpdate = traDiscountRepository.findAll().size();

        // Create the TraDiscount
        TraDiscountDTO traDiscountDTO = traDiscountMapper.DB2DTO(traDiscount);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraDiscountMockMvc.perform(put("/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/discount", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traDiscountDTO)))
            .andExpect(status().isCreated());

        // Validate the TraDiscount in the database
        List<CorDiscount> traDiscountList = traDiscountRepository.findAll();
        assertThat(traDiscountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraDiscount() throws Exception {
        // Initialize the database
        traDiscountRepository.saveAndFlush(traDiscount.network(corNetwork));
        int databaseSizeBeforeDelete = traDiscountRepository.findAll().size();

        // Get the traDiscount
        restTraDiscountMockMvc.perform(delete("/api/v1/network/{networkShortcut}/configuration/traffic/dictionary/discount/{id}", corNetwork.getShortcut(), traDiscount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorDiscount> traDiscountList = traDiscountRepository.findAll();
        assertThat(traDiscountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorDiscount.class);
    }

}
