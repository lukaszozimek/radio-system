package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchTimeParamsDTO;
import io.protone.scheduler.domain.SchTimeParams;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity TimeParams and its DTO TimeParamsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchTimeParamsMapper {
     SchTimeParams DTO2DB(SchTimeParamsDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

     SchTimeParamsDTO DB2DTO(SchTimeParams entity);

     List<SchTimeParamsDTO> DBs2DTOs(List<SchTimeParams> entityList);

    default List<SchTimeParams> DTOs2DBs(List<SchTimeParamsDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchTimeParams> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchTimeParamsDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

}
