package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.repository.SchEmissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


@Service
public class SchEmissionService {
    private final Logger log = LoggerFactory.getLogger(SchEmissionService.class);
    @Inject
    private SchEmissionRepository schEmissionRepository;
    @Inject
    private SchAttachmentService schAttachmentService;

    @Transactional
    public Set<SchEmission> saveEmission(Set<SchEmission> emissionSet) {
        return emissionSet.stream().map(schEmission -> {
            schEmission.attachments(schAttachmentService.saveAttachmenst(schEmission.getAttachments()));
            return schEmissionRepository.saveAndFlush(schEmission);
        }).collect(toSet());
    }

    @Transactional
    public void deleteEmissions(Set<SchEmission> emissionSet) {
        emissionSet.stream().forEach(schEmission -> {
            schAttachmentService.deleteAttachments(schEmission.getAttachments());
             schEmissionRepository.saveAndFlush(schEmission);
        });
    }
}
