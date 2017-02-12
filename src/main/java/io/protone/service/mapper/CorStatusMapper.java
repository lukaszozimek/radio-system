package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CorStatusDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CorStatus and its DTO CorStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorStatusMapper {

    CorStatusDTO corStatusToCorStatusDTO(CorStatus corStatus);

    List<CorStatusDTO> corStatusesToCorStatusDTOs(List<CorStatus> corStatuses);

    CorStatus corStatusDTOToCorStatus(CorStatusDTO corStatusDTO);

    List<CorStatus> corStatusDTOsToCorStatuses(List<CorStatusDTO> corStatusDTOs);
}
