package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchAttachment;
import io.protone.scheduler.repository.SchAttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static java.util.stream.Collectors.toSet;


@Service
public class SchAttachmentService {

    private SchAttachmentRepository schAttachmentRepository;

    @Transactional
    public Set<SchAttachment> saveAttachmenst(Set<SchAttachment> schAttachmentSet) {
        return schAttachmentSet.stream().map(schAttachment -> schAttachmentRepository.saveAndFlush(schAttachment)).collect(toSet());
    }


}