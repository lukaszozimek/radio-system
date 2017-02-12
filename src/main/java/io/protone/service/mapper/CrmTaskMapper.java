package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CrmTaskDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CrmTask and its DTO CrmTaskDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CrmTaskMapper {

    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "assignedTo.id", target = "assignedToId")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "network.id", target = "networkId")
    @Mapping(source = "opportunity.id", target = "opportunityId")
    @Mapping(source = "contact.id", target = "contactId")
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "lead.id", target = "leadId")
    @Mapping(source = "tasks.id", target = "tasksId")
    CrmTaskDTO crmTaskToCrmTaskDTO(CrmTask crmTask);

    List<CrmTaskDTO> crmTasksToCrmTaskDTOs(List<CrmTask> crmTasks);

    @Mapping(source = "createdById", target = "createdBy")
    @Mapping(source = "assignedToId", target = "assignedTo")
    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "networkId", target = "network")
    @Mapping(source = "opportunityId", target = "opportunity")
    @Mapping(source = "contactId", target = "contact")
    @Mapping(source = "accountId", target = "account")
    @Mapping(source = "leadId", target = "lead")
    @Mapping(source = "tasksId", target = "tasks")
    CrmTask crmTaskDTOToCrmTask(CrmTaskDTO crmTaskDTO);

    List<CrmTask> crmTaskDTOsToCrmTasks(List<CrmTaskDTO> crmTaskDTOs);

    default CorUser corUserFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorUser corUser = new CorUser();
        corUser.setId(id);
        return corUser;
    }

    default CrmTaskStatus crmTaskStatusFromId(Long id) {
        if (id == null) {
            return null;
        }
        CrmTaskStatus crmTaskStatus = new CrmTaskStatus();
        crmTaskStatus.setId(id);
        return crmTaskStatus;
    }

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }

    default CrmOpportunity crmOpportunityFromId(Long id) {
        if (id == null) {
            return null;
        }
        CrmOpportunity crmOpportunity = new CrmOpportunity();
        crmOpportunity.setId(id);
        return crmOpportunity;
    }

    default CrmContact crmContactFromId(Long id) {
        if (id == null) {
            return null;
        }
        CrmContact crmContact = new CrmContact();
        crmContact.setId(id);
        return crmContact;
    }

    default CrmAccount crmAccountFromId(Long id) {
        if (id == null) {
            return null;
        }
        CrmAccount crmAccount = new CrmAccount();
        crmAccount.setId(id);
        return crmAccount;
    }

    default CrmLead crmLeadFromId(Long id) {
        if (id == null) {
            return null;
        }
        CrmLead crmLead = new CrmLead();
        crmLead.setId(id);
        return crmLead;
    }

    default CrmTask crmTaskFromId(Long id) {
        if (id == null) {
            return null;
        }
        CrmTask crmTask = new CrmTask();
        crmTask.setId(id);
        return crmTask;
    }
}
