package io.protone.scheduler.mapper;

import com.google.common.base.Strings;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorTag;
import io.protone.core.mapper.CorPersonMapper;
import io.protone.library.api.dto.LibMediaItemDTO;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.mapper.*;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {
        LibAlbumMapper.class,
        LibArtistMapper.class,
        CorPersonMapper.class,
        LibLabelMapper.class,
        LibLibraryMapper.class,
        LibMarkerMapper.class,
        LibTrackMapper.class})
public interface SchItemMapper {

    default LibMediaItem toEntity(LibMediaItemDTO dto) {
        return null;
    }

    default LibMediaItemDTO toDto(LibMediaItem entity) {
        return null;
    }

    default List<LibMediaItem> toEntity(List<LibMediaItemDTO> dtoList) {
        return null;
    }

    default List<LibMediaItemDTO> toDto(List<LibMediaItem> entityList) {
        return null;
    }

}
