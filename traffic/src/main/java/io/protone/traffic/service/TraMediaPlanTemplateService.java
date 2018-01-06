package io.protone.traffic.service;


import io.protone.traffic.domain.TraMediaPlanTemplate;
import io.protone.traffic.repository.TraMediaPlanTemplateRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Service
public class TraMediaPlanTemplateService {

    @Inject
    private TraMediaPlanTemplateRepository traMediaPlanTemplateRepository;


    public TraMediaPlanTemplate findMediaPlanTemplate(Long id, String organizationShortcut, String channelShortcut) {
        return traMediaPlanTemplateRepository.findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, organizationShortcut, channelShortcut);
    }

    @Transactional
    public void deleteMediaPlanTemplate(Long id, String organizationShortcut, String channelShortcut) {
        traMediaPlanTemplateRepository.deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, organizationShortcut, channelShortcut);
    }

    @Transactional
    public TraMediaPlanTemplate saveMediaPlanTemplate(TraMediaPlanTemplate traMediaPlanTemplate) {
        return traMediaPlanTemplateRepository.save(traMediaPlanTemplate);
    }

    public Slice<TraMediaPlanTemplate> findAllMediaPlanTemplates(String organizationShortcut, String channelShortcut, Pageable pagable) {
        return traMediaPlanTemplateRepository.findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(organizationShortcut, channelShortcut, pagable);
    }


}
