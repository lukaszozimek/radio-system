package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchScheduleDTO;
import io.protone.scheduler.api.dto.thin.SchScheduleThinDTO;
import io.protone.scheduler.domain.SchSchedule;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper for the entity Schedule and its DTO ScheduleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchScheduleMapper extends SchEntityMapper<SchScheduleDTO, SchSchedule> {
    List<SchScheduleThinDTO> DBs2ThinDTOs(List<SchSchedule> schClockList);
    @AfterMapping
    default void schScheduleDTOToSchSchedulenAfterMapping(SchScheduleDTO dto, @MappingTarget SchSchedule entity,  @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
