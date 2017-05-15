package io.protone.web.api.traffic;

import io.protone.ProtoneApp;
import io.protone.domain.TraPlaylist;
import io.protone.repository.traffic.TraPlaylistRepository;
import io.protone.util.TestUtil;
import io.protone.web.rest.dto.traffic.TraPlaylistDTO;
import io.protone.web.rest.errors.ExceptionTranslator;
import io.protone.web.rest.mapper.TraPlaylistMapper;
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
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ProtoneApp.class)
public class TraPlaylistResourceImplTest {
//
//    private static final LocalDate DEFAULT_PLAYLIST_DATE = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_PLAYLIST_DATE = LocalDate.now(ZoneId.systemDefault());
//
//    @Autowired
//    private TraPlaylistRepository traPlaylistRepository;
//
//    @Autowired
//    private TraPlaylistMapper traPlaylistMapper;
//
//    @Autowired
//    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
//
//    @Autowired
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
//
//    @Autowired
//    private ExceptionTranslator exceptionTranslator;
//
//    @Autowired
//    private EntityManager em;
//
//    private MockMvc restTraPlaylistMockMvc;
//
//    private TraPlaylist traPlaylist;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        TraPlaylistResource traPlaylistResource = new TraPlaylistResource(traPlaylistRepository, traPlaylistMapper);
//        this.restTraPlaylistMockMvc = MockMvcBuilders.standaloneSetup(traPlaylistResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setControllerAdvice(exceptionTranslator)
//            .setMessageConverters(jacksonMessageConverter).build();
//    }
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static TraPlaylist createEntity(EntityManager em) {
//        TraPlaylist traPlaylist = new TraPlaylist()
//            .playlistDate(DEFAULT_PLAYLIST_DATE);
//        return traPlaylist;
//    }
//
//    @Before
//    public void initTest() {
//        traPlaylist = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createTraPlaylist() throws Exception {
//        int databaseSizeBeforeCreate = traPlaylistRepository.findAll().size();
//
//        // Create the TraPlaylist
//        TraPlaylistDTO traPlaylistDTO = traPlaylistMapper.traPlaylistToTraPlaylistDTO(traPlaylist);
//
//        restTraPlaylistMockMvc.perform(post("/api/tra-playlists")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the TraPlaylist in the database
//        List<TraPlaylist> traPlaylistList = traPlaylistRepository.findAll();
//        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate + 1);
//        TraPlaylist testTraPlaylist = traPlaylistList.get(traPlaylistList.size() - 1);
//        assertThat(testTraPlaylist.getPlaylistDate()).isEqualTo(DEFAULT_PLAYLIST_DATE);
//    }
//
//    @Test
//    @Transactional
//    public void createTraPlaylistWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = traPlaylistRepository.findAll().size();
//
//        // Create the TraPlaylist with an existing ID
//        TraPlaylist existingTraPlaylist = new TraPlaylist();
//        existingTraPlaylist.setId(1L);
//        TraPlaylistDTO existingTraPlaylistDTO = traPlaylistMapper.traPlaylistToTraPlaylistDTO(existingTraPlaylist);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restTraPlaylistMockMvc.perform(post("/api/tra-playlists")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(existingTraPlaylistDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Alice in the database
//        List<TraPlaylist> traPlaylistList = traPlaylistRepository.findAll();
//        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    public void getAllTraPlaylists() throws Exception {
//        // Initialize the database
//        traPlaylistRepository.saveAndFlush(traPlaylist);
//
//        // Get all the traPlaylistList
//        restTraPlaylistMockMvc.perform(get("/api/tra-playlists?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(traPlaylist.getId().intValue())))
//            .andExpect(jsonPath("$.[*].playlistDate").value(hasItem(DEFAULT_PLAYLIST_DATE.toString())));
//    }
//
//    @Test
//    @Transactional
//    public void getTraPlaylist() throws Exception {
//        // Initialize the database
//        traPlaylistRepository.saveAndFlush(traPlaylist);
//
//        // Get the traPlaylist
//        restTraPlaylistMockMvc.perform(get("/api/tra-playlists/{id}", traPlaylist.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(traPlaylist.getId().intValue()))
//            .andExpect(jsonPath("$.playlistDate").value(DEFAULT_PLAYLIST_DATE.toString()));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingTraPlaylist() throws Exception {
//        // Get the traPlaylist
//        restTraPlaylistMockMvc.perform(get("/api/tra-playlists/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateTraPlaylist() throws Exception {
//        // Initialize the database
//        traPlaylistRepository.saveAndFlush(traPlaylist);
//        int databaseSizeBeforeUpdate = traPlaylistRepository.findAll().size();
//
//        // Update the traPlaylist
//        TraPlaylist updatedTraPlaylist = traPlaylistRepository.findOne(traPlaylist.getId());
//        updatedTraPlaylist
//            .playlistDate(UPDATED_PLAYLIST_DATE);
//        TraPlaylistDTO traPlaylistDTO = traPlaylistMapper.traPlaylistToTraPlaylistDTO(updatedTraPlaylist);
//
//        restTraPlaylistMockMvc.perform(put("/api/tra-playlists")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
//            .andExpect(status().isOk());
//
//        // Validate the TraPlaylist in the database
//        List<TraPlaylist> traPlaylistList = traPlaylistRepository.findAll();
//        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate);
//        TraPlaylist testTraPlaylist = traPlaylistList.get(traPlaylistList.size() - 1);
//        assertThat(testTraPlaylist.getPlaylistDate()).isEqualTo(UPDATED_PLAYLIST_DATE);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingTraPlaylist() throws Exception {
//        int databaseSizeBeforeUpdate = traPlaylistRepository.findAll().size();
//
//        // Create the TraPlaylist
//        TraPlaylistDTO traPlaylistDTO = traPlaylistMapper.traPlaylistToTraPlaylistDTO(traPlaylist);
//
//        // If the entity doesn't have an ID, it will be created instead of just being updated
//        restTraPlaylistMockMvc.perform(put("/api/tra-playlists")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the TraPlaylist in the database
//        List<TraPlaylist> traPlaylistList = traPlaylistRepository.findAll();
//        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate + 1);
//    }
//
//    @Test
//    @Transactional
//    public void deleteTraPlaylist() throws Exception {
//        // Initialize the database
//        traPlaylistRepository.saveAndFlush(traPlaylist);
//        int databaseSizeBeforeDelete = traPlaylistRepository.findAll().size();
//
//        // Get the traPlaylist
//        restTraPlaylistMockMvc.perform(delete("/api/tra-playlists/{id}", traPlaylist.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isOk());
//
//        // Validate the database is empty
//        List<TraPlaylist> traPlaylistList = traPlaylistRepository.findAll();
//        assertThat(traPlaylistList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(TraPlaylist.class);
//    }
}
