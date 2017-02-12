package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LibImageObjectDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LibImageObject and its DTO LibImageObjectDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibImageObjectMapper {

    @Mapping(source = "cloudObject.id", target = "cloudObjectId")
    @Mapping(source = "imageItem.id", target = "imageItemId")
    LibImageObjectDTO libImageObjectToLibImageObjectDTO(LibImageObject libImageObject);

    List<LibImageObjectDTO> libImageObjectsToLibImageObjectDTOs(List<LibImageObject> libImageObjects);

    @Mapping(source = "cloudObjectId", target = "cloudObject")
    @Mapping(source = "imageItemId", target = "imageItem")
    LibImageObject libImageObjectDTOToLibImageObject(LibImageObjectDTO libImageObjectDTO);

    List<LibImageObject> libImageObjectDTOsToLibImageObjects(List<LibImageObjectDTO> libImageObjectDTOs);

    default LibCloudObject libCloudObjectFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibCloudObject libCloudObject = new LibCloudObject();
        libCloudObject.setId(id);
        return libCloudObject;
    }

    default LibImageItem libImageItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibImageItem libImageItem = new LibImageItem();
        libImageItem.setId(id);
        return libImageItem;
    }
}
