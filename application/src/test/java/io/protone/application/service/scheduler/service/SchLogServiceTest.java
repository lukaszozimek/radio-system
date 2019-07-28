package io.protone.application.service.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibFileItem;
import io.protone.library.domain.LibFileLibrary;
import io.protone.library.repository.LibFileItemRepository;
import io.protone.library.repository.LibFileLibraryRepository;
import io.protone.library.service.LibFileItemService;
import io.protone.scheduler.domain.SchLog;
import io.protone.scheduler.domain.SchLogConfiguration;
import io.protone.scheduler.repository.SchLogConfigurationRepository;
import io.protone.scheduler.repository.SchLogRepository;
import io.protone.scheduler.service.SchLogService;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchLogServiceTest {

    protected PodamFactory factory = new PodamFactoryImpl();

    @Mock
    private LibFileItemService libFileItemService;

    @Autowired
    private LibFileItemRepository libFileItemRepository;

    @Autowired
    private SchLogConfigurationRepository schLogConfigurationRepository;

    @Autowired
    private SchLogService schLogService;

    @Autowired
    private SchLogRepository schLogRepository;

    @Autowired
    private LibFileLibraryRepository libFileLibraryRepository;


    private SchLogConfiguration schLogConfiguration;

    private LibFileItem libFileItem;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    private LibFileLibrary libFileLibrary;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(schLogService, "libFileItemService", libFileItemService);
        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);
        corChannel = new CorChannel().shortcut("tes");
        corChannel.setId(1L);
        if (libFileLibrary == null) {
            libFileLibrary = new LibFileLibrary();
            libFileLibrary.setId(null);
            libFileLibrary.setCounter(0L);
            libFileLibrary.setPrefix("v");
            libFileLibrary.shortcut("100");
            libFileLibrary.setName("Testowa Bliblioteka Schedulera");
            libFileLibrary.setNetwork(corNetwork);
            libFileLibrary.setChannels(Sets.newHashSet(corChannel));
            this.libFileLibrary = this.libFileLibraryRepository.saveAndFlush(libFileLibrary);
        }
        schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.network(corNetwork);
        schLogConfiguration.channel(corChannel);
        schLogConfiguration = schLogConfigurationRepository.saveAndFlush(schLogConfiguration);
        libFileItem = factory.manufacturePojo(LibFileItem.class);
        libFileItem.setId(null);
        libFileItem.setLibrary(libFileLibrary);
        libFileItem.setCloudObject(null);
        libFileItem.network(corNetwork);
        libFileItem.channel(corChannel);
        libFileItem = libFileItemRepository.saveAndFlush(libFileItem);
    }


    @Test
    public void shouldGetLogs() throws Exception {
        //when
        SchLog schLog = factory.manufacturePojo(SchLog.class);
        schLog.setNetwork(corNetwork);
        schLog.setChannel(corChannel);
        schLog.schLogConfiguration(schLogConfiguration);
        schLog.fileItem(libFileItem);
        schLog = schLogRepository.save(schLog);

        //then
        Slice<SchLog> fetchedEntity = schLogService.findSchLogForNetworkAndChannelExtension(corNetwork.getShortcut(), corChannel.getShortcut(), schLog.getSchLogConfiguration().getExtension(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schLog.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(schLog.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());

    }

    @Test
    public void shouldSaveLog() throws Exception {
        //when
        LibFileItem libFileItem = factory.manufacturePojo(LibFileItem.class);
        libFileItem.setChannel(corChannel);
        libFileItem.setNetwork(corNetwork);
        libFileItem.setCloudObject(null);
        libFileItem.setLibrary(libFileLibrary);
        libFileItem.setId(null);
        libFileItem = libFileItemRepository.saveAndFlush(libFileItem);
        when(libFileItemService.uploadFileItemWithPredefinedContentType(anyString(), anyString(), any(), anyString())).thenReturn(libFileItem);
        SchLogConfiguration schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.setExtension("MUS");
        schLogConfiguration.setPattern("yyyyMMdd");
        schLogConfiguration.setChannel(corChannel);
        schLogConfiguration.setNetwork(corNetwork);
        schLogConfigurationRepository.saveAndFlush(schLogConfiguration);

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/withseparator/musicLog/20160703.MUS");
        MultipartFile multipartFile = new MockMultipartFile("testFile", "20160703.MUS", "test", inputStream);
        MultipartFile[] multipartFiles = Arrays.array(multipartFile);

        //then
        List<SchLog> fetchedEntity = schLogService.saveSchLog(multipartFiles, corNetwork, corChannel);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.get(0).getId());
        assertEquals(LocalDate.parse("20160703", DateTimeFormatter.ofPattern("yyyyMMdd")).toString(), fetchedEntity.get(0).getDate().toString());

    }

    @Test
    public void shouldDeleteLog() throws Exception {
        //when
        doNothing().when(libFileItemService).deleteFile(any(LibFileItem.class));
        SchLog schLog = factory.manufacturePojo(SchLog.class);
        schLog.setNetwork(corNetwork);
        schLog.setChannel(corChannel);
        schLog.fileItem(libFileItem);
        schLog.schLogConfiguration(schLogConfiguration);
        schLog = schLogRepository.saveAndFlush(schLog);
        //then
        schLogService.deleteSchLogByNetworkAndChannelAndDateAndExtension(corNetwork.getShortcut(), corChannel.getShortcut(), LocalDate.now(), schLog.getSchLogConfiguration().getExtension());
        SchLog fetchedEntity = schLogRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDateAndSchLogConfiguration_Extension(corNetwork.getShortcut(), corChannel.getShortcut(), LocalDate.now(), schLog.getSchLogConfiguration().getExtension());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetLog() throws Exception {
        //when
        SchLog schLog = factory.manufacturePojo(SchLog.class);
        schLog.setNetwork(corNetwork);
        schLog.setChannel(corChannel);
        schLog.fileItem(libFileItem);
        schLog.schLogConfiguration(schLogConfiguration);
        schLog = schLogRepository.save(schLog);

        //then
        SchLog fetchedEntity = schLogService.findSchLogForNetworkAndChannelAndDateAndExtension(corNetwork.getShortcut(), corChannel.getShortcut(), LocalDate.now(), schLog.getSchLogConfiguration().getExtension());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(schLog.getId(), fetchedEntity.getId());
        assertEquals(schLog.getNetwork(), fetchedEntity.getNetwork());

    }
}