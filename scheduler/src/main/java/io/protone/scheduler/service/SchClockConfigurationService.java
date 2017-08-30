package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchClockConfiguration;
import io.protone.scheduler.repository.SchClockConfigurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 30/08/2017.
 */

@Service
public class SchClockConfigurationService {
    private final Logger log = LoggerFactory.getLogger(SchClockConfigurationService.class);
    @Inject
    private SchClockConfigurationRepository schClockConfigurationRepository;
    @Inject
    private SchEventService schEventService;
    @Inject
    private SchEmissionConfigurationService schEmissionConfigurationService;

    @Transactional
    public SchClockConfiguration saveClockConfiguration(SchClockConfiguration schClockConfiguration) {
        schClockConfiguration.emissions(schEmissionConfigurationService.saveEmission(schClockConfiguration.getEmissions()));
        schClockConfiguration.events(schEventService.saveEvent(schClockConfiguration.getEvents()));
        return schClockConfigurationRepository.saveAndFlush(schClockConfiguration);
    }

    @Transactional(readOnly = true)
    public Slice<SchClockConfiguration> findSchClockConfigurationsForNetworkAndChannel(String networkShortcut, String channelShortcut, Pageable pagable) {
        return schClockConfigurationRepository.findAllByNetwork_ShortcutAndChannel_Shortcut(networkShortcut, channelShortcut, pagable);
    }

    @Transactional(readOnly = true)
    public SchClockConfiguration findSchClockConfigurationForNetworkAndChannelAndShortName(String networkShortcut, String channelShortcut, String shortName) {
        return schClockConfigurationRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(networkShortcut, channelShortcut, shortName);
    }

    @Transactional
    public void deleteSchClockConfigurationByNetworkAndChannelAndShortName(String networkShortcut, String channelShortcut, String shortName) {
        schClockConfigurationRepository.deleteByNetwork_ShortcutAndChannel_ShortcutAndShortName(networkShortcut, channelShortcut, shortName);
    }
}
