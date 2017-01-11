package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.TRACampaign;
import io.protone.repository.TRACampaignRepository;
import io.protone.service.dto.TRACampaignDTO;
import io.protone.service.mapper.TRACampaignMapper;

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
 * Test class for the TRACampaignResource REST controller.
 *
 * @see TRACampaignResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TRACampaignResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_PRIZE = 1L;
    private static final Long UPDATED_PRIZE = 2L;

    @Inject
    private TRACampaignRepository tRACampaignRepository;

    @Inject
    private TRACampaignMapper tRACampaignMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTRACampaignMockMvc;

    private TRACampaign tRACampaign;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TRACampaignResource tRACampaignResource = new TRACampaignResource();
        ReflectionTestUtils.setField(tRACampaignResource, "tRACampaignRepository", tRACampaignRepository);
        ReflectionTestUtils.setField(tRACampaignResource, "tRACampaignMapper", tRACampaignMapper);
        this.restTRACampaignMockMvc = MockMvcBuilders.standaloneSetup(tRACampaignResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TRACampaign createEntity(EntityManager em) {
        TRACampaign tRACampaign = new TRACampaign()
                .name(DEFAULT_NAME)
                .startDate(DEFAULT_START_DATE)
                .endDate(DEFAULT_END_DATE)
                .prize(DEFAULT_PRIZE);
        return tRACampaign;
    }

    @Before
    public void initTest() {
        tRACampaign = createEntity(em);
    }

    @Test
    @Transactional
    public void createTRACampaign() throws Exception {
        int databaseSizeBeforeCreate = tRACampaignRepository.findAll().size();

        // Create the TRACampaign
        TRACampaignDTO tRACampaignDTO = tRACampaignMapper.tRACampaignToTRACampaignDTO(tRACampaign);

        restTRACampaignMockMvc.perform(post("/api/t-ra-campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRACampaignDTO)))
            .andExpect(status().isCreated());

        // Validate the TRACampaign in the database
        List<TRACampaign> tRACampaignList = tRACampaignRepository.findAll();
        assertThat(tRACampaignList).hasSize(databaseSizeBeforeCreate + 1);
        TRACampaign testTRACampaign = tRACampaignList.get(tRACampaignList.size() - 1);
        assertThat(testTRACampaign.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTRACampaign.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTRACampaign.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTRACampaign.getPrize()).isEqualTo(DEFAULT_PRIZE);
    }

    @Test
    @Transactional
    public void createTRACampaignWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tRACampaignRepository.findAll().size();

        // Create the TRACampaign with an existing ID
        TRACampaign existingTRACampaign = new TRACampaign();
        existingTRACampaign.setId(1L);
        TRACampaignDTO existingTRACampaignDTO = tRACampaignMapper.tRACampaignToTRACampaignDTO(existingTRACampaign);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTRACampaignMockMvc.perform(post("/api/t-ra-campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTRACampaignDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TRACampaign> tRACampaignList = tRACampaignRepository.findAll();
        assertThat(tRACampaignList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tRACampaignRepository.findAll().size();
        // set the field null
        tRACampaign.setName(null);

        // Create the TRACampaign, which fails.
        TRACampaignDTO tRACampaignDTO = tRACampaignMapper.tRACampaignToTRACampaignDTO(tRACampaign);

        restTRACampaignMockMvc.perform(post("/api/t-ra-campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRACampaignDTO)))
            .andExpect(status().isBadRequest());

        List<TRACampaign> tRACampaignList = tRACampaignRepository.findAll();
        assertThat(tRACampaignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTRACampaigns() throws Exception {
        // Initialize the database
        tRACampaignRepository.saveAndFlush(tRACampaign);

        // Get all the tRACampaignList
        restTRACampaignMockMvc.perform(get("/api/t-ra-campaigns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tRACampaign.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].prize").value(hasItem(DEFAULT_PRIZE.intValue())));
    }

    @Test
    @Transactional
    public void getTRACampaign() throws Exception {
        // Initialize the database
        tRACampaignRepository.saveAndFlush(tRACampaign);

        // Get the tRACampaign
        restTRACampaignMockMvc.perform(get("/api/t-ra-campaigns/{id}", tRACampaign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tRACampaign.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.prize").value(DEFAULT_PRIZE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTRACampaign() throws Exception {
        // Get the tRACampaign
        restTRACampaignMockMvc.perform(get("/api/t-ra-campaigns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTRACampaign() throws Exception {
        // Initialize the database
        tRACampaignRepository.saveAndFlush(tRACampaign);
        int databaseSizeBeforeUpdate = tRACampaignRepository.findAll().size();

        // Update the tRACampaign
        TRACampaign updatedTRACampaign = tRACampaignRepository.findOne(tRACampaign.getId());
        updatedTRACampaign
                .name(UPDATED_NAME)
                .startDate(UPDATED_START_DATE)
                .endDate(UPDATED_END_DATE)
                .prize(UPDATED_PRIZE);
        TRACampaignDTO tRACampaignDTO = tRACampaignMapper.tRACampaignToTRACampaignDTO(updatedTRACampaign);

        restTRACampaignMockMvc.perform(put("/api/t-ra-campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRACampaignDTO)))
            .andExpect(status().isOk());

        // Validate the TRACampaign in the database
        List<TRACampaign> tRACampaignList = tRACampaignRepository.findAll();
        assertThat(tRACampaignList).hasSize(databaseSizeBeforeUpdate);
        TRACampaign testTRACampaign = tRACampaignList.get(tRACampaignList.size() - 1);
        assertThat(testTRACampaign.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTRACampaign.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTRACampaign.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTRACampaign.getPrize()).isEqualTo(UPDATED_PRIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingTRACampaign() throws Exception {
        int databaseSizeBeforeUpdate = tRACampaignRepository.findAll().size();

        // Create the TRACampaign
        TRACampaignDTO tRACampaignDTO = tRACampaignMapper.tRACampaignToTRACampaignDTO(tRACampaign);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTRACampaignMockMvc.perform(put("/api/t-ra-campaigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tRACampaignDTO)))
            .andExpect(status().isCreated());

        // Validate the TRACampaign in the database
        List<TRACampaign> tRACampaignList = tRACampaignRepository.findAll();
        assertThat(tRACampaignList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTRACampaign() throws Exception {
        // Initialize the database
        tRACampaignRepository.saveAndFlush(tRACampaign);
        int databaseSizeBeforeDelete = tRACampaignRepository.findAll().size();

        // Get the tRACampaign
        restTRACampaignMockMvc.perform(delete("/api/t-ra-campaigns/{id}", tRACampaign.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TRACampaign> tRACampaignList = tRACampaignRepository.findAll();
        assertThat(tRACampaignList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
