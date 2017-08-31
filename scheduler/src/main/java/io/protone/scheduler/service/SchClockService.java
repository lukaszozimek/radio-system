package io.protone.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.repository.SchClockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;


@Service
public class SchClockService {
    private final Logger log = LoggerFactory.getLogger(SchClockService.class);
    @Inject
    private SchClockRepository schClockRepository;
    @Inject
    private SchBlockService schBlockService;
    @Inject
    private SchEmissionService schEmissionService;

    @Transactional
    public SchClock saveClock(SchClock schClock) {
        schClock.emissions(schEmissionService.saveEmission(schClock.getEmissions()));
        schClock.blocks(schBlockService.saveBlocks(schClock.getBlocks()));
        return schClockRepository.saveAndFlush(schClock);
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
    public void deleteSchClockByNetworkAndChannelAndShortName(String networkShortcut, String channelShortcut, String shortName) {
        SchClock schClock = schClockRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(networkShortcut, channelShortcut, shortName);
        schEmissionService.deleteEmissions(schClock.getEmissions());
        schBlockService.deleteBlock(schClock.getBlocks());
        schClock.setEmissions(Sets.newHashSet());
        schClock.setBlocks(Sets.newHashSet());
        schClockRepository.delete(schClock);
    }

}
