package io.protone.application.web.api.scheduler;


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

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORTNAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORTNAME = "BBBBBBBBBB";
    @Autowired
    private SchClockRepository schClockRepository;


    @Autowired
    private SchClockService schClockService;

    @Autowired
    private SchClockMapper schClockMapper;

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
                .name(DEFAULT_NAME)
                .shortName(DEFAULT_SHORTNAME);
        return traPlaylist;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchClockResourceImpl traPlaylistResource = new SchClockResourceImpl();
        ReflectionTestUtils.setField(traPlaylistResource, "schClockService", schClockService);
        ReflectionTestUtils.setField(traPlaylistResource, "schClockMapper", schClockMapper);
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
        int databaseSizeBeforeCreate = schClockRepository.findAll().size();

        // Create the SchClock
        SchClockDTO traPlaylistDTO = schClockMapper.DB2DTO(traPlaylist);

        restSchClockMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isCreated());

        // Validate the SchClock in the database
        List<SchClock> traPlaylistList = schClockRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate + 1);
        SchClock testSchClock = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchClock.getName()).isEqualTo(DEFAULT_NAME);
    }


    @Test
    @Transactional
    public void createSchClockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schClockRepository.findAll().size();

        // Create the SchClock with an existing ID
        SchClock existingSchClock = new SchClock();
        existingSchClock.setId(1L);
        SchClockDTO existingSchClockDTO = schClockMapper.DB2DTO(existingSchClock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchClockMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingSchClockDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchClock> traPlaylistList = schClockRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchClocks() throws Exception {
        // Initialize the database
        schClockRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));

        // Get all the traPlaylistList
        restSchClockMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock?sort=id,desc", corNetwork.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(traPlaylist.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORTNAME.toString())));

    }

    @Test
    @Transactional
    public void getSchClock() throws Exception {
        // Initialize the database
        schClockRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));

        // Get the traPlaylist
        restSchClockMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/{shortName}", corNetwork.getShortcut(), corChannel.getShortcut(), DEFAULT_SHORTNAME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(traPlaylist.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORTNAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchClock() throws Exception {
        // Get the traPlaylist
        restSchClockMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/{shortName}", corNetwork.getShortcut(), corChannel.getShortcut(), Long.MAX_VALUE))
                .andExpect(status().isNotFound());

    }

    @Test
    @Transactional
    public void updateSchClock() throws Exception {
        // Initialize the database
        schClockRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeUpdate = schClockRepository.findAll().size();

        // Update the traPlaylist
        SchClock updatedSchClock = schClockRepository.findOne(traPlaylist.getId());
        updatedSchClock
                .name(UPDATED_NAME).shortName(UPDATED_SHORTNAME);
        SchClockDTO traPlaylistDTO = schClockMapper.DB2DTO(updatedSchClock);

        restSchClockMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isOk());

        // Validate the SchClock in the database
        List<SchClock> traPlaylistList = schClockRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate);
        SchClock testSchClock = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchClock.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchClock.getShortName()).isEqualTo(UPDATED_SHORTNAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSchClock() throws Exception {
        int databaseSizeBeforeUpdate = schClockRepository.findAll().size();

        // Create the SchClock
        SchClockDTO traPlaylistDTO = schClockMapper.DB2DTO(traPlaylist);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchClockMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isCreated());

        // Validate the SchClock in the database
        List<SchClock> traPlaylistList = schClockRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchClock() throws Exception {
        // Initialize the database
        schClockRepository.saveAndFlush(traPlaylist.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeDelete = schClockRepository.findAll().size();

        // Get the traPlaylist
        restSchClockMockMvc.perform(delete("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock/{shortName}", corNetwork.getShortcut(), corChannel.getShortcut(), DEFAULT_SHORTNAME)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SchClock> traPlaylistList = schClockRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        traPlaylist = createEntity(em).network(corNetwork).channel(corChannel);

        int databaseSizeBeforeTest = schClockRepository.findAll().size();
        // set the field null

        // Create the TraOrder, which fails.
        SchClockDTO traOrderDTO = schClockMapper.DB2DTO(traPlaylist);

        restSchClockMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/clock", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traOrderDTO)))
                .andExpect(status().isBadRequest());

        List<SchClock> traOrderList = schClockRepository.findAll();
        assertThat(traOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchClock.class);
    }
}
