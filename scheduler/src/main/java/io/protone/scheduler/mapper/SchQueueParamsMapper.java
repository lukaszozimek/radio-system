package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchQueueParamsDTO;
import io.protone.scheduler.domain.SchQueueParams;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity QueueParams and its DTO QueueParamsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchQueueParamsMapper extends SchEntityMapper<SchQueueParamsDTO, SchQueueParams> {
}
