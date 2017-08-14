package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchEventDTO;
import io.protone.scheduler.domain.SchEvent;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper for the entity Grid and its DTO GridDTO.
 */
@Mapper(componentModel = "spring", uses = {SchEmissionMapper.class,})
public interface SchEventMapper extends SchEntityMapper<SchEventDTO, SchEvent> {
    @AfterMapping
    default void schSchEventDTOToSchEventnAfterMapping(SchEventDTO dto, @MappingTarget SchEvent entity,  @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
