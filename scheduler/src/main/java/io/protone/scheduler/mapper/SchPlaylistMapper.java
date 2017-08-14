package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.domain.SchPlaylist;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper for the entity Playlist and its DTO PlaylistDTO.
 */
@Mapper(componentModel = "spring", uses = {SchEmissionMapper.class})
public interface SchPlaylistMapper extends SchEntityMapper<SchPlaylistDTO, SchPlaylist> {
    @AfterMapping
    default void schPlaylistDTOToSchPlaylistnAfterMapping(SchPlaylistDTO dto, @MappingTarget SchPlaylist entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
