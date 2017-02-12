package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CrmLeadStatusDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CrmLeadStatus and its DTO CrmLeadStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CrmLeadStatusMapper {

    @Mapping(source = "network.id", target = "networkId")
    CrmLeadStatusDTO crmLeadStatusToCrmLeadStatusDTO(CrmLeadStatus crmLeadStatus);

    List<CrmLeadStatusDTO> crmLeadStatusesToCrmLeadStatusDTOs(List<CrmLeadStatus> crmLeadStatuses);

    @Mapping(source = "networkId", target = "network")
    CrmLeadStatus crmLeadStatusDTOToCrmLeadStatus(CrmLeadStatusDTO crmLeadStatusDTO);

    List<CrmLeadStatus> crmLeadStatusDTOsToCrmLeadStatuses(List<CrmLeadStatusDTO> crmLeadStatusDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
