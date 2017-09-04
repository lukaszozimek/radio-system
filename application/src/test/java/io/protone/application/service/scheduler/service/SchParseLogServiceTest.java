package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBaseTest;
import io.protone.library.domain.LibFileItem;
import io.protone.library.service.LibFileItemService;
import io.protone.scheduler.domain.SchLog;
import io.protone.scheduler.domain.SchPlaylist;
import io.protone.scheduler.repository.SchPlaylistRepository;
import io.protone.scheduler.service.SchParseLogService;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchParseLogServiceTest extends SchedulerBaseTest {

    @Autowired
    private SchParseLogService parseLogService;

    @Mock
    private LibFileItemService libFileItemService;

    @Autowired
    private SchPlaylistRepository schPlaylistRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(parseLogService, "libFileItemService", libFileItemService);
        super.setUp();

    }

    @Test
    public void shouldParseRekLogWithoutSeparator() throws IOException {
        //when
        SchPlaylist schPlaylist = factory.manufacturePojo(SchPlaylist.class);
        schPlaylist.channel(corChannel);
        schPlaylist.network(corNetwork);
        schPlaylist.date(LocalDate.parse("20170826", DateTimeFormatter.ofPattern("yyyyMMdd")));
        schPlaylist = schPlaylistRepository.saveAndFlush(schPlaylist);
        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/shortlogs/withoutseparator/commercialLog/20170826.rek");

        when(libFileItemService.download(any())).thenReturn(IOUtils.toByteArray(file));

        //then
        parseLogService.parseLog(new SchLog().fileItem(new LibFileItem()), corChannel, corNetwork, schPlaylist);
    }

}