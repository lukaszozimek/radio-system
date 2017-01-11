package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CORNetworkDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CORNetwork and its DTO CORNetworkDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CORNetworkMapper {

    CORNetworkDTO cORNetworkToCORNetworkDTO(CORNetwork cORNetwork);

    List<CORNetworkDTO> cORNetworksToCORNetworkDTOs(List<CORNetwork> cORNetworks);

    CORNetwork cORNetworkDTOToCORNetwork(CORNetworkDTO cORNetworkDTO);

    List<CORNetwork> cORNetworkDTOsToCORNetworks(List<CORNetworkDTO> cORNetworkDTOs);
}
