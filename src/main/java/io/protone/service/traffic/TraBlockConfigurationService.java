package io.protone.service.traffic;

import io.protone.domain.TraBlockConfiguration;
import io.protone.repository.traffic.TraBlockConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Service
public class TraBlockConfigurationService {

    @Autowired
    private TraBlockConfigurationRepository traBlockConfigurationRepository;


    public TraBlockConfiguration findConfigurationBlock(Long id, String networkShortcut) {

        return traBlockConfigurationRepository.findOneByIdAndNetwork_Shortcut(id, networkShortcut);
    }

    public void deleteBlockConfiguration(Long id, String networkShortcut) {
        traBlockConfigurationRepository.deleteByIdAndNetwork_Shortcut(id, networkShortcut);
    }

    public TraBlockConfiguration saveBlockConfiguration(TraBlockConfiguration traBlockConfiguration) {
        return traBlockConfigurationRepository.save(traBlockConfiguration);
    }
}
