package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CorAddressDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CorAddress and its DTO CorAddressDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorAddressMapper {

    @Mapping(source = "network.id", target = "networkId")
    CorAddressDTO corAddressToCorAddressDTO(CorAddress corAddress);

    List<CorAddressDTO> corAddressesToCorAddressDTOs(List<CorAddress> corAddresses);

    @Mapping(source = "networkId", target = "network")
    CorAddress corAddressDTOToCorAddress(CorAddressDTO corAddressDTO);

    List<CorAddress> corAddressDTOsToCorAddresses(List<CorAddressDTO> corAddressDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
