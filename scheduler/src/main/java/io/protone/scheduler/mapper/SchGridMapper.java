package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchClockThinDTO;
import io.protone.scheduler.api.dto.SchGridDTO;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchGrid;
import org.mapstruct.Mapper;

import java.util.Map;
import java.util.Set;

/**
 * Mapper for the entity Grid and its DTO GridDTO.
 */
@Mapper(componentModel = "spring", uses = {SchScheduleMapper.class,})
public interface SchGridMapper extends SchEntityMapper<SchGridDTO, SchGrid> {

    SchGridDTO toDto(SchGrid grid);

    SchGrid toEntity(SchGridDTO gridDTO);

    default SchGrid fromId(Long id) {
        if (id == null) {
            return null;
        }
        SchGrid grid = new SchGrid();
        grid.setId(id);
        return grid;
    }

    default Map<String, SchClockThinDTO> map(Set<SchClock> value) {
        return null;
    }

    default Set<SchClock> map(Map<String, SchClockThinDTO> value) {
        return null;
    }
}
