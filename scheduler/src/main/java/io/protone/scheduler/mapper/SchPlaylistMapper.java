package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.domain.SchPlaylist;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity Playlist and its DTO PlaylistDTO.
 */
@Mapper(componentModel = "spring", uses = {SchEmissionMapper.class})
public interface SchPlaylistMapper {

    SchPlaylist toEntity(SchPlaylistDTO dto);

    SchPlaylistDTO toDto(SchPlaylist entity);

    List<SchPlaylist> toEntity(List<SchPlaylistDTO> dtoList);

    List<SchPlaylistDTO> toDto(List<SchPlaylist> entityList);
}
