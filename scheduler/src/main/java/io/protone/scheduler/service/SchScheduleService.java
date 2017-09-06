package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchSchedule;
import io.protone.scheduler.repository.SchScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Comparator;

import static java.util.stream.Collectors.toSet;


@Service
public class SchScheduleService {
    private final Logger log = LoggerFactory.getLogger(SchScheduleService.class);
    @Inject
    private SchScheduleRepository schScheduleRepository;

    @Inject
    private SchPlaylistService schPlaylistService;

    @Inject
    private SchClockService schClockService;

    @Transactional
    public SchSchedule saveSchedule(SchSchedule schSchedule) {
        SchSchedule entity = schScheduleRepository.saveAndFlush(schSchedule);
        SchSchedule finalEntity = entity;
        entity.clocks(schSchedule.getClocks().stream().sorted(Comparator.comparing(SchClock::getSequence)).map(schClock -> this.schClockService.saveClock(schClock, finalEntity)).collect(toSet()));
        entity = schScheduleRepository.saveAndFlush(entity);
        return entity;
    }

    @Transactional(readOnly = true)
    public Slice<SchSchedule> findSchSchedulesForNetworkAndChannel(String networkShortcut, String channelShortcut, Pageable pageable) {
        return schScheduleRepository.findAllByNetwork_ShortcutAndChannel_Shortcut(networkShortcut, channelShortcut, pageable);
    }

    @Transactional(readOnly = true)
    public SchSchedule findSchScheduleForNetworkAndChannelAndDate(String networkShortcut, String channelShortcut, LocalDate date) {
        return schScheduleRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDate(networkShortcut, channelShortcut, date);
    }

    @Transactional
    public void deleteSchScheduleByNetworkAndChannelAndShortNAme(String networkShortcut, String channelShortcut, LocalDate date) {
        schScheduleRepository.deleteByNetwork_ShortcutAndChannel_ShortcutAndDate(networkShortcut, channelShortcut, date);
    }
}