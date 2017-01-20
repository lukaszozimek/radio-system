package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibraryPT;
import io.protone.domain.CORNetwork;
import io.protone.domain.LIBLibrary;
import io.protone.service.dto.LIBLibraryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity LIBLibrary and its DTO LIBLibraryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomLIBLibraryMapper {

    @Mapping(source = "network.id", target = "networkId")
    LibraryPT libLibrary2LibraryPT(LIBLibrary library);

    List<LibraryPT> libLibraries2LibraryPTs(List<LIBLibrary> libraries);

    @Mapping(source = "networkId", target = "network")
    LIBLibrary libLibraryPTToLIBLibrary(LibraryPT library);

    List<LIBLibrary> libLibraryPTsToLibraries(List<LibraryPT> libraries);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
