package io.protone.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.scheduler.domain.SchEventEmission;
import io.protone.scheduler.repository.SchEventEmissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


@Service
public class SchEventEmissionService {
    private final Logger log = LoggerFactory.getLogger(SchEventEmissionService.class);
    @Inject
    private SchEventEmissionRepository schEmissionConfigurationRepository;

    @Inject
    private SchEventEmissionAttachmentService schEventEmissionAttachmentService;

    @Transactional
    public Set<SchEventEmission> saveEmission(Set<SchEventEmission> emissionSet) {
        if (emissionSet != null && !emissionSet.isEmpty()) {
            return emissionSet.stream().map(schEmission -> {
                schEmission.attachments(schEventEmissionAttachmentService.saveAttachmenst(schEmission.getAttachments()));
                return schEmissionConfigurationRepository.saveAndFlush(schEmission);
            }).collect(toSet());
        }
        return Sets.newHashSet();
    }

    @Transactional
    public void deleteEmissions(Set<SchEventEmission> emissionSet) {
        if (emissionSet != null) {
            emissionSet.stream().forEach(schEmission -> {
                schEventEmissionAttachmentService.deleteAttachments(schEmission.getAttachments());
                schEmissionConfigurationRepository.saveAndFlush(schEmission);
                schEmissionConfigurationRepository.delete(schEmission);

            });
        }

    }
}
