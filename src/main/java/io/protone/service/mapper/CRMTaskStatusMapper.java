package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CRMTaskStatusDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CRMTaskStatus and its DTO CRMTaskStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CRMTaskStatusMapper {

    @Mapping(source = "network.id", target = "networkId")
    CRMTaskStatusDTO cRMTaskStatusToCRMTaskStatusDTO(CRMTaskStatus cRMTaskStatus);

    List<CRMTaskStatusDTO> cRMTaskStatusesToCRMTaskStatusDTOs(List<CRMTaskStatus> cRMTaskStatuses);

    @Mapping(source = "networkId", target = "network")
    CRMTaskStatus cRMTaskStatusDTOToCRMTaskStatus(CRMTaskStatusDTO cRMTaskStatusDTO);

    List<CRMTaskStatus> cRMTaskStatusDTOsToCRMTaskStatuses(List<CRMTaskStatusDTO> cRMTaskStatusDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
