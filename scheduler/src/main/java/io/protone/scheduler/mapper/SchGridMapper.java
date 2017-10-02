package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.core.mapper.CorUserMapper;
import io.protone.scheduler.api.dto.SchGridDTO;
import io.protone.scheduler.api.dto.thin.SchGridThinDTO;
import io.protone.scheduler.domain.SchGrid;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity Grid and its DTO GridDTO.
 */
@Mapper(componentModel = "spring", uses = {SchGridClockConfigurationMapper.class, CorDictionaryMapper.class, CorUserMapper.class})
public interface SchGridMapper {
    @Mapping(source = "defaultGrid", target = "defaultGrid")
    SchGrid DTO2DB(SchGridDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

    @Mapping(source = "defaultGrid", target = "defaultGrid")
    SchGridDTO DB2DTO(SchGrid entity);

    List<SchGridDTO> DBs2DTOs(List<SchGrid> entityList);

    default List<SchGrid> DTOs2DBs(List<SchGridDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchGrid> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchGridDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

    List<SchGridThinDTO> DBs2ThinDTOs(List<SchGrid> schClockList);

    @Mapping(source = "defaultGrid", target = "defaultGrid")
    SchGridThinDTO DB2ThinDTO(SchGrid schClockList);


    @AfterMapping
    default void schGridDTOToSchGridnAfterMapping(SchGridDTO dto, @MappingTarget SchGrid entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
