package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LibMediaItemDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LibMediaItem and its DTO LibMediaItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibMediaItemMapper {

    @Mapping(source = "library.id", target = "libraryId")
    @Mapping(source = "label.id", target = "labelId")
    @Mapping(source = "artist.id", target = "artistId")
    @Mapping(source = "album.id", target = "albumId")
    @Mapping(source = "track.id", target = "trackId")
    LibMediaItemDTO libMediaItemToLibMediaItemDTO(LibMediaItem libMediaItem);

    List<LibMediaItemDTO> libMediaItemsToLibMediaItemDTOs(List<LibMediaItem> libMediaItems);

    @Mapping(source = "libraryId", target = "library")
    @Mapping(source = "labelId", target = "label")
    @Mapping(source = "artistId", target = "artist")
    @Mapping(source = "albumId", target = "album")
    @Mapping(source = "trackId", target = "track")
    LibMediaItem libMediaItemDTOToLibMediaItem(LibMediaItemDTO libMediaItemDTO);

    List<LibMediaItem> libMediaItemDTOsToLibMediaItems(List<LibMediaItemDTO> libMediaItemDTOs);

    default LibLibrary libLibraryFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibLibrary libLibrary = new LibLibrary();
        libLibrary.setId(id);
        return libLibrary;
    }

    default LibLabel libLabelFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibLabel libLabel = new LibLabel();
        libLabel.setId(id);
        return libLabel;
    }

    default LibArtist libArtistFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibArtist libArtist = new LibArtist();
        libArtist.setId(id);
        return libArtist;
    }

    default LibAlbum libAlbumFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibAlbum libAlbum = new LibAlbum();
        libAlbum.setId(id);
        return libAlbum;
    }

    default LibTrack libTrackFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibTrack libTrack = new LibTrack();
        libTrack.setId(id);
        return libTrack;
    }
}
