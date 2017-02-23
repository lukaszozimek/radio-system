package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LibTrack;
import io.protone.repository.LibTrackRepository;
import io.protone.service.dto.LibTrackDTO;
import io.protone.service.mapper.LibTrackMapper;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LibTrackResource REST controller.
 *
 * @see LibTrackResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibTrackResourceIntTest {

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

    @Autowired
    private LibTrackRepository libTrackRepository;

    @Autowired
    private LibTrackMapper libTrackMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restLibTrackMockMvc;

    private LibTrack libTrack;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            LibTrackResource libTrackResource = new LibTrackResource(libTrackRepository, libTrackMapper);
        this.restLibTrackMockMvc = MockMvcBuilders.standaloneSetup(libTrackResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibTrack createEntity(EntityManager em) {
        LibTrack libTrack = new LibTrack()
                .discNo(DEFAULT_DISC_NO)
                .trackNo(DEFAULT_TRACK_NO)
                .name(DEFAULT_NAME)
                .length(DEFAULT_LENGTH)
                .description(DEFAULT_DESCRIPTION);
        return libTrack;
    }

    @Before
    public void initTest() {
        libTrack = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibTrack() throws Exception {
        int databaseSizeBeforeCreate = libTrackRepository.findAll().size();

        // Create the LibTrack
        LibTrackDTO libTrackDTO = libTrackMapper.libTrackToLibTrackDTO(libTrack);

        restLibTrackMockMvc.perform(post("/api/lib-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libTrackDTO)))
            .andExpect(status().isCreated());

        // Validate the LibTrack in the database
        List<LibTrack> libTrackList = libTrackRepository.findAll();
        assertThat(libTrackList).hasSize(databaseSizeBeforeCreate + 1);
        LibTrack testLibTrack = libTrackList.get(libTrackList.size() - 1);
        assertThat(testLibTrack.getDiscNo()).isEqualTo(DEFAULT_DISC_NO);
        assertThat(testLibTrack.getTrackNo()).isEqualTo(DEFAULT_TRACK_NO);
        assertThat(testLibTrack.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLibTrack.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testLibTrack.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLibTrackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libTrackRepository.findAll().size();

        // Create the LibTrack with an existing ID
        LibTrack existingLibTrack = new LibTrack();
        existingLibTrack.setId(1L);
        LibTrackDTO existingLibTrackDTO = libTrackMapper.libTrackToLibTrackDTO(existingLibTrack);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibTrackMockMvc.perform(post("/api/lib-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLibTrackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LibTrack> libTrackList = libTrackRepository.findAll();
        assertThat(libTrackList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDiscNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = libTrackRepository.findAll().size();
        // set the field null
        libTrack.setDiscNo(null);

        // Create the LibTrack, which fails.
        LibTrackDTO libTrackDTO = libTrackMapper.libTrackToLibTrackDTO(libTrack);

        restLibTrackMockMvc.perform(post("/api/lib-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libTrackDTO)))
            .andExpect(status().isBadRequest());

        List<LibTrack> libTrackList = libTrackRepository.findAll();
        assertThat(libTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrackNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = libTrackRepository.findAll().size();
        // set the field null
        libTrack.setTrackNo(null);

        // Create the LibTrack, which fails.
        LibTrackDTO libTrackDTO = libTrackMapper.libTrackToLibTrackDTO(libTrack);

        restLibTrackMockMvc.perform(post("/api/lib-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libTrackDTO)))
            .andExpect(status().isBadRequest());

        List<LibTrack> libTrackList = libTrackRepository.findAll();
        assertThat(libTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = libTrackRepository.findAll().size();
        // set the field null
        libTrack.setName(null);

        // Create the LibTrack, which fails.
        LibTrackDTO libTrackDTO = libTrackMapper.libTrackToLibTrackDTO(libTrack);

        restLibTrackMockMvc.perform(post("/api/lib-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libTrackDTO)))
            .andExpect(status().isBadRequest());

        List<LibTrack> libTrackList = libTrackRepository.findAll();
        assertThat(libTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = libTrackRepository.findAll().size();
        // set the field null
        libTrack.setLength(null);

        // Create the LibTrack, which fails.
        LibTrackDTO libTrackDTO = libTrackMapper.libTrackToLibTrackDTO(libTrack);

        restLibTrackMockMvc.perform(post("/api/lib-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libTrackDTO)))
            .andExpect(status().isBadRequest());

        List<LibTrack> libTrackList = libTrackRepository.findAll();
        assertThat(libTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibTracks() throws Exception {
        // Initialize the database
        libTrackRepository.saveAndFlush(libTrack);

        // Get all the libTrackList
        restLibTrackMockMvc.perform(get("/api/lib-tracks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libTrack.getId().intValue())))
            .andExpect(jsonPath("$.[*].discNo").value(hasItem(DEFAULT_DISC_NO)))
            .andExpect(jsonPath("$.[*].trackNo").value(hasItem(DEFAULT_TRACK_NO)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLibTrack() throws Exception {
        // Initialize the database
        libTrackRepository.saveAndFlush(libTrack);

        // Get the libTrack
        restLibTrackMockMvc.perform(get("/api/lib-tracks/{id}", libTrack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libTrack.getId().intValue()))
            .andExpect(jsonPath("$.discNo").value(DEFAULT_DISC_NO))
            .andExpect(jsonPath("$.trackNo").value(DEFAULT_TRACK_NO))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLibTrack() throws Exception {
        // Get the libTrack
        restLibTrackMockMvc.perform(get("/api/lib-tracks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibTrack() throws Exception {
        // Initialize the database
        libTrackRepository.saveAndFlush(libTrack);
        int databaseSizeBeforeUpdate = libTrackRepository.findAll().size();

        // Update the libTrack
        LibTrack updatedLibTrack = libTrackRepository.findOne(libTrack.getId());
        updatedLibTrack
                .discNo(UPDATED_DISC_NO)
                .trackNo(UPDATED_TRACK_NO)
                .name(UPDATED_NAME)
                .length(UPDATED_LENGTH)
                .description(UPDATED_DESCRIPTION);
        LibTrackDTO libTrackDTO = libTrackMapper.libTrackToLibTrackDTO(updatedLibTrack);

        restLibTrackMockMvc.perform(put("/api/lib-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libTrackDTO)))
            .andExpect(status().isOk());

        // Validate the LibTrack in the database
        List<LibTrack> libTrackList = libTrackRepository.findAll();
        assertThat(libTrackList).hasSize(databaseSizeBeforeUpdate);
        LibTrack testLibTrack = libTrackList.get(libTrackList.size() - 1);
        assertThat(testLibTrack.getDiscNo()).isEqualTo(UPDATED_DISC_NO);
        assertThat(testLibTrack.getTrackNo()).isEqualTo(UPDATED_TRACK_NO);
        assertThat(testLibTrack.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibTrack.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testLibTrack.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLibTrack() throws Exception {
        int databaseSizeBeforeUpdate = libTrackRepository.findAll().size();

        // Create the LibTrack
        LibTrackDTO libTrackDTO = libTrackMapper.libTrackToLibTrackDTO(libTrack);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibTrackMockMvc.perform(put("/api/lib-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libTrackDTO)))
            .andExpect(status().isCreated());

        // Validate the LibTrack in the database
        List<LibTrack> libTrackList = libTrackRepository.findAll();
        assertThat(libTrackList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibTrack() throws Exception {
        // Initialize the database
        libTrackRepository.saveAndFlush(libTrack);
        int databaseSizeBeforeDelete = libTrackRepository.findAll().size();

        // Get the libTrack
        restLibTrackMockMvc.perform(delete("/api/lib-tracks/{id}", libTrack.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LibTrack> libTrackList = libTrackRepository.findAll();
        assertThat(libTrackList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibTrack.class);
    }
}
