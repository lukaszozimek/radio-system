package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CORTagDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CORTag and its DTO CORTagDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CORTagMapper {

    @Mapping(source = "network.id", target = "networkId")
    CORTagDTO cORTagToCORTagDTO(CORTag cORTag);

    List<CORTagDTO> cORTagsToCORTagDTOs(List<CORTag> cORTags);

    @Mapping(source = "networkId", target = "network")
    CORTag cORTagDTOToCORTag(CORTagDTO cORTagDTO);

    List<CORTag> cORTagDTOsToCORTags(List<CORTagDTO> cORTagDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
