package io.protone.library.mapper;

import com.google.common.base.Strings;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorTag;
import io.protone.core.mapper.CorPersonMapper;
import io.protone.core.mapper.CorPropertyValueMapper;
import io.protone.library.api.dto.LibMediaItemDTO;
import io.protone.library.domain.LibMediaItem;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {
        LibAlbumMapper.class,
        LibArtistMapper.class,
        CorPersonMapper.class,
        LibLabelMapper.class,
        LibLibraryMediaMapper.class,
        LibMarkerMapper.class,
        LibTrackMapper.class,
        CorPropertyValueMapper.class})
public interface LibMediaItemMapper {

    @Mapping(source = "properites", target = "properties")
    LibMediaItemDTO DB2DTO(LibMediaItem db);

    List<LibMediaItemDTO> DBs2DTOs(List<LibMediaItem> dbs);

    @Mapping(source = "properties", target = "properites")
    LibMediaItem DTO2DB(LibMediaItemDTO dto, @Context CorNetwork networkId);

    @AfterMapping
    default void LibMediaItemDTOToLibMediaItemAfterMapping(LibMediaItemDTO dto, @MappingTarget LibMediaItem entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

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


}
