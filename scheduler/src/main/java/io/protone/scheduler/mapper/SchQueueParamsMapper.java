package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchQueueParamsDTO;
import io.protone.scheduler.domain.SchQueueParams;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity QueueParams and its DTO QueueParamsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchQueueParamsMapper {

    SchQueueParams toEntity(SchQueueParamsDTO dto);

    SchQueueParamsDTO toDto(SchQueueParams entity);

    List<SchQueueParams> toEntity(List<SchQueueParamsDTO> dtoList);

    List<SchQueueParamsDTO> toDto(List<SchQueueParams> entityList);
}
