package io.protone.web.rest.mapper;

import com.google.common.base.Strings;
import io.protone.custom.service.dto.LibItemPT;
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

    LibItemPT DB2DTO(LibMediaItem db);

    List<LibItemPT> DBs2DTOs(List<LibMediaItem> dbs);

    LibMediaItem DTO2DB(LibItemPT dto, @Context CorNetwork networkId);

    default List<LibMediaItem> DTOs2DBs(List<LibItemPT> dtos, CorNetwork networkId) {
        List<LibMediaItem> libMediaItems = new ArrayList<>();
        if (dtos.isEmpty() || dtos == null) {
            return null;
        }
        for (LibItemPT dto : dtos) {
            libMediaItems.add(DTO2DB(dto, networkId));
        }
        return libMediaItems;
    }

    LibMediaItem libMediaItemFromLibMediaItemThinPt(LibMediaItemThinDTO id);

    LibMediaItemThinDTO libMediaItemThinPtFromLibMediaItem(LibMediaItem id);


    default LibItemPT.StateEnum mapState(LibItemStateEnum state) {

        if (state.compareTo(LibItemStateEnum.IS_ARCHIVED) == 0) {
            return LibItemPT.StateEnum.ARCHIVED;
        } else if (state.compareTo(LibItemStateEnum.IS_DELETED) == 0) {
            return LibItemPT.StateEnum.DELETED;
        } else if (state.compareTo(LibItemStateEnum.IS_DISABLED) == 0) {
            return LibItemPT.StateEnum.DISABLED;
        } else if (state.compareTo(LibItemStateEnum.IS_ENABLED) == 0) {
            return LibItemPT.StateEnum.ENABLED;
        } else if (state.compareTo(LibItemStateEnum.IS_ERROR) == 0) {
            return LibItemPT.StateEnum.ERROR;
        } else if (state.compareTo(LibItemStateEnum.IS_NEW) == 0) {
            return LibItemPT.StateEnum.NEW;
        } else if (state.compareTo(LibItemStateEnum.IS_POSTPROCESS) == 0) {
            return LibItemPT.StateEnum.POSTPROCESS;
        } else {
            return LibItemPT.StateEnum.OTHER;
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

    default LibItemPT.ResourceTypeEnum mapResourceType(LibItemTypeEnum itemType) {

        if (itemType.compareTo(LibItemTypeEnum.IT_AUDIO) == 0) {
            return LibItemPT.ResourceTypeEnum.AUDIO;
        } else if (itemType.compareTo(LibItemTypeEnum.IT_VIDEO) == 0) {
            return LibItemPT.ResourceTypeEnum.VIDEO;
        } else if (itemType.compareTo(LibItemTypeEnum.IT_COMMAND) == 0) {
            return LibItemPT.ResourceTypeEnum.COMMAND;
        } else {
            return LibItemPT.ResourceTypeEnum.OTHER;
        }
    }

    default LibItemTypeEnum mapItemType(LibItemPT.ResourceTypeEnum type) {

        if (type.compareTo(LibItemPT.ResourceTypeEnum.AUDIO) == 0) {
            return LibItemTypeEnum.IT_AUDIO;
        } else if (type.compareTo(LibItemPT.ResourceTypeEnum.VIDEO) == 0) {
            return LibItemTypeEnum.IT_VIDEO;
        } else if (type.compareTo(LibItemPT.ResourceTypeEnum.COMMAND) == 0) {
            return LibItemTypeEnum.IT_COMMAND;
        } else {
            return LibItemTypeEnum.IT_OTHER;
        }
    }

    default LibItemStateEnum mapState(LibItemPT.StateEnum state) {

        if (state.compareTo(LibItemPT.StateEnum.ARCHIVED) == 0) {
            return LibItemStateEnum.IS_ARCHIVED;
        } else if (state.compareTo(LibItemPT.StateEnum.DELETED) == 0) {
            return LibItemStateEnum.IS_DELETED;
        } else if (state.compareTo(LibItemPT.StateEnum.DISABLED) == 0) {
            return LibItemStateEnum.IS_DISABLED;
        } else if (state.compareTo(LibItemPT.StateEnum.ENABLED) == 0) {
            return LibItemStateEnum.IS_ENABLED;
        } else if (state.compareTo(LibItemPT.StateEnum.ERROR) == 0) {
            return LibItemStateEnum.IS_ERROR;
        } else if (state.compareTo(LibItemPT.StateEnum.NEW) == 0) {
            return LibItemStateEnum.IS_NEW;
        } else if (state.compareTo(LibItemPT.StateEnum.POSTPROCESS) == 0) {
            return LibItemStateEnum.IS_POSTPROCESS;
        } else {
            return LibItemStateEnum.IS_OTHER;
        }
    }

}
