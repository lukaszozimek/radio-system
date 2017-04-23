package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibAlbumPT;
import io.protone.domain.*;
import io.protone.domain.enumeration.LibAlbumTypeEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity LibAlbum and its DTO LibAlbumPT.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomLibAlbumMapper {

    @Mapping(source = "cover.id", target = "coverId")
    @Mapping(source = "label.id", target = "labelId")
    @Mapping(source = "artist.id", target = "artistId")
    LibAlbumPT DB2DTO(LibAlbum db);

    List<LibAlbumPT> DBs2DTOs(List<LibAlbum> dbs);

    @Mapping(source = "coverId", target = "cover")
    @Mapping(source = "labelId", target = "label")
    @Mapping(source = "artistId", target = "artist")
    LibAlbum DTO2DB(LibAlbumPT dto);

    List<LibAlbum> DTOs2DBs(List<LibAlbumPT> dtos);

    default LibImageItem mapLIBImage(Long id) {
        if (id == null) {
            return null;
        }
        LibImageItem lIBImageItem = new LibImageItem();
        lIBImageItem.setId(id);
        return lIBImageItem;
    }

    default LibLabel mapLibLabel(Long id) {
        if (id == null) {
            return null;
        }
        LibLabel lIBLabel = new LibLabel();
        lIBLabel.setId(id);
        return lIBLabel;
    }

    default LibArtist mapLibArtist(Long id) {
        if (id == null) {
            return null;
        }
        LibArtist lIBArtist = new LibArtist();
        lIBArtist.setId(id);
        return lIBArtist;
    }

    default LibAlbumPT.AlbumTypeEnum mapAlbumTypeEnum(LibAlbumTypeEnum value) {

        if (value.compareTo(LibAlbumTypeEnum.AT_ALBUM) == 0)
            return LibAlbumPT.AlbumTypeEnum.ALBUM;
        else if (value.compareTo(LibAlbumTypeEnum.AT_COMPILATION) == 0)
            return LibAlbumPT.AlbumTypeEnum.COMPILATION;
        else if (value.compareTo(LibAlbumTypeEnum.AT_SINGLE) == 0)
            return LibAlbumPT.AlbumTypeEnum.SINGLE;
        else
            return LibAlbumPT.AlbumTypeEnum.OTHER;
    }

    default LibAlbumTypeEnum mapLibAlbumTypeEnum(LibAlbumPT.AlbumTypeEnum value) {
        if (value.compareTo(LibAlbumPT.AlbumTypeEnum.ALBUM) == 0)
            return LibAlbumTypeEnum.AT_ALBUM;
        else if (value.compareTo(LibAlbumPT.AlbumTypeEnum.COMPILATION) == 0)
            return LibAlbumTypeEnum.AT_COMPILATION;
        else if (value.compareTo(LibAlbumPT.AlbumTypeEnum.SINGLE) == 0)
            return LibAlbumTypeEnum.AT_SINGLE;
        else
            return LibAlbumTypeEnum.AT_OTHER;
    }
}
