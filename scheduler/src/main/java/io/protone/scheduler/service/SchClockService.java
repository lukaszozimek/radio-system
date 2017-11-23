package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.repository.SchBlockSchBlockRepository;
import io.protone.scheduler.repository.SchClockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static java.util.stream.Collectors.toList;


@Service
public class SchClockService {
    private final Logger log = LoggerFactory.getLogger(SchClockService.class);
    @Inject
    private SchClockRepository schClockRepository;
    @Inject
    private SchBlockService schBlockService;
    @Inject
    private SchBlockSchBlockRepository schBlockSchBlockRepository;

    @Transactional
    public SchClock saveClock(SchClock schClock) {
        log.debug("Save Clock");
        schClock.getBlocks().stream().forEach(schBlockSchBlock -> {
            if (schBlockSchBlock.getChild().getId() != null) {
                schBlockService.deleteBlock(schBlockSchBlock.getChild().getId());
            }
        });
        schClockRepository.saveAndFlush(schClock);
        schClock.setBlocks(schClock.getBlocks().stream().map(schBlockSchBlock -> {
            schBlockSchBlock.parent(schClock).child(schBlockService.saveBlocks(schBlockSchBlock.getChild()));
            return schBlockSchBlockRepository.saveAndFlush(schBlockSchBlock);
        }).collect(toList()));

        return  schClock;

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

        schClockRepository.delete(schClock);
    }

    public void deleteByScheduleId(Long scheduleId) {

    }
}
