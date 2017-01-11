package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CRMTaskDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CRMTask and its DTO CRMTaskDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CRMTaskMapper {

    @Mapping(source = "network.id", target = "networkId")
    CRMTaskDTO cRMTaskToCRMTaskDTO(CRMTask cRMTask);

    List<CRMTaskDTO> cRMTasksToCRMTaskDTOs(List<CRMTask> cRMTasks);

    @Mapping(source = "networkId", target = "network")
    CRMTask cRMTaskDTOToCRMTask(CRMTaskDTO cRMTaskDTO);

    List<CRMTask> cRMTaskDTOsToCRMTasks(List<CRMTaskDTO> cRMTaskDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
