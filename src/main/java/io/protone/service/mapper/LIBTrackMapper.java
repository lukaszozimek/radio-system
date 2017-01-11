package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LIBTrackDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LIBTrack and its DTO LIBTrackDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LIBTrackMapper {

    @Mapping(source = "album.id", target = "albumId")
    @Mapping(source = "artist.id", target = "artistId")
    @Mapping(source = "network.id", target = "networkId")
    LIBTrackDTO lIBTrackToLIBTrackDTO(LIBTrack lIBTrack);

    List<LIBTrackDTO> lIBTracksToLIBTrackDTOs(List<LIBTrack> lIBTracks);

    @Mapping(source = "albumId", target = "album")
    @Mapping(source = "artistId", target = "artist")
    @Mapping(source = "networkId", target = "network")
    LIBTrack lIBTrackDTOToLIBTrack(LIBTrackDTO lIBTrackDTO);

    List<LIBTrack> lIBTrackDTOsToLIBTracks(List<LIBTrackDTO> lIBTrackDTOs);

    default LIBAlbum lIBAlbumFromId(Long id) {
        if (id == null) {
            return null;
        }
        LIBAlbum lIBAlbum = new LIBAlbum();
        lIBAlbum.setId(id);
        return lIBAlbum;
    }

    default LIBArtist lIBArtistFromId(Long id) {
        if (id == null) {
            return null;
        }
        LIBArtist lIBArtist = new LIBArtist();
        lIBArtist.setId(id);
        return lIBArtist;
    }

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
