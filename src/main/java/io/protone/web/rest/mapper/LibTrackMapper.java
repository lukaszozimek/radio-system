package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.LibPersonPT;
import io.protone.custom.service.dto.LibTrackPT;
import io.protone.domain.*;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity LibTrack and its DTO LibTrackDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibTrackMapper {

    @Mapping(source = "album.id", target = "albumId")
    @Mapping(source = "artist.id", target = "artistId")
    LibTrackPT DB2DTO(LibTrack lIBTrack);

    List<LibTrackPT> DBs2DTOs(List<LibTrack> lIBTracks);

    @Mapping(source = "albumId", target = "album")
    @Mapping(source = "artistId", target = "artist")
    LibTrack DTO2DB(LibTrackPT lIBTrackDTO, @Context CorNetwork network);

    default List<LibTrack> DTOs2DBs(List<LibTrackPT> lIBTrackDTOs, @Context CorNetwork network) {
        List<LibTrack> libTracks = new ArrayList<>();
        if (lIBTrackDTOs.isEmpty() || lIBTrackDTOs == null) {
            return null;
        }
        for (LibTrackPT dto : lIBTrackDTOs) {
            libTracks.add(DTO2DB(dto, network));
        }
        return libTracks;
    }

    @AfterMapping
    default void libTrackPTToLibTrackAfterMapping(LibTrackPT dto, @MappingTarget LibTrack entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

    default LibAlbum mapLibAlbum(Long id) {
        if (id == null) {
            return null;
        }
        LibAlbum lIBAlbum = new LibAlbum();
        lIBAlbum.setId(id);
        return lIBAlbum;
    }

    default LibArtist mapLibArtist(Long id) {
        if (id == null) {
            return null;
        }
        LibArtist lIBArtist = new LibArtist();
        lIBArtist.setId(id);
        return lIBArtist;
    }


}
