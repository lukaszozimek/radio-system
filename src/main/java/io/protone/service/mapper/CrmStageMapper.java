package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CrmStageDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CrmStage and its DTO CrmStageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CrmStageMapper {

    @Mapping(source = "network.id", target = "networkId")
    CrmStageDTO crmStageToCrmStageDTO(CrmStage crmStage);

    List<CrmStageDTO> crmStagesToCrmStageDTOs(List<CrmStage> crmStages);

    @Mapping(source = "networkId", target = "network")
    CrmStage crmStageDTOToCrmStage(CrmStageDTO crmStageDTO);

    List<CrmStage> crmStageDTOsToCrmStages(List<CrmStageDTO> crmStageDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
