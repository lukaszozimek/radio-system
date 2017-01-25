package io.protone.custom.service;

import io.protone.custom.service.dto.*;
import io.protone.custom.service.mapper.CustomCRMContactMapper;
import io.protone.custom.service.mapper.CustomCRMOpportunityMapper;
import io.protone.custom.service.mapper.CustomCRMTaskMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import io.protone.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
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

    @Inject
    private CRMContactService crmContactService;

    @Inject
    private CustomCRMOpportunityMapper customCRMOpportunityMapper;

    @Inject
    private CustomCRMContactMapper customCRMContactMapper;
    @Inject
    private UserService userService;

    @Inject
    private CRMTaskService taskService;

    public List<CrmOpportunityPT> getAllOpportunity(CORNetwork corNetwork) {
        List<CRMOpportunity> opportunities = opportunityRepository.findAll();
        return opportunities.stream().map(opportunity -> createDTO(opportunity, corNetwork)).collect(toList());
    }

    public CrmOpportunityPT saveOpportunity(CrmOpportunityPT opportunityPT, CORNetwork corNetwork) {
        CRMOpportunity opportunity = opportunityRepository.save(customCRMOpportunityMapper.createOpportunity(opportunityPT));
        CRMStage stage = stageRepository.findByNameAndNetwork(opportunityPT.getStage().getName(), corNetwork);
        CRMContact contact = crmContactRepository.findOne(customCRMContactMapper.createCrmContactEntity(opportunityPT.getContact()).getId());
        associationRepository.save(customCRMOpportunityMapper.createOpportunityContactAssociationEntity(opportunity, contact));
        associationRepository.save(customCRMOpportunityMapper.createOpportunityStageAssociationEntity(opportunity, stage));
        List<CrmTaskPT> crmTaskPTS = taskService.saveOpportunity(opportunity, opportunityPT, corNetwork);
        CrmContactPT crmContactPT = crmContactService.getContact(contact.getShortName(), corNetwork);
        return customCRMOpportunityMapper.buildDTOFromEntites(opportunity, stage, new CoreManagedUserPT(), crmContactPT, crmTaskPTS);
    }

    public void deleteOpportunity(String shortcut, CORNetwork corNetwork) {
        CRMOpportunity opportunity = opportunityRepository.findByNameAndNetwork(shortcut, corNetwork);
        List<CORAssociation> opportunityStageAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(opportunity.getId(), CRMStage.class.getName(), corNetwork);
        List<CORAssociation> opportunityContactAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(opportunity.getId(), CRMContact.class.getName(), corNetwork);
        associationRepository.delete(opportunityContactAssociation);

        taskService.deleteOpportunityTask(opportunity, corNetwork);
        associationRepository.delete(opportunityStageAssociation);
        opportunityRepository.delete(opportunity);
    }

    public CrmOpportunityPT getOpportunity(String shortcut, CORNetwork corNetwork) {
        CRMOpportunity opportunitie = opportunityRepository.findByNameAndNetwork(shortcut, corNetwork);
        return createDTO(opportunitie, corNetwork);
    }


    public CrmOpportunityPT update(CrmOpportunityPT opportunityPT, CORNetwork corNetwork) {
        deleteOpportunity(opportunityPT.getName(), corNetwork);
        return saveOpportunity(opportunityPT, corNetwork);
    }

    public List<CrmTaskPT> getTasksAssociatedWithLead(String shortcut, CORNetwork corNetwork) {
        CRMOpportunity opportunity = opportunityRepository.findByNameAndNetwork(shortcut, corNetwork);
        return taskService.getTasksAssociatedWithOpportunity(opportunity, corNetwork);
    }

    public CrmTaskPT getTaskAssociatedWithLead(String shortcut, Long taskId, CORNetwork corNetwork) {
        CRMOpportunity opportunity = opportunityRepository.findByNameAndNetwork(shortcut, corNetwork);
        return taskService.getTaskAssociatedWithOpportunity(opportunity, taskId, corNetwork);
    }

    public void deleteLeadTask(String shortcut, Long taskId, CORNetwork corNetwork) {
        CRMOpportunity opportunity = opportunityRepository.findByNameAndNetwork(shortcut, corNetwork);
        taskService.deleteOpportunityTask(opportunity, taskId, corNetwork);
    }

    public CrmTaskPT createTasksAssociatedWithLead(String shortcut, CrmTaskPT taskPT, CORNetwork corNetwork) {
        CRMOpportunity opportunity = opportunityRepository.findByNameAndNetwork(shortcut, corNetwork);
        return taskService.createTasksAssociatedWithOpportunity(opportunity, taskPT, corNetwork);
    }

    public CrmTaskPT updateLeadTask(String shortcut, CrmTaskPT crmTask, CORNetwork corNetwork) {
        CRMOpportunity crmOpportunity = opportunityRepository.findByNameAndNetwork(shortcut, corNetwork);
        return taskService.updateOportunityTask(crmOpportunity, crmTask, corNetwork);

    }

    private CrmOpportunityPT createDTO(CRMOpportunity opportunity, CORNetwork corNetwork) {
        List<CORAssociation> opportunityStageAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(opportunity.getId(), CRMStage.class.getName(), corNetwork);
        List<CORAssociation> opportunityContactAssociation = associationRepository.findBySourceIdAndTargetClassAndNetwork(opportunity.getId(), CRMContact.class.getName(), corNetwork);
        CRMStage stage = stageRepository.findOne(opportunityStageAssociation.get(0).getTargetId());
        CRMContact crmContact = crmContactRepository.findOne(opportunityContactAssociation.get(0).getTargetId());
        List<CrmTaskPT> taskPTS = taskService.getOpportunityTask(opportunity, corNetwork);
        return customCRMOpportunityMapper.buildDTOFromEntites(opportunity, stage, new CoreManagedUserPT(), crmContactService.getContact(crmContact.getShortName(), corNetwork), taskPTS);
    }


}
