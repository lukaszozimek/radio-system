package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LIBAlbumDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LIBAlbum and its DTO LIBAlbumDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LIBAlbumMapper {

    @Mapping(source = "cover.id", target = "coverId")
    @Mapping(source = "label.id", target = "labelId")
    @Mapping(source = "artist.id", target = "artistId")
    @Mapping(source = "network.id", target = "networkId")
    LIBAlbumDTO lIBAlbumToLIBAlbumDTO(LIBAlbum lIBAlbum);

    List<LIBAlbumDTO> lIBAlbumsToLIBAlbumDTOs(List<LIBAlbum> lIBAlbums);

    @Mapping(source = "coverId", target = "cover")
    @Mapping(source = "labelId", target = "label")
    @Mapping(source = "artistId", target = "artist")
    @Mapping(source = "networkId", target = "network")
    LIBAlbum lIBAlbumDTOToLIBAlbum(LIBAlbumDTO lIBAlbumDTO);

    List<LIBAlbum> lIBAlbumDTOsToLIBAlbums(List<LIBAlbumDTO> lIBAlbumDTOs);

    default LIBImageItem lIBImageItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        LIBImageItem lIBImageItem = new LIBImageItem();
        lIBImageItem.setId(id);
        return lIBImageItem;
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

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
