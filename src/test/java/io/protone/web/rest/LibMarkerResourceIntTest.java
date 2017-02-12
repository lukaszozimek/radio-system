package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LibMarker;
import io.protone.repository.LibMarkerRepository;
import io.protone.service.dto.LibMarkerDTO;
import io.protone.service.mapper.LibMarkerMapper;

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

import io.protone.domain.enumeration.LibMarkerTypeEnum;
/**
 * Test class for the LibMarkerResource REST controller.
 *
 * @see LibMarkerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibMarkerResourceIntTest {

    private static final LibMarkerTypeEnum DEFAULT_MARKER_TYPE = LibMarkerTypeEnum.MT_BASIC;
    private static final LibMarkerTypeEnum UPDATED_MARKER_TYPE = LibMarkerTypeEnum.MT_INTRO;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_START_TIME = 1;
    private static final Integer UPDATED_START_TIME = 2;

    @Autowired
    private LibMarkerRepository libMarkerRepository;

    @Autowired
    private LibMarkerMapper libMarkerMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restLibMarkerMockMvc;

    private LibMarker libMarker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            LibMarkerResource libMarkerResource = new LibMarkerResource(libMarkerRepository, libMarkerMapper);
        this.restLibMarkerMockMvc = MockMvcBuilders.standaloneSetup(libMarkerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibMarker createEntity(EntityManager em) {
        LibMarker libMarker = new LibMarker()
                .markerType(DEFAULT_MARKER_TYPE)
                .name(DEFAULT_NAME)
                .startTime(DEFAULT_START_TIME);
        return libMarker;
    }

    @Before
    public void initTest() {
        libMarker = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibMarker() throws Exception {
        int databaseSizeBeforeCreate = libMarkerRepository.findAll().size();

        // Create the LibMarker
        LibMarkerDTO libMarkerDTO = libMarkerMapper.libMarkerToLibMarkerDTO(libMarker);

        restLibMarkerMockMvc.perform(post("/api/lib-markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libMarkerDTO)))
            .andExpect(status().isCreated());

        // Validate the LibMarker in the database
        List<LibMarker> libMarkerList = libMarkerRepository.findAll();
        assertThat(libMarkerList).hasSize(databaseSizeBeforeCreate + 1);
        LibMarker testLibMarker = libMarkerList.get(libMarkerList.size() - 1);
        assertThat(testLibMarker.getMarkerType()).isEqualTo(DEFAULT_MARKER_TYPE);
        assertThat(testLibMarker.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLibMarker.getStartTime()).isEqualTo(DEFAULT_START_TIME);
    }

    @Test
    @Transactional
    public void createLibMarkerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libMarkerRepository.findAll().size();

        // Create the LibMarker with an existing ID
        LibMarker existingLibMarker = new LibMarker();
        existingLibMarker.setId(1L);
        LibMarkerDTO existingLibMarkerDTO = libMarkerMapper.libMarkerToLibMarkerDTO(existingLibMarker);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibMarkerMockMvc.perform(post("/api/lib-markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLibMarkerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LibMarker> libMarkerList = libMarkerRepository.findAll();
        assertThat(libMarkerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMarkerTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = libMarkerRepository.findAll().size();
        // set the field null
        libMarker.setMarkerType(null);

        // Create the LibMarker, which fails.
        LibMarkerDTO libMarkerDTO = libMarkerMapper.libMarkerToLibMarkerDTO(libMarker);

        restLibMarkerMockMvc.perform(post("/api/lib-markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libMarkerDTO)))
            .andExpect(status().isBadRequest());

        List<LibMarker> libMarkerList = libMarkerRepository.findAll();
        assertThat(libMarkerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = libMarkerRepository.findAll().size();
        // set the field null
        libMarker.setName(null);

        // Create the LibMarker, which fails.
        LibMarkerDTO libMarkerDTO = libMarkerMapper.libMarkerToLibMarkerDTO(libMarker);

        restLibMarkerMockMvc.perform(post("/api/lib-markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libMarkerDTO)))
            .andExpect(status().isBadRequest());

        List<LibMarker> libMarkerList = libMarkerRepository.findAll();
        assertThat(libMarkerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = libMarkerRepository.findAll().size();
        // set the field null
        libMarker.setStartTime(null);

        // Create the LibMarker, which fails.
        LibMarkerDTO libMarkerDTO = libMarkerMapper.libMarkerToLibMarkerDTO(libMarker);

        restLibMarkerMockMvc.perform(post("/api/lib-markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libMarkerDTO)))
            .andExpect(status().isBadRequest());

        List<LibMarker> libMarkerList = libMarkerRepository.findAll();
        assertThat(libMarkerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibMarkers() throws Exception {
        // Initialize the database
        libMarkerRepository.saveAndFlush(libMarker);

        // Get all the libMarkerList
        restLibMarkerMockMvc.perform(get("/api/lib-markers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libMarker.getId().intValue())))
            .andExpect(jsonPath("$.[*].markerType").value(hasItem(DEFAULT_MARKER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)));
    }

    @Test
    @Transactional
    public void getLibMarker() throws Exception {
        // Initialize the database
        libMarkerRepository.saveAndFlush(libMarker);

        // Get the libMarker
        restLibMarkerMockMvc.perform(get("/api/lib-markers/{id}", libMarker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libMarker.getId().intValue()))
            .andExpect(jsonPath("$.markerType").value(DEFAULT_MARKER_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME));
    }

    @Test
    @Transactional
    public void getNonExistingLibMarker() throws Exception {
        // Get the libMarker
        restLibMarkerMockMvc.perform(get("/api/lib-markers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibMarker() throws Exception {
        // Initialize the database
        libMarkerRepository.saveAndFlush(libMarker);
        int databaseSizeBeforeUpdate = libMarkerRepository.findAll().size();

        // Update the libMarker
        LibMarker updatedLibMarker = libMarkerRepository.findOne(libMarker.getId());
        updatedLibMarker
                .markerType(UPDATED_MARKER_TYPE)
                .name(UPDATED_NAME)
                .startTime(UPDATED_START_TIME);
        LibMarkerDTO libMarkerDTO = libMarkerMapper.libMarkerToLibMarkerDTO(updatedLibMarker);

        restLibMarkerMockMvc.perform(put("/api/lib-markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libMarkerDTO)))
            .andExpect(status().isOk());

        // Validate the LibMarker in the database
        List<LibMarker> libMarkerList = libMarkerRepository.findAll();
        assertThat(libMarkerList).hasSize(databaseSizeBeforeUpdate);
        LibMarker testLibMarker = libMarkerList.get(libMarkerList.size() - 1);
        assertThat(testLibMarker.getMarkerType()).isEqualTo(UPDATED_MARKER_TYPE);
        assertThat(testLibMarker.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibMarker.getStartTime()).isEqualTo(UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingLibMarker() throws Exception {
        int databaseSizeBeforeUpdate = libMarkerRepository.findAll().size();

        // Create the LibMarker
        LibMarkerDTO libMarkerDTO = libMarkerMapper.libMarkerToLibMarkerDTO(libMarker);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibMarkerMockMvc.perform(put("/api/lib-markers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libMarkerDTO)))
            .andExpect(status().isCreated());

        // Validate the LibMarker in the database
        List<LibMarker> libMarkerList = libMarkerRepository.findAll();
        assertThat(libMarkerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibMarker() throws Exception {
        // Initialize the database
        libMarkerRepository.saveAndFlush(libMarker);
        int databaseSizeBeforeDelete = libMarkerRepository.findAll().size();

        // Get the libMarker
        restLibMarkerMockMvc.perform(delete("/api/lib-markers/{id}", libMarker.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LibMarker> libMarkerList = libMarkerRepository.findAll();
        assertThat(libMarkerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibMarker.class);
    }
}
