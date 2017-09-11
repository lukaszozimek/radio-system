package io.protone.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.scheduler.domain.SchClockConfiguration;
import io.protone.scheduler.domain.SchEmissionConfiguration;
import io.protone.scheduler.domain.SchEventConfiguration;
import io.protone.scheduler.repository.SchEmissionConfigurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


@Service
public class SchEmissionConfigurationService {
    private final Logger log = LoggerFactory.getLogger(SchEmissionConfigurationService.class);
    @Inject
    private SchEmissionConfigurationRepository schEmissionConfigurationRepository;
    @Inject
    private SchAttachmentConfigurationService schAttachmentService;

    @Transactional
    public Set<SchEmissionConfiguration> saveEmissionEvent(Set<SchEmissionConfiguration> emissionSet, SchEventConfiguration schEventConfiguration) {
        if (emissionSet != null && !emissionSet.isEmpty()) {
            return emissionSet.stream().map(schEmission -> {
                SchEmissionConfiguration schEmissionConfiguration = schEmissionConfigurationRepository.saveAndFlush(schEmission.schEventConfiguration(schEventConfiguration));
                schEmission.attachments(schAttachmentService.saveAttachmenst(schEmission.getAttachments(), schEmissionConfiguration));
                return schEmissionConfiguration;
            }).collect(toSet());
        }
        return Sets.newHashSet();
    }


    public void deleteEmissions(Set<SchEmissionConfiguration> emissionSet) {
        if (emissionSet != null && !emissionSet.isEmpty()) {
            emissionSet.stream().forEach(schEmission -> {
                if (schEmission.getId() != null) {
                    schAttachmentService.deleteAttachments(schEmission.getAttachments());
                    schEmissionConfigurationRepository.saveAndFlush(schEmission.attachments(Sets.newHashSet()));
                }
                schEmissionConfigurationRepository.delete(schEmission);
            });
        }
    }

    @Transactional
    public Set<SchEmissionConfiguration> saveEmissionClock(Set<SchEmissionConfiguration> emissionSet, SchClockConfiguration schClockConfiguration) {
        if (emissionSet != null && !emissionSet.isEmpty()) {
            return emissionSet.stream().map(schEmission -> {
                SchEmissionConfiguration schEmissionConfiguration = schEmissionConfigurationRepository.saveAndFlush(schEmission.clock(schClockConfiguration));
                schEmission.attachments(schAttachmentService.saveAttachmenst(schEmission.getAttachments(), schEmissionConfiguration));
                return schEmissionConfiguration;
            }).collect(toSet());
        }
        return Sets.newHashSet();
    }
}
