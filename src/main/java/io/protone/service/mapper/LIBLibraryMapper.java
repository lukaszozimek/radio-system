package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LIBLibraryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LIBLibrary and its DTO LIBLibraryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LIBLibraryMapper {

    @Mapping(source = "network.id", target = "networkId")
    LIBLibraryDTO lIBLibraryToLIBLibraryDTO(LIBLibrary lIBLibrary);

    List<LIBLibraryDTO> lIBLibrariesToLIBLibraryDTOs(List<LIBLibrary> lIBLibraries);

    @Mapping(source = "networkId", target = "network")
    LIBLibrary lIBLibraryDTOToLIBLibrary(LIBLibraryDTO lIBLibraryDTO);

    List<LIBLibrary> lIBLibraryDTOsToLIBLibraries(List<LIBLibraryDTO> lIBLibraryDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
