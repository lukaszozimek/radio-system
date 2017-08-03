package io.protone.scheduler.mapper;

import io.protone.library.mapper.LibItemMapper;
import io.protone.scheduler.api.dto.SchEmissionDTO;
import io.protone.scheduler.domain.SchEmission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Emission and its DTO EmissionDTO.
 */
/*
FIXME: LibItem mapper refers to class in inner module which will become separate microservice in near future
 */
@Mapper(componentModel = "spring", uses = {SchPlaylistMapper.class, SchClockMapper.class, LibItemMapper.class, SchQueueParamsMapper.class, SchTimeParamsMapper.class, SchBlockMapper.class, })
public interface SchEmissionMapper extends SchEntityMapper<SchEmissionDTO, SchEmission> {
}
