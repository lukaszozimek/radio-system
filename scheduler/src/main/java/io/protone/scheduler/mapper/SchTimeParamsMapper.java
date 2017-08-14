package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchTimeParamsDTO;
import io.protone.scheduler.domain.SchTimeParams;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper for the entity TimeParams and its DTO TimeParamsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchTimeParamsMapper extends SchEntityMapper<SchTimeParamsDTO, SchTimeParams> {
    @AfterMapping
    default void schTimeParamsDTOToSchTimeParamsnAfterMapping(SchTimeParamsDTO dto, @MappingTarget SchTimeParams entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
