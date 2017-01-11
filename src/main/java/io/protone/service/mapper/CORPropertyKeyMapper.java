package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CORPropertyKeyDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CORPropertyKey and its DTO CORPropertyKeyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CORPropertyKeyMapper {

    @Mapping(source = "network.id", target = "networkId")
    CORPropertyKeyDTO cORPropertyKeyToCORPropertyKeyDTO(CORPropertyKey cORPropertyKey);

    List<CORPropertyKeyDTO> cORPropertyKeysToCORPropertyKeyDTOs(List<CORPropertyKey> cORPropertyKeys);

    @Mapping(source = "networkId", target = "network")
    CORPropertyKey cORPropertyKeyDTOToCORPropertyKey(CORPropertyKeyDTO cORPropertyKeyDTO);

    List<CORPropertyKey> cORPropertyKeyDTOsToCORPropertyKeys(List<CORPropertyKeyDTO> cORPropertyKeyDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
