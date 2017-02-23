package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CorChannelDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CorChannel and its DTO CorChannelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorChannelMapper {

    @Mapping(source = "network.id", target = "networkId")
    CorChannelDTO corChannelToCorChannelDTO(CorChannel corChannel);

    List<CorChannelDTO> corChannelsToCorChannelDTOs(List<CorChannel> corChannels);

    @Mapping(source = "networkId", target = "network")
    CorChannel corChannelDTOToCorChannel(CorChannelDTO corChannelDTO);

    List<CorChannel> corChannelDTOsToCorChannels(List<CorChannelDTO> corChannelDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
