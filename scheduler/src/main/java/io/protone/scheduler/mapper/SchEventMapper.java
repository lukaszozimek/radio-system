package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchEventDTO;
import io.protone.scheduler.domain.SchEvent;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Grid and its DTO GridDTO.
 */
@Mapper(componentModel = "spring", uses = {SchScheduleMapper.class,})
public interface SchEventMapper extends SchEntityMapper<SchEventDTO, SchEvent> {
}
