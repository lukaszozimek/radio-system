package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchConfigurationTimeParamsDTO;
import io.protone.scheduler.domain.SchConfigurationTimeParams;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity TimeParams and its DTO TimeParamsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchConfigurationTimeParamsMapper {
     SchConfigurationTimeParams DTO2DB(SchConfigurationTimeParamsDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

     SchConfigurationTimeParamsDTO DB2DTO(SchConfigurationTimeParams entity);

     List<SchConfigurationTimeParamsDTO> DBs2DTOs(List<SchConfigurationTimeParams> entityList);

    default List<SchConfigurationTimeParams> DTOs2DBs(List<SchConfigurationTimeParamsDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchConfigurationTimeParams> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchConfigurationTimeParamsDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

}
