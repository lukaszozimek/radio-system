package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.mapper.LibFileItemMapper;
import io.protone.scheduler.api.dto.SchLogDTO;
import io.protone.scheduler.api.dto.thin.SchLogThinDTO;
import io.protone.scheduler.domain.SchLog;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@Mapper(componentModel = "spring", uses = {SchLogConfigurationMapper.class, LibFileItemMapper.class})
public interface SchLogMapper {
     SchLog DTO2DB(SchLogDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

     SchLogDTO DB2DTO(SchLog entity);

     List<SchLogDTO> DBs2DTOs(List<SchLog> entityList);

    default List<SchLog> DTOs2DBs(List<SchLogDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchLog> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchLogDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }
    List<SchLogThinDTO> DBs2ThinDTOs(List<SchLog> schClockList);

    @AfterMapping
    default void schLogDTOToSchLogAfterMapping(SchLogDTO dto, @MappingTarget SchLog entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }

}
