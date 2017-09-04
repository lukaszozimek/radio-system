package io.protone.library.mapper;

import io.protone.core.mapper.CorPersonMapper;
import io.protone.core.mapper.CorPropertyValueMapper;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.library.domain.LibAlbum;
import io.protone.library.domain.LibArtist;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import org.mapstruct.Mapper;

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


    default String map(LibMediaLibrary value) {
        if(value==null){
            return null;
        }
        return value.getShortcut();
    }

    default String map(LibAlbum value) {
        if(value==null){
            return null;
        }
        return value.getName();
    }

    default String map(LibArtist value) {
        if(value==null){
            return null;
        }
        return value.getName();
    }

    default java.lang.String map(io.protone.core.domain.CorTag value) {
        if(value==null){
            return null;
        }
        return value.getTag();
    }
}
