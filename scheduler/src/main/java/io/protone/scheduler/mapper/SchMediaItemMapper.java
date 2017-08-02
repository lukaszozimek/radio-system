package io.protone.scheduler.mapper;

import io.protone.library.domain.LibAlbum;
import io.protone.library.domain.LibArtist;
import io.protone.library.domain.LibLibrary;
import io.protone.library.domain.LibMediaItem;
import io.protone.scheduler.api.dto.thin.SchLibItemThinDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity MediaItem and its DTO MediaItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchMediaItemMapper extends SchEntityMapper <SchLibItemThinDTO, LibMediaItem> {

    SchLibItemThinDTO toDto(LibMediaItem attachment);
    LibMediaItem toEntity(SchLibItemThinDTO attachmentDTO);

    default String map(LibLibrary value) {
        return value.getShortcut();
    }

    default String map(LibAlbum value) {
        return value.getName();
    }

    default String map(LibArtist value) {
        return value.getName();
    }
}
