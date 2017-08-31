package io.protone.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.scheduler.domain.SchEmissionConfiguration;
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
    public Set<SchEmissionConfiguration> saveEmission(Set<SchEmissionConfiguration> emissionSet) {
        if (emissionSet != null && !emissionSet.isEmpty()) {
            return emissionSet.stream().map(schEmission -> {
                schEmission.attachments(schAttachmentService.saveAttachmenst(schEmission.getAttachments()));
                return schEmissionConfigurationRepository.saveAndFlush(schEmission);
            }).collect(toSet());
        }
        return Sets.newHashSet();
    }

    @Transactional
    public void deleteEmissions(Set<SchEmissionConfiguration> emissionSet) {
        if (emissionSet != null && !emissionSet.isEmpty()) {
            emissionSet.stream().forEach(schEmission -> {
                schAttachmentService.deleteAttachments(schEmission.getAttachments());
                schEmissionConfigurationRepository.saveAndFlush(schEmission);
                schEmissionConfigurationRepository.delete(schEmission);
            });
        }
    }
}
