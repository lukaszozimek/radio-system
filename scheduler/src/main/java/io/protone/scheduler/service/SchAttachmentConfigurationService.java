package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchAttachmentConfiguration;
import io.protone.scheduler.repository.SchAttachmentConfigurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@Service
public class SchAttachmentConfigurationService {
    private final Logger log = LoggerFactory.getLogger(SchAttachmentConfigurationService.class);
    private SchAttachmentConfigurationRepository schAttachmentConfigurationRepository;

    @Transactional
    public Set<SchAttachmentConfiguration> saveAttachmenst(Set<SchAttachmentConfiguration> schAttachmentSet) {
        return schAttachmentSet.stream().map(schAttachment -> schAttachmentConfigurationRepository.saveAndFlush(schAttachment)).collect(toSet());
    }

    @Transactional
    public void deleteAttachments(Set<SchAttachmentConfiguration> schAttachmentSet) {
        schAttachmentSet.stream().forEach(schAttachment -> schAttachmentConfigurationRepository.delete(schAttachment));
    }
}
