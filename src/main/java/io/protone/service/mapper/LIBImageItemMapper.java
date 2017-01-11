package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LIBImageItemDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LIBImageItem and its DTO LIBImageItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LIBImageItemMapper {

    @Mapping(source = "library.id", target = "libraryId")
    LIBImageItemDTO lIBImageItemToLIBImageItemDTO(LIBImageItem lIBImageItem);

    List<LIBImageItemDTO> lIBImageItemsToLIBImageItemDTOs(List<LIBImageItem> lIBImageItems);

    @Mapping(source = "libraryId", target = "library")
    LIBImageItem lIBImageItemDTOToLIBImageItem(LIBImageItemDTO lIBImageItemDTO);

    List<LIBImageItem> lIBImageItemDTOsToLIBImageItems(List<LIBImageItemDTO> lIBImageItemDTOs);

    default LIBLibrary lIBLibraryFromId(Long id) {
        if (id == null) {
            return null;
        }
        LIBLibrary lIBLibrary = new LIBLibrary();
        lIBLibrary.setId(id);
        return lIBLibrary;
    }
}
