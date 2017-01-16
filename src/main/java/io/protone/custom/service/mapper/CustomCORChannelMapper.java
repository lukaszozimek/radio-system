package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreChannelPT;
import io.protone.domain.CORChannel;
import io.protone.domain.CORNetwork;
import io.protone.service.dto.CORChannelDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CORChannel and its DTO CORChannelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCORChannelMapper {

    @Mapping(source = "network.id", target = "networkId")
    CoreChannelPT cORChannelToCORChannelDTO(CORChannel cORChannel);

    List<CoreChannelPT> cORChannelsToCORChannelDTOs(List<CORChannel> cORChannels);

    @Mapping(source = "networkId", target = "network")
    CORChannel cORChannelDTOToCORChannel(CoreChannelPT cORChannelDTO);

    List<CORChannel> cORChannelDTOsToCORChannels(List<CoreChannelPT> cORChannelDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
