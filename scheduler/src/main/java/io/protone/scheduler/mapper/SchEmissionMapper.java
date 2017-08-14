package io.protone.scheduler.mapper;

import io.protone.library.domain.LibAlbum;
import io.protone.library.domain.LibArtist;
import io.protone.library.domain.LibLibrary;
import io.protone.library.mapper.LibItemMapper;
import io.protone.scheduler.api.dto.SchEmissionDTO;
import io.protone.scheduler.domain.SchEmission;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity Emission and its DTO EmissionDTO.
 */
/*
FIXME: LibItem mapper refers to class in inner module which will become separate microservice in near future
 */
@Mapper(componentModel = "spring", uses = {SchPlaylistMapper.class, SchItemThinMapper.class, SchQueueParamsMapper.class, SchTimeParamsMapper.class, SchBlockMapper.class,})
public interface SchEmissionMapper {
    SchEmission toEntity(SchEmissionDTO dto);

    SchEmissionDTO toDto(SchEmission entity);

    List<SchEmission> toEntity(List<SchEmissionDTO> dtoList);

    List<SchEmissionDTO> toDto(List<SchEmission> entityList);

    default String map(LibAlbum value) {
        return value.getName();
    }

    default String map(LibArtist value) {
        return value.getName();
    }
}
