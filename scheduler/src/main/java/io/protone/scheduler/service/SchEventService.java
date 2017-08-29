package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchEvent;
import io.protone.scheduler.repository.SchEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;


@Service
public class SchEventService {
    private final Logger log = LoggerFactory.getLogger(SchEventService.class);
    @Inject
    private SchEventRepository schEventRepository;

    @Inject
    private SchEmissionService schEmissionService;

    @Transactional
    public SchEvent saveEvent(SchEvent schEvent) {
        schEvent.emissions(schEmissionService.saveEmission(schEvent.getEmissions()));
        return schEventRepository.saveAndFlush(schEvent);
    }

    @Transactional(readOnly = true)
    public Slice<SchEvent> findSchEventsForNetworkAndChannel(String networkShortcut, String channelShortcut, Pageable pageable) {
        return schEventRepository.findAllByNetwork_ShortcutAndChannel_Shortcut(networkShortcut, channelShortcut, pageable);
    }

    @Transactional(readOnly = true)
    public SchEvent findSchEventsForNetworkAndChannelAndShortName(String networkShortcut, String channelShortcut, String shortName) {
        return schEventRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(networkShortcut, channelShortcut, shortName);
    }

    @Transactional
    public void deleteSchEventByNetworkAndChannelAndShortName(String networkShortcut, String channelShortcut, String shortName) {
        schEventRepository.deleteByNetwork_ShortcutAndChannel_ShortcutAndShortName(networkShortcut, channelShortcut, shortName);
    }

}
