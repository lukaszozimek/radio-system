package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchQueueParamsDTO;
import io.protone.scheduler.domain.SchQueueParams;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity QueueParams and its DTO QueueParamsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchQueueParamsMapper {
     SchQueueParams DTO2DB(SchQueueParamsDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

     SchQueueParamsDTO DB2DTO(SchQueueParams entity);

     List<SchQueueParamsDTO> DBs2DTOs(List<SchQueueParams> entityList);

    default List<SchQueueParams> DTOs2DBs(List<SchQueueParamsDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchQueueParams> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchQueueParamsDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

}
