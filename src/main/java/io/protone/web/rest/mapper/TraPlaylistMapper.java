package io.protone.web.rest.mapper;

import io.protone.domain.CorNetwork;
import io.protone.domain.TraDiscount;
import io.protone.domain.TraPlaylist;
import io.protone.web.rest.dto.traffic.TraDiscountDTO;
import io.protone.web.rest.dto.traffic.TraPlaylistDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Mapper(componentModel = "spring", uses = {TraBlockMapper.class})
public interface TraPlaylistMapper {
    @Mapping(source = "playlists", target = "blocks")
    TraPlaylistDTO DB2DTO(TraPlaylist traPlaylist);

    List<TraPlaylistDTO> DBs2DTOs(List<TraPlaylist> traPlaylists);
    @Mapping(source = "blocks", target = "playlists")
    TraPlaylist DTO2DB(TraPlaylistDTO traPlaylistDTO, @Context CorNetwork network);

    default List<TraPlaylist> DTOs2DBs(List<TraPlaylistDTO> traPlaylistDTOS, @Context CorNetwork network) {
        List<TraPlaylist> traPlaylists = new ArrayList<>();
        if (traPlaylistDTOS.isEmpty() || traPlaylistDTOS == null) {
            return null;
        }
        for (TraPlaylistDTO dto : traPlaylistDTOS) {
            traPlaylists.add(DTO2DB(dto, network));
        }
        return traPlaylists;
    }

    @AfterMapping
    default void confDiscountPTToTraDiscountAfterMapping(TraPlaylistDTO dto, @MappingTarget TraPlaylist entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
