package io.protone.application.web.api.traffic;

import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.traffic.impl.TraCompanyResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorNetworkService;
import io.protone.traffic.api.dto.TraCompanyDTO;
import io.protone.traffic.domain.TraCompany;
import io.protone.traffic.mapper.TraCompanyMapper;
import io.protone.traffic.repository.TraCompanyRepository;
import io.protone.traffic.service.TraCompanyService;
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

import static io.protone.application.web.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
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
public class TraCompanyResourceImplTest {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_ID_1 = "AAAAAAAAAA";
    private static final String UPDATED_TAX_ID_1 = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_ID_2 = "AAAAAAAAAA";
    private static final String UPDATED_TAX_ID_2 = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";


    @Autowired
    private TraCompanyRepository traCompanyRepository;

    @Autowired
    private TraCompanyMapper traCompanyMapper;

    @Autowired
    private CorNetworkService corNetworkService;
    @Autowired
    private TraCompanyService traCompanyService;
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTraMediaPlantMockMvc;

    private CorNetwork corNetwork;

    private TraCompany traCompany;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraCompany createEntity(EntityManager em) {

        return new TraCompany()
                .taxId1(DEFAULT_TAX_ID_1).taxId2(DEFAULT_TAX_ID_2)
                .description(DEFAULT_DESCRIPTION)
                .shortName(DEFAULT_SHORT_NAME)
                .name(DEFAULT_NAME);
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TraCompanyResourceImpl traMediaPlanTemplateResource = new TraCompanyResourceImpl();

        ReflectionTestUtils.setField(traMediaPlanTemplateResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(traMediaPlanTemplateResource, "traCompanyService", traCompanyService);
        ReflectionTestUtils.setField(traMediaPlanTemplateResource, "traCompanyMapper", traCompanyMapper);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);

        this.restTraMediaPlantMockMvc = MockMvcBuilders.standaloneSetup(traMediaPlanTemplateResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        traCompany = createEntity(em).network(corNetwork);
    }

    @Test
    @Transactional
    public void createTraCompany() throws Exception {
        int databaseSizeBeforeCreate = traCompanyRepository.findAll().size();

        // Create the TraDiscount
        TraCompanyDTO traMediaPlanTemplateDTO = traCompanyMapper.DB2DTO(traCompany);

        restTraMediaPlantMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/configuration/traffic/company", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traMediaPlanTemplateDTO)))
                .andExpect(status().isCreated());

        // Validate the TraDiscount in the database
        List<TraCompany> traMediaPlanList = traCompanyRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeCreate + 1);
        TraCompany testTraDiscount = traMediaPlanList.get(traMediaPlanList.size() - 1);

    }

    @Test
    @Transactional
    public void createTraCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traCompanyRepository.findAll().size();

        // Create the TraDiscount with an existing ID
        TraCompany existingTraDiscount = new TraCompany();
        existingTraDiscount.setId(1L);
        TraCompanyDTO existingTraCompanyDTO = traCompanyMapper.DB2DTO(existingTraDiscount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraMediaPlantMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/configuration/traffic/company", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingTraCompanyDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraCompany> traMediaPlanList = traCompanyRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTraCompany() throws Exception {
        // Initialize the database
        traCompanyRepository.saveAndFlush(traCompany.network(corNetwork));

        // Get all the traMediaPlanList
        restTraMediaPlantMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/configuration/traffic/company?sort=id,desc", corNetwork.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(traCompany.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].taxId1").value(hasItem(DEFAULT_TAX_ID_1.toString())))
                .andExpect(jsonPath("$.[*].taxId2").value(hasItem(DEFAULT_TAX_ID_2.toString())));

    }

    @Test
    @Transactional
    public void getTraCompany() throws Exception {
        // Initialize the database
        traCompanyRepository.saveAndFlush(traCompany.network(corNetwork));

        // Get the traCompany
        restTraMediaPlantMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/configuration/traffic/company/{id}", corNetwork.getShortcut(), traCompany.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(traCompany.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString())).
                andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.taxId1").value(DEFAULT_TAX_ID_1.toString()))
                .andExpect(jsonPath("$.taxId2").value(DEFAULT_TAX_ID_2.toString()));

    }

    @Test
    @Transactional
    public void getNonExistingTraCompany() throws Exception {
        // Get the traCompany
        restTraMediaPlantMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/configuration/traffic/company/{id}", corNetwork.getShortcut(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraCompany() throws Exception {
        // Initialize the database
        traCompanyRepository.saveAndFlush(traCompany.network(corNetwork));
        int databaseSizeBeforeUpdate = traCompanyRepository.findAll().size();

        // Update the traCompany
        TraCompany traMediaPlanTemplate = traCompanyRepository.findOne(this.traCompany.getId());
        traMediaPlanTemplate.name(UPDATED_NAME)
                .taxId1(UPDATED_TAX_ID_1).taxId2(UPDATED_TAX_ID_2)
                .description(UPDATED_DESCRIPTION)
                .shortName(UPDATED_SHORT_NAME);
        TraCompanyDTO traMediaPlanTemplateDTO = traCompanyMapper.DB2DTO(this.traCompany);

        restTraMediaPlantMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/configuration/traffic/company", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traMediaPlanTemplateDTO)))
                .andExpect(status().isOk());

        // Validate the TraDiscount in the database
        List<TraCompany> traCompanies = traCompanyRepository.findAll();
        assertThat(traCompanies).hasSize(databaseSizeBeforeUpdate);
        TraCompany traCompany = traCompanies.get(traCompanies.size() - 1);
        assertEquals(traCompany.getName(), UPDATED_NAME);
        assertEquals(traCompany.getTaxId1(), UPDATED_TAX_ID_1);
        assertEquals(traCompany.getTaxId2(), UPDATED_TAX_ID_2);
        assertEquals(traCompany.getDescription(), UPDATED_DESCRIPTION);
        assertEquals(traCompany.getShortName(), UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTraCompany() throws Exception {
        int databaseSizeBeforeUpdate = traCompanyRepository.findAll().size();

        // Create the TraDiscount
        TraCompanyDTO traMediaPlanTemplateDTO = traCompanyMapper.DB2DTO(traCompany);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraMediaPlantMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/configuration/traffic/company", corNetwork.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traMediaPlanTemplateDTO)))
                .andExpect(status().isCreated());

        // Validate the TraDiscount in the database
        List<TraCompany> traCompanies = traCompanyRepository.findAll();
        assertThat(traCompanies).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraCompany() throws Exception {
        // Initialize the database
        traCompanyRepository.saveAndFlush(traCompany.network(corNetwork));
        int databaseSizeBeforeDelete = traCompanyRepository.findAll().size();

        // Get the traCompany
        restTraMediaPlantMockMvc.perform(delete("/api/v1/organization/{organizationShortcut}/configuration/traffic/company/{id}", corNetwork.getShortcut(), traCompany.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TraCompany> traMediaPlanList = traCompanyRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }


}