package io.protone.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.scheduler.domain.SchAttachment;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.repository.SchAttachmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


@Service
public class SchAttachmentService {

    private final Logger log = LoggerFactory.getLogger(SchAttachmentService.class);

    @Inject
    private SchAttachmentRepository schAttachmentRepository;

    @Transactional
    public Set<SchAttachment> saveAttachmenst(Set<SchAttachment> schAttachmentSet) {
        if (schAttachmentSet != null && !schAttachmentSet.isEmpty()) {
            return schAttachmentSet.stream().map(schAttachment -> schAttachmentRepository.saveAndFlush(schAttachment)).collect(toSet());
        }
        return Sets.newHashSet();
    }

    @Transactional
    public void deleteAttachments(Set<SchAttachment> schAttachmentSet) {
        if (schAttachmentSet != null && !schAttachmentSet.isEmpty()) {
            schAttachmentSet.stream().forEach(schAttachment ->
                    schAttachmentRepository.delete(schAttachment.emission(null)));
        }
    }

    @Transactional
    public Set<SchAttachment> saveAttachmenst(Set<SchAttachment> attachments, SchEmission entitiy) {
        if (attachments != null && !attachments.isEmpty()) {
            return attachments.stream().map(schAttachment -> schAttachmentRepository.saveAndFlush(schAttachment.emission(entitiy))).collect(toSet());
        }
        return Sets.newHashSet();
    }
}