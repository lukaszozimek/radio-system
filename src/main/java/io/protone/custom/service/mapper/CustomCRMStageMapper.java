package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfCrmStagePT;
import io.protone.domain.CORNetwork;
import io.protone.domain.CRMStage;
import io.protone.service.dto.CRMStageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CRMStage and its DTO CRMStageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCRMStageMapper {

    ConfCrmStagePT cRMStageToCRMStageDTO(CRMStage cRMStage);

    List<ConfCrmStagePT> cRMStagesToCRMStageDTOs(List<CRMStage> cRMStages);

    CRMStage cRMStageDTOToCRMStage(ConfCrmStagePT cRMStageDTO);

    List<CRMStage> cRMStageDTOsToCRMStages(List<ConfCrmStagePT> cRMStageDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
