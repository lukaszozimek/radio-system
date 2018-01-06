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


    public TraBlockConfiguration findConfigurationBlock(Long id, String organizationShortcut, String channelShortcut) {
        return traBlockConfigurationRepository.findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, organizationShortcut, channelShortcut);
    }

    @Transactional
    public void deleteBlockConfiguration(Long id, String organizationShortcut, String channelShortcut) {
        traBlockConfigurationRepository.deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, organizationShortcut, channelShortcut);
    }

    @Transactional
    public TraBlockConfiguration saveBlockConfiguration(TraBlockConfiguration traBlockConfiguration) {
        return traBlockConfigurationRepository.saveAndFlush(traBlockConfiguration);
    }

    public List<TraBlockConfiguration> getAllBlockConfigurations(String organizationShortcut, String channelShortcut) {
        return traBlockConfigurationRepository.findAllByChannel_Organization_ShortcutAndChannel_Shortcut(organizationShortcut, channelShortcut);
    }

    public List<TraBlockConfiguration> getAllBlockConfigurationsByDay(String organizationShortcut, String channelShortcut, CorDayOfWeekEnum day) {
        return traBlockConfigurationRepository.findAllByChannel_Organization_ShortcutAndChannel_ShortcutAndDay(organizationShortcut, channelShortcut, day);

    }
}
