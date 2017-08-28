package io.protone.application.web.api.scheduler;


import com.google.common.collect.Lists;
import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.application.web.api.scheduler.impl.SchClockResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchClockDTO;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.mapper.SchClockMapper;
import io.protone.scheduler.repository.SchClockRepository;
import io.protone.scheduler.service.SchClockService;
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
public class SchClockResourceImplTest {

    private static final LocalDate DEFAULT_PLAYLIST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PLAYLIST_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SchClockRepository traPlaylistRepository;


    @Autowired
    private SchClockService traPlaylistService;

    @Autowired
    private SchClockMapper traPlaylistMapper;

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

    private MockMvc restSchClockMockMvc;

    private SchClock traPlaylist;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchClock createEntity(EntityManager em) {
        SchClock traPlaylist = new SchClock()
                .playlistDate(DEFAULT_PLAYLIST_DATE);
        return traPlaylist;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchClockResourceImpl traPlaylistResource = new SchClockResourceImpl();
        ReflectionTestUtils.setField(traPlaylistResource, "traPlaylistService", traPlaylistService);
        ReflectionTestUtils.setField(traPlaylistResource, "traPlaylistMapper", traPlaylistMapper);
        ReflectionTestUtils.setField(traPlaylistResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(traPlaylistResource, "corChannelService", corChannelService);


        this.restSchClockMockMvc = MockMvcBuilders.standaloneSetup(traPlaylistResource)
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
    public void createSchClock() throws Exception {
        int databaseSizeBeforeCreate = traPlaylistRepository.findAll().size();

        // Create the SchClock
        SchClockDTO traPlaylistDTO = traPlaylistMapper.DB2DTO(traPlaylist);

        restSchClockMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isCreated());

        // Validate the SchClock in the database
        List<SchClock> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate + 1);
        SchClock testSchClock = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchClock.getPlaylistDate()).isEqualTo(DEFAULT_PLAYLIST_DATE);
    }

    @Test
    @Transactional
    public void createBatchSchClock() throws Exception {
        int databaseSizeBeforeCreate = traPlaylistRepository.findAll().size();


        SchClock traPlaylist1 = createEntity(em).network(corNetwork).channel(corChannel);
        traPlaylist1.playlistDate(LocalDate.now().plusMonths(1));
        // Create the SchClock
        List<SchClockDTO> traPlaylistDTOS = traPlaylistMapper.DBs2DTOs(Lists.newArrayList(traPlaylist, traPlaylist1));


        restSchClockMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/batch", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTOS)))
                .andExpect(status().isCreated());

        // Validate the SchClock in the database
        List<SchClock> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate + 2);
    }

    @Test
    @Transactional
    public void createSchClockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traPlaylistRepository.findAll().size();

        // Create the SchClock with an existing ID
        SchClock existingSchClock = new SchClock();
        existingSchClock.setId(1L);
        SchClockDTO existingSchClockDTO = traPlaylistMapper.DB2DTO(existingSchClock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchClockMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingSchClockDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchClock> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchClocks() throws Exception {
        // Initialize the database
        traPlaylistRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));

        // Get all the traPlaylistList
        restSchClockMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist?sort=id,desc", corNetwork.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(traPlaylist.getId().intValue())))
                .andExpect(jsonPath("$.[*].playlistDate").value(hasItem(DEFAULT_PLAYLIST_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSchClock() throws Exception {
        // Initialize the database
        traPlaylistRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));

        // Get the traPlaylist
        restSchClockMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}", corNetwork.getShortcut(), corChannel.getShortcut(), DEFAULT_PLAYLIST_DATE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(traPlaylist.getId().intValue()))
                .andExpect(jsonPath("$.playlistDate").value(DEFAULT_PLAYLIST_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchClock() throws Exception {
        // Get the traPlaylist
        LocalDate localDate = LocalDate.now();
        restSchClockMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}", corNetwork.getShortcut(), corChannel.getShortcut(), localDate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.playlistDate").value(localDate.toString()));
    }

    @Test
    @Transactional
    public void updateSchClock() throws Exception {
        // Initialize the database
        traPlaylistRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeUpdate = traPlaylistRepository.findAll().size();

        // Update the traPlaylist
        SchClock updatedSchClock = traPlaylistRepository.findOne(traPlaylist.getId());
        updatedSchClock
                .playlistDate(UPDATED_PLAYLIST_DATE);
        SchClockDTO traPlaylistDTO = traPlaylistMapper.DB2DTO(updatedSchClock);

        restSchClockMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isOk());

        // Validate the SchClock in the database
        List<SchClock> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate);
        SchClock testSchClock = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchClock.getPlaylistDate()).isEqualTo(UPDATED_PLAYLIST_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSchClock() throws Exception {
        int databaseSizeBeforeUpdate = traPlaylistRepository.findAll().size();

        // Create the SchClock
        SchClockDTO traPlaylistDTO = traPlaylistMapper.DB2DTO(traPlaylist);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchClockMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isCreated());

        // Validate the SchClock in the database
        List<SchClock> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchClock() throws Exception {
        // Initialize the database
        traPlaylistRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeDelete = traPlaylistRepository.findAll().size();

        // Get the traPlaylist
        restSchClockMockMvc.perform(delete("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/{date}", corNetwork.getShortcut(), corChannel.getShortcut(), DEFAULT_PLAYLIST_DATE)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SchClock> traPlaylistList = traPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        traPlaylist = createEntity(em).network(corNetwork).channel(corChannel);

        int databaseSizeBeforeTest = traPlaylistRepository.findAll().size();
        // set the field null

        // Create the TraOrder, which fails.
        SchClockDTO traOrderDTO = traPlaylistMapper.DB2DTO(traPlaylist);

        restSchClockMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traOrderDTO)))
                .andExpect(status().isBadRequest());

        List<SchClock> traOrderList = traPlaylistRepository.findAll();
        assertThat(traOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchClock.class);
    }
}
