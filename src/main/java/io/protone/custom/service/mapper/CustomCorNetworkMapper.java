package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreNetworkPT;
import io.protone.domain.CorNetwork;
import io.protone.service.dto.CorNetworkDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity CorNetwork and its DTO CorNetworkDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCorNetworkMapper {

    CoreNetworkPT cORNetworkToCorNetworkDTO(CorNetwork cORNetwork);

    List<CoreNetworkPT> cORNetworksToCorNetworkDTOs(List<CorNetwork> cORNetworks);

    CorNetwork cORNetworkDTOToCorNetwork(CoreNetworkPT cORNetworkDTO);

    List<CorNetwork> cORNetworkDTOsToCorNetworks(List<CoreNetworkPT> cORNetworkDTOs);
}
