package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreKeyPT;
import io.protone.domain.CORNetwork;
import io.protone.domain.CORPropertyKey;
import io.protone.service.dto.CORPropertyKeyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CORPropertyKey and its DTO CORPropertyKeyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCORPropertyKeyMapper {

    @Mapping(source = "network.id", target = "networkId")
    CoreKeyPT cORPropertyKeyToCORPropertyKeyDTO(CORPropertyKey cORPropertyKey);

    List<CoreKeyPT> cORPropertyKeysToCORPropertyKeyDTOs(List<CORPropertyKey> cORPropertyKeys);

    @Mapping(source = "networkId", target = "network")
    CORPropertyKey cORPropertyKeyDTOToCORPropertyKey(CoreKeyPT cORPropertyKeyDTO);

    List<CORPropertyKey> cORPropertyKeyDTOsToCORPropertyKeys(List<CoreKeyPT> cORPropertyKeyDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
