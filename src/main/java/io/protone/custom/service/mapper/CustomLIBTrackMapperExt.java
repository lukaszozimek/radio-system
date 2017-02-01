package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibTrackPT;
import io.protone.domain.CORNetwork;
import io.protone.domain.LIBAlbum;
import io.protone.domain.LIBArtist;
import io.protone.domain.LIBTrack;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity LIBTrack and its DTO LIBTrackDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomLIBTrackMapperExt {

    @Mapping(source = "album.id", target = "albumId")
    @Mapping(source = "artist.id", target = "artistId")
    @Mapping(source = "network.id", target = "networkId")
    LibTrackPT DB2DTO(LIBTrack lIBTrack);

    List<LibTrackPT> DBs2DTOs(List<LIBTrack> lIBTracks);

    @Mapping(source = "albumId", target = "album")
    @Mapping(source = "artistId", target = "artist")
    @Mapping(source = "networkId", target = "network")
    LIBTrack DTO2DB(LibTrackPT lIBTrackDTO);

    List<LIBTrack> DTOs2DBs(List<LibTrackPT> lIBTrackDTOs);

    default LIBAlbum mapLIBAlbum(Long id) {
        if (id == null) {
            return null;
        }
        LIBAlbum lIBAlbum = new LIBAlbum();
        lIBAlbum.setId(id);
        return lIBAlbum;
    }

    default LIBArtist mapLIBArtist(Long id) {
        if (id == null) {
            return null;
        }
        LIBArtist lIBArtist = new LIBArtist();
        lIBArtist.setId(id);
        return lIBArtist;
    }

    default CORNetwork mapCORNetwork(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
