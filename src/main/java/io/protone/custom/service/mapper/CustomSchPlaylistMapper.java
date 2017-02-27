package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.SchPlaylistPT;
import io.protone.domain.CorChannel;
import io.protone.domain.SchPlaylist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity SchPlaylist and its DTO SchPlaylistDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomSchPlaylistMapper {

    @Mapping(source = "channel.id", target = "channelId")
    SchPlaylistPT DBToDTO(SchPlaylist schPlaylist);

    List<SchPlaylistPT> DBsToDTOs(List<SchPlaylist> schPlaylists);

    @Mapping(source = "channelId", target = "channel")
    SchPlaylist DTOToDB(SchPlaylistPT dto);

    List<SchPlaylist> DTOsToDBs(List<SchPlaylistPT> dtos);

    default CorChannel mapCorChannel(Long id) {
        if (id == null) {
            return null;
        }
        CorChannel corChannel = new CorChannel();
        corChannel.setId(id);
        return corChannel;
    }
}
