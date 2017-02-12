package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LibVideoObjectDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LibVideoObject and its DTO LibVideoObjectDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibVideoObjectMapper {

    @Mapping(source = "cloudObject.id", target = "cloudObjectId")
    @Mapping(source = "mediaItem.id", target = "mediaItemId")
    LibVideoObjectDTO libVideoObjectToLibVideoObjectDTO(LibVideoObject libVideoObject);

    List<LibVideoObjectDTO> libVideoObjectsToLibVideoObjectDTOs(List<LibVideoObject> libVideoObjects);

    @Mapping(source = "cloudObjectId", target = "cloudObject")
    @Mapping(source = "mediaItemId", target = "mediaItem")
    LibVideoObject libVideoObjectDTOToLibVideoObject(LibVideoObjectDTO libVideoObjectDTO);

    List<LibVideoObject> libVideoObjectDTOsToLibVideoObjects(List<LibVideoObjectDTO> libVideoObjectDTOs);

    default LibCloudObject libCloudObjectFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibCloudObject libCloudObject = new LibCloudObject();
        libCloudObject.setId(id);
        return libCloudObject;
    }

    default LibMediaItem libMediaItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibMediaItem libMediaItem = new LibMediaItem();
        libMediaItem.setId(id);
        return libMediaItem;
    }
}
