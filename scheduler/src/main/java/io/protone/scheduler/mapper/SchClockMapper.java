package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchClockDTO;
import io.protone.scheduler.domain.SchClock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Clock and its DTO ClockDTO.
 */
@Mapper(componentModel = "spring", uses = {SchGridMapper.class, SchQueueParamsMapper.class, SchTimeParamsMapper.class, })
public interface SchClockMapper extends SchEntityMapper<SchClockDTO, SchClock> {

    SchClockDTO toDto(SchClock clock);

    @Mapping(target = "blocks", ignore = true)
    @Mapping(target = "emissions", ignore = true)
    SchClock toEntity(SchClockDTO clockDTO);
    default SchClock fromId(Long id) {
        if (id == null) {
            return null;
        }
        SchClock clock = new SchClock();
        clock.setId(id);
        return clock;
    }
}
