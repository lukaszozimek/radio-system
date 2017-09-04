package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBaseTest;
import io.protone.library.domain.LibFileItem;
import io.protone.library.repository.LibFileItemRepository;
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

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchLogServiceTest extends SchedulerBaseTest {


    @Mock
    private LibFileItemService libFileItemService;

    @Inject
    private LibFileItemRepository libFileItemRepository;

    @Inject
    private SchLogConfigurationRepository schLogConfigurationRepository;

    @Autowired
    private SchLogService schLogService;

    @Autowired
    private SchLogRepository schLogRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(schLogService, "libFileItemService", libFileItemService);
        super.setUp();
    }


    @Test
    public void shouldGetLogs() throws Exception {
        //when
        SchLog schLog = factory.manufacturePojo(SchLog.class);
        schLog.setNetwork(corNetwork);
        schLog.setChannel(corChannel);
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
        when(libFileItemService.uploadFileItem(anyString(), anyString(), any())).thenReturn(libFileItem);
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
        SchLog schLog = factory.manufacturePojo(SchLog.class);
        schLog.setNetwork(corNetwork);
        schLog.setChannel(corChannel);
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
        schLog = schLogRepository.save(schLog);

        //then
        SchLog fetchedEntity = schLogService.findSchLogForNetworkAndChannelAndDateAndExtension(corNetwork.getShortcut(), corChannel.getShortcut(), LocalDate.now(), schLog.getSchLogConfiguration().getExtension());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(schLog.getId(), fetchedEntity.getId());
        assertEquals(schLog.getNetwork(), fetchedEntity.getNetwork());

    }
}