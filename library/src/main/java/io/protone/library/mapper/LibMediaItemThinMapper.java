package io.protone.library.mapper;

import io.protone.core.mapper.CorPersonMapper;
import io.protone.core.mapper.CorPropertyValueMapper;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.library.domain.LibAlbum;
import io.protone.library.domain.LibArtist;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper for the entity MediaItem and its DTO MediaItemDTO.
 */
@Mapper(componentModel = "spring", uses = {LibAlbumMapper.class,
        LibArtistMapper.class,
        CorPersonMapper.class,
        LibLabelMapper.class,
        LibLibraryMediaMapper.class,
        LibMarkerMapper.class,
        CorPropertyValueMapper.class})
public interface LibMediaItemThinMapper {

    LibMediaItemThinDTO DB2DTO(LibMediaItem db);

    List<LibMediaItemThinDTO> DBs2DTOs(List<LibMediaItem> dbs);

    @AfterMapping
    default void libMediaItemToLibMediaItemThinDTOAfterMapping(@MappingTarget LibMediaItemThinDTO dto, LibMediaItem entity) {
        if (entity.getNetwork() != null && entity.getLibrary() != null) {
            dto.setStream("/api/v1/network/" + entity.getNetwork().getShortcut() + "/library/media/" + entity.getLibrary().getShortcut() + "/item/" + entity.getIdx() + "/stream");
        }
    }


    default String map(LibMediaLibrary value) {
        if (value == null) {
            return null;
        }
        return value.getShortcut();
    }

    default String map(LibAlbum value) {
        if (value == null) {
            return null;
        }
        return value.getName();
    }

    default String map(LibArtist value) {
        if (value == null) {
            return null;
        }
        return value.getName();
    }

    default java.lang.String map(io.protone.core.domain.CorTag value) {
        if (value == null) {
            return null;
        }
        return value.getTag();
    }
}
