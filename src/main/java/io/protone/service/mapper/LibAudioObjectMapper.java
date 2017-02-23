package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LibAudioObjectDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LibAudioObject and its DTO LibAudioObjectDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibAudioObjectMapper {

    @Mapping(source = "cloudObject.id", target = "cloudObjectId")
    @Mapping(source = "mediaItem.id", target = "mediaItemId")
    LibAudioObjectDTO libAudioObjectToLibAudioObjectDTO(LibAudioObject libAudioObject);

    List<LibAudioObjectDTO> libAudioObjectsToLibAudioObjectDTOs(List<LibAudioObject> libAudioObjects);

    @Mapping(source = "cloudObjectId", target = "cloudObject")
    @Mapping(source = "mediaItemId", target = "mediaItem")
    LibAudioObject libAudioObjectDTOToLibAudioObject(LibAudioObjectDTO libAudioObjectDTO);

    List<LibAudioObject> libAudioObjectDTOsToLibAudioObjects(List<LibAudioObjectDTO> libAudioObjectDTOs);

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
