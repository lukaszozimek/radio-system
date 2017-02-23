package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CorPropertyKeyDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CorPropertyKey and its DTO CorPropertyKeyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorPropertyKeyMapper {

    @Mapping(source = "network.id", target = "networkId")
    CorPropertyKeyDTO corPropertyKeyToCorPropertyKeyDTO(CorPropertyKey corPropertyKey);

    List<CorPropertyKeyDTO> corPropertyKeysToCorPropertyKeyDTOs(List<CorPropertyKey> corPropertyKeys);

    @Mapping(source = "networkId", target = "network")
    CorPropertyKey corPropertyKeyDTOToCorPropertyKey(CorPropertyKeyDTO corPropertyKeyDTO);

    List<CorPropertyKey> corPropertyKeyDTOsToCorPropertyKeys(List<CorPropertyKeyDTO> corPropertyKeyDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
