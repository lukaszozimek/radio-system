package io.protone.scheduler.service;

import com.google.common.collect.Lists;
import io.protone.scheduler.domain.SchAttachment;
import io.protone.scheduler.repository.SchAttachmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;


@Service
public class SchAttachmentService {

    private final Logger log = LoggerFactory.getLogger(SchAttachmentService.class);

    @Inject
    private SchAttachmentRepository schAttachmentRepository;

    @Transactional
    public List<SchAttachment> saveAttachmenst(List<SchAttachment> schAttachmentSet) {
        if (schAttachmentSet != null && !schAttachmentSet.isEmpty()) {
            return schAttachmentSet.stream().map(schAttachment -> schAttachmentRepository.save(schAttachment)).collect(toList());
        }
        return Lists.newArrayList();
    }

    @Transactional
    public void deleteAttachments(Set<SchAttachment> schAttachmentSet) {
        if (schAttachmentSet != null && !schAttachmentSet.isEmpty()) {
            schAttachmentSet.stream().forEach(schAttachment ->
                    schAttachmentRepository.delete(schAttachment.emission(null)));
        }
    }


}