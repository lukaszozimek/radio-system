package io.protone.library.mapper;

import com.google.common.base.Strings;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorTag;
import io.protone.library.api.dto.LibFileItemDTO;
import io.protone.library.api.dto.thin.LibFileItemThinDTO;
import io.protone.library.domain.LibFileItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LibFileItemMapper {

    LibFileItemDTO DB2DTO(LibFileItem db);

    LibFileItemThinDTO DB2ThinDTO(LibFileItem db);

    List<LibFileItemDTO> DBs2DTOs(List<LibFileItem> dbs);

    List<LibFileItemThinDTO> DBs2ThinDTOs(List<LibFileItem> dbs);

    LibFileItem DTO2DB(LibFileItemDTO dto, @Context CorChannel channel);

    @AfterMapping
    default void LibFileItemDTOToLibFileItemAfterMapping(LibFileItemDTO dto, @MappingTarget LibFileItem entity, @Context CorChannel channel) {
        entity.setChannel(channel);
    }

    default List<LibFileItem> DTOs2DBs(List<LibFileItemDTO> dtos, CorChannel channel) {
        List<LibFileItem> LibFileItems = new ArrayList<>();
        if (dtos.isEmpty() || dtos == null) {
            return null;
        }
        for (LibFileItemDTO dto : dtos) {
            LibFileItems.add(DTO2DB(dto, channel));
        }
        return LibFileItems;
    }

    default CorTag corTagFromString(String tag, CorChannel channel) {

        if (Strings.isNullOrEmpty(tag)) {
            return null;
        }
        return new CorTag().tag(tag).channel(channel);
    }

    default String stringFromCorTag(CorTag tag) {

        if (tag == null) {
            return null;
        }
        return tag.getTag();
    }


}
