package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LIBTrack;
import io.protone.repository.LIBTrackRepository;
import io.protone.service.dto.LIBTrackDTO;
import io.protone.service.mapper.LIBTrackMapper;

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

/**
 * Test class for the LIBTrackResource REST controller.
 *
 * @see LIBTrackResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LIBTrackResourceIntTest {

    private static final Integer DEFAULT_DISC_NO = 1;
    private static final Integer UPDATED_DISC_NO = 2;

    private static final Integer DEFAULT_TRACK_NO = 1;
    private static final Integer UPDATED_TRACK_NO = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_LENGTH = 1L;
    private static final Long UPDATED_LENGTH = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private LIBTrackRepository lIBTrackRepository;

    @Inject
    private LIBTrackMapper lIBTrackMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLIBTrackMockMvc;

    private LIBTrack lIBTrack;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LIBTrackResource lIBTrackResource = new LIBTrackResource();
        ReflectionTestUtils.setField(lIBTrackResource, "lIBTrackRepository", lIBTrackRepository);
        ReflectionTestUtils.setField(lIBTrackResource, "lIBTrackMapper", lIBTrackMapper);
        this.restLIBTrackMockMvc = MockMvcBuilders.standaloneSetup(lIBTrackResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LIBTrack createEntity(EntityManager em) {
        LIBTrack lIBTrack = new LIBTrack()
                .discNo(DEFAULT_DISC_NO)
                .trackNo(DEFAULT_TRACK_NO)
                .name(DEFAULT_NAME)
                .length(DEFAULT_LENGTH)
                .description(DEFAULT_DESCRIPTION);
        return lIBTrack;
    }

    @Before
    public void initTest() {
        lIBTrack = createEntity(em);
    }

    @Test
    @Transactional
    public void createLIBTrack() throws Exception {
        int databaseSizeBeforeCreate = lIBTrackRepository.findAll().size();

        // Create the LIBTrack
        LIBTrackDTO lIBTrackDTO = lIBTrackMapper.lIBTrackToLIBTrackDTO(lIBTrack);

        restLIBTrackMockMvc.perform(post("/api/l-ib-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBTrackDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBTrack in the database
        List<LIBTrack> lIBTrackList = lIBTrackRepository.findAll();
        assertThat(lIBTrackList).hasSize(databaseSizeBeforeCreate + 1);
        LIBTrack testLIBTrack = lIBTrackList.get(lIBTrackList.size() - 1);
        assertThat(testLIBTrack.getDiscNo()).isEqualTo(DEFAULT_DISC_NO);
        assertThat(testLIBTrack.getTrackNo()).isEqualTo(DEFAULT_TRACK_NO);
        assertThat(testLIBTrack.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLIBTrack.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testLIBTrack.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLIBTrackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lIBTrackRepository.findAll().size();

        // Create the LIBTrack with an existing ID
        LIBTrack existingLIBTrack = new LIBTrack();
        existingLIBTrack.setId(1L);
        LIBTrackDTO existingLIBTrackDTO = lIBTrackMapper.lIBTrackToLIBTrackDTO(existingLIBTrack);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLIBTrackMockMvc.perform(post("/api/l-ib-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLIBTrackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LIBTrack> lIBTrackList = lIBTrackRepository.findAll();
        assertThat(lIBTrackList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDiscNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBTrackRepository.findAll().size();
        // set the field null
        lIBTrack.setDiscNo(null);

        // Create the LIBTrack, which fails.
        LIBTrackDTO lIBTrackDTO = lIBTrackMapper.lIBTrackToLIBTrackDTO(lIBTrack);

        restLIBTrackMockMvc.perform(post("/api/l-ib-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBTrackDTO)))
            .andExpect(status().isBadRequest());

        List<LIBTrack> lIBTrackList = lIBTrackRepository.findAll();
        assertThat(lIBTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrackNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBTrackRepository.findAll().size();
        // set the field null
        lIBTrack.setTrackNo(null);

        // Create the LIBTrack, which fails.
        LIBTrackDTO lIBTrackDTO = lIBTrackMapper.lIBTrackToLIBTrackDTO(lIBTrack);

        restLIBTrackMockMvc.perform(post("/api/l-ib-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBTrackDTO)))
            .andExpect(status().isBadRequest());

        List<LIBTrack> lIBTrackList = lIBTrackRepository.findAll();
        assertThat(lIBTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBTrackRepository.findAll().size();
        // set the field null
        lIBTrack.setName(null);

        // Create the LIBTrack, which fails.
        LIBTrackDTO lIBTrackDTO = lIBTrackMapper.lIBTrackToLIBTrackDTO(lIBTrack);

        restLIBTrackMockMvc.perform(post("/api/l-ib-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBTrackDTO)))
            .andExpect(status().isBadRequest());

        List<LIBTrack> lIBTrackList = lIBTrackRepository.findAll();
        assertThat(lIBTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBTrackRepository.findAll().size();
        // set the field null
        lIBTrack.setLength(null);

        // Create the LIBTrack, which fails.
        LIBTrackDTO lIBTrackDTO = lIBTrackMapper.lIBTrackToLIBTrackDTO(lIBTrack);

        restLIBTrackMockMvc.perform(post("/api/l-ib-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBTrackDTO)))
            .andExpect(status().isBadRequest());

        List<LIBTrack> lIBTrackList = lIBTrackRepository.findAll();
        assertThat(lIBTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLIBTracks() throws Exception {
        // Initialize the database
        lIBTrackRepository.saveAndFlush(lIBTrack);

        // Get all the lIBTrackList
        restLIBTrackMockMvc.perform(get("/api/l-ib-tracks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lIBTrack.getId().intValue())))
            .andExpect(jsonPath("$.[*].discNo").value(hasItem(DEFAULT_DISC_NO)))
            .andExpect(jsonPath("$.[*].trackNo").value(hasItem(DEFAULT_TRACK_NO)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLIBTrack() throws Exception {
        // Initialize the database
        lIBTrackRepository.saveAndFlush(lIBTrack);

        // Get the lIBTrack
        restLIBTrackMockMvc.perform(get("/api/l-ib-tracks/{id}", lIBTrack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lIBTrack.getId().intValue()))
            .andExpect(jsonPath("$.discNo").value(DEFAULT_DISC_NO))
            .andExpect(jsonPath("$.trackNo").value(DEFAULT_TRACK_NO))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLIBTrack() throws Exception {
        // Get the lIBTrack
        restLIBTrackMockMvc.perform(get("/api/l-ib-tracks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLIBTrack() throws Exception {
        // Initialize the database
        lIBTrackRepository.saveAndFlush(lIBTrack);
        int databaseSizeBeforeUpdate = lIBTrackRepository.findAll().size();

        // Update the lIBTrack
        LIBTrack updatedLIBTrack = lIBTrackRepository.findOne(lIBTrack.getId());
        updatedLIBTrack
                .discNo(UPDATED_DISC_NO)
                .trackNo(UPDATED_TRACK_NO)
                .name(UPDATED_NAME)
                .length(UPDATED_LENGTH)
                .description(UPDATED_DESCRIPTION);
        LIBTrackDTO lIBTrackDTO = lIBTrackMapper.lIBTrackToLIBTrackDTO(updatedLIBTrack);

        restLIBTrackMockMvc.perform(put("/api/l-ib-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBTrackDTO)))
            .andExpect(status().isOk());

        // Validate the LIBTrack in the database
        List<LIBTrack> lIBTrackList = lIBTrackRepository.findAll();
        assertThat(lIBTrackList).hasSize(databaseSizeBeforeUpdate);
        LIBTrack testLIBTrack = lIBTrackList.get(lIBTrackList.size() - 1);
        assertThat(testLIBTrack.getDiscNo()).isEqualTo(UPDATED_DISC_NO);
        assertThat(testLIBTrack.getTrackNo()).isEqualTo(UPDATED_TRACK_NO);
        assertThat(testLIBTrack.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLIBTrack.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testLIBTrack.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLIBTrack() throws Exception {
        int databaseSizeBeforeUpdate = lIBTrackRepository.findAll().size();

        // Create the LIBTrack
        LIBTrackDTO lIBTrackDTO = lIBTrackMapper.lIBTrackToLIBTrackDTO(lIBTrack);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLIBTrackMockMvc.perform(put("/api/l-ib-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBTrackDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBTrack in the database
        List<LIBTrack> lIBTrackList = lIBTrackRepository.findAll();
        assertThat(lIBTrackList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLIBTrack() throws Exception {
        // Initialize the database
        lIBTrackRepository.saveAndFlush(lIBTrack);
        int databaseSizeBeforeDelete = lIBTrackRepository.findAll().size();

        // Get the lIBTrack
        restLIBTrackMockMvc.perform(delete("/api/l-ib-tracks/{id}", lIBTrack.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LIBTrack> lIBTrackList = lIBTrackRepository.findAll();
        assertThat(lIBTrackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
