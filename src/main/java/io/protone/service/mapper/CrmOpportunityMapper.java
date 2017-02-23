package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CrmOpportunityDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CrmOpportunity and its DTO CrmOpportunityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CrmOpportunityMapper {

    @Mapping(source = "stage.id", target = "stageId")
    @Mapping(source = "keeper.id", target = "keeperId")
    @Mapping(source = "contact.id", target = "contactId")
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "lead.id", target = "leadId")
    @Mapping(source = "network.id", target = "networkId")
    CrmOpportunityDTO crmOpportunityToCrmOpportunityDTO(CrmOpportunity crmOpportunity);

    List<CrmOpportunityDTO> crmOpportunitiesToCrmOpportunityDTOs(List<CrmOpportunity> crmOpportunities);

    @Mapping(source = "stageId", target = "stage")
    @Mapping(source = "keeperId", target = "keeper")
    @Mapping(source = "contactId", target = "contact")
    @Mapping(source = "accountId", target = "account")
    @Mapping(source = "leadId", target = "lead")
    @Mapping(source = "networkId", target = "network")
    @Mapping(target = "tasks", ignore = true)
    CrmOpportunity crmOpportunityDTOToCrmOpportunity(CrmOpportunityDTO crmOpportunityDTO);

    List<CrmOpportunity> crmOpportunityDTOsToCrmOpportunities(List<CrmOpportunityDTO> crmOpportunityDTOs);

    default CrmStage crmStageFromId(Long id) {
        if (id == null) {
            return null;
        }
        CrmStage crmStage = new CrmStage();
        crmStage.setId(id);
        return crmStage;
    }

    default CorUser corUserFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorUser corUser = new CorUser();
        corUser.setId(id);
        return corUser;
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

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
