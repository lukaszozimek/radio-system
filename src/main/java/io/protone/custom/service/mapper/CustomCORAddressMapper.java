package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreAddressPT;
import io.protone.domain.CORAddress;
import io.protone.domain.CORNetwork;
import io.protone.service.dto.CORAddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Created by lukaszozimek on 19.01.2017.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCORAddressMapper {
    @Mapping(source = "network.id", target = "networkId")
    CoreAddressPT cORAddressToCORAddressDTO(CORAddress cORAddress);

    List<CoreAddressPT> cORAddressesToCORAddressDTOs(List<CORAddress> cORAddresses);

    @Mapping(source = "networkId", target = "network")
    CORAddress cORAddressDTOToCORAddress(CoreAddressPT cORAddressDTO);

    List<CORAddress> cORAddressDTOsToCORAddresses(List<CoreAddressPT> cORAddressDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
