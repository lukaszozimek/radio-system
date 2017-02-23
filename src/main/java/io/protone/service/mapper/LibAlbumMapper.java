package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LibAlbumDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LibAlbum and its DTO LibAlbumDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibAlbumMapper {

    @Mapping(source = "cover.id", target = "coverId")
    @Mapping(source = "label.id", target = "labelId")
    @Mapping(source = "artist.id", target = "artistId")
    @Mapping(source = "network.id", target = "networkId")
    LibAlbumDTO libAlbumToLibAlbumDTO(LibAlbum libAlbum);

    List<LibAlbumDTO> libAlbumsToLibAlbumDTOs(List<LibAlbum> libAlbums);

    @Mapping(source = "coverId", target = "cover")
    @Mapping(source = "labelId", target = "label")
    @Mapping(source = "artistId", target = "artist")
    @Mapping(source = "networkId", target = "network")
    LibAlbum libAlbumDTOToLibAlbum(LibAlbumDTO libAlbumDTO);

    List<LibAlbum> libAlbumDTOsToLibAlbums(List<LibAlbumDTO> libAlbumDTOs);

    default LibImageItem libImageItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibImageItem libImageItem = new LibImageItem();
        libImageItem.setId(id);
        return libImageItem;
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

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
