package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TraCampaingStatusDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TraCampaingStatus and its DTO TraCampaingStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraCampaingStatusMapper {

    TraCampaingStatusDTO traCampaingStatusToTraCampaingStatusDTO(TraCampaingStatus traCampaingStatus);

    List<TraCampaingStatusDTO> traCampaingStatusesToTraCampaingStatusDTOs(List<TraCampaingStatus> traCampaingStatuses);

    TraCampaingStatus traCampaingStatusDTOToTraCampaingStatus(TraCampaingStatusDTO traCampaingStatusDTO);

    List<TraCampaingStatus> traCampaingStatusDTOsToTraCampaingStatuses(List<TraCampaingStatusDTO> traCampaingStatusDTOs);
}
