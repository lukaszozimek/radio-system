package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CrmLeadDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CrmLead and its DTO CrmLeadDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CrmLeadMapper {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "addres.id", target = "addresId")
    @Mapping(source = "leadStatus.id", target = "leadStatusId")
    @Mapping(source = "leadSource.id", target = "leadSourceId")
    @Mapping(source = "keeper.id", target = "keeperId")
    @Mapping(source = "industry.id", target = "industryId")
    @Mapping(source = "area.id", target = "areaId")
    @Mapping(source = "network.id", target = "networkId")
    CrmLeadDTO crmLeadToCrmLeadDTO(CrmLead crmLead);

    List<CrmLeadDTO> crmLeadsToCrmLeadDTOs(List<CrmLead> crmLeads);

    @Mapping(source = "personId", target = "person")
    @Mapping(source = "addresId", target = "addres")
    @Mapping(source = "leadStatusId", target = "leadStatus")
    @Mapping(source = "leadSourceId", target = "leadSource")
    @Mapping(source = "keeperId", target = "keeper")
    @Mapping(source = "industryId", target = "industry")
    @Mapping(source = "areaId", target = "area")
    @Mapping(source = "networkId", target = "network")
    @Mapping(target = "tasks", ignore = true)
    CrmLead crmLeadDTOToCrmLead(CrmLeadDTO crmLeadDTO);

    List<CrmLead> crmLeadDTOsToCrmLeads(List<CrmLeadDTO> crmLeadDTOs);

    default CorPerson corPersonFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorPerson corPerson = new CorPerson();
        corPerson.setId(id);
        return corPerson;
    }

    default CorAddress corAddressFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorAddress corAddress = new CorAddress();
        corAddress.setId(id);
        return corAddress;
    }

    default CrmLeadStatus crmLeadStatusFromId(Long id) {
        if (id == null) {
            return null;
        }
        CrmLeadStatus crmLeadStatus = new CrmLeadStatus();
        crmLeadStatus.setId(id);
        return crmLeadStatus;
    }

    default CrmLeadSource crmLeadSourceFromId(Long id) {
        if (id == null) {
            return null;
        }
        CrmLeadSource crmLeadSource = new CrmLeadSource();
        crmLeadSource.setId(id);
        return crmLeadSource;
    }

    default CorUser corUserFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorUser corUser = new CorUser();
        corUser.setId(id);
        return corUser;
    }

    default TraIndustry traIndustryFromId(Long id) {
        if (id == null) {
            return null;
        }
        TraIndustry traIndustry = new TraIndustry();
        traIndustry.setId(id);
        return traIndustry;
    }

    default CorArea corAreaFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorArea corArea = new CorArea();
        corArea.setId(id);
        return corArea;
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
