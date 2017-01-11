package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LIBAudioObjectDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LIBAudioObject and its DTO LIBAudioObjectDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LIBAudioObjectMapper {

    @Mapping(source = "cloudObject.id", target = "cloudObjectId")
    @Mapping(source = "mediaItem.id", target = "mediaItemId")
    LIBAudioObjectDTO lIBAudioObjectToLIBAudioObjectDTO(LIBAudioObject lIBAudioObject);

    List<LIBAudioObjectDTO> lIBAudioObjectsToLIBAudioObjectDTOs(List<LIBAudioObject> lIBAudioObjects);

    @Mapping(source = "cloudObjectId", target = "cloudObject")
    @Mapping(source = "mediaItemId", target = "mediaItem")
    LIBAudioObject lIBAudioObjectDTOToLIBAudioObject(LIBAudioObjectDTO lIBAudioObjectDTO);

    List<LIBAudioObject> lIBAudioObjectDTOsToLIBAudioObjects(List<LIBAudioObjectDTO> lIBAudioObjectDTOs);

    default LIBCloudObject lIBCloudObjectFromId(Long id) {
        if (id == null) {
            return null;
        }
        LIBCloudObject lIBCloudObject = new LIBCloudObject();
        lIBCloudObject.setId(id);
        return lIBCloudObject;
    }

    default LIBMediaItem lIBMediaItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        LIBMediaItem lIBMediaItem = new LIBMediaItem();
        lIBMediaItem.setId(id);
        return lIBMediaItem;
    }
}
