package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchScheduleDTO;
import io.protone.scheduler.domain.SchSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Schedule and its DTO ScheduleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ScheduleMapper extends SchEntityMapper<SchScheduleDTO, SchSchedule> {
    
    @Mapping(target = "grids", ignore = true)
    SchSchedule toEntity(SchScheduleDTO scheduleDTO);
    default SchSchedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        SchSchedule schedule = new SchSchedule();
        schedule.setId(id);
        return schedule;
    }
}
