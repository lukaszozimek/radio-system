package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LIBVideoObjectDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LIBVideoObject and its DTO LIBVideoObjectDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LIBVideoObjectMapper {

    @Mapping(source = "cloudObject.id", target = "cloudObjectId")
    @Mapping(source = "mediaItem.id", target = "mediaItemId")
    LIBVideoObjectDTO lIBVideoObjectToLIBVideoObjectDTO(LIBVideoObject lIBVideoObject);

    List<LIBVideoObjectDTO> lIBVideoObjectsToLIBVideoObjectDTOs(List<LIBVideoObject> lIBVideoObjects);

    @Mapping(source = "cloudObjectId", target = "cloudObject")
    @Mapping(source = "mediaItemId", target = "mediaItem")
    LIBVideoObject lIBVideoObjectDTOToLIBVideoObject(LIBVideoObjectDTO lIBVideoObjectDTO);

    List<LIBVideoObject> lIBVideoObjectDTOsToLIBVideoObjects(List<LIBVideoObjectDTO> lIBVideoObjectDTOs);

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
