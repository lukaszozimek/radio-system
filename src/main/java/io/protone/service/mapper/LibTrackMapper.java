package io.protone.service.mapper;

import io.protone.custom.service.dto.LibTrackPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibAlbum;
import io.protone.domain.LibArtist;
import io.protone.domain.LibTrack;
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
    LibTrackPT DB2DTO(LibTrack lIBTrack);

    List<LibTrackPT> DBs2DTOs(List<LibTrack> lIBTracks);

    @Mapping(source = "albumId", target = "album")
    @Mapping(source = "artistId", target = "artist")
    LibTrack DTO2DB(LibTrackPT lIBTrackDTO);

    List<LibTrack> DTOs2DBs(List<LibTrackPT> lIBTrackDTOs);

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
