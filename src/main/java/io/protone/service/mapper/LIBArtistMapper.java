package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LIBArtistDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LIBArtist and its DTO LIBArtistDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LIBArtistMapper {

    @Mapping(source = "network.id", target = "networkId")
    LIBArtistDTO lIBArtistToLIBArtistDTO(LIBArtist lIBArtist);

    List<LIBArtistDTO> lIBArtistsToLIBArtistDTOs(List<LIBArtist> lIBArtists);

    @Mapping(source = "networkId", target = "network")
    LIBArtist lIBArtistDTOToLIBArtist(LIBArtistDTO lIBArtistDTO);

    List<LIBArtist> lIBArtistDTOsToLIBArtists(List<LIBArtistDTO> lIBArtistDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
