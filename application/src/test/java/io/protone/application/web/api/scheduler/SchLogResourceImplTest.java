package io.protone.application.web.api.scheduler;

import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.application.web.api.scheduler.impl.SchLogConfigurationResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchLogDTO;
import io.protone.scheduler.domain.SchLog;
import io.protone.scheduler.mapper.SchLogMapper;
import io.protone.scheduler.repository.SchLogRepository;
import io.protone.scheduler.service.SchLogService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 14.05.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SchLogResourceImplTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);


    @Autowired
    private SchLogRepository schLogRepository;


    @Autowired
    private SchLogService schLogService;

    @Autowired
    private SchLogMapper schLogMapper;

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

    private MockMvc restSchLogMockMvc;

    private SchLog schLog;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchLog createEntity(EntityManager em) {
        SchLog traPlaylist = new SchLog()
                .date(DEFAULT_DATE);
        return traPlaylist;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchLogConfigurationResourceImpl traPlaylistResource = new SchLogConfigurationResourceImpl();
        ReflectionTestUtils.setField(traPlaylistResource, "schLogService", schLogService);
        ReflectionTestUtils.setField(traPlaylistResource, "schLogMapper", schLogMapper);
        ReflectionTestUtils.setField(traPlaylistResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(traPlaylistResource, "corChannelService", corChannelService);


        this.restSchLogMockMvc = MockMvcBuilders.standaloneSetup(traPlaylistResource)
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
        schLog = createEntity(em).network(corNetwork).channel(corChannel);
    }

    @Test
    @Transactional
    public void createSchLog() throws Exception {
        int databaseSizeBeforeCreate = schLogRepository.findAll().size();

        // Create the SchLog
        SchLogDTO traPlaylistDTO = schLogMapper.DB2DTO(schLog);

        restSchLogMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isCreated());

        // Validate the SchLog in the database
        List<SchLog> traPlaylistList = schLogRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate + 1);
        SchLog testSchLog = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchLog.getDate()).isEqualTo(DEFAULT_DATE);
    }


    @Test
    @Transactional
    public void getAllSchLogs() throws Exception {
        // Initialize the database
        schLogRepository.saveAndFlush(schLog.network(corNetwork).channel(corChannel));

        // Get all the traPlaylistList
        restSchLogMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event?sort=id,desc", corNetwork.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(schLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_DATE.toString())));


    }

    @Test
    @Transactional
    public void getSchLog() throws Exception {
        // Initialize the database
        schLogRepository.saveAndFlush(schLog.network(corNetwork).channel(corChannel));

        // Get the schLog
        restSchLogMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/{shortName}", corNetwork.getShortcut(), corChannel.getShortcut(), schLog.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(schLog.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_DATE.toString()));

    }

    @Test
    @Transactional
    public void deleteSchLog() throws Exception {
        // Initialize the database
        schLogRepository.saveAndFlush(schLog.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeDelete = schLogRepository.findAll().size();

        // Get the schLog
        restSchLogMockMvc.perform(delete("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/event/{shortName}", corNetwork.getShortcut(), corChannel.getShortcut(), schLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SchLog> traPlaylistList = schLogRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeDelete - 1);
    }


    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchLog.class);
    }
}
