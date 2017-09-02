package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.core.mapper.CorUserMapper;
import io.protone.library.domain.LibLibrary;
import io.protone.scheduler.api.dto.SchClockConfigurationDTO;
import io.protone.scheduler.api.dto.thin.SchClockConfigurationThinDTO;
import io.protone.scheduler.domain.SchClockConfiguration;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@Mapper(componentModel = "spring", uses = {SchEmissionConfigurationMapper.class, SchQueueParamsMapper.class, SchConfigurationTimeParamsMapper.class, SchEventMapper.class, CorDictionaryMapper.class, CorUserMapper.class})
public interface SchClockConfigurationMapper {
    SchClockConfiguration DTO2DB(SchClockConfigurationDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

    SchClockConfigurationDTO DB2DTO(SchClockConfiguration entity);

    List<SchClockConfigurationDTO> DBs2DTOs(List<SchClockConfiguration> entityList);

    default List<SchClockConfiguration> DTOs2DBs(List<SchClockConfigurationDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchClockConfiguration> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchClockConfigurationDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

    List<SchClockConfigurationThinDTO> DBs2ThinDTOs(List<SchClockConfiguration> schClockList);

    default String map(LibLibrary value) {
        return null;
    }

    @AfterMapping
    default void schClockConfigurationDTOToSchClockConfigurationAfterMapping(SchClockConfigurationDTO dto, @MappingTarget SchClockConfiguration entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
