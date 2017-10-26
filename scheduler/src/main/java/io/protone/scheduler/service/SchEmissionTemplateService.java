package io.protone.scheduler.service;

import com.google.common.collect.Lists;
import io.protone.scheduler.domain.SchEmissionTemplate;
import io.protone.scheduler.domain.SchEventTemplate;
import io.protone.scheduler.repository.SchEmissionTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;


@Service
public class SchEmissionTemplateService {
    private final Logger log = LoggerFactory.getLogger(SchEmissionTemplateService.class);
    @Inject
    private SchEmissionTemplateRepository schEmissionTemplateRepository;
    @Inject
    private SchAttachmentTemplateService schAttachmentService;

    @Transactional
    public List<SchEmissionTemplate> saveEmissionEvent(List<SchEmissionTemplate> emissionSet, SchEventTemplate schEventConfiguration) {
        if (emissionSet != null && !emissionSet.isEmpty()) {
            return emissionSet.stream().map(schEmission -> {
                schEmission.setId(null);
                SchEmissionTemplate schEmissionTemplate = schEmissionTemplateRepository.saveAndFlush(schEmission.schEventTemplate(schEventConfiguration));
                schEmission.attachments(schAttachmentService.saveAttachmenst(schEmission.getAttachments(), schEmissionTemplate));
                return schEmissionTemplate;
            }).collect(toList());
        }
        return Lists.newArrayList();
    }


    public void deleteEmissions(List<SchEmissionTemplate> emissionSet) {
        if (emissionSet != null && !emissionSet.isEmpty()) {
            emissionSet.stream().forEach(schEmission -> {
                if (schEmission.getId() != null) {
                }

            });
        }
    }

    @Transactional
    public List<SchEmissionTemplate> saveEmissionClock(List<SchEmissionTemplate> emissionSet) {
        if (emissionSet != null && !emissionSet.isEmpty()) {
            return emissionSet.stream().map(schEmission -> {
                schEmission.attachments(schAttachmentService.saveAttachmenst(schEmission.getAttachments(), schEmission));
                return schEmission;
            }).collect(toList());
        }
        return Lists.newArrayList();
    }

    @Transactional
    public void deleteByEventId(Long eventId) {
        if (eventId != null) {
            Set<SchEmissionTemplate> eventEmissions = schEmissionTemplateRepository.findAllByEventTemplate_Id(eventId);
            if (eventEmissions != null && !eventEmissions.isEmpty()) {
                eventEmissions.stream().forEach(schEmissionConfiguration -> schAttachmentService.deleteByEmissionId(schEmissionConfiguration.getId()));
            }
            schEmissionTemplateRepository.deleteAllByEventTemplate_Id(eventId);
        }

    }
}
