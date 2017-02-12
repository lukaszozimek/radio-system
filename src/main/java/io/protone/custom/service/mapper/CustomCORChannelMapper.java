package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreChannelPT;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CORChannel and its DTO CORChannelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCORChannelMapper {

    @Mapping(source = "network.id", target = "networkId")
    CoreChannelPT cORChannelToCORChannelDTO(CorChannel cORChannel);

    List<CoreChannelPT> cORChannelsToCORChannelDTOs(List<CorChannel> cORChannels);

    @Mapping(source = "networkId", target = "network")
    CorChannel cORChannelDTOToCORChannel(CoreChannelPT cORChannelDTO);

    List<CorChannel> cORChannelDTOsToCORChannels(List<CoreChannelPT> cORChannelDTOs);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
