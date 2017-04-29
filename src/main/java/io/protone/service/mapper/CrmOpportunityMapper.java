package io.protone.service.mapper;

import io.protone.custom.service.dto.CrmLeadPT;
import io.protone.custom.service.dto.CrmOpportunityPT;
import io.protone.domain.*;
import io.protone.service.mapper.CorAddressMapper;
import io.protone.service.mapper.CorDictionaryMapper;
import io.protone.service.mapper.CrmTaskMapper;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 18.01.2017.
 */
@Mapper(componentModel = "spring", uses = {CrmTaskMapper.class, CorDictionaryMapper.class, CorAddressMapper.class})
public interface CrmOpportunityMapper {


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
    CrmOpportunity DTO2DB(CrmOpportunityPT crmOpportunityDTO, @Context CorNetwork corNetwork);

    @AfterMapping
    default void crmOpportunityPTToCrmOpportunityAfterMapping(CrmOpportunityPT dto, @MappingTarget CrmOpportunity entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

    default List<CrmOpportunity> DTOs2DBs(List<CrmOpportunityPT> crmOpportunityDTO, CorNetwork corNetwork) {
        List<CrmOpportunity> crmOpportunities = new ArrayList<>();
        if (crmOpportunityDTO.isEmpty() || crmOpportunityDTO == null) {
            return null;
        }
        for (CrmOpportunityPT dto : crmOpportunityDTO) {
            crmOpportunities.add(DTO2DB(dto, corNetwork));
        }
        return crmOpportunities;
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


}
