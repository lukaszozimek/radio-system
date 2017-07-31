package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.domain.SchPlaylist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Playlist and its DTO PlaylistDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchPlaylistMapper extends SchEntityMapper<SchPlaylistDTO, SchPlaylist> {
    
    @Mapping(target = "emissions", ignore = true)
    SchPlaylist toEntity(SchPlaylistDTO playlistDTO);
    default SchPlaylist fromId(Long id) {
        if (id == null) {
            return null;
        }
        SchPlaylist playlist = new SchPlaylist();
        playlist.setId(id);
        return playlist;
    }
}
