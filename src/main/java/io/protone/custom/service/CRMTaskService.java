package io.protone.custom.service;

import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.mapper.CustomCRMTaskMapper;
import io.protone.domain.*;
import io.protone.repository.CORAssociationRepository;
import io.protone.repository.CRMTaskRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 25.01.2017.
 */
@Service
public class CRMTaskService {

    @Inject
    private CORAssociationRepository corAssociationRepository;

    @Inject
    private CRMTaskRepository crmTaskRepository;

    @Inject
    private CustomCRMTaskMapper customCRMTaskMapper;

    public CrmTaskPT getTaskAssociatedWithOpportunity(CRMOpportunity opportunity, Long taskId, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClass(opportunity.getId(), taskId, CRMTask.class.getName());
        CRMTask crmTask = crmTaskRepository.findOne(task.getId());
        return customCRMTaskMapper.createCrmTask(crmTask);
    }

    public List<CrmTaskPT> getTasksAssociatedWithOpportunity(CRMOpportunity opportunity, CORNetwork corNetwork) {
        List<CORAssociation> leadTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClass(opportunity.getId(), CRMTask.class.getName());
        List<Long> tasksID = leadTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        List<CRMTask> taskList = crmTaskRepository.findAll(tasksID);
        return customCRMTaskMapper.transformTasksFromEntity(taskList);
    }

    public void deleteOpportunityTask(CRMOpportunity opportunity, Long taskId, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClass(opportunity.getId(), taskId, CRMTask.class.getName());
        crmTaskRepository.delete(task.getId());
        corAssociationRepository.delete(task);
    }

    public CrmTaskPT createTasksAssociatedWithOpportunity(CRMOpportunity opportunity, CrmTaskPT taskPT, CORNetwork corNetwork) {
        CRMTask crmTask = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(taskPT));
        corAssociationRepository.save(customCRMTaskMapper.createOpportunityTaskAssociationEntity(opportunity, crmTask));
        return customCRMTaskMapper.createCrmTask(crmTask);
    }

    public CrmTaskPT updateOportunityTask(CRMOpportunity opportunity, CrmTaskPT crmTask, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClass(opportunity.getId(), crmTask.getId(), CRMTask.class.getName());
        CRMTask task1 = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(crmTask));
        return customCRMTaskMapper.createCrmTask(task1);
    }

    public List<CrmTaskPT> getTasksAssociatedWithLead(CRMLead crmLead, CORNetwork corNetwork) {
        List<CORAssociation> leadTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CRMTask.class.getName());
        List<Long> tasksID = leadTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        List<CRMTask> taskList = crmTaskRepository.findAll(tasksID);
        return customCRMTaskMapper.transformTasksFromEntity(taskList);
    }

    public CrmTaskPT getTaskAssociatedWithLead(CRMLead crmLead, Long taskId, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClass(crmLead.getId(), taskId, CRMTask.class.getName());
        CRMTask crmTask = crmTaskRepository.findOne(task.getId());
        return customCRMTaskMapper.createCrmTask(crmTask);
    }

    public void deleteLeadTask(CRMLead crmLead, Long taskId, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClass(crmLead.getId(), taskId, CRMTask.class.getName());
        crmTaskRepository.delete(task.getId());
        corAssociationRepository.delete(task);
    }

    public CrmTaskPT createTasksAssociatedWithLead(CRMLead crmLead, CrmTaskPT taskPT, CORNetwork corNetwork) {
        CRMTask crmTask = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(taskPT));
        corAssociationRepository.save(customCRMTaskMapper.createLeadTaskAssociationEntity(crmLead, crmTask));
        return customCRMTaskMapper.createCrmTask(crmTask);
    }

    public CrmTaskPT updateLeadTask(CRMLead crmLead, CrmTaskPT crmTask, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClass(crmLead.getId(), crmTask.getId(), CRMTask.class.getName());
        CRMTask crmTask1 = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(crmTask));
        return customCRMTaskMapper.createCrmTask(crmTask1);
    }

    public CrmTaskPT createTasksAssociatedWithCustomer(CRMAccount crmAccount, CrmTaskPT taskPT, CORNetwork corNetwork) {
        CRMTask crmTask = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(taskPT));
        corAssociationRepository.save(customCRMTaskMapper.createAccountTaskAssociationEntity(crmAccount, crmTask));
        return customCRMTaskMapper.createCrmTask(crmTask);
    }

    public List<CrmTaskPT> getTasksAssociatedWithCustomer(CRMAccount crmAccount, CORNetwork corNetwork) {
        List<CORAssociation> leadTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CRMTask.class.getName());
        List<Long> tasksID = leadTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        List<CRMTask> taskList = crmTaskRepository.findAll(tasksID);
        return customCRMTaskMapper.transformTasksFromEntity(taskList);
    }

    public CrmTaskPT getTaskAssociatedWithCustomer(CRMAccount crmAccount, Long taskId, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClass(crmAccount.getId(), taskId, CRMTask.class.getName());
        CRMTask crmTask = crmTaskRepository.findOne(task.getTargetId());
        return customCRMTaskMapper.createCrmTask(crmTask);
    }

    public void deleteCustomerTask(CRMAccount crmAccount, Long taskId, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClass(crmAccount.getId(), taskId, CRMTask.class.getName());
        crmTaskRepository.delete(task.getTargetId());
        corAssociationRepository.delete(task);
    }

    public CrmTaskPT updateCustomerTask(CRMAccount crmAccount, CrmTaskPT crmTask, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClass(crmAccount.getId(), crmTask.getId(), CRMTask.class.getName());
        CRMTask task1 = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(crmTask));
        return customCRMTaskMapper.createCrmTask(task1);
    }

    public List<CrmTaskPT> getTasksAssociatedWithContact(CRMContact crmContact, CORNetwork corNetwork) {
        List<CORAssociation> leadTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmContact.getId(), CRMTask.class.getName());
        List<Long> tasksID = leadTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        List<CRMTask> taskList = crmTaskRepository.findAll(tasksID);
        return customCRMTaskMapper.transformTasksFromEntity(taskList);
    }

    public CrmTaskPT getTaskAssociatedWithContact(CRMContact crmContact, Long taskId, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClass(crmContact.getId(), taskId, CRMTask.class.getName());
        CRMTask crmTask = crmTaskRepository.findOne(task.getId());
        return customCRMTaskMapper.createCrmTask(crmTask);
    }

    public void deleteContactTask(CRMContact crmContact, Long taskId, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClass(crmContact.getId(), taskId, CRMTask.class.getName());
        crmTaskRepository.delete(task.getId());
        corAssociationRepository.delete(task);
    }

    public CrmTaskPT createTasksAssociatedWithContact(CRMContact crmContact, CrmTaskPT taskPT, CORNetwork corNetwork) {
        CRMTask crmTask = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(taskPT));
        corAssociationRepository.save(customCRMTaskMapper.createContactTaskAssociationEntity(crmContact, crmTask));
        return customCRMTaskMapper.createCrmTask(crmTask);
    }

    public CrmTaskPT updateTaskContact(CRMContact crmContactt, CrmTaskPT crmTask, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClass(crmContactt.getId(), crmTask.getId(), CRMTask.class.getName());
        CRMTask task1 = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(crmTask));
        return customCRMTaskMapper.createCrmTask(task1);

    }

}
