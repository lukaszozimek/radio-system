package io.protone.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.scheduler.domain.SchAttachmentTemplate;
import io.protone.scheduler.domain.SchEmissionTemplate;
import io.protone.scheduler.repository.SchAttachmentTemplateRepository;
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
public class SchAttachmentTemplateService {
    private final Logger log = LoggerFactory.getLogger(SchAttachmentTemplateService.class);
    @Inject
    private SchAttachmentTemplateRepository schAttachmentTemplateRepository;

    @Transactional
    public Set<SchAttachmentTemplate> saveAttachmenst(Set<SchAttachmentTemplate> schAttachmentSet, SchEmissionTemplate schEmissionTemplate) {
        if (schAttachmentSet != null && !schAttachmentSet.isEmpty()) {
            return schAttachmentSet.stream().map(schAttachment -> schAttachmentTemplateRepository.saveAndFlush(schAttachment.emission(schEmissionTemplate))).collect(toSet());
        }
        return Sets.newHashSet();
    }

    @Transactional
    public void deleteAttachments(Set<SchAttachmentTemplate> schAttachmentSet) {
        if (schAttachmentSet != null && !schAttachmentSet.isEmpty()) {
            schAttachmentSet.stream().forEach(schAttachment -> schAttachmentTemplateRepository.delete(schAttachment));
        }
    }

    @Transactional
    public void deleteByEmissionId(Long emissionId) {
        schAttachmentTemplateRepository.deleteAllByEmission_Id(emissionId);
    }
}
