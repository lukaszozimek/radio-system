package io.protone.library.mapper;

import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorChannelMapper;
import io.protone.library.api.dto.LibFileLibraryDTO;
import io.protone.library.domain.LibFileLibrary;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {CorChannelMapper.class})
public interface LibFileLibraryMapper {


    LibFileLibraryDTO DB2DTO(LibFileLibrary libLibrary);

    List<LibFileLibraryDTO> DBs2DTOs(List<LibFileLibrary> libLibraries);

    @Mapping(source = "channels", target = "channels")
    LibFileLibrary DTO2DB(LibFileLibraryDTO libLibraryDTO, @Context CorNetwork corNetwork);

    default List<LibFileLibrary> DTOs2DBs(List<LibFileLibraryDTO> libLibraryDTOS, CorNetwork networkId) {
        List<LibFileLibrary> crmLeads = new ArrayList<>();
        if (libLibraryDTOS.isEmpty() || libLibraryDTOS == null) {
            return null;
        }
        for (LibFileLibraryDTO dto : libLibraryDTOS) {
            crmLeads.add(DTO2DB(dto, networkId));
        }
        return crmLeads;
    }

    @AfterMapping
    default void libraryPTToLibLibraryAfterMapping(LibFileLibraryDTO dto, @MappingTarget LibFileLibrary entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);

    }
}
