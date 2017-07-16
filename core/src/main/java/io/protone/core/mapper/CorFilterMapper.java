package io.protone.core.mapper;


import io.protone.core.api.dto.CorChannelDTO;
import io.protone.core.api.dto.CorFilterDTO;
import io.protone.core.api.dto.thin.CorFilterThinDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorFilter;
import io.protone.core.domain.CorNetwork;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {})
public interface CorFilterMapper {
    CorFilterDTO DB2DTO(CorFilter cORAddress);

    List<CorFilterDTO> DBs2DTOs(List<CorFilter> cORAddresses);

    List<CorFilterDTO> DBs2DTOs(Set<CorFilter> cORAddresses);

    CorFilter DTO2DB(CorFilterThinDTO cORAddressDTO);

    CorFilter DTO2DB(CorFilterDTO cORAddressDTO, @Context CorNetwork corNetwork);

    default List<CorFilter> DTOs2DBs(List<CorFilterDTO> corChannelDTOS, CorNetwork networkId) {
        List<CorFilter> corAddresses = new ArrayList<>();
        if (corChannelDTOS.isEmpty() || corChannelDTOS == null) {
            return null;
        }
        for (CorFilterDTO dto : corChannelDTOS) {
            corAddresses.add(DTO2DB(dto, networkId));
        }
        return corAddresses;
    }

    default Set<CorFilter> DTOs2DBsSet(List<CorFilterDTO> corChannelDTOS, CorNetwork networkId) {
        Set<CorFilter> corAddresses = new HashSet<>();
        if (corChannelDTOS.isEmpty() || corChannelDTOS == null) {
            return null;
        }
        for (CorFilterDTO dto : corChannelDTOS) {
            corAddresses.add(DTO2DB(dto, networkId));
        }
        return corAddresses;
    }

    @AfterMapping
    default void coreChannelPTToCorChannelAfterMapping(CorFilterDTO dto, @MappingTarget CorFilter entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
