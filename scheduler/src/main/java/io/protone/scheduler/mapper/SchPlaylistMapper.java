package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.api.dto.thin.SchPlaylistThinDTO;
import io.protone.scheduler.domain.SchPlaylist;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity Playlist and its DTO PlaylistDTO.
 */
@Mapper(componentModel = "spring", uses = {SchEmissionMapper.class})
public interface SchPlaylistMapper {

     SchPlaylist DTO2DB(SchPlaylistDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

     SchPlaylistDTO DB2DTO(SchPlaylist entity);

     List<SchPlaylistDTO> DBs2DTOs(List<SchPlaylist> entityList);

    default List<SchPlaylist> DTOs2DBs(List<SchPlaylistDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchPlaylist> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchPlaylistDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

    List<SchPlaylistThinDTO> DBs2ThinDTOs(List<SchPlaylist> schClockList);

    @AfterMapping
    default void schPlaylistDTOToSchPlaylistnAfterMapping(SchPlaylistDTO dto, @MappingTarget SchPlaylist entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
