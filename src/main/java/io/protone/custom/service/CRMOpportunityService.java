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

    public List<CrmOpportunityPT> getAllOpportunity() {
        List<CrmOpportunityPT> crmOpportunityPTList = new ArrayList<>();
        List<CRMOpportunity> opportunities = opportunityRepository.findAll();
        opportunities.stream().forEach(opportunity -> {
            crmOpportunityPTList.add(createDTO(opportunity));
        });
        return crmOpportunityPTList;
    }

    public CrmOpportunityPT saveOpportunity(CrmOpportunityPT opportunityPT) {
        CRMOpportunity opportunity = opportunityRepository.save(customCRMOpportunityMapper.createOpportunity(opportunityPT));
        List<CRMTask> taskList = taskRepository.save(customCRMTaskMapper.createTasksEntity(opportunityPT.getTasks()));
        CRMStage stage = stageRepository.findByName(opportunityPT.getStage().getName());
        CRMContact contact = crmContactRepository.findOne(customCRMContactMapper.createCrmContactEntity(opportunityPT.getContact()).getId());

        associationRepository.save(customCRMOpportunityMapper.createOpportunityContactAssociationEntity(opportunity, contact));
        associationRepository.save(customCRMOpportunityMapper.createOpportunityStageAssociationEntity(opportunity, stage));
        associationRepository.save(customCRMOpportunityMapper.createOpportunityTasksAssociationEntity(opportunity, taskList));

        CrmContactPT crmContactPT = crmContactService.getContact(contact.getShortName());
        return customCRMOpportunityMapper.buildDTOFromEntites(opportunity, stage, new CoreManagedUserPT(), crmContactPT, taskList);
    }

    public void deleteOpportunity(String shortcut) {
        CRMOpportunity opportunity = opportunityRepository.findByName(shortcut);
        List<CORAssociation> opportunityStageAssociation = associationRepository.findBySourceIdAndTargetClass(opportunity.getId(), CRMStage.class.getName());
        List<CORAssociation> opportunityContactAssociation = associationRepository.findBySourceIdAndTargetClass(opportunity.getId(), CRMContact.class.getName());
        List<CORAssociation> opportunityTaskAssociation = associationRepository.findBySourceIdAndTargetClass(opportunity.getId(), CRMTask.class.getName());
        List<CRMTask> crmTasks = taskRepository.findAll(opportunityTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList()));
        associationRepository.delete(opportunityContactAssociation);
        associationRepository.delete(opportunityTaskAssociation);
        associationRepository.delete(opportunityStageAssociation);
        taskRepository.delete(crmTasks);
        opportunityRepository.delete(opportunity);
    }

    public CrmOpportunityPT getOpportunity(String shortcut) {
        CRMOpportunity opportunitie = opportunityRepository.findByName(shortcut);
        return createDTO(opportunitie);
    }

    public CrmTaskPT getTaskAssociatedWithLead(String shortcut, Long taskId) {
        CRMOpportunity opportunity = opportunityRepository.findByName(shortcut);
        CORAssociation task = associationRepository.findBySourceIdAndTargetIdAndTargetClass(opportunity.getId(), taskId, CRMTask.class.getName());
        CRMTask crmTask = taskRepository.findOne(task.getId());
        return customCRMTaskMapper.createCrmTask(crmTask);
    }

    public List<CrmTaskPT> getTasksAssociatedWithLead(String shortcut) {
        CRMOpportunity opportunity = opportunityRepository.findByName(shortcut);
        List<CORAssociation> leadTaskAssociation = associationRepository.findBySourceIdAndTargetClass(opportunity.getId(), CRMTask.class.getName());
        List<Long> tasksID = leadTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        List<CRMTask> taskList = taskRepository.findAll(tasksID);
        return customCRMTaskMapper.transformTasksFromEntity(taskList);
    }

    public CrmOpportunityPT update(CrmOpportunityPT opportunityPT) {
        deleteOpportunity(opportunityPT.getName());
        return saveOpportunity(opportunityPT);
    }

    public void deleteLeadTask(String shortcut, Long taskId) {
        CRMOpportunity opportunity = opportunityRepository.findByName(shortcut);
        CORAssociation task = associationRepository.findBySourceIdAndTargetIdAndTargetClass(opportunity.getId(), taskId, CRMTask.class.getName());
        taskRepository.delete(task.getId());
        associationRepository.delete(task);
    }

    public CrmTaskPT createTasksAssociatedWithLead(String shortcut, CrmTaskPT taskPT) {
        CRMOpportunity opportunity = opportunityRepository.findByName(shortcut);
        CRMTask crmTask = taskRepository.save(customCRMTaskMapper.createTaskEntity(taskPT));
        associationRepository.save(customCRMOpportunityMapper.createOpportunityTaskAssociationEntity(opportunity, crmTask));
        return customCRMTaskMapper.createCrmTask(crmTask);
    }

    private CrmOpportunityPT createDTO(CRMOpportunity opportunity) {
        List<CORAssociation> opportunityStageAssociation = associationRepository.findBySourceIdAndTargetClass(opportunity.getId(), CRMStage.class.getName());
        List<CORAssociation> opportunityContactAssociation = associationRepository.findBySourceIdAndTargetClass(opportunity.getId(), CRMContact.class.getName());
        List<CORAssociation> opportunityTaskAssociation = associationRepository.findBySourceIdAndTargetClass(opportunity.getId(), CRMTask.class.getName());
        CRMStage stage = stageRepository.findOne(opportunityStageAssociation.get(0).getTargetId());
        CRMContact crmContact = crmContactRepository.findOne(opportunityContactAssociation.get(0).getTargetId());
        List<CRMTask> crmTasks = taskRepository.findAll(opportunityTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList()));
        return customCRMOpportunityMapper.buildDTOFromEntites(opportunity, stage, new CoreManagedUserPT(), crmContactService.getContact(crmContact.getShortName()), crmTasks);
    }

    public CrmTaskPT updateLeadTask(String shortcut, CrmTaskPT crmTask) {
        CRMOpportunity crmAccount = opportunityRepository.findByName(shortcut);
        CORAssociation task = associationRepository.findBySourceIdAndTargetIdAndTargetClass(crmAccount.getId(), crmTask.getId(), CRMTask.class.getName());
        CRMTask task1 = taskRepository.save(customCRMTaskMapper.createTaskEntity(crmTask));
        return customCRMTaskMapper.createCrmTask(task1);
    }

}
