package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.scheduler.domain.SchLog;
import io.protone.scheduler.repository.SchLogRepository;
import io.protone.scheduler.service.SchLogService;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchLogServiceTest {

    private SchLogService schLogService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;


    @Autowired
    private CorChannelRepository corChannelRepository;

    @Autowired
    private SchLogRepository schLogRepository;


    private CorNetwork corNetwork;

    private CorChannel corChannel;

    private PodamFactory factory;


    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);

        corChannel = factory.manufacturePojo(CorChannel.class);
        corChannel.setId(null);
        corChannel.setShortcut("Clc");
        corChannel.network(corNetwork);
        corChannelRepository.saveAndFlush(corChannel);

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
        SchLog schLog = factory.manufacturePojo(SchLog.class);
        schLog.setNetwork(corNetwork);
        schLog.setChannel(corChannel);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/image/sample_image.png");
        MultipartFile multipartFile = new MockMultipartFile("testFile", inputStream);
        MultipartFile[] multipartFiles = Arrays.array(multipartFile);
        List<SchLog> fetchedEntity = schLogService.saveSchLog(multipartFiles, corNetwork, corChannel);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.get(0).getId());
        assertEquals(schLog.getNetwork(), fetchedEntity.get(0).getNetwork());
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