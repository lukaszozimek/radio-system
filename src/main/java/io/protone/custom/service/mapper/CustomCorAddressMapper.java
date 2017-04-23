package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreAddressPT;
import io.protone.domain.CorAddress;
import io.protone.domain.CorNetwork;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Created by lukaszozimek on 19.01.2017.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCorAddressMapper {
    CoreAddressPT cORAddressToCorAddressDTO(CorAddress cORAddress);

    List<CoreAddressPT> cORAddressesToCorAddressDTOs(List<CorAddress> cORAddresses);

    CorAddress cORAddressDTOToCorAddress(CoreAddressPT cORAddressDTO);

    List<CorAddress> cORAddressDTOsToCorAddresses(List<CoreAddressPT> cORAddressDTOs);

}
