package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CorNetworkDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CorNetwork and its DTO CorNetworkDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorNetworkMapper {

    CorNetworkDTO corNetworkToCorNetworkDTO(CorNetwork corNetwork);

    List<CorNetworkDTO> corNetworksToCorNetworkDTOs(List<CorNetwork> corNetworks);

    CorNetwork corNetworkDTOToCorNetwork(CorNetworkDTO corNetworkDTO);

    List<CorNetwork> corNetworkDTOsToCorNetworks(List<CorNetworkDTO> corNetworkDTOs);
}
