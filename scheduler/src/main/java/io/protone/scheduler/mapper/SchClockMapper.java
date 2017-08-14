package io.protone.scheduler.mapper;

import io.protone.library.domain.LibAlbum;
import io.protone.library.domain.LibArtist;
import io.protone.library.domain.LibLibrary;
import io.protone.scheduler.api.dto.SchClockDTO;
import io.protone.scheduler.domain.SchClock;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity Clock and its DTO ClockDTO.
 */
@Mapper(componentModel = "spring", uses = {SchGridMapper.class, SchQueueParamsMapper.class, SchTimeParamsMapper.class,})
public interface SchClockMapper {
    SchClock toEntity(SchClockDTO dto);

    SchClockDTO toDto(SchClock entity);

    List<SchClock> toEntity(List<SchClockDTO> dtoList);

    List<SchClockDTO> toDto(List<SchClock> entityList);

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
