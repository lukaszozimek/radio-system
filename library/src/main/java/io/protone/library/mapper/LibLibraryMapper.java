package io.protone.library.mapper;

import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorChannelMapper;
import io.protone.library.api.dto.LibLibraryDTO;
import io.protone.library.domain.LibLibrary;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {CorChannelMapper.class})
public interface LibLibraryMapper {

    LibLibraryDTO DB2DTO(LibLibrary libLibrary);

    List<LibLibraryDTO> DBs2DTOs(List<LibLibrary> libLibraries);

    @Mapping(source = "channels", target = "channels")
    LibLibrary DTO2DB(LibLibraryDTO libLibraryDTO, @Context CorNetwork corNetwork);

    default List<LibLibrary> DTOs2DBs(List<LibLibraryDTO> libLibraryDTOS, CorNetwork networkId) {
        List<LibLibrary> crmLeads = new ArrayList<>();
        if (libLibraryDTOS.isEmpty() || libLibraryDTOS == null) {
            return null;
        }
        for (LibLibraryDTO dto : libLibraryDTOS) {
            crmLeads.add(DTO2DB(dto, networkId));
        }
        return crmLeads;
    }

    @AfterMapping
    default void libraryPTToLibLibraryAfterMapping(LibLibraryDTO dto, @MappingTarget LibLibrary entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);

    }
}
