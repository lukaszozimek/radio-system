package io.protone.crm.mapper;

import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorAddressMapper;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.crm.api.dto.CrmOpportunityDTO;
import io.protone.crm.api.dto.thin.CrmOpportunityThinDTO;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.domain.CrmContact;
import io.protone.crm.domain.CrmLead;
import io.protone.crm.domain.CrmOpportunity;
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
    @Mapping(source = "contact.shortName", target = "contactId")
    @Mapping(source = "account.shortName", target = "accountId")
    @Mapping(source = "lead.shortname", target = "leadId")
    CrmOpportunityDTO DB2DTO(CrmOpportunity crmOpportunity);

    List<CrmOpportunityDTO> DBs2DTOs(List<CrmOpportunity> crmOpportunities);

    @Mapping(source = "stage", target = "stage")
    @Mapping(source = "keeper", target = "opportunityOwner")
    @Mapping(source = "contact.shortName", target = "contactId")
    @Mapping(source = "account.shortName", target = "accountId")
    @Mapping(source = "lead.shortname", target = "leadId")
    CrmOpportunityThinDTO DB2ThinDTO(CrmOpportunity crmOpportunity);


    List<CrmOpportunityThinDTO> DBs2ThinDTOs(List<CrmOpportunity> crmOpportunities);


    @Mapping(source = "stage", target = "stage")
    @Mapping(source = "opportunityOwner", target = "keeper")
    @Mapping(source = "contactId", target = "contact", ignore = true)
    @Mapping(source = "accountId", target = "account", ignore = true)
    @Mapping(source = "leadId", target = "lead", ignore = true)
    CrmOpportunity DTO2DB(CrmOpportunityDTO crmOpportunityDTO, @Context CorNetwork corNetwork);

    @AfterMapping
    default void crmOpportunityPTToCrmOpportunityAfterMapping(CrmOpportunityDTO dto, @MappingTarget CrmOpportunity entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

    default List<CrmOpportunity> DTOs2DBs(List<CrmOpportunityDTO> crmOpportunityDTO, CorNetwork corNetwork) {
        List<CrmOpportunity> crmOpportunities = new ArrayList<>();
        if (crmOpportunityDTO.isEmpty() || crmOpportunityDTO == null) {
            return null;
        }
        for (CrmOpportunityDTO dto : crmOpportunityDTO) {
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
