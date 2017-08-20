package io.protone.traffic.service;


import io.protone.traffic.domain.TraMediaPlanTemplate;
import io.protone.traffic.repository.TraMediaPlanTemplateRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Service
public class TraMediaPlanTemplateService {

    @Inject
    private TraMediaPlanTemplateRepository traMediaPlanTemplateRepository;


    public TraMediaPlanTemplate findMediaPlanTemplate(Long id, String networkShortcut) {
        return traMediaPlanTemplateRepository.findOneByIdAndNetwork_Shortcut(id, networkShortcut);
    }

    @Transactional
    public void deleteMediaPlanTemplate(Long id, String networkShortcut) {
        traMediaPlanTemplateRepository.deleteByIdAndNetwork_Shortcut(id, networkShortcut);
    }

    @Transactional
    public TraMediaPlanTemplate saveMediaPlanTemplate(TraMediaPlanTemplate traMediaPlanTemplate) {
        return traMediaPlanTemplateRepository.save(traMediaPlanTemplate);
    }

    public List<TraMediaPlanTemplate> findAllMediaPlanTemplates(String networkShortcut, Pageable pagable) {
        return traMediaPlanTemplateRepository.findAllByNetwork_Shortcut(networkShortcut, pagable);
    }


}