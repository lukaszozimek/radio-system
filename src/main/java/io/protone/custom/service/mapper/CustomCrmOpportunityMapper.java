package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CorDictionaryPT;
import io.protone.custom.service.dto.CrmOpportunityPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.dto.thin.CoreUserThinPT;
import io.protone.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

/**
 * Created by lukaszozimek on 18.01.2017.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCrmOpportunityMapper {


    @Mapping(source = "stage", target = "stage")
    @Mapping(source = "keeper", target = "opportunityOwner")
    @Mapping(source = "contact.id", target = "contactId")
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "lead.id", target = "leadId")
    CrmOpportunityPT DB2DTO(CrmOpportunity crmOpportunity);

    List<CrmOpportunityPT> DBs2DTOs(List<CrmOpportunity> crmOpportunities);

    @Mapping(source = "stage", target = "stage")
    @Mapping(source = "opportunityOwner", target = "keeper")
    @Mapping(source = "contactId", target = "contact")
    @Mapping(source = "accountId", target = "account")
    @Mapping(source = "leadId", target = "lead")
    CrmOpportunity DTO2DB(CrmOpportunityPT crmOpportunityDTO);

    List<CrmOpportunity> DTOs2DBs(List<CrmOpportunityPT> crmOpportunityDTOs);

    CorDictionary corDictionaryFromCorDictionaryPT(CorDictionaryPT coreUserThinPT);

    CorDictionaryPT corDictionaryPTFromCorDictionary(CorDictionary coreUserThinPT);


    CorUser corUserFromCoreUserThinPT(CoreUserThinPT coreUserThinPT);

    CoreUserThinPT coreUserThinPTFromCorUser(CorUser coreUserThinPT);

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

    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "assignedTo", target = "assignedTo")
    CrmTaskPT crmTaskPTFromCrmTask(CrmTask cORAddress);

    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "assignedTo", target = "assignedTo")
    CrmTask crmTaskFromCrmTaskPT(CrmTaskPT cORAddressDTO);


}
