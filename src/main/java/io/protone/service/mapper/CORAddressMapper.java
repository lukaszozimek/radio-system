package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CORAddressDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CORAddress and its DTO CORAddressDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CORAddressMapper {

    @Mapping(source = "network.id", target = "networkId")
    CORAddressDTO cORAddressToCORAddressDTO(CORAddress cORAddress);

    List<CORAddressDTO> cORAddressesToCORAddressDTOs(List<CORAddress> cORAddresses);

    @Mapping(source = "networkId", target = "network")
    CORAddress cORAddressDTOToCORAddress(CORAddressDTO cORAddressDTO);

    List<CORAddress> cORAddressDTOsToCORAddresses(List<CORAddressDTO> cORAddressDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
