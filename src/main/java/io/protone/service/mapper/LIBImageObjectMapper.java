package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LIBImageObjectDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LIBImageObject and its DTO LIBImageObjectDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LIBImageObjectMapper {

    @Mapping(source = "cloudObject.id", target = "cloudObjectId")
    @Mapping(source = "imageItem.id", target = "imageItemId")
    LIBImageObjectDTO lIBImageObjectToLIBImageObjectDTO(LIBImageObject lIBImageObject);

    List<LIBImageObjectDTO> lIBImageObjectsToLIBImageObjectDTOs(List<LIBImageObject> lIBImageObjects);

    @Mapping(source = "cloudObjectId", target = "cloudObject")
    @Mapping(source = "imageItemId", target = "imageItem")
    LIBImageObject lIBImageObjectDTOToLIBImageObject(LIBImageObjectDTO lIBImageObjectDTO);

    List<LIBImageObject> lIBImageObjectDTOsToLIBImageObjects(List<LIBImageObjectDTO> lIBImageObjectDTOs);

    default LIBCloudObject lIBCloudObjectFromId(Long id) {
        if (id == null) {
            return null;
        }
        LIBCloudObject lIBCloudObject = new LIBCloudObject();
        lIBCloudObject.setId(id);
        return lIBCloudObject;
    }

    default LIBImageItem lIBImageItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        LIBImageItem lIBImageItem = new LIBImageItem();
        lIBImageItem.setId(id);
        return lIBImageItem;
    }
}
