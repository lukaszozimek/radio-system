package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchEmissionTemplate;
import io.protone.scheduler.repository.SchEmissionTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;


@Service
public class SchEmissionTemplateService {
    private final Logger log = LoggerFactory.getLogger(SchEmissionTemplateService.class);
    @Inject
    private SchEmissionTemplateRepository schEmissionTemplateRepository;
    @Inject
    private SchAttachmentTemplateService schAttachmentService;


    public void deleteEmissions(List<SchEmissionTemplate> emissionSet) {
        if (emissionSet != null && !emissionSet.isEmpty()) {
            emissionSet.stream().forEach(schEmission -> {
                if (schEmission.getId() != null) {
                }

            });
        }
    }


}
