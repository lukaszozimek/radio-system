package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibMediaItemPT;
import io.protone.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity LibMediaItem and its DTO LibMediaItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomLibMediaItemMapper {

    @Mapping(source = "library.id", target = "libraryId")
    @Mapping(source = "label.id", target = "labelId")
    @Mapping(source = "artist.id", target = "artistId")
    @Mapping(source = "album.id", target = "albumId")
    @Mapping(source = "track.id", target = "trackId")
    LibMediaItemPT lIBMediaItemToLibMediaItemPT(LibMediaItem lIBMediaItem);

    List<LibMediaItemPT> lIBMediaItemsToLibMediaItemPTs(List<LibMediaItem> lIBMediaItems);

    @Mapping(source = "libraryId", target = "library")
    @Mapping(source = "labelId", target = "label")
    @Mapping(source = "artistId", target = "artist")
    @Mapping(source = "albumId", target = "album")
    @Mapping(source = "trackId", target = "track")
    LibMediaItem lIBMediaItemPTToLibMediaItem(LibMediaItemPT LibMediaItemPT);

    List<LibMediaItem> lIBMediaItemPTsToLibMediaItems(List<LibMediaItemPT> lIBMediaItemDTOs);

    default LibLibrary lIBLibraryFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibLibrary lIBLibrary = new LibLibrary();
        lIBLibrary.setId(id);
        return lIBLibrary;
    }

    default LibLabel lIBLabelFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibLabel lIBLabel = new LibLabel();
        lIBLabel.setId(id);
        return lIBLabel;
    }

    default LibArtist lIBArtistFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibArtist lIBArtist = new LibArtist();
        lIBArtist.setId(id);
        return lIBArtist;
    }

    default LibAlbum lIBAlbumFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibAlbum lIBAlbum = new LibAlbum();
        lIBAlbum.setId(id);
        return lIBAlbum;
    }

    default LibTrack lIBTrackFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibTrack lIBTrack = new LibTrack();
        lIBTrack.setId(id);
        return lIBTrack;
    }
}
