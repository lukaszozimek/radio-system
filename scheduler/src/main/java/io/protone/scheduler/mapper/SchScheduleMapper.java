package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchScheduleDTO;
import io.protone.scheduler.domain.SchSchedule;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity Schedule and its DTO ScheduleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchScheduleMapper {

    SchSchedule toEntity(SchScheduleDTO dto);

    SchScheduleDTO toDto(SchSchedule entity);

    List<SchSchedule> toEntity(List<SchScheduleDTO> dtoList);

    List<SchScheduleDTO> toDto(List<SchSchedule> entityList);
}
