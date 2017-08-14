package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchQueueParamsDTO;
import io.protone.scheduler.domain.SchQueueParams;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper for the entity QueueParams and its DTO QueueParamsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchQueueParamsMapper extends SchEntityMapper<SchQueueParamsDTO, SchQueueParams> {
    @AfterMapping
    default void schQueueParamsDTOToSchQueueParamsnAfterMapping(SchQueueParamsDTO dto, @MappingTarget SchQueueParams entity,  @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
