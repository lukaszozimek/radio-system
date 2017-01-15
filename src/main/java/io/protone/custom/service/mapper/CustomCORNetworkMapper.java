package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreNetworkPT;
import io.protone.domain.CORNetwork;
import io.protone.service.dto.CORNetworkDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity CORNetwork and its DTO CORNetworkDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCORNetworkMapper {

    CoreNetworkPT cORNetworkToCORNetworkDTO(CORNetwork cORNetwork);

    List<CoreNetworkPT> cORNetworksToCORNetworkDTOs(List<CORNetwork> cORNetworks);

    CORNetwork cORNetworkDTOToCORNetwork(CoreNetworkPT cORNetworkDTO);

    List<CORNetwork> cORNetworkDTOsToCORNetworks(List<CoreNetworkPT> cORNetworkDTOs);
}
