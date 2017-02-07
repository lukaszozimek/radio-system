package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreAddressPT;
import io.protone.domain.CorAddress;
import io.protone.domain.CorNetwork;
import io.protone.service.dto.CorAddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Created by lukaszozimek on 19.01.2017.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCorAddressMapper {
    @Mapping(source = "network.id", target = "networkId")
    CoreAddressPT cORAddressToCorAddressDTO(CorAddress cORAddress);

    List<CoreAddressPT> cORAddressesToCorAddressDTOs(List<CorAddress> cORAddresses);

    @Mapping(source = "networkId", target = "network")
    CorAddress cORAddressDTOToCorAddress(CoreAddressPT cORAddressDTO);

    List<CorAddress> cORAddressDTOsToCorAddresses(List<CoreAddressPT> cORAddressDTOs);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
