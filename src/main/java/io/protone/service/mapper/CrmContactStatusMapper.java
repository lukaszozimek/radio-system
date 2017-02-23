package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CrmContactStatusDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CrmContactStatus and its DTO CrmContactStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CrmContactStatusMapper {

    @Mapping(source = "network.id", target = "networkId")
    CrmContactStatusDTO crmContactStatusToCrmContactStatusDTO(CrmContactStatus crmContactStatus);

    List<CrmContactStatusDTO> crmContactStatusesToCrmContactStatusDTOs(List<CrmContactStatus> crmContactStatuses);

    @Mapping(source = "networkId", target = "network")
    CrmContactStatus crmContactStatusDTOToCrmContactStatus(CrmContactStatusDTO crmContactStatusDTO);

    List<CrmContactStatus> crmContactStatusDTOsToCrmContactStatuses(List<CrmContactStatusDTO> crmContactStatusDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
