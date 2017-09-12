package io.protone.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.scheduler.domain.SchAttachmentConfiguration;
import io.protone.scheduler.domain.SchEmissionConfiguration;
import io.protone.scheduler.repository.SchAttachmentConfigurationRepository;
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
public class SchAttachmentConfigurationService {
    private final Logger log = LoggerFactory.getLogger(SchAttachmentConfigurationService.class);
    @Inject
    private SchAttachmentConfigurationRepository schAttachmentConfigurationRepository;

    @Transactional
    public Set<SchAttachmentConfiguration> saveAttachmenst(Set<SchAttachmentConfiguration> schAttachmentSet, SchEmissionConfiguration schEmissionConfiguration) {
        if (schAttachmentSet != null && !schAttachmentSet.isEmpty()) {
            return schAttachmentSet.stream().map(schAttachment -> schAttachmentConfigurationRepository.saveAndFlush(schAttachment.emission(schEmissionConfiguration))).collect(toSet());
        }
        return Sets.newHashSet();
    }

    @Transactional
    public void deleteAttachments(Set<SchAttachmentConfiguration> schAttachmentSet) {
        if (schAttachmentSet != null && !schAttachmentSet.isEmpty()) {
            schAttachmentSet.stream().forEach(schAttachment -> schAttachmentConfigurationRepository.delete(schAttachment));
        }
    }

    @Transactional
    public void deleteByEmissionId(Long emissionId) {
        schAttachmentConfigurationRepository.deleteAllByEmission_Id(emissionId);
    }
}
