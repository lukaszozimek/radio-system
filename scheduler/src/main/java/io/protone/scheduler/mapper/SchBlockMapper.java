package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchBlockDTO;
import io.protone.scheduler.domain.SchBlock;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity Block and its DTO BlockDTO.
 */
@Mapper(componentModel = "spring", uses = {SchEmissionMapper.class, SchQueueParamsMapper.class, SchTimeParamsMapper.class,})
public interface SchBlockMapper  {

    SchBlock toEntity(SchBlockDTO dto);

    SchBlockDTO toDto(SchBlock entity);

    List<SchBlock> toEntity(List<SchBlockDTO> dtoList);

    List<SchBlockDTO> toDto(List<SchBlock> entityList);
}
