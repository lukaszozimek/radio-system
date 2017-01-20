package io.protone.custom.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.custom.service.dto.*;
import io.protone.custom.service.mapper.CustomCRMOpportunityMapper;
import io.protone.custom.service.mapper.CustomCRMTaskMapper;
import io.protone.domain.CRMContact;
import io.protone.domain.CRMOpportunity;
import io.protone.domain.CRMStage;
import io.protone.domain.CRMTask;
import io.protone.repository.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
public class CRMOpportunityService {

    @Inject
    private CRMStageRepository stageRepository;

    @Inject
    private CRMContactRepository crmContactRepository;

    @Inject
    private CORAssociationRepository associationRepository;

    @Inject
    private CRMOpportunityRepository opportunityRepository;

    @Inject
    private CRMTaskRepository taskRepository;
    @Inject
    private CustomCRMTaskMapper customCRMTaskMapper;

    public List<CrmOpportunityPT> getAllOpportunity() {

        return null;
    }

    public CrmOpportunityPT saveOpportunity(CrmOpportunityPT opportunityPT) {
       // CRMOpportunity opportunity = opportunityRepository.save(customCRMOpportunityMapper.createOpportunity(opportunityPT));
        List<CRMTask> taskList = taskRepository.save(customCRMTaskMapper.createTasksEntity(opportunityPT.getTasks()));
        CRMStage stage = stageRepository.findByName(opportunityPT.getStage().getName());
        //CRMContact contact = crmContactRepository.findOne(customCRMOpportunityMapper.createContactEntity(opportunityPT).getId());
        //association to user
        return null;
    }

    public void deleteOpportunity() {

    }

    public CrmOpportunityPT getOpportunity(String shortcut) {
        return null;
    }
}
