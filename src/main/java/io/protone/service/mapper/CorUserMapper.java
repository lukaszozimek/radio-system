package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CorUserDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CorUser and its DTO CorUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorUserMapper {

    @Mapping(source = "network.id", target = "networkId")
    @Mapping(source = "channel.id", target = "channelId")
    CorUserDTO corUserToCorUserDTO(CorUser corUser);

    List<CorUserDTO> corUsersToCorUserDTOs(List<CorUser> corUsers);

    @Mapping(source = "networkId", target = "network")
    @Mapping(source = "channelId", target = "channel")
    CorUser corUserDTOToCorUser(CorUserDTO corUserDTO);

    List<CorUser> corUserDTOsToCorUsers(List<CorUserDTO> corUserDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }

    default CorChannel corChannelFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorChannel corChannel = new CorChannel();
        corChannel.setId(id);
        return corChannel;
    }
}
