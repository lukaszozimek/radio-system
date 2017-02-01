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

        if (value.compareTo(LIBMarkerTypeEnum.MT_BASIC) == 0)
            return LibMarkerPT.MarkerTypeEnum.BASIC;
        else if (value.compareTo(LIBMarkerTypeEnum.MT_CUSTOM) == 0)
            return LibMarkerPT.MarkerTypeEnum.CUSTOM;
        else if (value.compareTo(LIBMarkerTypeEnum.MT_FADE) == 0)
            return LibMarkerPT.MarkerTypeEnum.FADE;
        else if (value.compareTo(LIBMarkerTypeEnum.MT_HOOK) == 0)
            return LibMarkerPT.MarkerTypeEnum.HOOK;
        else if (value.compareTo(LIBMarkerTypeEnum.MT_INTRO) == 0)
            return LibMarkerPT.MarkerTypeEnum.INTRO;
        else if (value.compareTo(LIBMarkerTypeEnum.MT_LOOP) == 0)
            return LibMarkerPT.MarkerTypeEnum.LOOP;
        else
            return LibMarkerPT.MarkerTypeEnum.CUSTOM;
    }

    default LIBMarkerTypeEnum mapLIBMarkerTypeEnum(LibMarkerPT.MarkerTypeEnum value) {

        if (value.compareTo(LibMarkerPT.MarkerTypeEnum.BASIC) == 0)
            return LIBMarkerTypeEnum.MT_BASIC;
        else if (value.compareTo(LibMarkerPT.MarkerTypeEnum.CUSTOM) == 0)
            return LIBMarkerTypeEnum.MT_CUSTOM;
        else if (value.compareTo(LibMarkerPT.MarkerTypeEnum.FADE) == 0)
            return LIBMarkerTypeEnum.MT_FADE;
        else if (value.compareTo(LibMarkerPT.MarkerTypeEnum.HOOK) == 0)
            return LIBMarkerTypeEnum.MT_HOOK;
        else if (value.compareTo(LibMarkerPT.MarkerTypeEnum.INTRO) == 0)
            return LIBMarkerTypeEnum.MT_INTRO;
        else if (value.compareTo(LibMarkerPT.MarkerTypeEnum.LOOP) == 0)
            return LIBMarkerTypeEnum.MT_LOOP;
        else
            return LIBMarkerTypeEnum.MT_CUSTOM;
    }
}
