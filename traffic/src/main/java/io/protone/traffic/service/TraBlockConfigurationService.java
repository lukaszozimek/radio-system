package io.protone.traffic.service;


import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import io.protone.traffic.domain.TraBlockConfiguration;
import io.protone.traffic.repository.TraBlockConfigurationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Service
public class TraBlockConfigurationService {

    @Inject
    private TraBlockConfigurationRepository traBlockConfigurationRepository;


    public TraBlockConfiguration findConfigurationBlock(Long id, String networkShortcut) {
        return traBlockConfigurationRepository.findOneByIdAndNetwork_Shortcut(id, networkShortcut);
    }

    @Transactional
    public void deleteBlockConfiguration(Long id, String networkShortcut) {
        traBlockConfigurationRepository.deleteByIdAndNetwork_Shortcut(id, networkShortcut);
    }

    @Transactional
    public TraBlockConfiguration saveBlockConfiguration(TraBlockConfiguration traBlockConfiguration) {
        return traBlockConfigurationRepository.saveAndFlush(traBlockConfiguration);
    }

    public List<TraBlockConfiguration> getAllBlockConfigurations(String networkShortcut, String channelShortcut) {
        return traBlockConfigurationRepository.findAllByNetwork_ShortcutAndChannel_Shortcut(networkShortcut, channelShortcut);
    }

    public List<TraBlockConfiguration> getAllBlockConfigurationsByDay(String networkShortcut, CorDayOfWeekEnum day) {
        return traBlockConfigurationRepository.findAllByNetwork_ShortcutAndDay(networkShortcut, day);

    }
}
