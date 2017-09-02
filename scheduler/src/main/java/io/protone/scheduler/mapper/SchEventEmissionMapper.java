package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibLibrary;
import io.protone.library.mapper.LibMediaItemThinMapper;
import io.protone.scheduler.api.dto.SchEventEmissionDTO;
import io.protone.scheduler.domain.SchEventEmission;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity Emission and its DTO EmissionDTO.
 */

@Mapper(componentModel = "spring", uses = {SchClockConfigurationMapper.class, LibMediaItemThinMapper.class, SchQueueParamsMapper.class, SchConfigurationTimeParamsMapper.class, SchEventConfigurationMapper.class, SchEventMapper.class})
public interface SchEventEmissionMapper {
    SchEventEmission DTO2DB(SchEventEmissionDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

    SchEventEmissionDTO DB2DTO(SchEventEmission entity);

    List<SchEventEmissionDTO> DBs2DTOs(List<SchEventEmission> entityList);

    default List<SchEventEmission> DTOs2DBs(List<SchEventEmissionDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchEventEmission> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchEventEmissionDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

    default String map(LibLibrary value) {
        return null;
    }

    @AfterMapping
    default void schSchEmissionToSchEmissionAfterMapping(SchEventEmissionDTO dto, @MappingTarget SchEventEmission entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
