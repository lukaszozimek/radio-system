package io.protone.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchSchedule;
import io.protone.scheduler.repository.SchClockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Set;


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
    public SchClock saveClock(SchClock schClock, SchSchedule schSchedule) {
        SchClock entity = schClockRepository.saveAndFlush(schClock);
        entity.setSchSchedule(schSchedule);
        entity.emissions(schEmissionService.saveEmission(schClock.getEmissions(), entity));
        entity.blocks(schBlockService.saveBlocks(schClock.getBlocks(), entity));
        return schClockRepository.saveAndFlush(entity);
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
        schClockRepository.save(schClock);
        schClockRepository.delete(schClock);
    }

    public void deleteByScheduleId(Long scheduleId) {
        Set<SchClock> schClocks = schClockRepository.findAllBySchSchedule_Id(scheduleId);
        if (schClocks != null && !schClocks.isEmpty()) {
            schClocks.stream().forEach(schClock -> {
                this.schEmissionService.deleteEmissions(schClock.getEmissions());
                this.schBlockService.deleteBlock(schClock.getBlocks());
            });
        }
        schClockRepository.deleteAllBySchSchedule_Id(scheduleId);
    }
}
