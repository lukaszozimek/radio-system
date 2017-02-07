package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibMarkerPT;
import io.protone.domain.LibMarker;
import io.protone.domain.LibMediaItem;
import io.protone.domain.enumeration.LibMarkerTypeEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity LibMarker and its DTO LibMarkerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomLibMarkerMapperExt {

    @Mapping(source = "mediaItem.id", target = "mediaItemId")
    LibMarkerPT DB2DTO(LibMarker lIBMarker);

    List<LibMarkerPT> DBs2DTOs(List<LibMarker> lIBMarkers);

    @Mapping(source = "mediaItemId", target = "mediaItem")
    LibMarker DTO2DB(LibMarkerPT lIBMarkerDTO);

    List<LibMarker> DTOs2DBs(List<LibMarkerPT> lIBMarkerDTOs);

    default LibMediaItem mapLibMediaItem(Long id) {
        if (id == null) {
            return null;
        }
        LibMediaItem lIBMediaItem = new LibMediaItem();
        lIBMediaItem.setId(id);
        return lIBMediaItem;
    }

    default LibMarkerPT.MarkerTypeEnum mapLibMarkerPT_MarkerTypeEnum(LibMarkerTypeEnum value) {

        if (value.compareTo(LibMarkerTypeEnum.MT_BASIC) == 0)
            return LibMarkerPT.MarkerTypeEnum.BASIC;
        else if (value.compareTo(LibMarkerTypeEnum.MT_CUSTOM) == 0)
            return LibMarkerPT.MarkerTypeEnum.CUSTOM;
        else if (value.compareTo(LibMarkerTypeEnum.MT_FADE) == 0)
            return LibMarkerPT.MarkerTypeEnum.FADE;
        else if (value.compareTo(LibMarkerTypeEnum.MT_HOOK) == 0)
            return LibMarkerPT.MarkerTypeEnum.HOOK;
        else if (value.compareTo(LibMarkerTypeEnum.MT_INTRO) == 0)
            return LibMarkerPT.MarkerTypeEnum.INTRO;
        else if (value.compareTo(LibMarkerTypeEnum.MT_LOOP) == 0)
            return LibMarkerPT.MarkerTypeEnum.LOOP;
        else
            return LibMarkerPT.MarkerTypeEnum.CUSTOM;
    }

    default LibMarkerTypeEnum mapLibMarkerTypeEnum(LibMarkerPT.MarkerTypeEnum value) {

        if (value.compareTo(LibMarkerPT.MarkerTypeEnum.BASIC) == 0)
            return LibMarkerTypeEnum.MT_BASIC;
        else if (value.compareTo(LibMarkerPT.MarkerTypeEnum.CUSTOM) == 0)
            return LibMarkerTypeEnum.MT_CUSTOM;
        else if (value.compareTo(LibMarkerPT.MarkerTypeEnum.FADE) == 0)
            return LibMarkerTypeEnum.MT_FADE;
        else if (value.compareTo(LibMarkerPT.MarkerTypeEnum.HOOK) == 0)
            return LibMarkerTypeEnum.MT_HOOK;
        else if (value.compareTo(LibMarkerPT.MarkerTypeEnum.INTRO) == 0)
            return LibMarkerTypeEnum.MT_INTRO;
        else if (value.compareTo(LibMarkerPT.MarkerTypeEnum.LOOP) == 0)
            return LibMarkerTypeEnum.MT_LOOP;
        else
            return LibMarkerTypeEnum.MT_CUSTOM;
    }
}
