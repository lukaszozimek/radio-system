package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CRMOpportunity;
import io.protone.repository.CRMOpportunityRepository;
import io.protone.service.dto.CRMOpportunityDTO;
import io.protone.service.mapper.CRMOpportunityMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CRMOpportunityResource REST controller.
 *
 * @see CRMOpportunityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CRMOpportunityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_TRY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_TRY = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CLOSE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CLOSE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PROBABILITY = 1;
    private static final Integer UPDATED_PROBABILITY = 2;

    @Inject
    private CRMOpportunityRepository cRMOpportunityRepository;

    @Inject
    private CRMOpportunityMapper cRMOpportunityMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCRMOpportunityMockMvc;

    private CRMOpportunity cRMOpportunity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CRMOpportunityResource cRMOpportunityResource = new CRMOpportunityResource();
        ReflectionTestUtils.setField(cRMOpportunityResource, "cRMOpportunityRepository", cRMOpportunityRepository);
        ReflectionTestUtils.setField(cRMOpportunityResource, "cRMOpportunityMapper", cRMOpportunityMapper);
        this.restCRMOpportunityMockMvc = MockMvcBuilders.standaloneSetup(cRMOpportunityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRMOpportunity createEntity(EntityManager em) {
        CRMOpportunity cRMOpportunity = new CRMOpportunity()
                .name(DEFAULT_NAME)
                .lastTry(DEFAULT_LAST_TRY)
                .closeDate(DEFAULT_CLOSE_DATE)
                .probability(DEFAULT_PROBABILITY);
        return cRMOpportunity;
    }

    @Before
    public void initTest() {
        cRMOpportunity = createEntity(em);
    }

    @Test
    @Transactional
    public void createCRMOpportunity() throws Exception {
        int databaseSizeBeforeCreate = cRMOpportunityRepository.findAll().size();

        // Create the CRMOpportunity
        CRMOpportunityDTO cRMOpportunityDTO = cRMOpportunityMapper.cRMOpportunityToCRMOpportunityDTO(cRMOpportunity);

        restCRMOpportunityMockMvc.perform(post("/api/c-rm-opportunities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMOpportunityDTO)))
            .andExpect(status().isCreated());

        // Validate the CRMOpportunity in the database
        List<CRMOpportunity> cRMOpportunityList = cRMOpportunityRepository.findAll();
        assertThat(cRMOpportunityList).hasSize(databaseSizeBeforeCreate + 1);
        CRMOpportunity testCRMOpportunity = cRMOpportunityList.get(cRMOpportunityList.size() - 1);
        assertThat(testCRMOpportunity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCRMOpportunity.getLastTry()).isEqualTo(DEFAULT_LAST_TRY);
        assertThat(testCRMOpportunity.getCloseDate()).isEqualTo(DEFAULT_CLOSE_DATE);
        assertThat(testCRMOpportunity.getProbability()).isEqualTo(DEFAULT_PROBABILITY);
    }

    @Test
    @Transactional
    public void createCRMOpportunityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cRMOpportunityRepository.findAll().size();

        // Create the CRMOpportunity with an existing ID
        CRMOpportunity existingCRMOpportunity = new CRMOpportunity();
        existingCRMOpportunity.setId(1L);
        CRMOpportunityDTO existingCRMOpportunityDTO = cRMOpportunityMapper.cRMOpportunityToCRMOpportunityDTO(existingCRMOpportunity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCRMOpportunityMockMvc.perform(post("/api/c-rm-opportunities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCRMOpportunityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CRMOpportunity> cRMOpportunityList = cRMOpportunityRepository.findAll();
        assertThat(cRMOpportunityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCRMOpportunities() throws Exception {
        // Initialize the database
        cRMOpportunityRepository.saveAndFlush(cRMOpportunity);

        // Get all the cRMOpportunityList
        restCRMOpportunityMockMvc.perform(get("/api/c-rm-opportunities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRMOpportunity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastTry").value(hasItem(DEFAULT_LAST_TRY.toString())))
            .andExpect(jsonPath("$.[*].closeDate").value(hasItem(DEFAULT_CLOSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].probability").value(hasItem(DEFAULT_PROBABILITY)));
    }

    @Test
    @Transactional
    public void getCRMOpportunity() throws Exception {
        // Initialize the database
        cRMOpportunityRepository.saveAndFlush(cRMOpportunity);

        // Get the cRMOpportunity
        restCRMOpportunityMockMvc.perform(get("/api/c-rm-opportunities/{id}", cRMOpportunity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cRMOpportunity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.lastTry").value(DEFAULT_LAST_TRY.toString()))
            .andExpect(jsonPath("$.closeDate").value(DEFAULT_CLOSE_DATE.toString()))
            .andExpect(jsonPath("$.probability").value(DEFAULT_PROBABILITY));
    }

    @Test
    @Transactional
    public void getNonExistingCRMOpportunity() throws Exception {
        // Get the cRMOpportunity
        restCRMOpportunityMockMvc.perform(get("/api/c-rm-opportunities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCRMOpportunity() throws Exception {
        // Initialize the database
        cRMOpportunityRepository.saveAndFlush(cRMOpportunity);
        int databaseSizeBeforeUpdate = cRMOpportunityRepository.findAll().size();

        // Update the cRMOpportunity
        CRMOpportunity updatedCRMOpportunity = cRMOpportunityRepository.findOne(cRMOpportunity.getId());
        updatedCRMOpportunity
                .name(UPDATED_NAME)
                .lastTry(UPDATED_LAST_TRY)
                .closeDate(UPDATED_CLOSE_DATE)
                .probability(UPDATED_PROBABILITY);
        CRMOpportunityDTO cRMOpportunityDTO = cRMOpportunityMapper.cRMOpportunityToCRMOpportunityDTO(updatedCRMOpportunity);

        restCRMOpportunityMockMvc.perform(put("/api/c-rm-opportunities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMOpportunityDTO)))
            .andExpect(status().isOk());

        // Validate the CRMOpportunity in the database
        List<CRMOpportunity> cRMOpportunityList = cRMOpportunityRepository.findAll();
        assertThat(cRMOpportunityList).hasSize(databaseSizeBeforeUpdate);
        CRMOpportunity testCRMOpportunity = cRMOpportunityList.get(cRMOpportunityList.size() - 1);
        assertThat(testCRMOpportunity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCRMOpportunity.getLastTry()).isEqualTo(UPDATED_LAST_TRY);
        assertThat(testCRMOpportunity.getCloseDate()).isEqualTo(UPDATED_CLOSE_DATE);
        assertThat(testCRMOpportunity.getProbability()).isEqualTo(UPDATED_PROBABILITY);
    }

    @Test
    @Transactional
    public void updateNonExistingCRMOpportunity() throws Exception {
        int databaseSizeBeforeUpdate = cRMOpportunityRepository.findAll().size();

        // Create the CRMOpportunity
        CRMOpportunityDTO cRMOpportunityDTO = cRMOpportunityMapper.cRMOpportunityToCRMOpportunityDTO(cRMOpportunity);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCRMOpportunityMockMvc.perform(put("/api/c-rm-opportunities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cRMOpportunityDTO)))
            .andExpect(status().isCreated());

        // Validate the CRMOpportunity in the database
        List<CRMOpportunity> cRMOpportunityList = cRMOpportunityRepository.findAll();
        assertThat(cRMOpportunityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCRMOpportunity() throws Exception {
        // Initialize the database
        cRMOpportunityRepository.saveAndFlush(cRMOpportunity);
        int databaseSizeBeforeDelete = cRMOpportunityRepository.findAll().size();

        // Get the cRMOpportunity
        restCRMOpportunityMockMvc.perform(delete("/api/c-rm-opportunities/{id}", cRMOpportunity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CRMOpportunity> cRMOpportunityList = cRMOpportunityRepository.findAll();
        assertThat(cRMOpportunityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
