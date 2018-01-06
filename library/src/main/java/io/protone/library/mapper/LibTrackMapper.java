package io.protone.library.mapper;


import io.protone.core.domain.CorNetwork;
import io.protone.library.api.dto.LibTrackDTO;
import io.protone.library.domain.LibAlbum;
import io.protone.library.domain.LibArtist;
import io.protone.library.domain.LibTrack;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity LibTrack and its DTO LibTrackDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibTrackMapper {

    @Mapping(source = "album.id", target = "albumId")
    @Mapping(source = "artist.id", target = "artistId")
    LibTrackDTO DB2DTO(LibTrack lIBTrack);

    List<LibTrackDTO> DBs2DTOs(List<LibTrack> lIBTracks);

    @Mapping(source = "albumId", target = "album")
    @Mapping(source = "artistId", target = "artist")
    LibTrack DTO2DB(LibTrackDTO lIBTrackDTO);

    List<LibTrack> DTOs2DBs(List<LibTrackDTO> lIBTrackDTOs);

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
