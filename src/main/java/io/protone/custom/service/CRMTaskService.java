package io.protone.custom.service;

import io.protone.custom.service.dto.*;
import io.protone.custom.service.mapper.CustomCORUserMapper;
import io.protone.custom.service.mapper.CustomCRMTaskMapper;
import io.protone.domain.*;
import io.protone.repository.CORAssociationRepository;
import io.protone.repository.CRMTaskRepository;
import io.protone.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static io.protone.custom.service.mapper.CustomCORUserMapper.ASSIGNED_TO;
import static io.protone.custom.service.mapper.CustomCORUserMapper.CREATED_BY;
import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 25.01.2017.
 */
@Service
@Transactional
public class CRMTaskService {

    @Inject
    private CORAssociationRepository corAssociationRepository;

    @Inject
    private CRMTaskRepository crmTaskRepository;

    @Inject
    private CustomCRMTaskMapper customCRMTaskMapper;
    @Inject
    private CustomCORUserMapper customCORUserMapper;
    @Inject
    private UserRepository userRepository;

    public List<CrmTaskPT> saveCustomerTasks(CrmAccountPT crmAccountPT, CRMAccount crmAccount, CORNetwork corNetwork) {
        crmAccountPT.getTasks().stream().forEach(crmTaskPT -> {
            CRMTask crmTask = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(crmTaskPT));
            corAssociationRepository.save(customCRMTaskMapper.createAccountTaskAssociationEntity(crmAccount, crmTask));
            corAssociationRepository.save(customCORUserMapper.createCRMAssignetToTaskAssociation(crmTask, customCORUserMapper.tranformUserDTO(crmTaskPT.getAssignedTo()), corNetwork));
            corAssociationRepository.save(customCORUserMapper.createCRMCreatedByTaskAssociation(crmTask, customCORUserMapper.tranformUserDTO(crmTaskPT.getCreatedBy()), corNetwork));
        });
        return getTasksAssociatedWithCustomer(crmAccount, corNetwork);
    }

    public void deleteCustomerTasks(CRMAccount crmAccount, CORNetwork corNetwork) {
        List<CORAssociation> contactTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmAccount.getId(), CRMTask.class.getName(), corNetwork);
        contactTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList()).forEach(taskId -> {
            deleteTaskById(taskId, corNetwork);
        });
    }

    public void deleteLeadTask(CRMLead crmLead, CORNetwork corNetwork) {
        List<CORAssociation> leadTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CRMTask.class.getName(), corNetwork);
        leadTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList()).forEach(id -> {
            deleteTaskById(id, corNetwork);
        });
        corAssociationRepository.deleteBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CRMTask.class.getName(), corNetwork);

    }

    public void deleteOpportunityTask(CRMOpportunity crmOpportunity, CORNetwork corNetwork) {
        List<CORAssociation> opportunityTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmOpportunity.getId(), CRMTask.class.getName(), corNetwork);
        List<CRMTask> crmTasks = crmTaskRepository.findAll(opportunityTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList()));
        corAssociationRepository.delete(opportunityTaskAssociation);
        crmTaskRepository.delete(crmTasks);
    }

    public List<CrmTaskPT> saveOpportunity(CRMOpportunity opportunity, CrmOpportunityPT crmOpportunityPT, CORNetwork corNetwork) {
        crmOpportunityPT.getTasks().stream().forEach(crmTaskPT -> {

            CRMTask crmTask = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(crmTaskPT));
            corAssociationRepository.save(customCRMTaskMapper.createOpportunityTaskAssociationEntity(opportunity, crmTask));
            corAssociationRepository.save(customCORUserMapper.createCRMAssignetToTaskAssociation(crmTask, customCORUserMapper.tranformUserDTO(crmTaskPT.getAssignedTo()), corNetwork));
            corAssociationRepository.save(customCORUserMapper.createCRMCreatedByTaskAssociation(crmTask, customCORUserMapper.tranformUserDTO(crmTaskPT.getCreatedBy()), corNetwork));

        });
        return getOpportunityTask(opportunity, corNetwork);
    }

    public List<CrmTaskPT> getOpportunityTask(CRMOpportunity opportunity, CORNetwork corNetwork) {
        List<CORAssociation> opportunityTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(opportunity.getId(), CRMTask.class.getName(), corNetwork);
        List<Long> listTaskId = opportunityTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        return listTaskId.stream().map(id -> createTaskById(id, corNetwork)).collect(toList());
    }

    public List<CrmTaskPT> getCrmContactTask(CRMContact crmContact, CORNetwork corNetwork) {
        List<CORAssociation> contactTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmContact.getId(), CRMTask.class.getName(), corNetwork);
        List<Long> listTaskId = contactTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        return listTaskId.stream().map(id -> createTaskById(id, corNetwork)).collect(toList());
    }

    public List<CrmTaskPT> saveContactTask(CRMContact crmContact, CrmContactPT crmContactPT, CORNetwork corNetwork) {
        crmContactPT.getTasks().stream().forEach(crmTaskPT -> {

            CRMTask crmTask = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(crmTaskPT));
            corAssociationRepository.save(customCRMTaskMapper.createContactTaskAssociationEntity(crmContact, crmTask));
            corAssociationRepository.save(customCORUserMapper.createCRMAssignetToTaskAssociation(crmTask, customCORUserMapper.tranformUserDTO(crmTaskPT.getAssignedTo()), corNetwork));
            corAssociationRepository.save(customCORUserMapper.createCRMCreatedByTaskAssociation(crmTask, customCORUserMapper.tranformUserDTO(crmTaskPT.getCreatedBy()), corNetwork));

        });
        return getCrmContactTask(crmContact, corNetwork);
    }

    public void deleteCrmContactTask(CRMContact crmContact, CORNetwork corNetwork) {
        List<CORAssociation> contactTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmContact.getId(), CRMTask.class.getName(), corNetwork);
        contactTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList()).forEach(taskId -> {
            deleteTaskById(taskId, corNetwork);
        });
    }

    public List<CrmTaskPT> getLeadTasks(CRMLead crmLead, CORNetwork corNetwork) {
        List<CORAssociation> crmLeadTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CRMTask.class.getName(), corNetwork);
        List<Long> listTaskId = crmLeadTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        return listTaskId.stream().map(id -> createTaskById(id, corNetwork)).collect(toList());
    }

    public List<CrmTaskPT> saveLeadTasks(CrmLeadPT crmLeadPt, CRMLead lead, CORNetwork corNetwork) {
        crmLeadPt.getTasks().stream().forEach(crmTaskPT -> {
            CRMTask crmTask = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(crmTaskPT));

            corAssociationRepository.save(customCRMTaskMapper.createLeadTaskAssociationEntity(lead, crmTask));
            corAssociationRepository.save(customCORUserMapper.createCRMAssignetToTaskAssociation(crmTask, customCORUserMapper.tranformUserDTO(crmTaskPT.getAssignedTo()), corNetwork));
            corAssociationRepository.save(customCORUserMapper.createCRMCreatedByTaskAssociation(crmTask, customCORUserMapper.tranformUserDTO(crmTaskPT.getCreatedBy()), corNetwork));
        });
        return getLeadTasks(lead, corNetwork);
    }

    public List<CrmTaskPT> fetchCustomerTasks(CRMAccount crmAccount, CORNetwork corNetwork) {
        List<CORAssociation> crmAccountTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmAccount.getId(), CRMTask.class.getName(), corNetwork);
        List<Long> listTaskId = crmAccountTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        return listTaskId.stream().map(id -> createTaskById(id, corNetwork)).collect(toList());
    }

    public CrmTaskPT getTaskAssociatedWithOpportunity(CRMOpportunity opportunity, Long taskId, CORNetwork
        corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClassAndNetwork(opportunity.getId(), taskId, CRMTask.class.getName(), corNetwork);
        CRMTask crmTask = crmTaskRepository.findOne(task.getId());
        return createTaskById(crmTask.getId(), corNetwork);
    }

    public List<CrmTaskPT> getTasksAssociatedWithOpportunity(CRMOpportunity opportunity, CORNetwork corNetwork) {
        List<CORAssociation> crmOpportunityTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(opportunity.getId(), CRMTask.class.getName(), corNetwork);
        List<Long> listTaskId = crmOpportunityTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        return listTaskId.stream().map(id -> createTaskById(id, corNetwork)).collect(toList());
    }

    public void deleteOpportunityTask(CRMOpportunity opportunity, Long taskId, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClassAndNetwork(opportunity.getId(), taskId, CRMTask.class.getName(), corNetwork);
        deleteTaskById(task.getTargetId(), corNetwork);
        corAssociationRepository.delete(task);
    }

    public CrmTaskPT createTasksAssociatedWithOpportunity(CRMOpportunity opportunity, CrmTaskPT taskPT, CORNetwork
        corNetwork) {
        CRMTask crmTask = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(taskPT));
        corAssociationRepository.save(customCRMTaskMapper.createOpportunityTaskAssociationEntity(opportunity, crmTask));
        return customCRMTaskMapper.createCrmTask(crmTask, new CoreManagedUserPT(), new CoreManagedUserPT());
    }

    public CrmTaskPT updateOportunityTask(CRMOpportunity opportunity, CrmTaskPT crmTask, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClassAndNetwork(opportunity.getId(), crmTask.getId(), CRMTask.class.getName(), corNetwork);
        CRMTask task1 = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(crmTask));
        return createTaskById(task1.getId(), corNetwork);
    }

    public List<CrmTaskPT> getTasksAssociatedWithLead(CRMLead crmLead, CORNetwork corNetwork) {
        List<CORAssociation> opportunityTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmLead.getId(), CRMTask.class.getName(), corNetwork);
        List<Long> listTaskId = opportunityTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        return listTaskId.stream().map(id -> createTaskById(id, corNetwork)).collect(toList());
    }

    public CrmTaskPT getTaskAssociatedWithLead(CRMLead crmLead, Long taskId, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClassAndNetwork(crmLead.getId(), taskId, CRMTask.class.getName(), corNetwork);
        CRMTask crmTask = crmTaskRepository.findOne(task.getId());
        return customCRMTaskMapper.createCrmTask(crmTask, new CoreManagedUserPT(), new CoreManagedUserPT());
    }

    public void deleteLeadTask(CRMLead crmLead, Long taskId, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClassAndNetwork(crmLead.getId(), taskId, CRMTask.class.getName(), corNetwork);
        deleteTaskById(task.getTargetId(), corNetwork);
        corAssociationRepository.delete(task);
    }

    public CrmTaskPT createTasksAssociatedWithLead(CRMLead crmLead, CrmTaskPT taskPT, CORNetwork corNetwork) {
        CRMTask crmTask = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(taskPT));
        corAssociationRepository.save(customCRMTaskMapper.createLeadTaskAssociationEntity(crmLead, crmTask));
        return customCRMTaskMapper.createCrmTask(crmTask, new CoreManagedUserPT(), new CoreManagedUserPT());
    }

    public CrmTaskPT updateLeadTask(CRMLead crmLead, CrmTaskPT crmTask, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClassAndNetwork(crmLead.getId(), crmTask.getId(), CRMTask.class.getName(), corNetwork);
        CRMTask crmTask1 = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(crmTask));
        return customCRMTaskMapper.createCrmTask(crmTask1, new CoreManagedUserPT(), new CoreManagedUserPT());
    }

    public CrmTaskPT createTasksAssociatedWithCustomer(CRMAccount crmAccount, CrmTaskPT taskPT, CORNetwork
        corNetwork) {
        CRMTask crmTask = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(taskPT));
        corAssociationRepository.save(customCRMTaskMapper.createAccountTaskAssociationEntity(crmAccount, crmTask));
        return customCRMTaskMapper.createCrmTask(crmTask, new CoreManagedUserPT(), new CoreManagedUserPT());
    }

    public List<CrmTaskPT> getTasksAssociatedWithCustomer(CRMAccount crmAccount, CORNetwork corNetwork) {
        List<CORAssociation> crmAccountTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmAccount.getId(), CRMTask.class.getName(), corNetwork);
        List<Long> listTaskId = crmAccountTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        return listTaskId.stream().map(id -> createTaskById(id, corNetwork)).collect(toList());
    }

    public CrmTaskPT getTaskAssociatedWithCustomer(CRMAccount crmAccount, Long taskId, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClassAndNetwork(crmAccount.getId(), taskId, CRMTask.class.getName(), corNetwork);
        CRMTask crmTask = crmTaskRepository.findOne(task.getTargetId());
        return createTaskById(crmTask.getId(), corNetwork);
    }

    public void deleteCustomerTask(CRMAccount crmAccount, Long taskId, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClassAndNetwork(crmAccount.getId(), taskId, CRMTask.class.getName(), corNetwork);
        deleteTaskById(task.getTargetId(), corNetwork);
        corAssociationRepository.delete(task);
    }

    public CrmTaskPT updateCustomerTask(CRMAccount crmAccount, CrmTaskPT crmTask, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClassAndNetwork(crmAccount.getId(), crmTask.getId(), CRMTask.class.getName(), corNetwork);
        CRMTask task1 = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(crmTask));
        return customCRMTaskMapper.createCrmTask(task1, new CoreManagedUserPT(), new CoreManagedUserPT());
    }

    public List<CrmTaskPT> getTasksAssociatedWithContact(CRMContact crmContact, CORNetwork corNetwork) {
        List<CORAssociation> crmContactTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmContact.getId(), CRMTask.class.getName(), corNetwork);
        List<Long> listTaskId = crmContactTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        return listTaskId.stream().map(id -> createTaskById(id, corNetwork)).collect(toList());
    }

    public CrmTaskPT getTaskAssociatedWithContact(CRMContact crmContact, Long taskId, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClassAndNetwork(crmContact.getId(), taskId, CRMTask.class.getName(), corNetwork);
        CRMTask crmTask = crmTaskRepository.findOne(task.getId());
        return createTaskById(crmTask.getId(), corNetwork);
    }

    public void deleteContactTask(CRMContact crmContact, Long taskId, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClassAndNetwork(crmContact.getId(), taskId, CRMTask.class.getName(), corNetwork);
        deleteTaskById(task.getTargetId(), corNetwork);
        corAssociationRepository.delete(task);
    }

    public CrmTaskPT createTasksAssociatedWithContact(CRMContact crmContact, CrmTaskPT taskPT, CORNetwork
        corNetwork) {

        CRMTask crmTask = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(taskPT));
        corAssociationRepository.save(customCRMTaskMapper.createContactTaskAssociationEntity(crmContact, crmTask));
        corAssociationRepository.save(customCORUserMapper.createCRMAssignetToTaskAssociation(crmTask, customCORUserMapper.tranformUserDTO(taskPT.getAssignedTo()), corNetwork));
        corAssociationRepository.save(customCORUserMapper.createCRMCreatedByTaskAssociation(crmTask, customCORUserMapper.tranformUserDTO(taskPT.getCreatedBy()), corNetwork));

        return customCRMTaskMapper.createCrmTask(crmTask, new CoreManagedUserPT(), new CoreManagedUserPT());
    }

    public CrmTaskPT updateTaskContact(CRMContact crmContactt, CrmTaskPT crmTask, CORNetwork corNetwork) {
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClassAndNetwork(crmContactt.getId(), crmTask.getId(), CRMTask.class.getName(), corNetwork);
        CRMTask task1 = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(crmTask));
        return customCRMTaskMapper.createCrmTask(task1, new CoreManagedUserPT(), new CoreManagedUserPT());

    }

    private CrmTaskPT createTaskById(Long id, CORNetwork corNetwork) {
        CRMTask crmTask = crmTaskRepository.findOne(id);
        CORAssociation assignedTo = corAssociationRepository.findOneBySourceIdAndTargetClassAndNetworkAndName(id, User.class.getName(), corNetwork, ASSIGNED_TO);
        CORAssociation createdBy = corAssociationRepository.findOneBySourceIdAndTargetClassAndNetworkAndName(id, User.class.getName(), corNetwork, CREATED_BY);
        User assignedToUser = userRepository.findOne(assignedTo.getTargetId());
        User createdByUser = userRepository.findOne(createdBy.getTargetId());
        return customCRMTaskMapper.createCrmTask(crmTask, customCORUserMapper.transformUserEnity(assignedToUser), customCORUserMapper.transformUserEnity(createdByUser));

    }

    private void deleteTaskById(Long id, CORNetwork corNetwork) {
        CRMTask crmTask = crmTaskRepository.findOne(id);
        corAssociationRepository.deleteBySourceIdAndTargetClassAndNetworkAndName(id, User.class.getName(), corNetwork, ASSIGNED_TO);
        corAssociationRepository.deleteBySourceIdAndTargetClassAndNetworkAndName(id, User.class.getName(), corNetwork, CREATED_BY);
        crmTaskRepository.delete(crmTask);
    }

}
