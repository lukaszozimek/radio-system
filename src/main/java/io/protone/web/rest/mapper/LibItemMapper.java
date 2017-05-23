package io.protone.web.rest.mapper;

import com.google.common.base.Strings;
import io.protone.domain.CorPerson;
import io.protone.web.rest.dto.cor.CorPersonDTO;
import io.protone.web.rest.dto.library.LibMediaItemDTO;
import io.protone.web.rest.dto.library.thin.LibMediaItemThinDTO;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorTag;
import io.protone.domain.LibMediaItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

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

    LibMediaItem libMediaItemFromLibMediaItemThinPt(LibMediaItemThinDTO id);

    LibMediaItemThinDTO libMediaItemThinPtFromLibMediaItem(LibMediaItem id);



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
