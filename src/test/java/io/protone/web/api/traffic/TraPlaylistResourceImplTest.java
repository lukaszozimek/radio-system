package io.protone.web.api.traffic;

import com.google.common.collect.Lists;
import io.protone.ProtoneApp;
import io.protone.domain.*;
import io.protone.repository.traffic.TraPlaylistRepository;
import io.protone.service.cor.CorChannelService;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.traffic.TraPlaylistService;
import io.protone.util.TestUtil;
import io.protone.web.api.cor.CorNetworkResourceIntTest;
import io.protone.web.api.traffic.impl.TraPlaylistResourceImpl;
import io.protone.web.rest.dto.traffic.TraOrderDTO;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraPlaylistResourceImplTest {

    private static final LocalDate DEFAULT_PLAYLIST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PLAYLIST_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TraPlaylistRepository traPlaylistRepository;


    @Autowired
    private TraPlaylistService traPlaylistService;

    @Autowired
    private TraPlaylistMapper traPlaylistMapper;

    @Autowired
    private CorChannelService corChannelService;

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

    private MockMvc restTraPlaylistMockMvc;

    private TraPlaylist traPlaylist;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraPlaylist createEntity(EntityManager em) {
        TraPlaylist traPlaylist = new TraPlaylist()
            .playlistDate(DEFAULT_PLAYLIST_DATE);
        return traPlaylist;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TraPlaylistResourceImpl traPlaylistResource = new TraPlaylistResourceImpl();
        ReflectionTestUtils.setField(traPlaylistResource, "traPlaylistService", traPlaylistService);
        ReflectionTestUtils.setField(traPlaylistResource, "traPlaylistMapper", traPlaylistMapper);
        ReflectionTestUtils.setField(traPlaylistResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(traPlaylistResource, "corChannelService", corChannelService);

        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);

        corChannel = new CorChannel().shortcut("tes");
        corChannel.setId(1L);
        this.restTraPlaylistMockMvc = MockMvcBuilders.standaloneSetup(traPlaylistResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        traPlaylist = createEntity(em).network(corNetwork).channel(corChannel);
    }

    @Test
    @Transactional
    public void createTraPlaylist() throws Exception {
        int databaseSizeBeforeCreate = traPlaylistRepository.findAll().size();

        // Create the TraPlaylist
        TraPlaylistDTO traPlaylistDTO = traPlaylistMapper.DB2DTO(traPlaylist);

        restTraPlaylistMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
            .andExpect(status().isCreated());

        // Validate the TraPlaylist in the database
        List<TraPlaylist> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate + 1);
        TraPlaylist testTraPlaylist = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testTraPlaylist.getPlaylistDate()).isEqualTo(DEFAULT_PLAYLIST_DATE);
    }

    @Test
    @Transactional
    public void createBatchTraPlaylist() throws Exception {
        int databaseSizeBeforeCreate = traPlaylistRepository.findAll().size();

        // Create the TraPlaylist
        List<TraPlaylistDTO> traPlaylistDTOS = traPlaylistMapper.DBs2DTOs(Lists.newArrayList(traPlaylist, traPlaylist.playlistDate(LocalDate.now().plusMonths(1))));


        restTraPlaylistMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/batch", corNetwork.getShortcut(), corChannel.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTOS)))
            .andExpect(status().isCreated());

        // Validate the TraPlaylist in the database
        List<TraPlaylist> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate + 2);
    }

    @Test
    @Transactional
    public void createTraPlaylistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traPlaylistRepository.findAll().size();

        // Create the TraPlaylist with an existing ID
        TraPlaylist existingTraPlaylist = new TraPlaylist();
        existingTraPlaylist.setId(1L);
        TraPlaylistDTO existingTraPlaylistDTO = traPlaylistMapper.DB2DTO(existingTraPlaylist);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraPlaylistMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTraPlaylistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraPlaylist> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTraPlaylists() throws Exception {
        // Initialize the database
        traPlaylistRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));

        // Get all the traPlaylistList
        restTraPlaylistMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist?sort=id,desc", corNetwork.getShortcut(), corChannel.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traPlaylist.getId().intValue())))
            .andExpect(jsonPath("$.[*].playlistDate").value(hasItem(DEFAULT_PLAYLIST_DATE.toString())));
    }

    @Test
    @Transactional
    public void getTraPlaylist() throws Exception {
        // Initialize the database
        traPlaylistRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));

        // Get the traPlaylist
        restTraPlaylistMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}", corNetwork.getShortcut(), corChannel.getShortcut(), DEFAULT_PLAYLIST_DATE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traPlaylist.getId().intValue()))
            .andExpect(jsonPath("$.playlistDate").value(DEFAULT_PLAYLIST_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraPlaylist() throws Exception {
        // Get the traPlaylist
        restTraPlaylistMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}", corNetwork.getShortcut(), corChannel.getShortcut(), LocalDate.now()))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraPlaylist() throws Exception {
        // Initialize the database
        traPlaylistRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeUpdate = traPlaylistRepository.findAll().size();

        // Update the traPlaylist
        TraPlaylist updatedTraPlaylist = traPlaylistRepository.findOne(traPlaylist.getId());
        updatedTraPlaylist
            .playlistDate(UPDATED_PLAYLIST_DATE);
        TraPlaylistDTO traPlaylistDTO = traPlaylistMapper.DB2DTO(updatedTraPlaylist);

        restTraPlaylistMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
            .andExpect(status().isOk());

        // Validate the TraPlaylist in the database
        List<TraPlaylist> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate);
        TraPlaylist testTraPlaylist = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testTraPlaylist.getPlaylistDate()).isEqualTo(UPDATED_PLAYLIST_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTraPlaylist() throws Exception {
        int databaseSizeBeforeUpdate = traPlaylistRepository.findAll().size();

        // Create the TraPlaylist
        TraPlaylistDTO traPlaylistDTO = traPlaylistMapper.DB2DTO(traPlaylist);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraPlaylistMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
            .andExpect(status().isCreated());

        // Validate the TraPlaylist in the database
        List<TraPlaylist> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraPlaylist() throws Exception {
        // Initialize the database
        traPlaylistRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeDelete = traPlaylistRepository.findAll().size();

        // Get the traPlaylist
        restTraPlaylistMockMvc.perform(delete("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}", corNetwork.getShortcut(), corChannel.getShortcut(), DEFAULT_PLAYLIST_DATE)
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TraPlaylist> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void shouldDownloadTraPlaylist() throws Exception {
        // Initialize the database

        traPlaylistService.savePlaylist(traPlaylist.network(corNetwork).channel(corChannel).addPlaylists(new TraBlock().channel(corChannel).network(corNetwork).addEmissions(new TraEmission().timeStart(1L).timeStop(2L))));
        String csvFileName = DEFAULT_PLAYLIST_DATE + ".csv";
        // Get the traPlaylist
        restTraPlaylistMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}/download", corNetwork.getShortcut(), corChannel.getShortcut(), DEFAULT_PLAYLIST_DATE)
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/csv"))
            .andExpect(header().string("Content-Disposition", String.format("attachment; filename=\"%s\"",
                csvFileName)));

        // Validate the database is empty

    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        traPlaylist = createEntity(em).network(corNetwork).channel(corChannel);

        int databaseSizeBeforeTest = traPlaylistRepository.findAll().size();
        // set the field null
        traPlaylist.setPlaylistDate(null);

        // Create the TraOrder, which fails.
        TraPlaylistDTO traOrderDTO = traPlaylistMapper.DB2DTO(traPlaylist);

        restTraPlaylistMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traOrderDTO)))
            .andExpect(status().isBadRequest());

        List<TraPlaylist> traOrderList = traPlaylistRepository.findAll();
        assertThat(traOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraPlaylist.class);
    }
}
