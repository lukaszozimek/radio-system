package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchSchedule;
import io.protone.scheduler.service.schedule.build.SchScheduleBuilderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;

/**
 * Created by lukaszozimek on 08/10/2017.
 */
@Service
public class SchScheduleServiceWrapper {
    @Inject
    private SchScheduleBuilderService schScheduleBuilderService;

    @Inject
    private SchScheduleService schScheduleService;

    @Transactional
    public SchSchedule buildSchedule(LocalDate localDate, String gridShortName, String organizationShortcut, String channelShortcut) throws Exception {
        SchSchedule schSchedule = schScheduleBuilderService.buildScheduleForDate(localDate, gridShortName, organizationShortcut, channelShortcut);
        return this.schScheduleService.saveSchedule(schSchedule);
    }

    @Transactional
    public SchSchedule buildDefaultSchedule(LocalDate localDate, String organizationShortcut, String channelShortcut) {
        SchSchedule schSchedule = schScheduleBuilderService.buildDefaultSchedule(localDate, organizationShortcut, channelShortcut);
        return this.schScheduleService.saveSchedule(schSchedule);
    }
}
