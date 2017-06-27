package io.protone.application.web.rest.mapper;

import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraPlaylist;
import io.protone.web.rest.dto.traffic.TraPlaylistDTO;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Mapper(componentModel = "spring", uses = {TraBlockMapper.class})
public interface TraPlaylistMapper {

    @Mapping(source = "playlistDate", target = "playlistDate")
    @Mapping(source = "playlists", target = "blocks")
    TraPlaylistDTO DB2DTO(TraPlaylist traPlaylist);

    List<TraPlaylistDTO> DBs2DTOs(List<TraPlaylist> traPlaylists);

    @Mapping(source = "playlistDate", target = "playlistDate")
    @Mapping(source = "blocks", target = "playlists")
    TraPlaylist DTO2DB(TraPlaylistDTO traPlaylistDTO, @Context CorNetwork network, @Context CorChannel corChannel);

    default List<TraPlaylist> DTOs2DBs(List<TraPlaylistDTO> traPlaylistDTOS, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<TraPlaylist> traPlaylists = new ArrayList<>();
        if (traPlaylistDTOS.isEmpty() || traPlaylistDTOS == null) {
            return null;
        }
        for (TraPlaylistDTO dto : traPlaylistDTOS) {
            traPlaylists.add(DTO2DB(dto, network, corChannel));
        }
        return traPlaylists;
    }

    @AfterMapping
    default void traPlaylistDTOToTraPlaylistMapperAfterMapping(TraPlaylistDTO dto, @MappingTarget TraPlaylist entity, @Context CorNetwork corNetwork, @Context CorChannel corChannel) {
        entity.setNetwork(corNetwork);
        entity.setChannel(corChannel);
    }
}
