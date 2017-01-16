package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfTagPT;
import io.protone.domain.CORNetwork;
import io.protone.domain.CORTag;
import io.protone.service.dto.CORTagDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CORTag and its DTO CORTagDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCORTagMapper {

    @Mapping(source = "network.id", target = "networkId")
    ConfTagPT cORTagToCORTagDTO(CORTag cORTag);

    List<ConfTagPT> cORTagsToCORTagDTOs(List<CORTag> cORTags);

    @Mapping(source = "networkId", target = "network")
    CORTag cORTagDTOToCORTag(ConfTagPT cORTagDTO);

    List<CORTag> cORTagDTOsToCORTags(List<ConfTagPT> cORTagDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
