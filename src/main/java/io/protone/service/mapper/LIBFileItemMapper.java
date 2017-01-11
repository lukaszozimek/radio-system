package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LIBFileItemDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LIBFileItem and its DTO LIBFileItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LIBFileItemMapper {

    @Mapping(source = "library.id", target = "libraryId")
    @Mapping(source = "parentFile.id", target = "parentFileId")
    LIBFileItemDTO lIBFileItemToLIBFileItemDTO(LIBFileItem lIBFileItem);

    List<LIBFileItemDTO> lIBFileItemsToLIBFileItemDTOs(List<LIBFileItem> lIBFileItems);

    @Mapping(source = "libraryId", target = "library")
    @Mapping(source = "parentFileId", target = "parentFile")
    LIBFileItem lIBFileItemDTOToLIBFileItem(LIBFileItemDTO lIBFileItemDTO);

    List<LIBFileItem> lIBFileItemDTOsToLIBFileItems(List<LIBFileItemDTO> lIBFileItemDTOs);

    default LIBLibrary lIBLibraryFromId(Long id) {
        if (id == null) {
            return null;
        }
        LIBLibrary lIBLibrary = new LIBLibrary();
        lIBLibrary.setId(id);
        return lIBLibrary;
    }

    default LIBFileItem lIBFileItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        LIBFileItem lIBFileItem = new LIBFileItem();
        lIBFileItem.setId(id);
        return lIBFileItem;
    }
}
