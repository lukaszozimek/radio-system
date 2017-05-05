package io.protone.web.rest.mapper;

import com.google.common.base.Strings;
import io.protone.custom.service.dto.LibMediaItemDTO;
import io.protone.web.rest.dto.library.thin.LibMediaItemThinDTO;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorTag;
import io.protone.domain.LibMediaItem;
import io.protone.domain.enumeration.LibItemStateEnum;
import io.protone.domain.enumeration.LibItemTypeEnum;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {
    LibAlbumMapper.class,
    LibArtistMapper.class,
    CorPersonMapper.class,
    LibLabelMapper.class,
    LibLibraryMapper.class,
    LibMarkerMapper.class,
    LibTrackMapper.class})
public interface LibItemMapper {

    LibMediaItemDTO DB2DTO(LibMediaItem db);

    List<LibMediaItemDTO> DBs2DTOs(List<LibMediaItem> dbs);

    LibMediaItem DTO2DB(LibMediaItemDTO dto, @Context CorNetwork networkId);

    default List<LibMediaItem> DTOs2DBs(List<LibMediaItemDTO> dtos, CorNetwork networkId) {
        List<LibMediaItem> libMediaItems = new ArrayList<>();
        if (dtos.isEmpty() || dtos == null) {
            return null;
        }
        for (LibMediaItemDTO dto : dtos) {
            libMediaItems.add(DTO2DB(dto, networkId));
        }
        return libMediaItems;
    }

    LibMediaItem libMediaItemFromLibMediaItemThinPt(LibMediaItemThinDTO id);

    LibMediaItemThinDTO libMediaItemThinPtFromLibMediaItem(LibMediaItem id);


    default LibMediaItemDTO.StateEnum mapState(LibItemStateEnum state) {

        if (state.compareTo(LibItemStateEnum.IS_ARCHIVED) == 0) {
            return LibMediaItemDTO.StateEnum.ARCHIVED;
        } else if (state.compareTo(LibItemStateEnum.IS_DELETED) == 0) {
            return LibMediaItemDTO.StateEnum.DELETED;
        } else if (state.compareTo(LibItemStateEnum.IS_DISABLED) == 0) {
            return LibMediaItemDTO.StateEnum.DISABLED;
        } else if (state.compareTo(LibItemStateEnum.IS_ENABLED) == 0) {
            return LibMediaItemDTO.StateEnum.ENABLED;
        } else if (state.compareTo(LibItemStateEnum.IS_ERROR) == 0) {
            return LibMediaItemDTO.StateEnum.ERROR;
        } else if (state.compareTo(LibItemStateEnum.IS_NEW) == 0) {
            return LibMediaItemDTO.StateEnum.NEW;
        } else if (state.compareTo(LibItemStateEnum.IS_POSTPROCESS) == 0) {
            return LibMediaItemDTO.StateEnum.POSTPROCESS;
        } else {
            return LibMediaItemDTO.StateEnum.OTHER;
        }
    }

    default CorTag corTagFromString(String tag, CorNetwork networkId) {

        if (Strings.isNullOrEmpty(tag)) {
            return null;
        }
        return new CorTag().tag(tag).network(networkId);
    }

    default String stringFromCorTag(CorTag tag) {

        if (tag == null) {
            return null;
        }
        return tag.getTag();
    }

    default LibMediaItemDTO.ResourceTypeEnum mapResourceType(LibItemTypeEnum itemType) {

        if (itemType.compareTo(LibItemTypeEnum.IT_AUDIO) == 0) {
            return LibMediaItemDTO.ResourceTypeEnum.AUDIO;
        } else if (itemType.compareTo(LibItemTypeEnum.IT_VIDEO) == 0) {
            return LibMediaItemDTO.ResourceTypeEnum.VIDEO;
        } else if (itemType.compareTo(LibItemTypeEnum.IT_COMMAND) == 0) {
            return LibMediaItemDTO.ResourceTypeEnum.COMMAND;
        } else {
            return LibMediaItemDTO.ResourceTypeEnum.OTHER;
        }
    }

    default LibItemTypeEnum mapItemType(LibMediaItemDTO.ResourceTypeEnum type) {

        if (type.compareTo(LibMediaItemDTO.ResourceTypeEnum.AUDIO) == 0) {
            return LibItemTypeEnum.IT_AUDIO;
        } else if (type.compareTo(LibMediaItemDTO.ResourceTypeEnum.VIDEO) == 0) {
            return LibItemTypeEnum.IT_VIDEO;
        } else if (type.compareTo(LibMediaItemDTO.ResourceTypeEnum.COMMAND) == 0) {
            return LibItemTypeEnum.IT_COMMAND;
        } else {
            return LibItemTypeEnum.IT_OTHER;
        }
    }

    default LibItemStateEnum mapState(LibMediaItemDTO.StateEnum state) {

        if (state.compareTo(LibMediaItemDTO.StateEnum.ARCHIVED) == 0) {
            return LibItemStateEnum.IS_ARCHIVED;
        } else if (state.compareTo(LibMediaItemDTO.StateEnum.DELETED) == 0) {
            return LibItemStateEnum.IS_DELETED;
        } else if (state.compareTo(LibMediaItemDTO.StateEnum.DISABLED) == 0) {
            return LibItemStateEnum.IS_DISABLED;
        } else if (state.compareTo(LibMediaItemDTO.StateEnum.ENABLED) == 0) {
            return LibItemStateEnum.IS_ENABLED;
        } else if (state.compareTo(LibMediaItemDTO.StateEnum.ERROR) == 0) {
            return LibItemStateEnum.IS_ERROR;
        } else if (state.compareTo(LibMediaItemDTO.StateEnum.NEW) == 0) {
            return LibItemStateEnum.IS_NEW;
        } else if (state.compareTo(LibMediaItemDTO.StateEnum.POSTPROCESS) == 0) {
            return LibItemStateEnum.IS_POSTPROCESS;
        } else {
            return LibItemStateEnum.IS_OTHER;
        }
    }

}
