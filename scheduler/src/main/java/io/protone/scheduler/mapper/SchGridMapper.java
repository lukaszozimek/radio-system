package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchClockThinDTO;
import io.protone.scheduler.api.dto.SchGridDTO;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchGrid;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Mapper for the entity Grid and its DTO GridDTO.
 */
@Mapper(componentModel = "spring", uses = {SchScheduleMapper.class,})
public interface SchGridMapper {

    SchGrid toEntity(SchGridDTO dto);

    SchGridDTO toDto(SchGrid entity);

    List<SchGrid> toEntity(List<SchGridDTO> dtoList);

    List<SchGridDTO> toDto(List<SchGrid> entityList);
}
