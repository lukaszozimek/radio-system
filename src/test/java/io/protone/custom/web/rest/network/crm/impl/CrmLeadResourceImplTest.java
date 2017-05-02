package io.protone.custom.web.rest.network.crm.impl;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CrmLeadPT;
import io.protone.custom.web.rest.network.TestUtil;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmLead;
import io.protone.repository.crm.CrmLeadRepository;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.crm.CrmLeadService;
import io.protone.web.rest.errors.ExceptionTranslator;
import io.protone.web.rest.mapper.CrmLeadMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 02/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmLeadResourceImplTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORTNAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CrmLeadRepository crmLeadRepository;

    @Autowired
    private CrmLeadMapper crmLeadMapper;

    @Autowired
    private CrmLeadService crmLeadService;

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

    private MockMvc restCrmLeadMockMvc;

    private CrmLead crmLead;

    private CorNetwork corNetwork;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ApiNetworkCrmLeadImpl crmLeadResource = new ApiNetworkCrmLeadImpl();

        ReflectionTestUtils.setField(crmLeadResource, "crmLeadService", crmLeadService);
        ReflectionTestUtils.setField(crmLeadResource, "crmLeadMapper", crmLeadMapper);
        ReflectionTestUtils.setField(crmLeadResource, "corNetworkService", corNetworkService);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);

        this.restCrmLeadMockMvc = MockMvcBuilders.standaloneSetup(crmLeadResource)
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
    public static CrmLead createEntity(EntityManager em) {
        CrmLead crmLead = new CrmLead()
            .name(DEFAULT_NAME)
            .shortname(DEFAULT_SHORTNAME)
            .description(DEFAULT_DESCRIPTION);
        return crmLead;
    }

    @Before
    public void initTest() {
        crmLead = createEntity(em);
    }

    @Test
    @Transactional
    public void createCrmLead() throws Exception {
        int databaseSizeBeforeCreate = crmLeadRepository.findAll().size();

        // Create the CrmLead
        CrmLeadPT crmLeadDTO = crmLeadMapper.DB2DTO(crmLead);

        restCrmLeadMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/lead", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmLeadDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmLead in the database
        List<CrmLead> crmLeadList = crmLeadRepository.findAll();
        assertThat(crmLeadList).hasSize(databaseSizeBeforeCreate + 1);
        CrmLead testCrmLead = crmLeadList.get(crmLeadList.size() - 1);
        assertThat(testCrmLead.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCrmLead.getShortname()).isEqualTo(DEFAULT_SHORTNAME);
        assertThat(testCrmLead.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCrmLeadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = crmLeadRepository.findAll().size();

        // Create the CrmLead with an existing ID
        CrmLead existingCrmLead = new CrmLead();
        existingCrmLead.setId(1L);
        CrmLeadPT existingCrmLeadDTO = crmLeadMapper.DB2DTO(existingCrmLead);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrmLeadMockMvc.perform(post("/api/v1/network/{networkShortcut}/crm/lead", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCrmLeadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CrmLead> crmLeadList = crmLeadRepository.findAll();
        assertThat(crmLeadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCrmLeads() throws Exception {
        // Initialize the database
        crmLeadRepository.saveAndFlush(crmLead);

        // Get all the crmLeadList
        restCrmLeadMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/lead?sort=id,desc", corNetwork.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmLead.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].shortname").value(hasItem(DEFAULT_SHORTNAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCrmLead() throws Exception {
        // Initialize the database
        crmLeadRepository.saveAndFlush(crmLead);

        // Get the crmLead
        restCrmLeadMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/lead/{shortName}", corNetwork.getShortcut(), crmLead.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(crmLead.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.shortname").value(DEFAULT_SHORTNAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCrmLead() throws Exception {
        // Get the crmLead
        restCrmLeadMockMvc.perform(get("/api/v1/network/{networkShortcut}/crm/lead/{shortName}", corNetwork.getShortcut(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrmLead() throws Exception {
        // Initialize the database
        crmLeadRepository.saveAndFlush(crmLead);
        int databaseSizeBeforeUpdate = crmLeadRepository.findAll().size();

        // Update the crmLead
        CrmLead updatedCrmLead = crmLeadRepository.findOne(crmLead.getId());
        updatedCrmLead
            .name(UPDATED_NAME)
            .shortname(UPDATED_SHORTNAME)
            .description(UPDATED_DESCRIPTION);
        CrmLeadPT crmLeadDTO = crmLeadMapper.DB2DTO(updatedCrmLead);

        restCrmLeadMockMvc.perform(put("/api/v1/network/{networkShortcut}/crm/lead", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmLeadDTO)))
            .andExpect(status().isOk());

        // Validate the CrmLead in the database
        List<CrmLead> crmLeadList = crmLeadRepository.findAll();
        assertThat(crmLeadList).hasSize(databaseSizeBeforeUpdate);
        CrmLead testCrmLead = crmLeadList.get(crmLeadList.size() - 1);
        assertThat(testCrmLead.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCrmLead.getShortname()).isEqualTo(UPDATED_SHORTNAME);
        assertThat(testCrmLead.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCrmLead() throws Exception {
        int databaseSizeBeforeUpdate = crmLeadRepository.findAll().size();

        // Create the CrmLead
        CrmLeadPT crmLeadDTO = crmLeadMapper.DB2DTO(crmLead);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCrmLeadMockMvc.perform(put("/api/v1/network/{networkShortcut}/crm/lead", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(crmLeadDTO)))
            .andExpect(status().isCreated());

        // Validate the CrmLead in the database
        List<CrmLead> crmLeadList = crmLeadRepository.findAll();
        assertThat(crmLeadList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCrmLead() throws Exception {
        // Initialize the database
        crmLeadRepository.saveAndFlush(crmLead);
        int databaseSizeBeforeDelete = crmLeadRepository.findAll().size();

        // Get the crmLead
        restCrmLeadMockMvc.perform(delete("/api/v1/network/{networkShortcut}/crm/lead/{shortName}", corNetwork.getShortcut(), crmLead.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CrmLead> crmLeadList = crmLeadRepository.findAll();
        assertThat(crmLeadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrmLead.class);
    }

}
