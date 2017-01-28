package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreManagedUserPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.domain.*;
import io.protone.repository.UserRepository;
import io.protone.service.UserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lukaszozimek on 19.01.2017.
 */
@Service
public class CustomCRMTaskMapper {

    public static final String CRM_TASK_STATUS="CRMTaskStatus";

    public CrmTaskPT createCrmTask(CRMTask crmTask, CRMTaskStatus crmTaskStatus, CoreManagedUserPT assignedUser, CoreManagedUserPT createdBy) {
        return new CrmTaskPT().id(crmTask.getId())
            .activityDate(crmTask.getActivityDate().toString())
            .activityLenght(crmTask.getActivityLength())
            .crmTaskStatus(crmTaskStatus)
            .assignedTo(assignedUser)
            .comment(crmTask.getComment())
            .subject(crmTask.getSubject())
            .createdBy(createdBy);
    }

    public List<CRMTask> createTasksEntity(List<CrmTaskPT> crmTaskPTS) {
        return crmTaskPTS.stream().map(this::createTaskEntity).collect(Collectors.toList());
    }

    public CRMTask createTaskEntity(CrmTaskPT taskPT) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        CRMTask crmTask = new CRMTask();
        crmTask.setId(taskPT.getId());
        crmTask.setSubject(taskPT.getSubject());
        crmTask.setActivityDate(LocalDate.parse(taskPT.getActivityDate(), formatter));
        crmTask.setActivityLength(taskPT.getActivityLenght());
        crmTask.setComment(taskPT.getComment());
        return crmTask;
    }

    public List<CORAssociation> createAccountTasksAssociationEntity(CRMAccount crmContact, List<CRMTask> crmTasks, CORNetwork corNetwork) {
        return crmTasks.stream().map(crmTask -> createAccountTaskAssociationEntity(crmContact, crmTask, corNetwork)).collect(Collectors.toList());
    }

    public CORAssociation createAccountTaskAssociationEntity(CRMAccount crmContact, CRMTask crmTask, CORNetwork corNetwork) {
        CORAssociation association = new CORAssociation();
        association.setName("CRMTask");
        association.setSourceClass(CRMAccount.class.getName());
        association.setSourceId(crmContact.getId());
        association.setTargetClass(CRMTask.class.getName());
        association.setTargetId(crmTask.getId());
        association.setNetwork(corNetwork);
        return association;
    }

    public List<CORAssociation> createLeadTasksAssociationEntity(CRMLead lead, List<CRMTask> crmTasks, CORNetwork corNetwork) {
        return crmTasks.stream().map(crmTask -> createLeadTaskAssociationEntity(lead, crmTask, corNetwork)).collect(Collectors.toList());
    }

    public CORAssociation createLeadTaskAssociationEntity(CRMLead lead, CRMTask crmTask, CORNetwork corNetwork) {
        CORAssociation association = new CORAssociation();
        association.setName("CRMTask");
        association.setSourceClass(CRMLead.class.getName());
        association.setSourceId(lead.getId());
        association.setTargetClass(CRMTask.class.getName());
        association.setTargetId(crmTask.getId());
        association.setNetwork(corNetwork);
        return association;
    }

    public List<CORAssociation> createOpportunityTasksAssociationEntity(CRMOpportunity crmContact, List<CRMTask> crmTasks, CORNetwork corNetwork) {
        return crmTasks.stream().map(crmTask -> createOpportunityTaskAssociationEntity(crmContact, crmTask, corNetwork)).collect(Collectors.toList());
    }

    public CORAssociation createOpportunityTaskAssociationEntity(CRMOpportunity opportunity, CRMTask crmTask, CORNetwork corNetwork) {
        CORAssociation association = new CORAssociation();
        association.setName("CRMTask");
        association.setSourceClass(CRMOpportunity.class.getName());
        association.setSourceId(opportunity.getId());
        association.setTargetClass(CRMTask.class.getName());
        association.setTargetId(crmTask.getId());
        association.setNetwork(corNetwork);
        return association;
    }

    public List<CORAssociation> createContactTasksAssociationEntity(CRMContact crmContact, List<CRMTask> crmTasks, CORNetwork corNetwork) {
        return crmTasks.stream().map(crmTask -> createContactTaskAssociationEntity(crmContact, crmTask, corNetwork)).collect(Collectors.toList());
    }

    public CORAssociation createContactTaskAssociationEntity(CRMContact crmContact, CRMTask crmTask, CORNetwork corNetwork) {
        CORAssociation association = new CORAssociation();
        association.setName("CRMTask");
        association.setSourceClass(CRMContact.class.getName());
        association.setSourceId(crmContact.getId());
        association.setTargetClass(CRMTask.class.getName());
        association.setTargetId(crmTask.getId());
        association.setNetwork(corNetwork);
        return association;
    }
    public CORAssociation createTaskStatusAssociationEntity(CRMTask task, CRMTaskStatus crmTaskStatus, CORNetwork corNetwork) {
        CORAssociation association = new CORAssociation();
        association.setName(CRM_TASK_STATUS);
        association.setSourceClass(CRMTask.class.getName());
        association.setSourceId(task.getId());
        association.setTargetClass(CRMTaskStatus.class.getName());
        association.setTargetId(crmTaskStatus.getId());
        association.setNetwork(corNetwork);
        return association;
    }
}
