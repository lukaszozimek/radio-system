package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.SchPlaylistDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SchPlaylist and its DTO SchPlaylistDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchPlaylistMapper {

    @Mapping(source = "channel.id", target = "channelId")
    SchPlaylistDTO schPlaylistToSchPlaylistDTO(SchPlaylist schPlaylist);

    List<SchPlaylistDTO> schPlaylistsToSchPlaylistDTOs(List<SchPlaylist> schPlaylists);

    @Mapping(source = "channelId", target = "channel")
    SchPlaylist schPlaylistDTOToSchPlaylist(SchPlaylistDTO schPlaylistDTO);

    List<SchPlaylist> schPlaylistDTOsToSchPlaylists(List<SchPlaylistDTO> schPlaylistDTOs);

    default CorChannel corChannelFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorChannel corChannel = new CorChannel();
        corChannel.setId(id);
        return corChannel;
    }
}
