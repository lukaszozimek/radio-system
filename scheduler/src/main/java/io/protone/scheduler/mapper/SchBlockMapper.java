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

    @ValueMappings({
            @ValueMapping(source = "MUSIC_IMPORT", target = "ET_MUSIC_IMPORT"),
            @ValueMapping(source = "COMMERCIAL_IMPORT", target = "ET_COMMERCIAL_IMPORT"),
            @ValueMapping(source = "OTHER", target = "ET_OTHER")
    })
    io.protone.scheduler.domain.enumeration.EventTypeEnum map(SchBlockDTO.SchEventTypeEnum source);

    @ValueMappings({ //ET_MUSIC_IMPORT, ET_COMMERCIAL_IMPORT, ET_OTHER
            @ValueMapping(source = "ET_MUSIC_IMPORT", target = "MUSIC_IMPORT"),
            @ValueMapping(source = "ET_COMMERCIAL_IMPORT", target = "COMMERCIAL_IMPORT"),
            @ValueMapping(source = "ET_OTHER", target = "OTHER")
    })
    SchBlockDTO.SchEventTypeEnum map(io.protone.scheduler.domain.enumeration.EventTypeEnum source);

}
