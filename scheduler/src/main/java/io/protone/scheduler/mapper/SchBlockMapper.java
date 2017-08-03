package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchBlockDTO;
import io.protone.scheduler.domain.SchBlock;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Block and its DTO BlockDTO.
 */
@Mapper(componentModel = "spring", uses = {SchQueueParamsMapper.class, SchTimeParamsMapper.class,})
public interface SchBlockMapper extends SchEntityMapper<SchBlockDTO, SchBlock> {
}
