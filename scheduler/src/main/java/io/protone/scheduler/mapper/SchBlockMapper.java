package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchBlockDTO;
import io.protone.scheduler.domain.SchBlock;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity Block and its DTO BlockDTO.
 */
@Mapper(componentModel = "spring", uses = {SchQueueParamsMapper.class, SchEmissionMapper.class, SchTimeParamsMapper.class,})
public interface SchBlockMapper  {
     SchBlock DTO2DB(SchBlockDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

     SchBlockDTO DB2DTO(SchBlock entity);

     List<SchBlockDTO> DBs2DTOs(List<SchBlock> entityList);

    default List<SchBlock> DTOs2DBs(List<SchBlockDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchBlock> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchBlockDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }
    @AfterMapping
    default void schBlockDTOToSchBlockAfterMapping(SchBlockDTO dto, @MappingTarget SchBlock entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
