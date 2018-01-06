package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.mapper.LibMediaItemThinMapper;
import io.protone.scheduler.api.dto.SchEmissionDTO;
import io.protone.scheduler.domain.SchEmission;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity Emission and its DTO EmissionDTO.
 */

@Mapper(componentModel = "spring", uses = {SchPlaylistMapper.class, SchClockMapper.class, LibMediaItemThinMapper.class, SchBlockMapper.class,})
public interface SchEmissionMapper {
    SchEmission DTO2DB(SchEmissionDTO dto, @Context CorChannel corChannel);

    SchEmissionDTO DB2DTO(SchEmission entity);

    List<SchEmissionDTO> DBs2DTOs(List<SchEmission> entityList);

    default List<SchEmission> DTOs2DBs(List<SchEmissionDTO> dList, @Context CorChannel corChannel) {
        List<SchEmission> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchEmissionDTO dto : dList) {
            eList.add(DTO2DB(dto, corChannel));
        }
        return eList;
    }

    @AfterMapping
    default void schSchEmissionToSchEmissionAfterMapping(SchEmissionDTO dto, @MappingTarget SchEmission entity, @Context CorChannel corChannel) {
        entity.setChannel(corChannel);
    }
}
