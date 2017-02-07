package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreKeyPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorPropertyKey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorPropertyKey and its DTO CorPropertyKeyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCorPropertyKeyMapper {

    @Mapping(source = "network.id", target = "networkId")
    CoreKeyPT cORPropertyKeyToCorPropertyKeyDTO(CorPropertyKey cORPropertyKey);

    List<CoreKeyPT> cORPropertyKeysToCorPropertyKeyDTOs(List<CorPropertyKey> cORPropertyKeys);

    @Mapping(source = "networkId", target = "network")
    CorPropertyKey cORPropertyKeyDTOToCorPropertyKey(CoreKeyPT cORPropertyKeyDTO);

    List<CorPropertyKey> cORPropertyKeyDTOsToCorPropertyKeys(List<CoreKeyPT> cORPropertyKeyDTOs);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
