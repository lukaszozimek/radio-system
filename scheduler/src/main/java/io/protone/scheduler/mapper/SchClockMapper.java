package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchClockDTO;
import io.protone.scheduler.domain.SchClock;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper for the entity Clock and its DTO ClockDTO.
 */
@Mapper(componentModel = "spring", uses = { SchEmissionMapper.class, SchQueueParamsMapper.class, SchTimeParamsMapper.class,})
public interface SchClockMapper extends SchEntityMapper<SchClockDTO, SchClock> {
    @AfterMapping
    default void schClockDTOToSchClockAfterMapping(SchClockDTO dto, @MappingTarget SchClock entity,  @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
