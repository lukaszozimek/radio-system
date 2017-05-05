package io.protone.web.rest.mapper;

import io.protone.web.rest.dto.cor.CoreAddressDTO;
import io.protone.domain.CorAddress;
import io.protone.domain.CorNetwork;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 19.01.2017.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorAddressMapper {
    CoreAddressDTO DB2DTO(CorAddress cORAddress);

    List<CoreAddressDTO> DBs2DTOs(List<CorAddress> cORAddresses);

    CorAddress DTO2DB(CoreAddressDTO cORAddressDTO, @Context CorNetwork corNetwork);

    default List<CorAddress> DTOs2DBs(List<CoreAddressDTO> coreAddressDTOS, CorNetwork networkId) {
        List<CorAddress> corAddresses = new ArrayList<>();
        if (coreAddressDTOS.isEmpty() || coreAddressDTOS == null) {
            return null;
        }
        for (CoreAddressDTO dto : coreAddressDTOS) {
            corAddresses.add(DTO2DB(dto, networkId));
        }
        return corAddresses;
    }

    @AfterMapping
    default void coreAddressPTToCorAddressAfterMapping(CoreAddressDTO dto, @MappingTarget CorAddress entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
