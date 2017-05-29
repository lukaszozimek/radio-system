package io.protone.web.rest.mapper;

import io.protone.web.rest.dto.library.LibMarkerDTO;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibMarker;
import io.protone.domain.LibMediaItem;
import io.protone.domain.enumeration.LibMarkerTypeEnum;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity LibMarker and its DTO LibMarkerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibMarkerMapper {

    @Mapping(source = "mediaItem.id", target = "mediaItemId")
    LibMarkerDTO DB2DTO(LibMarker lIBMarker);

    List<LibMarkerDTO> DBs2DTOs(List<LibMarker> lIBMarkers);

    @Mapping(source = "mediaItemId", target = "mediaItem")
    LibMarker DTO2DB(LibMarkerDTO lIBMarkerDTO, @Context CorNetwork network);

    default List<LibMarker> DTOs2DBs(List<LibMarkerDTO> lIBMarkerDTOs, @Context CorNetwork network) {
        List<LibMarker> corPeople = new ArrayList<>();
        if (lIBMarkerDTOs.isEmpty() || lIBMarkerDTOs == null) {
            return null;
        }
        for (LibMarkerDTO dto : lIBMarkerDTOs) {
            corPeople.add(DTO2DB(dto, network));
        }
        return corPeople;
    }

    @AfterMapping
    default void libMarkerPTToLibMarkerAfterMapping(LibMarkerDTO dto, @MappingTarget LibMarker entity, @Context CorNetwork corNetwork) {
       //  entity.setNetwork(corNetwork);
    }

    default LibMediaItem libMediaItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibMediaItem lIBMediaItem = new LibMediaItem();
        lIBMediaItem.setId(id);
        return lIBMediaItem;
    }

    default Long idFromLibMediaItem(LibMediaItem libMediaItem) {
        if (libMediaItem == null) {
            return null;
        }
        return libMediaItem.getId();
    }

}
