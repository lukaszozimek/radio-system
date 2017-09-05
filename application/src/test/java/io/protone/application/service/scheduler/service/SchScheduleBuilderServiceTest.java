package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBuildSchedulerBaseTest;
import io.protone.scheduler.domain.SchGrid;
import io.protone.scheduler.domain.SchSchedule;
import io.protone.scheduler.repository.SchGridRepository;
import io.protone.scheduler.service.schedule.build.SchScheduleBuilderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.time.LocalDate;

import static org.junit.Assert.*;

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

    @Before
    public void setUp() throws Exception {
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
        assertTrue(schSchedule.getClocks().isEmpty());
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
        assertTrue(!schSchedule.getClocks().isEmpty());
        assertEquals(1, schSchedule.getClocks().size());
        assertNotNull(schSchedule.getClocks().stream().findFirst().get());
        assertNotNull(schSchedule.getClocks().stream().findFirst().get().getSequence());
        assertNotNull(schSchedule.getClocks().stream().findFirst().get().getLength());
        assertEquals(3600000L, schSchedule.getClocks().stream().findFirst().get().getLength().longValue());
        assertEquals(1L, schSchedule.getClocks().stream().findFirst().get().getSequence().longValue());
        assertNotNull(schSchedule.getClocks().stream().findFirst().get().getEmissions());
        assertEquals(4, schSchedule.getClocks().stream().findFirst().get().getEmissions().size());

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
        assertTrue(!schSchedule.getClocks().isEmpty());
        assertEquals(1, schSchedule.getClocks().size());
        assertNotNull(schSchedule.getClocks().stream().findFirst().get());
        assertNotNull(schSchedule.getClocks().stream().findFirst().get().getSequence());
        assertNotNull(schSchedule.getClocks().stream().findFirst().get().getLength());
        assertEquals(3600000L, schSchedule.getClocks().stream().findFirst().get().getLength().longValue());
        assertEquals(1L, schSchedule.getClocks().stream().findFirst().get().getSequence().longValue());
        assertNotNull(schSchedule.getClocks().stream().findFirst().get().getEmissions());
        assertEquals(2, schSchedule.getClocks().stream().findFirst().get().getEmissions().size());
        assertEquals(3, schSchedule.getClocks().stream().findFirst().get().getBlocks().size());
        schSchedule.getClocks().stream().findFirst().get().getBlocks().stream().forEach(block -> {
            assertEquals(3, block.getBlocks().size());
            assertNotNull(block.getChannel());
            assertNotNull(block.getNetwork());
            block.getBlocks().stream().forEach(nestedBlock -> {
                assertTrue(nestedBlock.getBlocks().isEmpty());
                assertEquals(3, nestedBlock.getEmissions().size());
                nestedBlock.getEmissions().stream().forEach(emissionsInNestedBlock -> {
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
            assertEquals(3, block.getEmissions().size());
            block.getEmissions().stream().forEach(blockEmission -> {
                assertNotNull(blockEmission.getSequence());
                assertNotNull(blockEmission.getMediaItem());
                assertNotNull(blockEmission.getNetwork());
                assertNotNull(blockEmission.getChannel());
                assertEquals(3, blockEmission.getAttachments().size());
                blockEmission.getAttachments().stream().forEach(attachment -> {
                    assertNotNull(attachment);
                    assertNotNull(attachment.getSequence());
                    assertNotNull(attachment.getNetwork());
                    assertNotNull(attachment.getChannel());
                });

            });

        });

    }

    @Test
    public void shouldBuildScheduleWithOneHourContainingEventsImportAndEmissionsGrid() {

    }

    @Test
    public void shouldBuildScheduleWithOneHourContainingEventsImportEventsAndEmissionsGrid() {

    }

}