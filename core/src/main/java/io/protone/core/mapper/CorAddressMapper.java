package io.protone.core.mapper;


import io.protone.core.api.dto.CoreAddressDTO;
import io.protone.core.domain.CorAddress;
import io.protone.core.domain.CorChannel;
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

    CorAddress DTO2DB(CoreAddressDTO cORAddressDTO, @Context CorChannel corNetwork);

    default List<CorAddress> DTOs2DBs(List<CoreAddressDTO> coreAddressDTOS, CorChannel networkId) {
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
    default void coreAddressPTToCorAddressAfterMapping(CoreAddressDTO dto, @MappingTarget CorAddress entity, @Context CorChannel corNetwork) {
        entity.setChannel(corNetwork);
    }
}
