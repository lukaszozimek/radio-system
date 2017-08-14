package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.mapper.LibMediaItemThinMapper;
import io.protone.scheduler.api.dto.SchEmissionDTO;
import io.protone.scheduler.domain.SchEmission;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper for the entity Emission and its DTO EmissionDTO.
 */
/*
FIXME: LibItem mapper refers to class in inner module which will become separate microservice in near future
 */
@Mapper(componentModel = "spring", uses = {SchPlaylistMapper.class, SchClockMapper.class, LibMediaItemThinMapper.class, SchQueueParamsMapper.class, SchTimeParamsMapper.class, SchBlockMapper.class,})
public interface SchEmissionMapper extends SchEntityMapper<SchEmissionDTO, SchEmission> {
    @AfterMapping
    default void schSchEmissionToSchEmissionAfterMapping(SchEmissionDTO dto, @MappingTarget SchEmission entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
