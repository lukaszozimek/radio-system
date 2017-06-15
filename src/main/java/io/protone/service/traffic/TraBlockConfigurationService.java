package io.protone.service.traffic;

import io.protone.domain.TraBlockConfiguration;
import io.protone.domain.enumeration.CorDayOfWeekEnum;
import io.protone.repository.traffic.TraBlockConfigurationRepository;
import org.springframework.data.domain.Pageable;
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
        return traBlockConfigurationRepository.save(traBlockConfiguration);
    }

    public List<TraBlockConfiguration> getAllBlockConfigurations(String networkShortcut, Pageable pagable) {
        return traBlockConfigurationRepository.findAllByNetwork_Shortcut(networkShortcut, pagable);
    }

    public List<TraBlockConfiguration> getAllBlockConfigurationsByDay(String networkShortcut, CorDayOfWeekEnum day) {
        return traBlockConfigurationRepository.findAllByNetwork_ShortcutAndDay(networkShortcut, day);

    }
}
