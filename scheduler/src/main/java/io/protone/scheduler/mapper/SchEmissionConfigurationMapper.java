package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibLibrary;
import io.protone.library.mapper.LibMediaItemThinMapper;
import io.protone.scheduler.api.dto.SchEmissionConfigurationDTO;
import io.protone.scheduler.domain.SchEmissionConfiguration;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity Emission and its DTO EmissionDTO.
 */
/*
FIXME: LibItem mapper refers to class in inner module which will become separate microservice in near future
 */
@Mapper(componentModel = "spring", uses = {SchClockConfigurationMapper.class, LibMediaItemThinMapper.class, SchQueueParamsMapper.class, SchTimeParamsMapper.class, SchEventConfigurationMapper.class, SchEventMapper.class})
public interface SchEmissionConfigurationMapper {
     SchEmissionConfiguration DTO2DB(SchEmissionConfigurationDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

     SchEmissionConfigurationDTO DB2DTO(SchEmissionConfiguration entity);

     List<SchEmissionConfigurationDTO> DBs2DTOs(List<SchEmissionConfiguration> entityList);

    default List<SchEmissionConfiguration> DTOs2DBs(List<SchEmissionConfigurationDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchEmissionConfiguration> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchEmissionConfigurationDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

    default String map(LibLibrary value) {
        return null;
    }

    @AfterMapping
    default void schSchEmissionToSchEmissionAfterMapping(SchEmissionConfigurationDTO dto, @MappingTarget SchEmissionConfiguration entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
