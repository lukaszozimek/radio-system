package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchEventConfiguration;
import io.protone.scheduler.repository.SchEventConfigurationRepository;
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
public class SchEventConfigurationService {

    private final Logger log = LoggerFactory.getLogger(SchEventConfigurationService.class);
    @Inject
    private SchEventConfigurationRepository eventRepository;

    @Inject
    private SchEmissionConfigurationService schEmissionConfigurationService;


    @Transactional(readOnly = true)
    public Slice<SchEventConfiguration> findSchEventConfigurationsForNetworkAndChannel(String networkShortcut, String channelShortcut, Pageable pagable) {
        return eventRepository.findAllByNetwork_ShortcutAndChannel_Shortcut(networkShortcut, channelShortcut, pagable);
    }

    @Transactional
    public SchEventConfiguration saveEventConfiguration(SchEventConfiguration schEventConfiguration) {
        SchEventConfiguration beforeSave;
        beforeSave = eventRepository.saveAndFlush(schEventConfiguration);
        this.schEmissionConfigurationService.deleteByEventId(schEventConfiguration.getId());
        beforeSave.emissions(schEmissionConfigurationService.saveEmissionEvent(schEventConfiguration.getEmissions(), beforeSave));
        eventRepository.saveAndFlush(beforeSave);
        eventRepository.flush();
        return findSchEventConfigurationsForNetworkAndChannelAndShortName(schEventConfiguration.getNetwork().getShortcut(), schEventConfiguration.getChannel().getShortcut(), schEventConfiguration.getShortName());
    }

    @Transactional
    public void deleteSchEventConfigurationByNetworkAndChannelAndShortName(String networkShortcut, String channelShortcut, String shortName) {
        SchEventConfiguration schEventConfiguration = findSchEventConfigurationsForNetworkAndChannelAndShortName(networkShortcut, channelShortcut, shortName);
        if (schEventConfiguration != null) {
            this.schEmissionConfigurationService.deleteByEventId(schEventConfiguration.getId());
            eventRepository.deleteByNetwork_ShortcutAndChannel_ShortcutAndShortName(networkShortcut, channelShortcut, shortName);
        }
    }

    @Transactional(readOnly = true)
    public SchEventConfiguration findSchEventConfigurationsForNetworkAndChannelAndShortName(String networkShortcut, String channelShortcut, String shortName) {
        return eventRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(networkShortcut, channelShortcut, shortName);
    }
}
