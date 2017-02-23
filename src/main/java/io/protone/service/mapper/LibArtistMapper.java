package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LibArtistDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LibArtist and its DTO LibArtistDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibArtistMapper {

    @Mapping(source = "network.id", target = "networkId")
    LibArtistDTO libArtistToLibArtistDTO(LibArtist libArtist);

    List<LibArtistDTO> libArtistsToLibArtistDTOs(List<LibArtist> libArtists);

    @Mapping(source = "networkId", target = "network")
    LibArtist libArtistDTOToLibArtist(LibArtistDTO libArtistDTO);

    List<LibArtist> libArtistDTOsToLibArtists(List<LibArtistDTO> libArtistDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
