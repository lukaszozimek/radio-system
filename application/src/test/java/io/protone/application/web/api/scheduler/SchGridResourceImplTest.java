package io.protone.application.web.api.scheduler;


import com.google.common.collect.Lists;
import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.application.web.api.scheduler.impl.SchGridResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchGridDTO;
import io.protone.scheduler.domain.SchGrid;
import io.protone.scheduler.mapper.SchGridMapper;
import io.protone.scheduler.repository.SchGridRepository;
import io.protone.scheduler.service.SchGridService;
import io.protone.traffic.domain.TraBlock;
import io.protone.traffic.domain.TraEmission;
import org.junit.Before;
import org.junit.Ignore;
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
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 14.05.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SchGridResourceImplTest {

    private static final LocalDate DEFAULT_PLAYLIST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PLAYLIST_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SchGridRepository traPlaylistRepository;


    @Autowired
    private SchGridService traPlaylistService;

    @Autowired
    private SchGridMapper traPlaylistMapper;

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

    private MockMvc restSchGridMockMvc;

    private SchGrid traPlaylist;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchGrid createEntity(EntityManager em) {
        SchGrid traPlaylist = new SchGrid()
                .playlistDate(DEFAULT_PLAYLIST_DATE);
        return traPlaylist;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchGridResourceImpl traPlaylistResource = new SchGridResourceImpl();
        ReflectionTestUtils.setField(traPlaylistResource, "traPlaylistService", traPlaylistService);
        ReflectionTestUtils.setField(traPlaylistResource, "traPlaylistMapper", traPlaylistMapper);
        ReflectionTestUtils.setField(traPlaylistResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(traPlaylistResource, "corChannelService", corChannelService);


        this.restSchGridMockMvc = MockMvcBuilders.standaloneSetup(traPlaylistResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);
        corChannel = new CorChannel().shortcut("tes");
        corChannel.setId(1L);
        traPlaylist = createEntity(em).network(corNetwork).channel(corChannel);
    }

    @Test
    @Transactional
    public void createSchGrid() throws Exception {
        int databaseSizeBeforeCreate = traPlaylistRepository.findAll().size();

        // Create the SchGrid
        SchGridDTO traPlaylistDTO = traPlaylistMapper.DB2DTO(traPlaylist);

        restSchGridMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isCreated());

        // Validate the SchGrid in the database
        List<SchGrid> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate + 1);
        SchGrid testSchGrid = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchGrid.getPlaylistDate()).isEqualTo(DEFAULT_PLAYLIST_DATE);
    }

    @Test
    @Transactional
    public void createBatchSchGrid() throws Exception {
        int databaseSizeBeforeCreate = traPlaylistRepository.findAll().size();


        SchGrid traPlaylist1 = createEntity(em).network(corNetwork).channel(corChannel);
        traPlaylist1.playlistDate(LocalDate.now().plusMonths(1));
        // Create the SchGrid
        List<SchGridDTO> traPlaylistDTOS = traPlaylistMapper.DBs2DTOs(Lists.newArrayList(traPlaylist, traPlaylist1));


        restSchGridMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/batch", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTOS)))
                .andExpect(status().isCreated());

        // Validate the SchGrid in the database
        List<SchGrid> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate + 2);
    }

    @Test
    @Transactional
    public void createSchGridWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traPlaylistRepository.findAll().size();

        // Create the SchGrid with an existing ID
        SchGrid existingSchGrid = new SchGrid();
        existingSchGrid.setId(1L);
        SchGridDTO existingSchGridDTO = traPlaylistMapper.DB2DTO(existingSchGrid);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchGridMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingSchGridDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchGrid> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchGrids() throws Exception {
        // Initialize the database
        traPlaylistRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));

        // Get all the traPlaylistList
        restSchGridMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist?sort=id,desc", corNetwork.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(traPlaylist.getId().intValue())))
                .andExpect(jsonPath("$.[*].playlistDate").value(hasItem(DEFAULT_PLAYLIST_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSchGrid() throws Exception {
        // Initialize the database
        traPlaylistRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));

        // Get the traPlaylist
        restSchGridMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}", corNetwork.getShortcut(), corChannel.getShortcut(), DEFAULT_PLAYLIST_DATE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(traPlaylist.getId().intValue()))
                .andExpect(jsonPath("$.playlistDate").value(DEFAULT_PLAYLIST_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchGrid() throws Exception {
        // Get the traPlaylist
        LocalDate localDate = LocalDate.now();
        restSchGridMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}", corNetwork.getShortcut(), corChannel.getShortcut(), localDate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.playlistDate").value(localDate.toString()));
    }

    @Test
    @Transactional
    public void updateSchGrid() throws Exception {
        // Initialize the database
        traPlaylistRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeUpdate = traPlaylistRepository.findAll().size();

        // Update the traPlaylist
        SchGrid updatedSchGrid = traPlaylistRepository.findOne(traPlaylist.getId());
        updatedSchGrid
                .playlistDate(UPDATED_PLAYLIST_DATE);
        SchGridDTO traPlaylistDTO = traPlaylistMapper.DB2DTO(updatedSchGrid);

        restSchGridMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isOk());

        // Validate the SchGrid in the database
        List<SchGrid> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate);
        SchGrid testSchGrid = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchGrid.getPlaylistDate()).isEqualTo(UPDATED_PLAYLIST_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSchGrid() throws Exception {
        int databaseSizeBeforeUpdate = traPlaylistRepository.findAll().size();

        // Create the SchGrid
        SchGridDTO traPlaylistDTO = traPlaylistMapper.DB2DTO(traPlaylist);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchGridMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isCreated());

        // Validate the SchGrid in the database
        List<SchGrid> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchGrid() throws Exception {
        // Initialize the database
        traPlaylistRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeDelete = traPlaylistRepository.findAll().size();

        // Get the traPlaylist
        restSchGridMockMvc.perform(delete("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}", corNetwork.getShortcut(), corChannel.getShortcut(), DEFAULT_PLAYLIST_DATE)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SchGrid> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeDelete - 1);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        traPlaylist = createEntity(em).network(corNetwork).channel(corChannel);

        int databaseSizeBeforeTest = traPlaylistRepository.findAll().size();
        // set the field null
        traPlaylist.setPlaylistDate(null);

        // Create the TraOrder, which fails.
        SchGridDTO traOrderDTO = traPlaylistMapper.DB2DTO(traPlaylist);

        restSchGridMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traOrderDTO)))
                .andExpect(status().isBadRequest());

        List<SchGrid> traOrderList = traPlaylistRepository.findAll();
        assertThat(traOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchGrid.class);
    }
}
