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
import java.time.LocalDateTime;
import java.time.LocalTime;

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
        LocalDate localDate = LocalDate.now();
        SchGrid schGrid = schGridRepository.saveAndFlush(buildGridForDay(corDayOfWeekEnumMap.get(localDate.getDayOfWeek()), true));
        SchSchedule schSchedule = schScheduleBuilderService.buildDefaultSchedule(localDate, corNetwork.getShortcut(), corChannel.getShortcut());
        assertNotNull(schSchedule);
        assertEquals(schSchedule.getNetwork(), schGrid.getNetwork());
        assertEquals(schSchedule.getChannel(), schGrid.getChannel());
        assertEquals(schSchedule.getDate(), localDate);
        assertTrue(schSchedule.getClocks().isEmpty());
    }


    @Test(expected = BadRequestException.class)
    public void shouldNotBuildScheduleWithEmptyGrid() {
        LocalDate localDate = LocalDate.now();
        schGridRepository.saveAndFlush(buildGridForDay(corDayOfWeekEnumMap.get(localDate.getDayOfWeek()), false));
        schScheduleBuilderService.buildDefaultSchedule(localDate, corNetwork.getShortcut(), corChannel.getShortcut());
    }

    @Test
    public void shouldBuildScheduleWithOneHourGrid() {
        LocalDate localDate = LocalDate.now();
        SchGrid schGrid = schGridRepository.saveAndFlush(buildGridForDayWitClock(corDayOfWeekEnumMap.get(localDate.getDayOfWeek()), true));
        SchSchedule schSchedule = schScheduleBuilderService.buildDefaultSchedule(localDate, corNetwork.getShortcut(), corChannel.getShortcut());
        assertNotNull(schSchedule);
        assertEquals(schSchedule.getNetwork(), schGrid.getNetwork());
        assertEquals(schSchedule.getChannel(), schGrid.getChannel());
        assertEquals(schSchedule.getDate(), localDate);
        assertTrue(!schSchedule.getClocks().isEmpty());
        assertEquals(1, schSchedule.getClocks().size());
        assertNotNull(schSchedule.getClocks().stream().findFirst().get());
        assertNotNull(schSchedule.getClocks().stream().findFirst().get().getSequence());
        assertEquals(1L, schSchedule.getClocks().stream().findFirst().get().getSequence().longValue());
        assertEquals(LocalDateTime.of(localDate, LocalTime.of(00, 00, 00)), schSchedule.getClocks().stream().findFirst().get().getTimeParams().getStartTime());
        assertEquals(LocalDateTime.of(localDate, LocalTime.of(01, 00, 00)), schSchedule.getClocks().stream().findFirst().get().getTimeParams().getEndTime());
        assertNotNull(schSchedule.getClocks().stream().findFirst().get().getEmissions());
        assertEquals(4, schSchedule.getClocks().stream().findFirst().get().getEmissions());

    }


    @Test
    public void shouldBuildScheduleWithOneHourContainingEventsGrid() {

    }

    @Test
    public void shouldBuildScheduleWithOneHourContainingEventsImportAndEmissionsGrid() {

    }

    @Test
    public void shouldBuildScheduleWithOneHourContainingEventsImportEventsAndEmissionsGrid() {

    }

}