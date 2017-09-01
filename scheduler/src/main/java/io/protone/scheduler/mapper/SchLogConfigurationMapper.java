package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.core.mapper.CorUserMapper;
import io.protone.scheduler.api.dto.SchLogConfigurationDTO;
import io.protone.scheduler.api.dto.thin.SchLogConfigurationThinDTO;
import io.protone.scheduler.domain.SchLogConfiguration;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@Mapper(componentModel = "spring", uses = {SchLogColumnConfigurationMapper.class, CorDictionaryMapper.class, CorUserMapper.class})
public interface SchLogConfigurationMapper {
    SchLogConfiguration DTO2DB(SchLogConfigurationDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

    SchLogConfigurationDTO DB2DTO(SchLogConfiguration entity);

    List<SchLogConfigurationDTO> DBs2DTOs(List<SchLogConfiguration> entityList);

    default List<SchLogConfiguration> DTOs2DBs(List<SchLogConfigurationDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchLogConfiguration> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchLogConfigurationDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

    List<SchLogConfigurationThinDTO> DBs2ThinDTOs(List<SchLogConfiguration> schClockList);

    @AfterMapping
    default void schLogConfigurationDTOToSchLogConfigurationAfterMapping(SchLogConfigurationDTO dto, @MappingTarget SchLogConfiguration entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
