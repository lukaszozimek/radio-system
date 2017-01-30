package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibMediaItemPT;
import io.protone.domain.*;
import io.protone.service.dto.LIBMediaItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity LIBMediaItem and its DTO LIBMediaItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomLIBMediaItemMapper {

    @Mapping(source = "library.id", target = "libraryId")
    @Mapping(source = "label.id", target = "labelId")
    @Mapping(source = "artist.id", target = "artistId")
    @Mapping(source = "album.id", target = "albumId")
    @Mapping(source = "track.id", target = "trackId")
    LibMediaItemPT lIBMediaItemToLIBMediaItemPT(LIBMediaItem lIBMediaItem);

    List<LibMediaItemPT> lIBMediaItemsToLIBMediaItemPTs(List<LIBMediaItem> lIBMediaItems);

    @Mapping(source = "libraryId", target = "library")
    @Mapping(source = "labelId", target = "label")
    @Mapping(source = "artistId", target = "artist")
    @Mapping(source = "albumId", target = "album")
    @Mapping(source = "trackId", target = "track")
    LIBMediaItem lIBMediaItemPTToLIBMediaItem(LibMediaItemPT LibMediaItemPT);

    List<LIBMediaItem> lIBMediaItemPTsToLIBMediaItems(List<LibMediaItemPT> lIBMediaItemDTOs);

    default LIBLibrary lIBLibraryFromId(Long id) {
        if (id == null) {
            return null;
        }
        LIBLibrary lIBLibrary = new LIBLibrary();
        lIBLibrary.setId(id);
        return lIBLibrary;
    }

    default LIBLabel lIBLabelFromId(Long id) {
        if (id == null) {
            return null;
        }
        LIBLabel lIBLabel = new LIBLabel();
        lIBLabel.setId(id);
        return lIBLabel;
    }

    default LIBArtist lIBArtistFromId(Long id) {
        if (id == null) {
            return null;
        }
        LIBArtist lIBArtist = new LIBArtist();
        lIBArtist.setId(id);
        return lIBArtist;
    }

    default LIBAlbum lIBAlbumFromId(Long id) {
        if (id == null) {
            return null;
        }
        LIBAlbum lIBAlbum = new LIBAlbum();
        lIBAlbum.setId(id);
        return lIBAlbum;
    }

    default LIBTrack lIBTrackFromId(Long id) {
        if (id == null) {
            return null;
        }
        LIBTrack lIBTrack = new LIBTrack();
        lIBTrack.setId(id);
        return lIBTrack;
    }
}
