package io.protone.scheduler.mapper;

import io.protone.core.mapper.CorPersonMapper;
import io.protone.library.api.dto.LibMediaItemDTO;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.library.domain.LibLibrary;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.mapper.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        LibAlbumMapper.class,
        LibArtistMapper.class,
        CorPersonMapper.class,
        LibLabelMapper.class,
        LibMarkerMapper.class,
        LibTrackMapper.class})
public interface SchItemThinMapper {
    LibMediaItem toEntity(LibMediaItemThinDTO dto);

    LibMediaItemThinDTO toDto(LibMediaItem entity);

    List<LibMediaItem> toEntity(List<LibMediaItemThinDTO> dtoList);

    List<LibMediaItemThinDTO> toDto(List<LibMediaItem> entityList);

    default String map(LibLibrary value) {
        return value.getShortcut();
    }
}
