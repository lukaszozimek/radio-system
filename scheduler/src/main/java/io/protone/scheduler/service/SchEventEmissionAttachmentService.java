package io.protone.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.scheduler.domain.SchEventEmission;
import io.protone.scheduler.domain.SchEventEmissionAttachment;
import io.protone.scheduler.repository.SchEventEmissionAttachmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@Service
public class SchEventEmissionAttachmentService {
    private final Logger log = LoggerFactory.getLogger(SchEventEmissionAttachmentService.class);

    @Inject
    private SchEventEmissionAttachmentRepository schAttachmentConfigurationRepository;

    @Transactional
    public Set<SchEventEmissionAttachment> saveAttachmenst(Set<SchEventEmissionAttachment> schAttachmentSet, SchEventEmission schEventEmission) {
        if (schAttachmentSet != null && !schAttachmentSet.isEmpty()) {
            return schAttachmentSet.stream().map(schAttachment -> schAttachmentConfigurationRepository.saveAndFlush(schAttachment.emission(schEventEmission))).collect(toSet());
        }
        return Sets.newHashSet();
    }

    @Transactional
    public void deleteAttachments(Set<SchEventEmissionAttachment> schAttachmentSet) {
        if (schAttachmentSet != null) {
            schAttachmentSet.stream().forEach(schAttachment -> schAttachmentConfigurationRepository.delete(schAttachment));
        }
    }
}
