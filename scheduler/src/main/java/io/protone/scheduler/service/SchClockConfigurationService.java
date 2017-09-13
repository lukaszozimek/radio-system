package io.protone.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.scheduler.api.dto.SchClockConfigurationDTO;
import io.protone.scheduler.domain.SchClockConfiguration;
import io.protone.scheduler.mapper.SchClockConfigurationMapper;
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

    @Inject
    private SchClockConfigurationMapper schClockConfigurationMapper;

    @Transactional
    public SchClockConfiguration saveClockConfiguration(SchClockConfiguration schClockConfiguration) {
        SchClockConfiguration beforeSave;
        beforeSave = schClockConfigurationRepository.saveAndFlush(schClockConfiguration);
        beforeSave.setEvents(Sets.newHashSet());
        beforeSave.setEmissions(Sets.newHashSet());
        beforeSave.emissions(schEmissionConfigurationService.saveEmissionClock(schClockConfiguration.getEmissions(), beforeSave));
        beforeSave.events(schEventService.saveEvent(schClockConfiguration.getEvents(), beforeSave));
        schClockConfigurationRepository.saveAndFlush(beforeSave);
        return findSchClockConfigurationForNetworkAndChannelAndShortName(schClockConfiguration.getNetwork().getShortcut(), schClockConfiguration.getChannel().getShortcut(), schClockConfiguration.getShortName());
    }

    @Transactional(readOnly = true)
    public Slice<SchClockConfiguration> findSchClockConfigurationsForNetworkAndChannel(String networkShortcut, String channelShortcut, Pageable pagable) {
        return schClockConfigurationRepository.findAllByNetwork_ShortcutAndChannel_Shortcut(networkShortcut, channelShortcut, pagable);
    }

    @Transactional(readOnly = true)
    public SchClockConfiguration findSchClockConfigurationForNetworkAndChannelAndShortName(String networkShortcut, String channelShortcut, String shortName) {
        return schClockConfigurationRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(networkShortcut, channelShortcut, shortName);
    }

    @Transactional(readOnly = true)
    public SchClockConfigurationDTO findDTOSchClockConfigurationForNetworkAndChannelAndShortName(String networkShortcut, String channelShortcut, String shortName) {
        return schClockConfigurationMapper.DB2DTO(schClockConfigurationRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(networkShortcut, channelShortcut, shortName));
    }

    @Transactional
    public void deleteSchClockConfigurationByNetworkAndChannelAndShortName(String networkShortcut, String channelShortcut, String shortName) {
        SchClockConfiguration schClock = schClockConfigurationRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(networkShortcut, channelShortcut, shortName);
        schEmissionConfigurationService.deleteEmissions(schClock.getEmissions());
        schEventService.deleteEvent(schClock.getEvents());
        schClock.setEmissions(Sets.newHashSet());
        schClock.setEvents(Sets.newHashSet());
        schClockConfigurationRepository.delete(schClock);
    }
}
