package io.protone.scheduler.service;

import io.protone.scheduler.api.dto.SchScheduleDTO;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchSchedule;
import io.protone.scheduler.mapper.SchScheduleMapper;
import io.protone.scheduler.repository.SchScheduleRepository;
import io.protone.scheduler.service.time.SchScheduleDTOTimeCalculatorService;
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
    @Inject
    private SchScheduleDTOTimeCalculatorService schScheduleDTOTimeCalculatorService;
    @Inject
    private SchScheduleMapper schScheduleMapper;

    @Transactional
    public SchSchedule saveSchedule(SchSchedule schSchedule) {
        SchSchedule entity = schScheduleRepository.saveAndFlush(schSchedule);
        SchSchedule finalEntity = entity;
        if (schSchedule.getClocks() != null && !schSchedule.getClocks().isEmpty()) {
            entity.clocks(schSchedule.getClocks().stream().sorted(Comparator.comparing(SchClock::getSequence)).map(schClock -> this.schClockService.saveClock(schClock, finalEntity)).collect(toSet()));
        }
        entity = schScheduleRepository.saveAndFlush(entity);
        return entity;
    }

    @Transactional(readOnly = true)
    public Slice<SchSchedule> findSchSchedulesForNetworkAndChannel(String networkShortcut, String channelShortcut, Pageable pageable) {
        return schScheduleRepository.findAllByNetwork_ShortcutAndChannel_Shortcut(networkShortcut, channelShortcut, pageable);
    }

    @Transactional(readOnly = true)
    public SchScheduleDTO findSchScheduleForNetworkAndChannelAndDate(String networkShortcut, String channelShortcut, LocalDate date) {
        SchSchedule schSchedule = schScheduleRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDate(networkShortcut, channelShortcut, date);

        if (schSchedule != null) {
            return schScheduleDTOTimeCalculatorService.calculateTimeInSchPlaylistDTO(schScheduleMapper.DB2DTO(schSchedule));
        }
        return null;
    }

    @Transactional
    public void deleteSchScheduleByNetworkAndChannelAndShortNAme(String networkShortcut, String channelShortcut, LocalDate date) {
        schScheduleRepository.deleteByNetwork_ShortcutAndChannel_ShortcutAndDate(networkShortcut, channelShortcut, date);
    }
}