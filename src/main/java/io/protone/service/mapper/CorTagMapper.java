package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CorTagDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CorTag and its DTO CorTagDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorTagMapper {

    @Mapping(source = "network.id", target = "networkId")
    @Mapping(source = "tags.id", target = "tagsId")
    CorTagDTO corTagToCorTagDTO(CorTag corTag);

    List<CorTagDTO> corTagsToCorTagDTOs(List<CorTag> corTags);

    @Mapping(source = "networkId", target = "network")
    @Mapping(source = "tagsId", target = "tags")
    CorTag corTagDTOToCorTag(CorTagDTO corTagDTO);

    List<CorTag> corTagDTOsToCorTags(List<CorTagDTO> corTagDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
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
