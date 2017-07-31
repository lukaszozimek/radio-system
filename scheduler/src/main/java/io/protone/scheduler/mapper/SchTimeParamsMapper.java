package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchTimeParamsDTO;
import io.protone.scheduler.domain.*;

import org.mapstruct.*;

/**
 * Mapper for the entity TimeParams and its DTO TimeParamsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchTimeParamsMapper extends SchEntityMapper<SchTimeParamsDTO, SchTimeParams> {
    
    
    default SchTimeParams fromId(Long id) {
        if (id == null) {
            return null;
        }
        SchTimeParams timeParams = new SchTimeParams();
        timeParams.setId(id);
        return timeParams;
    }
}
