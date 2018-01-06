package io.protone.core.mapper;


import io.protone.core.api.dto.CorChannelDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorOrganization;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {})
public interface CorChannelMapper {
    @Mapping(source = "corImageItem.publicUrl", target = "publicUrl")
    CorChannelDTO DB2DTO(CorChannel cORAddress);

    List<CorChannelDTO> DBs2DTOs(List<CorChannel> cORAddresses);

    List<CorChannelDTO> DBs2DTOs(Set<CorChannel> cORAddresses);

    CorChannel DTO2DB(CorChannelDTO cORAddressDTO, @Context CorOrganization corNetwork);

    default List<CorChannel> DTOs2DBs(List<CorChannelDTO> corChannelDTOS, CorOrganization organization) {
        List<CorChannel> corAddresses = new ArrayList<>();
        if (corChannelDTOS.isEmpty() || corChannelDTOS == null) {
            return null;
        }
        for (CorChannelDTO dto : corChannelDTOS) {
            corAddresses.add(DTO2DB(dto, organization));
        }
        return corAddresses;
    }

    default Set<CorChannel> DTOs2DBsSet(List<CorChannelDTO> corChannelDTOS, CorOrganization organization) {
        Set<CorChannel> corAddresses = new HashSet<>();
        if (corChannelDTOS.isEmpty() || corChannelDTOS == null) {
            return null;
        }
        for (CorChannelDTO dto : corChannelDTOS) {
            corAddresses.add(DTO2DB(dto, organization));
        }
        return corAddresses;
    }

    @AfterMapping
    default void coreChannelPTToCorChannelAfterMapping(CorChannelDTO dto, @MappingTarget CorChannel entity, @Context CorOrganization organization) {
        entity.setOrganization(organization);
    }
}
