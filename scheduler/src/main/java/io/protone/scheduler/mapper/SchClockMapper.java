package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.scheduler.api.dto.SchClockDTO;
import io.protone.scheduler.api.dto.thin.SchClockThinDTO;
import io.protone.scheduler.domain.SchClock;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity Clock and its DTO ClockDTO.
 */
@Mapper(componentModel = "spring", uses = {SchEmissionMapper.class, SchTimeParamsMapper.class, CorDictionaryMapper.class})
public interface SchClockMapper {
    SchClock DTO2DB(SchClockDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

    SchClockDTO DB2DTO(SchClock entity);

    List<SchClockDTO> DBs2DTOs(List<SchClock> entityList);

    default List<SchClock> DTOs2DBs(List<SchClockDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchClock> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchClockDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

    List<SchClockThinDTO> DBs2ThinDTOs(List<SchClock> schClockList);

    default String map(LibMediaLibrary value) {
        return null;
    }

    @AfterMapping
    default void schClockDTOToSchClockAfterMapping(SchClockDTO dto, @MappingTarget SchClock entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
