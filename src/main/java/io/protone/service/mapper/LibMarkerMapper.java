package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LibMarkerDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LibMarker and its DTO LibMarkerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibMarkerMapper {

    @Mapping(source = "mediaItem.id", target = "mediaItemId")
    LibMarkerDTO libMarkerToLibMarkerDTO(LibMarker libMarker);

    List<LibMarkerDTO> libMarkersToLibMarkerDTOs(List<LibMarker> libMarkers);

    @Mapping(source = "mediaItemId", target = "mediaItem")
    LibMarker libMarkerDTOToLibMarker(LibMarkerDTO libMarkerDTO);

    List<LibMarker> libMarkerDTOsToLibMarkers(List<LibMarkerDTO> libMarkerDTOs);

    default LibMediaItem libMediaItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibMediaItem libMediaItem = new LibMediaItem();
        libMediaItem.setId(id);
        return libMediaItem;
    }
}
