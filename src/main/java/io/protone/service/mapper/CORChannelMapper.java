package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CORChannelDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CORChannel and its DTO CORChannelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CORChannelMapper {

    @Mapping(source = "network.id", target = "networkId")
    CORChannelDTO cORChannelToCORChannelDTO(CORChannel cORChannel);

    List<CORChannelDTO> cORChannelsToCORChannelDTOs(List<CORChannel> cORChannels);

    @Mapping(source = "networkId", target = "network")
    CORChannel cORChannelDTOToCORChannel(CORChannelDTO cORChannelDTO);

    List<CORChannel> cORChannelDTOsToCORChannels(List<CORChannelDTO> cORChannelDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
