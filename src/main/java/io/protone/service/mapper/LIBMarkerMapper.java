package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LIBMarkerDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LIBMarker and its DTO LIBMarkerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LIBMarkerMapper {

    @Mapping(source = "mediaItem.id", target = "mediaItemId")
    LIBMarkerDTO lIBMarkerToLIBMarkerDTO(LIBMarker lIBMarker);

    List<LIBMarkerDTO> lIBMarkersToLIBMarkerDTOs(List<LIBMarker> lIBMarkers);

    @Mapping(source = "mediaItemId", target = "mediaItem")
    LIBMarker lIBMarkerDTOToLIBMarker(LIBMarkerDTO lIBMarkerDTO);

    List<LIBMarker> lIBMarkerDTOsToLIBMarkers(List<LIBMarkerDTO> lIBMarkerDTOs);

    default LIBMediaItem lIBMediaItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        LIBMediaItem lIBMediaItem = new LIBMediaItem();
        lIBMediaItem.setId(id);
        return lIBMediaItem;
    }
}
