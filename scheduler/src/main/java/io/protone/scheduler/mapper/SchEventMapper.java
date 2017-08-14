package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchEventDTO;
import io.protone.scheduler.domain.SchEvent;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity Grid and its DTO GridDTO.
 */
@Mapper(componentModel = "spring", uses = {SchEmissionMapper.class, SchScheduleMapper.class,})
public interface SchEventMapper {

    SchEvent toEntity(SchEventDTO dto);

    SchEventDTO toDto(SchEvent entity);

    List<SchEvent> toEntity(List<SchEventDTO> dtoList);

    List<SchEventDTO> toDto(List<SchEvent> entityList);
}
