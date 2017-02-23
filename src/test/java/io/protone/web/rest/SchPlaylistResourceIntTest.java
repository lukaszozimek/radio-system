package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.SchPlaylist;
import io.protone.repository.SchPlaylistRepository;
import io.protone.service.dto.SchPlaylistDTO;
import io.protone.service.mapper.SchPlaylistMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SchPlaylistResource REST controller.
 *
 * @see SchPlaylistResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SchPlaylistResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DIM_YEAR = 1;
    private static final Integer UPDATED_DIM_YEAR = 2;

    private static final Integer DEFAULT_DIM_MONTH = 1;
    private static final Integer UPDATED_DIM_MONTH = 2;

    private static final Integer DEFAULT_DIM_DAY = 1;
    private static final Integer UPDATED_DIM_DAY = 2;

    @Autowired
    private SchPlaylistRepository schPlaylistRepository;

    @Autowired
    private SchPlaylistMapper schPlaylistMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restSchPlaylistMockMvc;

    private SchPlaylist schPlaylist;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            SchPlaylistResource schPlaylistResource = new SchPlaylistResource(schPlaylistRepository, schPlaylistMapper);
        this.restSchPlaylistMockMvc = MockMvcBuilders.standaloneSetup(schPlaylistResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchPlaylist createEntity(EntityManager em) {
        SchPlaylist schPlaylist = new SchPlaylist()
                .date(DEFAULT_DATE)
                .dimYear(DEFAULT_DIM_YEAR)
                .dimMonth(DEFAULT_DIM_MONTH)
                .dimDay(DEFAULT_DIM_DAY);
        return schPlaylist;
    }

    @Before
    public void initTest() {
        schPlaylist = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchPlaylist() throws Exception {
        int databaseSizeBeforeCreate = schPlaylistRepository.findAll().size();

        // Create the SchPlaylist
        SchPlaylistDTO schPlaylistDTO = schPlaylistMapper.schPlaylistToSchPlaylistDTO(schPlaylist);

        restSchPlaylistMockMvc.perform(post("/api/sch-playlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schPlaylistDTO)))
            .andExpect(status().isCreated());

        // Validate the SchPlaylist in the database
        List<SchPlaylist> schPlaylistList = schPlaylistRepository.findAll();
        assertThat(schPlaylistList).hasSize(databaseSizeBeforeCreate + 1);
        SchPlaylist testSchPlaylist = schPlaylistList.get(schPlaylistList.size() - 1);
        assertThat(testSchPlaylist.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSchPlaylist.getDimYear()).isEqualTo(DEFAULT_DIM_YEAR);
        assertThat(testSchPlaylist.getDimMonth()).isEqualTo(DEFAULT_DIM_MONTH);
        assertThat(testSchPlaylist.getDimDay()).isEqualTo(DEFAULT_DIM_DAY);
    }

    @Test
    @Transactional
    public void createSchPlaylistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schPlaylistRepository.findAll().size();

        // Create the SchPlaylist with an existing ID
        SchPlaylist existingSchPlaylist = new SchPlaylist();
        existingSchPlaylist.setId(1L);
        SchPlaylistDTO existingSchPlaylistDTO = schPlaylistMapper.schPlaylistToSchPlaylistDTO(existingSchPlaylist);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchPlaylistMockMvc.perform(post("/api/sch-playlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSchPlaylistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchPlaylist> schPlaylistList = schPlaylistRepository.findAll();
        assertThat(schPlaylistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = schPlaylistRepository.findAll().size();
        // set the field null
        schPlaylist.setDate(null);

        // Create the SchPlaylist, which fails.
        SchPlaylistDTO schPlaylistDTO = schPlaylistMapper.schPlaylistToSchPlaylistDTO(schPlaylist);

        restSchPlaylistMockMvc.perform(post("/api/sch-playlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schPlaylistDTO)))
            .andExpect(status().isBadRequest());

        List<SchPlaylist> schPlaylistList = schPlaylistRepository.findAll();
        assertThat(schPlaylistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchPlaylists() throws Exception {
        // Initialize the database
        schPlaylistRepository.saveAndFlush(schPlaylist);

        // Get all the schPlaylistList
        restSchPlaylistMockMvc.perform(get("/api/sch-playlists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schPlaylist.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dimYear").value(hasItem(DEFAULT_DIM_YEAR)))
            .andExpect(jsonPath("$.[*].dimMonth").value(hasItem(DEFAULT_DIM_MONTH)))
            .andExpect(jsonPath("$.[*].dimDay").value(hasItem(DEFAULT_DIM_DAY)));
    }

    @Test
    @Transactional
    public void getSchPlaylist() throws Exception {
        // Initialize the database
        schPlaylistRepository.saveAndFlush(schPlaylist);

        // Get the schPlaylist
        restSchPlaylistMockMvc.perform(get("/api/sch-playlists/{id}", schPlaylist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schPlaylist.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.dimYear").value(DEFAULT_DIM_YEAR))
            .andExpect(jsonPath("$.dimMonth").value(DEFAULT_DIM_MONTH))
            .andExpect(jsonPath("$.dimDay").value(DEFAULT_DIM_DAY));
    }

    @Test
    @Transactional
    public void getNonExistingSchPlaylist() throws Exception {
        // Get the schPlaylist
        restSchPlaylistMockMvc.perform(get("/api/sch-playlists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchPlaylist() throws Exception {
        // Initialize the database
        schPlaylistRepository.saveAndFlush(schPlaylist);
        int databaseSizeBeforeUpdate = schPlaylistRepository.findAll().size();

        // Update the schPlaylist
        SchPlaylist updatedSchPlaylist = schPlaylistRepository.findOne(schPlaylist.getId());
        updatedSchPlaylist
                .date(UPDATED_DATE)
                .dimYear(UPDATED_DIM_YEAR)
                .dimMonth(UPDATED_DIM_MONTH)
                .dimDay(UPDATED_DIM_DAY);
        SchPlaylistDTO schPlaylistDTO = schPlaylistMapper.schPlaylistToSchPlaylistDTO(updatedSchPlaylist);

        restSchPlaylistMockMvc.perform(put("/api/sch-playlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schPlaylistDTO)))
            .andExpect(status().isOk());

        // Validate the SchPlaylist in the database
        List<SchPlaylist> schPlaylistList = schPlaylistRepository.findAll();
        assertThat(schPlaylistList).hasSize(databaseSizeBeforeUpdate);
        SchPlaylist testSchPlaylist = schPlaylistList.get(schPlaylistList.size() - 1);
        assertThat(testSchPlaylist.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSchPlaylist.getDimYear()).isEqualTo(UPDATED_DIM_YEAR);
        assertThat(testSchPlaylist.getDimMonth()).isEqualTo(UPDATED_DIM_MONTH);
        assertThat(testSchPlaylist.getDimDay()).isEqualTo(UPDATED_DIM_DAY);
    }

    @Test
    @Transactional
    public void updateNonExistingSchPlaylist() throws Exception {
        int databaseSizeBeforeUpdate = schPlaylistRepository.findAll().size();

        // Create the SchPlaylist
        SchPlaylistDTO schPlaylistDTO = schPlaylistMapper.schPlaylistToSchPlaylistDTO(schPlaylist);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchPlaylistMockMvc.perform(put("/api/sch-playlists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schPlaylistDTO)))
            .andExpect(status().isCreated());

        // Validate the SchPlaylist in the database
        List<SchPlaylist> schPlaylistList = schPlaylistRepository.findAll();
        assertThat(schPlaylistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchPlaylist() throws Exception {
        // Initialize the database
        schPlaylistRepository.saveAndFlush(schPlaylist);
        int databaseSizeBeforeDelete = schPlaylistRepository.findAll().size();

        // Get the schPlaylist
        restSchPlaylistMockMvc.perform(delete("/api/sch-playlists/{id}", schPlaylist.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SchPlaylist> schPlaylistList = schPlaylistRepository.findAll();
        assertThat(schPlaylistList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchPlaylist.class);
    }
}
