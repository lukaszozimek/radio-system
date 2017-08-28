package io.protone.application.web.api.scheduler;


import com.google.common.collect.Lists;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.application.web.api.scheduler.impl.SchPlaylistResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.domain.SchPlaylist;
import io.protone.scheduler.mapper.SchPlaylistMapper;
import io.protone.scheduler.repository.SchPlaylistRepository;
import io.protone.scheduler.service.SchPlaylistService;
import io.protone.traffic.domain.TraBlock;
import io.protone.traffic.domain.TraEmission;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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


public class SchPlaylistResourceImplTest {


    private static final LocalDate DEFAULT_PLAYLIST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PLAYLIST_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SchPlaylistRepository traPlaylistRepository;


    @Autowired
    private SchPlaylistService traPlaylistService;

    @Autowired
    private SchPlaylistMapper traPlaylistMapper;

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

    private MockMvc restSchPlaylistMockMvc;

    private SchPlaylist traPlaylist;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchPlaylist createEntity(EntityManager em) {
        SchPlaylist traPlaylist = new SchPlaylist()
                .playlistDate(DEFAULT_PLAYLIST_DATE);
        return traPlaylist;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchPlaylistResourceImpl traPlaylistResource = new SchPlaylistResourceImpl();
        ReflectionTestUtils.setField(traPlaylistResource, "traPlaylistService", traPlaylistService);
        ReflectionTestUtils.setField(traPlaylistResource, "traPlaylistMapper", traPlaylistMapper);
        ReflectionTestUtils.setField(traPlaylistResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(traPlaylistResource, "corChannelService", corChannelService);


        this.restSchPlaylistMockMvc = MockMvcBuilders.standaloneSetup(traPlaylistResource)
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
    public void createSchPlaylist() throws Exception {
        int databaseSizeBeforeCreate = traPlaylistRepository.findAll().size();

        // Create the SchPlaylist
        SchPlaylistDTO traPlaylistDTO = traPlaylistMapper.DB2DTO(traPlaylist);

        restSchPlaylistMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isCreated());

        // Validate the SchPlaylist in the database
        List<SchPlaylist> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate + 1);
        SchPlaylist testSchPlaylist = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchPlaylist.getPlaylistDate()).isEqualTo(DEFAULT_PLAYLIST_DATE);
    }

    @Test
    @Transactional
    public void createBatchSchPlaylist() throws Exception {
        int databaseSizeBeforeCreate = traPlaylistRepository.findAll().size();


        SchPlaylist traPlaylist1 = createEntity(em).network(corNetwork).channel(corChannel);
        traPlaylist1.playlistDate(LocalDate.now().plusMonths(1));
        // Create the SchPlaylist
        List<SchPlaylistDTO> traPlaylistDTOS = traPlaylistMapper.DBs2DTOs(Lists.newArrayList(traPlaylist, traPlaylist1));


        restSchPlaylistMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/batch", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTOS)))
                .andExpect(status().isCreated());

        // Validate the SchPlaylist in the database
        List<SchPlaylist> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate + 2);
    }

    @Test
    @Transactional
    public void createSchPlaylistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traPlaylistRepository.findAll().size();

        // Create the SchPlaylist with an existing ID
        SchPlaylist existingSchPlaylist = new SchPlaylist();
        existingSchPlaylist.setId(1L);
        SchPlaylistDTO existingSchPlaylistDTO = traPlaylistMapper.DB2DTO(existingSchPlaylist);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchPlaylistMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingSchPlaylistDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchPlaylist> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchPlaylists() throws Exception {
        // Initialize the database
        traPlaylistRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));

        // Get all the traPlaylistList
        restSchPlaylistMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist?sort=id,desc", corNetwork.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(traPlaylist.getId().intValue())))
                .andExpect(jsonPath("$.[*].playlistDate").value(hasItem(DEFAULT_PLAYLIST_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSchPlaylist() throws Exception {
        // Initialize the database
        traPlaylistRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));

        // Get the traPlaylist
        restSchPlaylistMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}", corNetwork.getShortcut(), corChannel.getShortcut(), DEFAULT_PLAYLIST_DATE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(traPlaylist.getId().intValue()))
                .andExpect(jsonPath("$.playlistDate").value(DEFAULT_PLAYLIST_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchPlaylist() throws Exception {
        // Get the traPlaylist
        LocalDate localDate = LocalDate.now();
        restSchPlaylistMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}", corNetwork.getShortcut(), corChannel.getShortcut(), localDate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.playlistDate").value(localDate.toString()));
    }

    @Test
    @Transactional
    public void updateSchPlaylist() throws Exception {
        // Initialize the database
        traPlaylistRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeUpdate = traPlaylistRepository.findAll().size();

        // Update the traPlaylist
        SchPlaylist updatedSchPlaylist = traPlaylistRepository.findOne(traPlaylist.getId());
        updatedSchPlaylist
                .playlistDate(UPDATED_PLAYLIST_DATE);
        SchPlaylistDTO traPlaylistDTO = traPlaylistMapper.DB2DTO(updatedSchPlaylist);

        restSchPlaylistMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isOk());

        // Validate the SchPlaylist in the database
        List<SchPlaylist> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate);
        SchPlaylist testSchPlaylist = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchPlaylist.getPlaylistDate()).isEqualTo(UPDATED_PLAYLIST_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSchPlaylist() throws Exception {
        int databaseSizeBeforeUpdate = traPlaylistRepository.findAll().size();

        // Create the SchPlaylist
        SchPlaylistDTO traPlaylistDTO = traPlaylistMapper.DB2DTO(traPlaylist);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchPlaylistMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isCreated());

        // Validate the SchPlaylist in the database
        List<SchPlaylist> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchPlaylist() throws Exception {
        // Initialize the database
        traPlaylistRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeDelete = traPlaylistRepository.findAll().size();

        // Get the traPlaylist
        restSchPlaylistMockMvc.perform(delete("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}", corNetwork.getShortcut(), corChannel.getShortcut(), DEFAULT_PLAYLIST_DATE)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SchPlaylist> traPlaylistList = traPlaylistRepository.findAll();
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
        SchPlaylistDTO traOrderDTO = traPlaylistMapper.DB2DTO(traPlaylist);

        restSchPlaylistMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traOrderDTO)))
                .andExpect(status().isBadRequest());

        List<SchPlaylist> traOrderList = traPlaylistRepository.findAll();
        assertThat(traOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchPlaylist.class);
    }

}
