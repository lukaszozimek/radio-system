package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBuildSchedulerBaseTest;
import io.protone.library.domain.LibFileItem;
import io.protone.library.repository.LibFileItemRepository;
import io.protone.library.service.LibFileItemService;
import io.protone.scheduler.domain.SchGrid;
import io.protone.scheduler.domain.SchLog;
import io.protone.scheduler.domain.SchSchedule;
import io.protone.scheduler.domain.enumeration.EventTypeEnum;
import io.protone.scheduler.repository.SchGridRepository;
import io.protone.scheduler.repository.SchLogRepository;
import io.protone.scheduler.service.SchParseLogService;
import io.protone.scheduler.service.schedule.build.SchScheduleBuilderService;
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
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.BadRequestException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.protone.application.service.scheduler.service.SchParseLogServiceTest.buildMusLogConfigurationWithSeparator;
import static io.protone.application.service.scheduler.service.SchParseLogServiceTest.buildOPRLogConfigurationWithSeparator;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchScheduleBuilderServiceTest extends SchedulerBuildSchedulerBaseTest {

    @Autowired
    private SchGridRepository schGridRepository;

    @Autowired
    private SchScheduleBuilderService schScheduleBuilderService;

    @Mock
    private LibFileItemService libFileItemService;

    @Autowired
    private SchParseLogService parseLogService;

    @Autowired
    private SchLogRepository schLogRepository;


    @Autowired
    private LibFileItemRepository libFileItemRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        super.setUp();
    }

    @Test
    public void shouldBuildScheduleWithEmptyDefaultGrid() {
        //when
        LocalDate localDate = LocalDate.now();
        SchGrid schGrid = schGridRepository.saveAndFlush(buildGridForDay(corDayOfWeekEnumMap.get(localDate.getDayOfWeek()), true));
        //then
        SchSchedule schSchedule = schScheduleBuilderService.buildDefaultSchedule(localDate, corNetwork.getShortcut(), corChannel.getShortcut());
        //assert
        assertNotNull(schSchedule);
        assertEquals(schSchedule.getNetwork(), schGrid.getNetwork());
        assertEquals(schSchedule.getChannel(), schGrid.getChannel());
        assertEquals(schSchedule.getDate(), localDate);
        assertTrue(schSchedule.getBlocks().isEmpty());
    }


    @Test(expected = BadRequestException.class)
    public void shouldNotBuildScheduleWithEmptyGrid() {
        //when
        LocalDate localDate = LocalDate.now();
        schGridRepository.saveAndFlush(buildGridForDay(corDayOfWeekEnumMap.get(localDate.getDayOfWeek()), false));

        //then
        schScheduleBuilderService.buildDefaultSchedule(localDate, corNetwork.getShortcut(), corChannel.getShortcut());
    }

    @Test
    public void shouldBuildScheduleWithOneHourGrid() {
        //when
        LocalDate localDate = LocalDate.now();
        SchGrid schGrid = schGridRepository.saveAndFlush(buildGridForDayWitClock(corDayOfWeekEnumMap.get(localDate.getDayOfWeek()), true));

        //then
        SchSchedule schSchedule = schScheduleBuilderService.buildDefaultSchedule(localDate, corNetwork.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(schSchedule);
        assertEquals(schSchedule.getNetwork(), schGrid.getNetwork());
        assertEquals(schSchedule.getChannel(), schGrid.getChannel());
        assertEquals(schSchedule.getDate(), localDate);
        assertTrue(!schSchedule.getBlocks().isEmpty());
        assertEquals(1, schSchedule.getBlocks().size());
        assertNotNull(schSchedule.getBlocks().stream().findFirst().get());
        assertNotNull(schSchedule.getBlocks().stream().findFirst().get().getChild().getLength());
        assertNotNull(schSchedule.getBlocks().stream().findFirst().get().getChild().getEmissions());
        assertEquals(4, schSchedule.getBlocks().stream().findFirst().get().getChild().getEmissions().size());

    }


    @Test
    public void shouldBuildScheduleWithOneHourContainingEventsGrid() {
        //when
        LocalDate localDate = LocalDate.now();
        SchGrid schGrid = schGridRepository.saveAndFlush(buildGridForDayWitClockWithNestedEvents(corDayOfWeekEnumMap.get(localDate.getDayOfWeek()), true));

        //then
        SchSchedule schSchedule = schScheduleBuilderService.buildDefaultSchedule(localDate, corNetwork.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(schSchedule);
        assertEquals(schSchedule.getNetwork(), schGrid.getNetwork());
        assertEquals(schSchedule.getChannel(), schGrid.getChannel());
        assertEquals(schSchedule.getDate(), localDate);
        assertTrue(!schSchedule.getBlocks().isEmpty());
        assertEquals(1, schSchedule.getBlocks().size());
        assertNotNull(schSchedule.getBlocks().stream().findFirst().get());
        assertNotNull(schSchedule.getBlocks().stream().findFirst().get().getSequence());
        assertNotNull(schSchedule.getBlocks().stream().findFirst().get().getChild().getLength());
        assertEquals(0, schSchedule.getBlocks().stream().findFirst().get().getSequence().longValue());
        assertNotNull(schSchedule.getBlocks().stream().findFirst().get().getChild().getEmissions());
        assertEquals(2, schSchedule.getBlocks().stream().findFirst().get().getChild().getEmissions().size());
        assertEquals(3, schSchedule.getBlocks().stream().findFirst().get().getChild().getBlocks().size());
        schSchedule.getBlocks().stream().findFirst().get().getChild().getBlocks().stream().forEach(block -> {
            assertEquals(1, block.getChild().getBlocks().size());
            assertNotNull(block.getChild().getChannel());
            assertNotNull(block.getChild().getNetwork());
            block.getChild().getBlocks().stream().forEach(nestedBlock -> {
                assertTrue(nestedBlock.getChild().getBlocks().isEmpty());
                assertEquals(3, nestedBlock.getChild().getEmissions().size());
                nestedBlock.getChild().getEmissions().stream().forEach(emissionsInNestedBlock -> {
                    assertNotNull(emissionsInNestedBlock.getSequence());
                    assertNotNull(emissionsInNestedBlock.getMediaItem());
                    assertEquals(3, emissionsInNestedBlock.getAttachments().size());
                    assertNotNull(emissionsInNestedBlock.getChannel());
                    assertNotNull(emissionsInNestedBlock.getNetwork());
                    emissionsInNestedBlock.getAttachments().stream().forEach(nestedAttachments -> {
                        assertNotNull(nestedAttachments);
                        assertNotNull(nestedAttachments.getSequence());
                        assertNotNull(nestedAttachments.getNetwork());
                        assertNotNull(nestedAttachments.getChannel());

                    });
                });
            });
            assertEquals(3, block.getChild().getEmissions().size());
            block.getChild().getEmissions().stream().forEach(emission -> {
                assertNotNull(emission.getSequence());
                assertNotNull(emission.getMediaItem());
                assertNotNull(emission.getNetwork());
                assertNotNull(emission.getChannel());
                assertEquals(3, emission.getAttachments().size());
                emission.getAttachments().stream().forEach(attachment -> {
                    assertNotNull(attachment);
                    assertNotNull(attachment.getSequence());
                    assertNotNull(attachment.getNetwork());
                    assertNotNull(attachment.getChannel());
                });

            });

        });

    }

    @Test
    public void shouldBuildScheduleWithOneHourContainingEventsImportAndEmissionsGrid() throws IOException {
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
        SchSchedule schSchedule = schScheduleBuilderService.buildDefaultSchedule(localDate, corNetwork.getShortcut(), corChannel.getShortcut());
        assertNotNull(schSchedule);
        assertEquals(schSchedule.getNetwork(), schGrid.getNetwork());
        assertEquals(schSchedule.getChannel(), schGrid.getChannel());
        assertEquals(schSchedule.getDate(), localDate);
        assertTrue(!schSchedule.getBlocks().isEmpty());
        assertEquals(1, schSchedule.getBlocks().size());
        assertNotNull(schSchedule.getBlocks().stream().findFirst().get());
        assertNotNull(schSchedule.getBlocks().stream().findFirst().get().getSequence());
        assertNotNull(schSchedule.getBlocks().stream().findFirst().get().getChild().getLength());
        assertNotNull(schSchedule.getBlocks().stream().findFirst().get().getChild().getEmissions());
        assertEquals(2, schSchedule.getBlocks().stream().findFirst().get().getChild().getEmissions().size());
        assertEquals(3, schSchedule.getBlocks().stream().findFirst().get().getChild().getBlocks().size());
        schSchedule.getBlocks().stream().findFirst().get().getChild().getBlocks().stream().forEach(block -> {
            if (block.getChild().getEventType() != null && block.getChild().getEventType().equals(EventTypeEnum.ET_IMPORT_LOG)) {
                assertEquals(7, block.getChild().getEmissions().size());

                assertNotNull(block.getChild().getChannel());
                assertNotNull(block.getChild().getNetwork());
            }
            if (block.getChild().getEventType() != null && block.getChild().getEventType().equals(EventTypeEnum.ET_MUSIC)) {
                block.getChild().getBlocks().stream().forEach(nestedBlock -> {
                    if (nestedBlock.getChild().getEventType() != null && nestedBlock.getChild().getEventType().equals(EventTypeEnum.ET_IMPORT_LOG)) {
                        assertEquals(4, nestedBlock.getChild().getEmissions().size());
                        assertEquals(0, nestedBlock.getChild().getBlocks().size());

                        assertNotNull(nestedBlock.getChild().getChannel());
                        assertNotNull(nestedBlock.getChild().getNetwork());
                    } else {
                        assertTrue(nestedBlock.getChild().getBlocks().isEmpty());
                        assertEquals(3, nestedBlock.getChild().getEmissions().size());
                        nestedBlock.getChild().getEmissions().stream().forEach(emissionsInNestedBlock -> {
                            assertNotNull(emissionsInNestedBlock.getSequence());
                            assertNotNull(emissionsInNestedBlock.getMediaItem());
                            assertEquals(3, emissionsInNestedBlock.getAttachments().size());
                            assertNotNull(emissionsInNestedBlock.getChannel());
                            assertNotNull(emissionsInNestedBlock.getNetwork());
                            emissionsInNestedBlock.getAttachments().stream().forEach(nestedAttachments -> {
                                assertNotNull(nestedAttachments);
                                assertNotNull(nestedAttachments.getSequence());
                                assertNotNull(nestedAttachments.getNetwork());
                                assertNotNull(nestedAttachments.getChannel());

                            });
                        });
                    }

                });
            }
        });

    }

}