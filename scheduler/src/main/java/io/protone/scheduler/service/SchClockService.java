package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.repository.SchClockRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;


@Service
public class SchClockService {
    @Inject
    private SchClockRepository schClockRepository;

    @Transactional
    public SchClock saveClock(SchClock schGrid) {
        return schClockRepository.saveAndFlush(schGrid);
    }

    @Transactional(readOnly = true)
    public Slice<SchClock> findSchClocksForNetworkAndChannel(String networkShortcut, String channelShortcut, Pageable pageable) {
        return schClockRepository.findAllByNetwork_ShortcutAndChannel_Shortcut(networkShortcut, channelShortcut, pageable);
    }

    @Transactional(readOnly = true)
    public SchClock findSchClockForNetworkAndChannelAndShortName(String networkShortcut, String channelShortcut, String shortName) {
        return schClockRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(networkShortcut, channelShortcut, shortName);
    }

    @Transactional
    public void deleteSchClockByNetworkAndChannelAndShortNAme(String networkShortcut, String channelShortcut, String shortName) {
        schClockRepository.deleteByShortNameAndNetwork_ShortcutAndChannel_ShortcutAndShortName(networkShortcut, channelShortcut, shortName);
    }

}
