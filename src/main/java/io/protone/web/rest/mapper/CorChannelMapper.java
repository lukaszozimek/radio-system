package io.protone.web.rest.mapper;

import io.protone.web.rest.dto.cor.CorChannelDTO;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface CorChannelMapper {
    CorChannelDTO DB2DTO(CorChannel cORAddress);

    List<CorChannelDTO> DBs2DTOs(List<CorChannel> cORAddresses);

    CorChannel DTO2DB(CorChannelDTO cORAddressDTO, @Context CorNetwork corNetwork);

    default List<CorChannel> DTOs2DBs(List<CorChannelDTO> corChannelDTOS, CorNetwork networkId) {
        List<CorChannel> corAddresses = new ArrayList<>();
        if (corChannelDTOS.isEmpty() || corChannelDTOS == null) {
            return null;
        }
        for (CorChannelDTO dto : corChannelDTOS) {
            corAddresses.add(DTO2DB(dto, networkId));
        }
        return corAddresses;
    }

    @AfterMapping
    default void coreChannelPTToCorChannelAfterMapping(CorChannelDTO dto, @MappingTarget CorChannel entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
