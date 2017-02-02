package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LIBMarker;
import io.protone.repository.LIBMarkerRepository;
import io.protone.service.dto.LIBMarkerDTO;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.protone.domain.enumeration.LIBMarkerTypeEnum;
/**
 * Test class for the LIBMarkerResource REST controller.
 *
 * @see LIBMarkerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LIBMarkerResourceIntTest {

    private static final LIBMarkerTypeEnum DEFAULT_MARKER_TYPE = LIBMarkerTypeEnum.MT_BASIC;
    private static final LIBMarkerTypeEnum UPDATED_MARKER_TYPE = LIBMarkerTypeEnum.MT_INTRO;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_START_TIME = 1;
    private static final Integer UPDATED_START_TIME = 2;

    @Inject
    private LIBMarkerRepository lIBMarkerRepository;

    @Inject
    private LIBMarkerMapper lIBMarkerMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLIBMarkerMockMvc;

    private LIBMarker lIBMarker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LIBMarkerResource lIBMarkerResource = new LIBMarkerResource();
        ReflectionTestUtils.setField(lIBMarkerResource, "lIBMarkerRepository", lIBMarkerRepository);
        ReflectionTestUtils.setField(lIBMarkerResource, "lIBMarkerMapper", lIBMarkerMapper);
        this.restLIBMarkerMockMvc = MockMvcBuilders.standaloneSetup(lIBMarkerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LIBMarker createEntity(EntityManager em) {
        LIBMarker lIBMarker = new LIBMarker()
                .markerType(DEFAULT_MARKER_TYPE)
                .name(DEFAULT_NAME)
                .startTime(DEFAULT_START_TIME);
        return lIBMarker;
    }

    @Before
    public void initTest() {
        lIBMarker = createEntity(em);
    }

    @Test
    @Transactional
    public void createLIBMarker() throws Exception {
        int databaseSizeBeforeCreate = lIBMarkerRepository.findAll().size();

        // Create the LIBMarker
        LIBMarkerDTO lIBMarkerDTO = lIBMarkerMapper.lIBMarkerToLIBMarkerDTO(lIBMarker);

        restLIBMarkerMockMvc.perform(post("/api/l-ib-markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBMarkerDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBMarker in the database
        List<LIBMarker> lIBMarkerList = lIBMarkerRepository.findAll();
        assertThat(lIBMarkerList).hasSize(databaseSizeBeforeCreate + 1);
        LIBMarker testLIBMarker = lIBMarkerList.get(lIBMarkerList.size() - 1);
        assertThat(testLIBMarker.getMarkerType()).isEqualTo(DEFAULT_MARKER_TYPE);
        assertThat(testLIBMarker.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLIBMarker.getStartTime()).isEqualTo(DEFAULT_START_TIME);
    }

    @Test
    @Transactional
    public void createLIBMarkerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lIBMarkerRepository.findAll().size();

        // Create the LIBMarker with an existing ID
        LIBMarker existingLIBMarker = new LIBMarker();
        existingLIBMarker.setId(1L);
        LIBMarkerDTO existingLIBMarkerDTO = lIBMarkerMapper.lIBMarkerToLIBMarkerDTO(existingLIBMarker);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLIBMarkerMockMvc.perform(post("/api/l-ib-markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLIBMarkerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LIBMarker> lIBMarkerList = lIBMarkerRepository.findAll();
        assertThat(lIBMarkerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMarkerTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBMarkerRepository.findAll().size();
        // set the field null
        lIBMarker.setMarkerType(null);

        // Create the LIBMarker, which fails.
        LIBMarkerDTO lIBMarkerDTO = lIBMarkerMapper.lIBMarkerToLIBMarkerDTO(lIBMarker);

        restLIBMarkerMockMvc.perform(post("/api/l-ib-markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBMarkerDTO)))
            .andExpect(status().isBadRequest());

        List<LIBMarker> lIBMarkerList = lIBMarkerRepository.findAll();
        assertThat(lIBMarkerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBMarkerRepository.findAll().size();
        // set the field null
        lIBMarker.setName(null);

        // Create the LIBMarker, which fails.
        LIBMarkerDTO lIBMarkerDTO = lIBMarkerMapper.lIBMarkerToLIBMarkerDTO(lIBMarker);

        restLIBMarkerMockMvc.perform(post("/api/l-ib-markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBMarkerDTO)))
            .andExpect(status().isBadRequest());

        List<LIBMarker> lIBMarkerList = lIBMarkerRepository.findAll();
        assertThat(lIBMarkerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBMarkerRepository.findAll().size();
        // set the field null
        lIBMarker.setStartTime(null);

        // Create the LIBMarker, which fails.
        LIBMarkerDTO lIBMarkerDTO = lIBMarkerMapper.lIBMarkerToLIBMarkerDTO(lIBMarker);

        restLIBMarkerMockMvc.perform(post("/api/l-ib-markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBMarkerDTO)))
            .andExpect(status().isBadRequest());

        List<LIBMarker> lIBMarkerList = lIBMarkerRepository.findAll();
        assertThat(lIBMarkerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLIBMarkers() throws Exception {
        // Initialize the database
        lIBMarkerRepository.saveAndFlush(lIBMarker);

        // Get all the lIBMarkerList
        restLIBMarkerMockMvc.perform(get("/api/l-ib-markers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lIBMarker.getId().intValue())))
            .andExpect(jsonPath("$.[*].markerType").value(hasItem(DEFAULT_MARKER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)));
    }

    @Test
    @Transactional
    public void getLIBMarker() throws Exception {
        // Initialize the database
        lIBMarkerRepository.saveAndFlush(lIBMarker);

        // Get the lIBMarker
        restLIBMarkerMockMvc.perform(get("/api/l-ib-markers/{id}", lIBMarker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lIBMarker.getId().intValue()))
            .andExpect(jsonPath("$.markerType").value(DEFAULT_MARKER_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME));
    }

    @Test
    @Transactional
    public void getNonExistingLIBMarker() throws Exception {
        // Get the lIBMarker
        restLIBMarkerMockMvc.perform(get("/api/l-ib-markers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLIBMarker() throws Exception {
        // Initialize the database
        lIBMarkerRepository.saveAndFlush(lIBMarker);
        int databaseSizeBeforeUpdate = lIBMarkerRepository.findAll().size();

        // Update the lIBMarker
        LIBMarker updatedLIBMarker = lIBMarkerRepository.findOne(lIBMarker.getId());
        updatedLIBMarker
                .markerType(UPDATED_MARKER_TYPE)
                .name(UPDATED_NAME)
                .startTime(UPDATED_START_TIME);
        LIBMarkerDTO lIBMarkerDTO = lIBMarkerMapper.lIBMarkerToLIBMarkerDTO(updatedLIBMarker);

        restLIBMarkerMockMvc.perform(put("/api/l-ib-markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBMarkerDTO)))
            .andExpect(status().isOk());

        // Validate the LIBMarker in the database
        List<LIBMarker> lIBMarkerList = lIBMarkerRepository.findAll();
        assertThat(lIBMarkerList).hasSize(databaseSizeBeforeUpdate);
        LIBMarker testLIBMarker = lIBMarkerList.get(lIBMarkerList.size() - 1);
        assertThat(testLIBMarker.getMarkerType()).isEqualTo(UPDATED_MARKER_TYPE);
        assertThat(testLIBMarker.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLIBMarker.getStartTime()).isEqualTo(UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingLIBMarker() throws Exception {
        int databaseSizeBeforeUpdate = lIBMarkerRepository.findAll().size();

        // Create the LIBMarker
        LIBMarkerDTO lIBMarkerDTO = lIBMarkerMapper.lIBMarkerToLIBMarkerDTO(lIBMarker);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLIBMarkerMockMvc.perform(put("/api/l-ib-markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBMarkerDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBMarker in the database
        List<LIBMarker> lIBMarkerList = lIBMarkerRepository.findAll();
        assertThat(lIBMarkerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLIBMarker() throws Exception {
        // Initialize the database
        lIBMarkerRepository.saveAndFlush(lIBMarker);
        int databaseSizeBeforeDelete = lIBMarkerRepository.findAll().size();

        // Get the lIBMarker
        restLIBMarkerMockMvc.perform(delete("/api/l-ib-markers/{id}", lIBMarker.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LIBMarker> lIBMarkerList = lIBMarkerRepository.findAll();
        assertThat(lIBMarkerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
