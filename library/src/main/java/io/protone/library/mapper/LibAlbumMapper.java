package io.protone.library.mapper;


import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorImageItem;
import io.protone.library.api.dto.LibAlbumDTO;
import io.protone.library.domain.LibAlbum;
import io.protone.library.domain.LibArtist;
import io.protone.library.domain.LibLabel;
import io.protone.library.domain.enumeration.LibAlbumTypeEnum;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity LibAlbum and its DTO LibAlbumDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibAlbumMapper {

    @Mapping(source = "label.id", target = "labelId")
    @Mapping(source = "mainImage.publicUrl", target = "publicUrl")
    @Mapping(source = "artist.id", target = "artistId")
    LibAlbumDTO DB2DTO(LibAlbum db);

    List<LibAlbumDTO> DBs2DTOs(List<LibAlbum> dbs);

    @Mapping(source = "labelId", target = "label")
    @Mapping(source = "artistId", target = "artist")
    LibAlbum DTO2DB(LibAlbumDTO dto, @Context CorChannel network);

    default List<LibAlbum> DTOs2DBs(List<LibAlbumDTO> dtos, @Context CorChannel network) {
        List<LibAlbum> libAlbums = new ArrayList<>();
        if (dtos.isEmpty() || dtos == null) {
            return null;
        }
        for (LibAlbumDTO dto : dtos) {
            libAlbums.add(DTO2DB(dto, network));
        }
        return libAlbums;
    }

    @AfterMapping
    default void libAlbumPTToLibAlbumAfterMapping(LibAlbumDTO dto, @MappingTarget LibAlbum entity, @Context CorChannel corNetwork) {
        entity.setChannel(corNetwork);
    }

    default CorImageItem mapCorImageItem(Long id) {
        if (id == null) {
            return null;
        }
        CorImageItem lIBImageItem = new CorImageItem();
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

    default LibAlbumDTO.AlbumTypeEnum mapAlbumTypeEnum(LibAlbumTypeEnum value) {
        if (value != null) {
            if (value.compareTo(LibAlbumTypeEnum.AT_ALBUM) == 0) {
                return LibAlbumDTO.AlbumTypeEnum.ALBUM;
            } else if (value.compareTo(LibAlbumTypeEnum.AT_COMPILATION) == 0) {
                return LibAlbumDTO.AlbumTypeEnum.COMPILATION;
            } else if (value.compareTo(LibAlbumTypeEnum.AT_SINGLE) == 0) {
                return LibAlbumDTO.AlbumTypeEnum.SINGLE;
            } else {
                return LibAlbumDTO.AlbumTypeEnum.OTHER;
            }
        }
        return null;
    }

    default LibAlbumTypeEnum mapLibAlbumTypeEnum(LibAlbumDTO.AlbumTypeEnum value) {
        if (value != null) {
            if (value.compareTo(LibAlbumDTO.AlbumTypeEnum.ALBUM) == 0) {
                return LibAlbumTypeEnum.AT_ALBUM;
            } else if (value.compareTo(LibAlbumDTO.AlbumTypeEnum.COMPILATION) == 0) {
                return LibAlbumTypeEnum.AT_COMPILATION;
            } else if (value.compareTo(LibAlbumDTO.AlbumTypeEnum.SINGLE) == 0) {
                return LibAlbumTypeEnum.AT_SINGLE;
            } else {
                return LibAlbumTypeEnum.AT_OTHER;
            }
        }
        return null;
    }
}
