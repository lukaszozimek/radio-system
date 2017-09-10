package io.protone.application.service.scheduler.service.time;

import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBaseTest;
import io.protone.application.service.scheduler.base.SchedulerBuildSchedulerBaseTest;
import io.protone.library.domain.LibFileItem;
import io.protone.library.repository.LibFileItemRepository;
import io.protone.library.service.LibFileItemService;
import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.domain.SchGrid;
import io.protone.scheduler.domain.SchLog;
import io.protone.scheduler.domain.SchPlaylist;
import io.protone.scheduler.domain.SchSchedule;
import io.protone.scheduler.repository.SchGridRepository;
import io.protone.scheduler.repository.SchLogRepository;
import io.protone.scheduler.repository.SchPlaylistRepository;
import io.protone.scheduler.service.SchParseLogService;
import io.protone.scheduler.service.SchScheduleService;
import io.protone.scheduler.service.schedule.build.SchScheduleBuilderService;
import io.protone.scheduler.service.time.SchPlaylistDTOTimeCalculatorService;
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

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 08/09/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchPlaylistDTOTimeCalculatorServiceTest extends SchedulerBuildSchedulerBaseTest {

    @Autowired
    private SchPlaylistDTOTimeCalculatorService schPlaylistDTOTimeCalculatorService;
    @Autowired
    private SchParseLogService parseLogService;
    @Mock
    private LibFileItemService libFileItemService;
    @Autowired
    private LibFileItemRepository libFileItemRepository;
    @Autowired
    private SchLogRepository schLogRepository;
    @Autowired
    private SchGridRepository schGridRepository;
    @Autowired
    private SchScheduleBuilderService schScheduleBuilderService;
    @Autowired
    private SchScheduleService schScheduleService;
    @Autowired
    private SchPlaylistRepository schPlaylistRepository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        super.setUp();
    }

    @Test
    public void shouldCalculateTimeForBlockNestedBlocks() throws IOException {
        //when
        ReflectionTestUtils.setField(parseLogService, "libFileItemService", libFileItemService);
        LibFileItem schLogMusFile = libFileItemRepository.saveAndFlush(new LibFileItem().idx("log").name("MUSLOG").network(corNetwork).channel(corChannel));
        LibFileItem schLogOprFile = libFileItemRepository.saveAndFlush(new LibFileItem().idx("log1").name("OPRLOG").network(corNetwork).channel(corChannel));
        SchLog schLogMusic = schLogRepository.saveAndFlush(new SchLog().fileItem(schLogMusFile).schLogConfiguration(buildMusLogConfigurationWithSeparator()).network(corNetwork).channel(corChannel).date(LocalDate.parse("20170826", DateTimeFormatter.ofPattern("yyyyMMdd"))));
        SchLog schLogOpr = schLogRepository.saveAndFlush(new SchLog().fileItem(schLogOprFile).schLogConfiguration(buildOPRLogConfigurationWithSeparator()).network(corNetwork).channel(corChannel).date(LocalDate.parse("20170826", DateTimeFormatter.ofPattern("yyyyMMdd"))));
        InputStream musLogStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/mapping/musicLog/20170826.MUS");
        InputStream oprLogStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/mapping/newsCollect/20170826.OPR");
        when(libFileItemService.download(schLogMusFile)).thenReturn(IOUtils.toByteArray(musLogStream));
        when(libFileItemService.download(schLogOprFile)).thenReturn(IOUtils.toByteArray(oprLogStream));
        LocalDate localDate = LocalDate.of(2017, 8, 26);
        SchGrid schGrid = schGridRepository.saveAndFlush(buildGridForDayWitClockMusicAndImportEventsAndEmissionsConfiguration(corDayOfWeekEnumMap.get(localDate.getDayOfWeek()), true));

        //then
        SchSchedule schSchedule = schScheduleBuilderService.buildDefaultSchedule(localDate, corNetwork.getShortcut(), corChannel.getShortcut());

        schSchedule = schScheduleService.saveSchedule(schSchedule);
        SchPlaylistDTO resultDTO = schPlaylistDTOTimeCalculatorService.calculateTimeInSchPlaylistDTO(schSchedule);
        assertNotNull(resultDTO);
    }

}