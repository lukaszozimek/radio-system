package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.CoreAddressPT;
import io.protone.custom.service.dto.CoreChannelPT;
import io.protone.domain.CorAddress;
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
    CoreChannelPT DB2DTO(CorChannel cORAddress);

    List<CoreChannelPT> DBs2DTOs(List<CorChannel> cORAddresses);

    CorChannel DTO2DB(CoreChannelPT cORAddressDTO, @Context CorNetwork corNetwork);

    default List<CorChannel> DTOs2DBs(List<CoreChannelPT> coreChannelPTS, CorNetwork networkId) {
        List<CorChannel> corAddresses = new ArrayList<>();
        if (coreChannelPTS.isEmpty() || coreChannelPTS == null) {
            return null;
        }
        for (CoreChannelPT dto : coreChannelPTS) {
            corAddresses.add(DTO2DB(dto, networkId));
        }
        return corAddresses;
    }

    @AfterMapping
    default void coreChannelPTToCorChannelAfterMapping(CoreChannelPT dto, @MappingTarget CorChannel entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
