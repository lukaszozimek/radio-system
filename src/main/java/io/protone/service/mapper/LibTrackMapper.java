package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LibTrackDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LibTrack and its DTO LibTrackDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibTrackMapper {

    @Mapping(source = "album.id", target = "albumId")
    @Mapping(source = "artist.id", target = "artistId")
    @Mapping(source = "network.id", target = "networkId")
    LibTrackDTO libTrackToLibTrackDTO(LibTrack libTrack);

    List<LibTrackDTO> libTracksToLibTrackDTOs(List<LibTrack> libTracks);

    @Mapping(source = "albumId", target = "album")
    @Mapping(source = "artistId", target = "artist")
    @Mapping(source = "networkId", target = "network")
    LibTrack libTrackDTOToLibTrack(LibTrackDTO libTrackDTO);

    List<LibTrack> libTrackDTOsToLibTracks(List<LibTrackDTO> libTrackDTOs);

    default LibAlbum libAlbumFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibAlbum libAlbum = new LibAlbum();
        libAlbum.setId(id);
        return libAlbum;
    }

    default LibArtist libArtistFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibArtist libArtist = new LibArtist();
        libArtist.setId(id);
        return libArtist;
    }

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
