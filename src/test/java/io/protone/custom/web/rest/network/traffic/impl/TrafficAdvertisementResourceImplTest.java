package io.protone.custom.web.rest.network.traffic.impl;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.web.rest.network.TestUtil;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraAdvertisement;
import io.protone.repository.traffic.TraAdvertisementRepository;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.traffic.TraAdvertisementService;
import io.protone.web.rest.errors.ExceptionTranslator;
import io.protone.web.rest.mapper.TraAdvertisementMapper;
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
public class TrafficAdvertisementResourceImplTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";
    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private TraAdvertisementRepository traAdvertisementRepository;

    @Autowired
    private TraAdvertisementMapper traAdvertisementMapper;
    @Autowired
    private TraAdvertisementService traAdvertisementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTraAdvertisementMockMvc;

    private TraAdvertisement traAdvertisement;

    private CorNetwork corNetwork;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ApiNetworkTrafficAdvertisementImpl traAdvertisementResource = new ApiNetworkTrafficAdvertisementImpl();
        ReflectionTestUtils.setField(traAdvertisementResource, "traAdvertisementService", traAdvertisementService);
        ReflectionTestUtils.setField(traAdvertisementResource, "traAdvertisementMapper", traAdvertisementMapper);
        ReflectionTestUtils.setField(traAdvertisementResource, "corNetworkService", corNetworkService);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);

        this.restTraAdvertisementMockMvc = MockMvcBuilders.standaloneSetup(traAdvertisementResource)
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
    public static TraAdvertisement createEntity(EntityManager em) {
        TraAdvertisement traAdvertisement = new TraAdvertisement()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return traAdvertisement;
    }

    @Before
    public void initTest() {
        traAdvertisement = createEntity(em).network(corNetwork);
    }

    @Test
    @Transactional
    public void createTraAdvertisement() throws Exception {
        int databaseSizeBeforeCreate = traAdvertisementRepository.findAll().size();

        // Create the TraAdvertisement
        TraAdvertisementPT traAdvertisementDTO = traAdvertisementMapper.DB2DTO(traAdvertisement);

        restTraAdvertisementMockMvc.perform(post("/api/v1/network/{networkShortcut}/traffic/advertisement",corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traAdvertisementDTO)))
            .andExpect(status().isCreated());

        // Validate the TraAdvertisement in the database
        List<TraAdvertisement> traAdvertisementList = traAdvertisementRepository.findAll();
        assertThat(traAdvertisementList).hasSize(databaseSizeBeforeCreate + 1);
        TraAdvertisement testTraAdvertisement = traAdvertisementList.get(traAdvertisementList.size() - 1);
        assertThat(testTraAdvertisement.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTraAdvertisement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTraAdvertisementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traAdvertisementRepository.findAll().size();

        // Create the TraAdvertisement with an existing ID
        TraAdvertisement existingTraAdvertisement = new TraAdvertisement();
        existingTraAdvertisement.setId(1L);
        TraAdvertisementPT existingTraAdvertisementDTO = traAdvertisementMapper.DB2DTO(existingTraAdvertisement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraAdvertisementMockMvc.perform(post("/api/v1/network/{networkShortcut}/traffic/advertisement",corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTraAdvertisementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraAdvertisement> traAdvertisementList = traAdvertisementRepository.findAll();
        assertThat(traAdvertisementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = traAdvertisementRepository.findAll().size();
        // set the field null
        traAdvertisement.setName(null);

        // Create the TraAdvertisement, which fails.
        TraAdvertisementPT traAdvertisementDTO = traAdvertisementMapper.DB2DTO(traAdvertisement);

        restTraAdvertisementMockMvc.perform(post("/api/v1/network/{networkShortcut}/traffic/advertisement",corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traAdvertisementDTO)))
            .andExpect(status().isBadRequest());

        List<TraAdvertisement> traAdvertisementList = traAdvertisementRepository.findAll();
        assertThat(traAdvertisementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTraAdvertisements() throws Exception {
        // Initialize the database
        traAdvertisementRepository.saveAndFlush(traAdvertisement.network(corNetwork));

        // Get all the traAdvertisementList
        restTraAdvertisementMockMvc.perform(get("/api/v1/network/{networkShortcut}/traffic/advertisement?sort=id,desc",corNetwork.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traAdvertisement.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTraAdvertisement() throws Exception {
        // Initialize the database
        traAdvertisementRepository.saveAndFlush(traAdvertisement.network(corNetwork));

        // Get the traAdvertisement
        restTraAdvertisementMockMvc.perform(get("/api/v1/network/{networkShortcut}/traffic/advertisement/{id}",corNetwork.getShortcut(), traAdvertisement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traAdvertisement.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraAdvertisement() throws Exception {
        // Get the traAdvertisement
        restTraAdvertisementMockMvc.perform(get("/api/v1/network/{networkShortcut}/traffic/advertisement/{id}",corNetwork.getShortcut(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraAdvertisement() throws Exception {
        // Initialize the database
        traAdvertisementRepository.saveAndFlush(traAdvertisement.network(corNetwork));
        int databaseSizeBeforeUpdate = traAdvertisementRepository.findAll().size();

        // Update the traAdvertisement
        TraAdvertisement updatedTraAdvertisement = traAdvertisementRepository.findOne(traAdvertisement.getId());
        updatedTraAdvertisement
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        TraAdvertisementPT traAdvertisementDTO = traAdvertisementMapper.DB2DTO(updatedTraAdvertisement);

        restTraAdvertisementMockMvc.perform(put("/api/v1/network/{networkShortcut}/traffic/advertisement",corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traAdvertisementDTO)))
            .andExpect(status().isOk());

        // Validate the TraAdvertisement in the database
        List<TraAdvertisement> traAdvertisementList = traAdvertisementRepository.findAll();
        assertThat(traAdvertisementList).hasSize(databaseSizeBeforeUpdate);
        TraAdvertisement testTraAdvertisement = traAdvertisementList.get(traAdvertisementList.size() - 1);
        assertThat(testTraAdvertisement.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraAdvertisement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTraAdvertisement() throws Exception {
        int databaseSizeBeforeUpdate = traAdvertisementRepository.findAll().size();

        // Create the TraAdvertisement
        TraAdvertisementPT traAdvertisementDTO = traAdvertisementMapper.DB2DTO(traAdvertisement);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraAdvertisementMockMvc.perform(put("/api/v1/network/{networkShortcut}/traffic/advertisement",corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traAdvertisementDTO)))
            .andExpect(status().isCreated());

        // Validate the TraAdvertisement in the database
        List<TraAdvertisement> traAdvertisementList = traAdvertisementRepository.findAll();
        assertThat(traAdvertisementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraAdvertisement() throws Exception {
        // Initialize the database
        traAdvertisementRepository.saveAndFlush(traAdvertisement.network(corNetwork));
        int databaseSizeBeforeDelete = traAdvertisementRepository.findAll().size();

        // Get the traAdvertisement
        restTraAdvertisementMockMvc.perform(delete("/api/v1/network/{networkShortcut}/traffic/advertisement/{id}",corNetwork.getShortcut(), traAdvertisement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TraAdvertisement> traAdvertisementList = traAdvertisementRepository.findAll();
        assertThat(traAdvertisementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraAdvertisement.class);
    }
}
