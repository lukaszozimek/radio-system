package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchClockDTO;
import io.protone.scheduler.domain.SchClock;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Clock and its DTO ClockDTO.
 */
@Mapper(componentModel = "spring", uses = {SchGridMapper.class, SchQueueParamsMapper.class, SchTimeParamsMapper.class,})
public interface SchClockMapper extends SchEntityMapper<SchClockDTO, SchClock> {
}
