package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibAlbumPT;
import io.protone.domain.*;
import io.protone.domain.enumeration.LIBAlbumTypeEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity LIBAlbum and its DTO LibAlbumPT.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomLIBAlbumMapper {

    @Mapping(source = "cover.id", target = "coverId")
    @Mapping(source = "label.id", target = "labelId")
    @Mapping(source = "artist.id", target = "artistId")
    @Mapping(source = "network.id", target = "networkId")
    LibAlbumPT DB2DTO(LIBAlbum db);

    List<LibAlbumPT> DBs2DTOs(List<LIBAlbum> dbs);

    @Mapping(source = "coverId", target = "cover")
    @Mapping(source = "labelId", target = "label")
    @Mapping(source = "artistId", target = "artist")
    @Mapping(source = "networkId", target = "network")
    LIBAlbum DTO2DB(LibAlbumPT dto);

    List<LIBAlbum> DTOs2DBs(List<LibAlbumPT> dtos);

    default LIBImageItem mapLIBImage(Long id) {
        if (id == null) {
            return null;
        }
        LIBImageItem lIBImageItem = new LIBImageItem();
        lIBImageItem.setId(id);
        return lIBImageItem;
    }

    default LIBLabel mapLIBLabel(Long id) {
        if (id == null) {
            return null;
        }
        LIBLabel lIBLabel = new LIBLabel();
        lIBLabel.setId(id);
        return lIBLabel;
    }

    default LIBArtist mapLIBArtist (Long id) {
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

    default LibAlbumPT.AlbumTypeEnum mapAlbumTypeEnum(LIBAlbumTypeEnum value) {

        if (value.compareTo(LIBAlbumTypeEnum.AT_ALBUM) == 0)
            return LibAlbumPT.AlbumTypeEnum.ALBUM;
        else if (value.compareTo(LIBAlbumTypeEnum.AT_COMPILATION) == 0)
            return LibAlbumPT.AlbumTypeEnum.COMPILATION;
        else if (value.compareTo(LIBAlbumTypeEnum.AT_SINGLE) == 0)
            return LibAlbumPT.AlbumTypeEnum.SINGLE;
        else
            return LibAlbumPT.AlbumTypeEnum.OTHER;
    }

    default LIBAlbumTypeEnum mapLIBAlbumTypeEnum(LibAlbumPT.AlbumTypeEnum value) {

        if (value.compareTo(LibAlbumPT.AlbumTypeEnum.ALBUM) == 0)
            return LIBAlbumTypeEnum.AT_ALBUM;
        else if (value.compareTo(LibAlbumPT.AlbumTypeEnum.COMPILATION) == 0)
            return LIBAlbumTypeEnum.AT_COMPILATION;
        else if (value.compareTo(LibAlbumPT.AlbumTypeEnum.SINGLE) == 0)
            return LIBAlbumTypeEnum.AT_SINGLE;
        else
            return LIBAlbumTypeEnum.AT_OTHER;
    }
}
