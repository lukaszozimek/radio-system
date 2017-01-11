package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CRMStageDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CRMStage and its DTO CRMStageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CRMStageMapper {

    @Mapping(source = "network.id", target = "networkId")
    CRMStageDTO cRMStageToCRMStageDTO(CRMStage cRMStage);

    List<CRMStageDTO> cRMStagesToCRMStageDTOs(List<CRMStage> cRMStages);

    @Mapping(source = "networkId", target = "network")
    CRMStage cRMStageDTOToCRMStage(CRMStageDTO cRMStageDTO);

    List<CRMStage> cRMStageDTOsToCRMStages(List<CRMStageDTO> cRMStageDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
