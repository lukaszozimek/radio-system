package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfCrmStagePT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmStage;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity CrmStage and its DTO CrmStageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCrmStageMapper {

    ConfCrmStagePT cRMStageToCrmStageDTO(CrmStage cRMStage);

    List<ConfCrmStagePT> cRMStagesToCrmStageDTOs(List<CrmStage> cRMStages);

    CrmStage cRMStageDTOToCrmStage(ConfCrmStagePT cRMStageDTO);

    List<CrmStage> cRMStageDTOsToCrmStages(List<ConfCrmStagePT> cRMStageDTOs);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
