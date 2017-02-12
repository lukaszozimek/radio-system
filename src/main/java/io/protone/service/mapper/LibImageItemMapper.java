package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LibImageItemDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LibImageItem and its DTO LibImageItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibImageItemMapper {

    @Mapping(source = "library.id", target = "libraryId")
    LibImageItemDTO libImageItemToLibImageItemDTO(LibImageItem libImageItem);

    List<LibImageItemDTO> libImageItemsToLibImageItemDTOs(List<LibImageItem> libImageItems);

    @Mapping(source = "libraryId", target = "library")
    LibImageItem libImageItemDTOToLibImageItem(LibImageItemDTO libImageItemDTO);

    List<LibImageItem> libImageItemDTOsToLibImageItems(List<LibImageItemDTO> libImageItemDTOs);

    default LibLibrary libLibraryFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibLibrary libLibrary = new LibLibrary();
        libLibrary.setId(id);
        return libLibrary;
    }
}
