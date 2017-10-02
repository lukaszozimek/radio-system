package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.library.domain.LibAlbum;
import io.protone.library.domain.LibArtist;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.scheduler.api.dto.SchEventDTO;
import io.protone.scheduler.api.dto.thin.SchEventThinDTO;
import io.protone.scheduler.domain.SchEvent;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity Grid and its DTO GridDTO.
 */
@Mapper(componentModel = "spring", uses = {SchEventEmissionMapper.class, CorDictionaryMapper.class, SchLogColumnConfigurationMapper.class})
public interface SchEventMapper {
    @Mapping(source = "schLogConfiguration", target = "schLogConfiguration")
    SchEvent DTO2DB(SchEventDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

    @Mapping(source = "schLogConfiguration", target = "schLogConfiguration")
    SchEventDTO DB2DTO(SchEvent entity);

    List<SchEventDTO> DBs2DTOs(List<SchEvent> entityList);

    default List<SchEvent> DTOs2DBs(List<SchEventDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchEvent> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchEventDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

    List<SchEventThinDTO> DBs2ThinDTOs(List<SchEvent> schClockList);

    default String map(LibMediaLibrary value) {
        return null;
    }

    default String map(LibAlbum value) {
        return null;
    }

    default String map(LibArtist value) {
        return null;
    }

    @AfterMapping
    default void schSchEventDTOToSchEventnAfterMapping(SchEventDTO dto, @MappingTarget SchEvent entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
