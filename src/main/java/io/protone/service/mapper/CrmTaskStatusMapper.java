package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CrmTaskStatusDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CrmTaskStatus and its DTO CrmTaskStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CrmTaskStatusMapper {

    @Mapping(source = "network.id", target = "networkId")
    CrmTaskStatusDTO crmTaskStatusToCrmTaskStatusDTO(CrmTaskStatus crmTaskStatus);

    List<CrmTaskStatusDTO> crmTaskStatusesToCrmTaskStatusDTOs(List<CrmTaskStatus> crmTaskStatuses);

    @Mapping(source = "networkId", target = "network")
    CrmTaskStatus crmTaskStatusDTOToCrmTaskStatus(CrmTaskStatusDTO crmTaskStatusDTO);

    List<CrmTaskStatus> crmTaskStatusDTOsToCrmTaskStatuses(List<CrmTaskStatusDTO> crmTaskStatusDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
