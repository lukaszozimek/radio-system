package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchBlockDTO;
import io.protone.scheduler.domain.*;

import org.mapstruct.*;

/**
 * Mapper for the entity Block and its DTO BlockDTO.
 */
@Mapper(componentModel = "spring", uses = {SchClockMapper.class, SchQueueParamsMapper.class, SchTimeParamsMapper.class, })
public interface SchBlockMapper extends SchEntityMapper<SchBlockDTO, SchBlock> {

    SchBlockDTO toDto(SchBlock block);

    @Mapping(target = "blocks", ignore = true)
    @Mapping(target = "emissions", ignore = true)
    SchBlock toEntity(SchBlockDTO blockDTO);
    default SchBlock fromId(Long id) {
        if (id == null) {
            return null;
        }
        SchBlock block = new SchBlock();
        block.setId(id);
        return block;
    }
}
