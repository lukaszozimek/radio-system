package io.protone.application.web.api.scheduler;

import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.application.web.api.scheduler.impl.SchLogResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.library.domain.LibFileItem;
import io.protone.library.repository.LibFileItemRepository;
import io.protone.library.service.LibFileItemService;
import io.protone.scheduler.domain.SchLog;
import io.protone.scheduler.domain.SchLogConfiguration;
import io.protone.scheduler.mapper.SchLogMapper;
import io.protone.scheduler.repository.SchLogConfigurationRepository;
import io.protone.scheduler.repository.SchLogRepository;
import io.protone.scheduler.service.SchLogService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 14.05.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchLogResourceImplTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);

    @Autowired
    private SchLogRepository schLogRepository;

    @Autowired
    private LibFileItemRepository libFileItemRepository;

    @Autowired
    private SchLogService schLogService;

    @Mock
    private LibFileItemService libFileItemService;

    @Autowired
    private SchLogConfigurationRepository schLogConfigurationRepository;

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

    private LibFileItem libFileItem;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    private SchLogConfiguration schLogConfiguration;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchLog createEntity(EntityManager em) {
        SchLog schLog = new SchLog()
                .date(DEFAULT_DATE);
        return schLog;
    }

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        SchLogResourceImpl schLogResource = new SchLogResourceImpl();
        when(libFileItemService.uploadFileItem(anyString(), anyString(), anyObject())).thenReturn(libFileItem);
        doNothing().when(libFileItemService).deleteFile(any(LibFileItem.class));
        ReflectionTestUtils.setField(schLogService, "libFileItemService", libFileItemService);
        ReflectionTestUtils.setField(schLogResource, "schLogService", schLogService);
        ReflectionTestUtils.setField(schLogResource, "schLogMapper", schLogMapper);
        ReflectionTestUtils.setField(schLogResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(schLogResource, "corChannelService", corChannelService);


        this.restSchLogMockMvc = MockMvcBuilders.standaloneSetup(schLogResource)
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
        libFileItem = libFileItemRepository.saveAndFlush(new LibFileItem().idx("test").name("test").channel(corChannel).network(corNetwork));
        schLogConfiguration = schLogConfigurationRepository.saveAndFlush(SchLogConfigurationResourceImplTest.createEntity(em).network(corNetwork).channel(corChannel));
        schLog.schLogConfiguration(schLogConfiguration);
    }

    @Test
    @Transactional
    public void createSchLog() throws Exception {
        int databaseSizeBeforeCreate = schLogRepository.findAll().size();
        libFileItem = libFileItemRepository.saveAndFlush(libFileItem);
        when(libFileItemService.uploadFileItem(anyString(), anyString(), any())).thenReturn(libFileItem);
        schLogConfiguration.setExtension("MUS");
        schLogConfiguration.setPattern("yyyyMMdd");
        schLogConfiguration.setChannel(corChannel);
        schLogConfiguration.setNetwork(corNetwork);
        schLogConfigurationRepository.saveAndFlush(schLogConfiguration);

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/withseparator/musicLog/20160703.MUS");
        MockMultipartFile multipartFile = new MockMultipartFile("files", "20160703.MUS", "application/octet-stream", inputStream);
        restSchLogMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/log/{extension}", corNetwork.getShortcut(), corChannel.getShortcut(), schLog.getSchLogConfiguration().getExtension())
                .file(multipartFile))
                .andExpect(status().isCreated());

        // Validate the SchLog in the database
        List<SchLog> schLogs = schLogRepository.findAll();
        assertThat(schLogs).hasSize(databaseSizeBeforeCreate + 1);
        SchLog testSchLog = schLogs.get(schLogs.size() - 1);
        assertThat(testSchLog.getDate()).isEqualTo("2016-07-03");
    }


    @Test
    @Transactional
    public void getAllSchLogs() throws Exception {
        // Initialize the database
        schLogRepository.saveAndFlush(schLog.fileItem(libFileItem).schLogConfiguration(schLogConfiguration).network(corNetwork).channel(corChannel));

        // Get all the traPlaylistList
        restSchLogMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/log/{extension}?sort=id,desc", corNetwork.getShortcut(), corChannel.getShortcut(), schLog.getSchLogConfiguration().getExtension()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(schLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));


    }


    @Test
    @Transactional
    public void deleteSchLog() throws Exception {
        // Initialize the database
        schLogRepository.saveAndFlush(schLog.fileItem(libFileItem).schLogConfiguration(schLogConfiguration).network(corNetwork).channel(corChannel));
        int databaseSizeBeforeDelete = schLogRepository.findAll().size();

        // Get the schLog
        restSchLogMockMvc.perform(delete("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/scheduler/log/{extension}/{date}", corNetwork.getShortcut(), corChannel.getShortcut(), schLog.getSchLogConfiguration().getExtension(), schLog.getDate())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SchLog> schLogs = schLogRepository.findAll();
        assertThat(schLogs).hasSize(databaseSizeBeforeDelete - 1);
    }


    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchLog.class);
    }
}
