package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchBlockDTO;
import io.protone.scheduler.domain.SchBlock;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper for the entity Block and its DTO BlockDTO.
 */
@Mapper(componentModel = "spring", uses = {SchQueueParamsMapper.class, SchEmissionMapper.class, SchTimeParamsMapper.class,})
public interface SchBlockMapper extends SchEntityMapper<SchBlockDTO, SchBlock> {
    @AfterMapping
    default void schBlockDTOToSchBlockAfterMapping(SchBlockDTO dto, @MappingTarget SchBlock entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
