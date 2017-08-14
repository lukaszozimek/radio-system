package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchTimeParamsDTO;
import io.protone.scheduler.domain.SchTimeParams;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity TimeParams and its DTO TimeParamsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchTimeParamsMapper {

    SchTimeParams toEntity(SchTimeParamsDTO dto);

    SchTimeParamsDTO toDto(SchTimeParams entity);

    List<SchTimeParams> toEntity(List<SchTimeParamsDTO> dtoList);

    List<SchTimeParamsDTO> toDto(List<SchTimeParams> entityList);
}
