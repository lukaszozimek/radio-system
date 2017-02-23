package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TraOrderStatusDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TraOrderStatus and its DTO TraOrderStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraOrderStatusMapper {

    TraOrderStatusDTO traOrderStatusToTraOrderStatusDTO(TraOrderStatus traOrderStatus);

    List<TraOrderStatusDTO> traOrderStatusesToTraOrderStatusDTOs(List<TraOrderStatus> traOrderStatuses);

    TraOrderStatus traOrderStatusDTOToTraOrderStatus(TraOrderStatusDTO traOrderStatusDTO);

    List<TraOrderStatus> traOrderStatusDTOsToTraOrderStatuses(List<TraOrderStatusDTO> traOrderStatusDTOs);
}
