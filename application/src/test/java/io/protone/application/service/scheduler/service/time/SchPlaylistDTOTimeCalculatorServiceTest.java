package io.protone.application.service.scheduler.service.time;

import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBuildSchedulerBaseTest;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibFileItem;
import io.protone.library.repository.LibFileItemRepository;
import io.protone.library.service.LibFileItemService;
import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.domain.SchGrid;
import io.protone.scheduler.domain.SchLog;
import io.protone.scheduler.domain.SchPlaylist;
import io.protone.scheduler.domain.SchSchedule;
import io.protone.scheduler.repository.SchGridRepository;
import io.protone.scheduler.repository.SchLogConfigurationRepository;
import io.protone.scheduler.repository.SchLogRepository;
import io.protone.scheduler.repository.SchPlaylistRepository;
import io.protone.scheduler.service.SchParseLogService;
import io.protone.scheduler.service.SchScheduleServiceWrapper;
import io.protone.scheduler.service.time.SchPlaylistDTOTimeCalculatorService;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.protone.application.service.scheduler.service.SchParseLogServiceTest.buildMusLogConfigurationWithSeparator;
import static io.protone.application.service.scheduler.service.SchParseLogServiceTest.buildOPRLogConfigurationWithSeparator;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 08/09/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SchPlaylistDTOTimeCalculatorServiceTest extends SchedulerBuildSchedulerBaseTest {
    private PodamFactory factory = new PodamFactoryImpl();
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
    private SchPlaylistRepository schPlaylistRepository;
    @Autowired
    private SchLogConfigurationRepository schLogConfigurationRepository;

    @Autowired
    private SchScheduleServiceWrapper schScheduleService;
    private CorNetwork corNetwork;
    private CorChannel corChannel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);
        corChannel = new CorChannel().shortcut("tes");
        corChannel.setId(1L);
    }

    @Test
    public void shouldCalculateTimeForBlockNestedBlocks() throws IOException {
        //when
        ReflectionTestUtils.setField(parseLogService, "libFileItemService", libFileItemService);
        LibFileItem schLogMusFile = libFileItemRepository.saveAndFlush(new LibFileItem().idx("log").name("MUSLOG").network(corNetwork).channel(corChannel));
        LibFileItem schLogOprFile = libFileItemRepository.saveAndFlush(new LibFileItem().idx("log1").name("OPRLOG").network(corNetwork).channel(corChannel));
        SchLog schLogMusic = schLogRepository.saveAndFlush(new SchLog().fileItem(schLogMusFile).schLogConfiguration(buildMusLogConfigurationWithSeparator(factory, schLogConfigurationRepository, corNetwork, corChannel)).network(corNetwork).channel(corChannel).date(LocalDate.parse("20170826", DateTimeFormatter.ofPattern("yyyyMMdd"))));
        SchLog schLogOpr = schLogRepository.saveAndFlush(new SchLog().fileItem(schLogOprFile).schLogConfiguration(buildOPRLogConfigurationWithSeparator(factory, schLogConfigurationRepository, corNetwork, corChannel)).network(corNetwork).channel(corChannel).date(LocalDate.parse("20170826", DateTimeFormatter.ofPattern("yyyyMMdd"))));
        InputStream musLogStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/mapping/musicLog/20170826.MUS");
        InputStream oprLogStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/mapping/newsCollect/20170826.OPR");
        when(libFileItemService.download(schLogMusFile)).thenReturn(IOUtils.toByteArray(musLogStream));
        when(libFileItemService.download(schLogOprFile)).thenReturn(IOUtils.toByteArray(oprLogStream));
        LocalDate localDate = LocalDate.of(2017, 8, 26);
        SchGrid schGrid = schGridRepository.saveAndFlush(buildGridForDayWitClockMusicAndImportEventsAndEmissionsConfiguration(corDayOfWeekEnumMap.get(localDate.getDayOfWeek()), true));
        //then
        SchSchedule schSchedule = schScheduleService.buildDefaultSchedule(localDate, corNetwork.getShortcut(), corChannel.getShortcut());
        SchPlaylist schPlaylist = schPlaylistRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDate(corNetwork.getShortcut(), corChannel.getShortcut(), schSchedule.getDate());
        SchPlaylistDTO resultDTO = schPlaylistDTOTimeCalculatorService.calculateTimeInSchPlaylistDTO(schPlaylist);
        assertNotNull(resultDTO);
        assertEquals(21, resultDTO.getEmissions().size());
    }

}