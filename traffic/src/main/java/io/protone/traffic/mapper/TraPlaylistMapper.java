package io.protone.traffic.mapper;


import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.traffic.api.dto.TraPlaylistDTO;
import io.protone.traffic.api.dto.thin.TraPlaylistThinDTO;
import io.protone.traffic.domain.TraPlaylist;
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

    @Mapping(source = "playlistDate", target = "playlistDate")
    TraPlaylistThinDTO DB2ThinDTO(TraPlaylist traPlaylist);


    List<TraPlaylistThinDTO> DBs2ThinDTOs(List<TraPlaylist> traPlaylists);

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
