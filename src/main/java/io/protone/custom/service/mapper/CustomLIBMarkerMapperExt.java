package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibMarkerPT;
import io.protone.domain.LIBMarker;
import io.protone.domain.LIBMediaItem;
import io.protone.domain.enumeration.LIBMarkerTypeEnum;
import io.protone.service.dto.LIBMarkerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity LIBMarker and its DTO LIBMarkerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomLIBMarkerMapperExt {

    @Mapping(source = "mediaItem.id", target = "mediaItemId")
    LibMarkerPT DB2DTO(LIBMarker lIBMarker);

    List<LibMarkerPT> DBs2DTOs(List<LIBMarker> lIBMarkers);

    @Mapping(source = "mediaItemId", target = "mediaItem")
    LIBMarker DTO2DB(LibMarkerPT lIBMarkerDTO);

    List<LIBMarker> DTOs2DBs(List<LibMarkerPT> lIBMarkerDTOs);

    default LIBMediaItem mapLIBMediaItem(Long id) {
        if (id == null) {
            return null;
        }
        LIBMediaItem lIBMediaItem = new LIBMediaItem();
        lIBMediaItem.setId(id);
        return lIBMediaItem;
    }

    default LibMarkerPT.MarkerTypeEnum mapLibMarkerPT_MarkerTypeEnum(LIBMarkerTypeEnum value) {
        return LibMarkerPT.MarkerTypeEnum.valueOf(value.toString());
    }

    default LIBMarkerTypeEnum mapLIBMarkerTypeEnum(LibMarkerPT.MarkerTypeEnum value) {
        return LIBMarkerTypeEnum.valueOf(value.toString());
    }
}
