package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.domain.SchPlaylist;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Playlist and its DTO PlaylistDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchPlaylistMapper extends SchEntityMapper<SchPlaylistDTO, SchPlaylist> {
}
