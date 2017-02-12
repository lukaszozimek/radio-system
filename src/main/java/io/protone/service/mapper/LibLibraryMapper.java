package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LibLibraryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LibLibrary and its DTO LibLibraryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibLibraryMapper {

    @Mapping(source = "network.id", target = "networkId")
    LibLibraryDTO libLibraryToLibLibraryDTO(LibLibrary libLibrary);

    List<LibLibraryDTO> libLibrariesToLibLibraryDTOs(List<LibLibrary> libLibraries);

    @Mapping(source = "networkId", target = "network")
    LibLibrary libLibraryDTOToLibLibrary(LibLibraryDTO libLibraryDTO);

    List<LibLibrary> libLibraryDTOsToLibLibraries(List<LibLibraryDTO> libLibraryDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
