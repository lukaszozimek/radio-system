package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LibFileItemDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LibFileItem and its DTO LibFileItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibFileItemMapper {

    @Mapping(source = "library.id", target = "libraryId")
    @Mapping(source = "parentFile.id", target = "parentFileId")
    LibFileItemDTO libFileItemToLibFileItemDTO(LibFileItem libFileItem);

    List<LibFileItemDTO> libFileItemsToLibFileItemDTOs(List<LibFileItem> libFileItems);

    @Mapping(source = "libraryId", target = "library")
    @Mapping(source = "parentFileId", target = "parentFile")
    LibFileItem libFileItemDTOToLibFileItem(LibFileItemDTO libFileItemDTO);

    List<LibFileItem> libFileItemDTOsToLibFileItems(List<LibFileItemDTO> libFileItemDTOs);

    default LibLibrary libLibraryFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibLibrary libLibrary = new LibLibrary();
        libLibrary.setId(id);
        return libLibrary;
    }

    default LibFileItem libFileItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibFileItem libFileItem = new LibFileItem();
        libFileItem.setId(id);
        return libFileItem;
    }
}
