package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchSchedule;
import io.protone.scheduler.repository.SchScheduleRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;


@Service
public class SchScheduleService {
    @Inject
    private SchScheduleRepository schScheduleRepository;

    @Transactional
    public SchSchedule saveSchedule(SchSchedule schSchedule) {
        return schScheduleRepository.saveAndFlush(schSchedule);
    }

    @Transactional(readOnly = true)
    public Slice<SchSchedule> findSchGridsForNetworkAndChannel(String networkShortcut, String channelShortcut, Pageable pageable) {
        return schScheduleRepository.findAllByNetwork_ShortcutAndChannel_Shortcut(networkShortcut, channelShortcut, pageable);
    }

    @Transactional(readOnly = true)
    public SchSchedule findSchGridForNetworkAndChannelAndDate(String networkShortcut, String channelShortcut, LocalDate date) {
        return schScheduleRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDate(networkShortcut, channelShortcut, date);
    }

    @Transactional
    public void deleteSchScheduleByNetworkAndChannelAndShortNAme(String networkShortcut, String channelShortcut, LocalDate date) {
        schScheduleRepository.deleteByShortNameAndNetwork_ShortcutAndChannel_ShortcutAndDate(networkShortcut, channelShortcut, date);
    }
}