package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchLogConfigurationColumnDTO;
import io.protone.scheduler.domain.SchLogColumn;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@Mapper(componentModel = "spring")
public interface SchLogColumnConfigurationMapper {
     SchLogColumn DTO2DB(SchLogConfigurationColumnDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

     SchLogConfigurationColumnDTO DB2DTO(SchLogColumn entity);

     List<SchLogConfigurationColumnDTO> DBs2DTOs(List<SchLogColumn> entityList);

    default List<SchLogColumn> DTOs2DBs(List<SchLogConfigurationColumnDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchLogColumn> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchLogConfigurationColumnDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

    @AfterMapping
    default void schLogConfigurationColumnDTOToSchLogColumnAfterMapping(SchLogConfigurationColumnDTO dto, @MappingTarget SchLogColumn entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }

}