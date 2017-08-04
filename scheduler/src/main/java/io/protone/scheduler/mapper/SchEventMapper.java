package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchClockThinDTO;
import io.protone.scheduler.api.dto.SchEventDTO;
import io.protone.scheduler.api.dto.SchGridDTO;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchEvent;
import io.protone.scheduler.domain.SchGrid;
import org.mapstruct.Mapper;

import java.util.Map;
import java.util.Set;

/**
 * Mapper for the entity Grid and its DTO GridDTO.
 */
@Mapper(componentModel = "spring", uses = {SchScheduleMapper.class,})
public interface SchEventMapper extends SchEntityMapper<SchEventDTO, SchEvent> {
}
