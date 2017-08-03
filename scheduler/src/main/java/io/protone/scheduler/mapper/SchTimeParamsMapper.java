package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchTimeParamsDTO;
import io.protone.scheduler.domain.SchTimeParams;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity TimeParams and its DTO TimeParamsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchTimeParamsMapper extends SchEntityMapper<SchTimeParamsDTO, SchTimeParams> {
}
