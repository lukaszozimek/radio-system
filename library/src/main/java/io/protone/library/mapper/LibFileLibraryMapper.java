package io.protone.library.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorChannelMapper;
import io.protone.library.api.dto.LibFileLibraryDTO;
import io.protone.library.api.dto.LibMediaLibraryDTO;
import io.protone.library.domain.LibFileLibrary;
import io.protone.library.domain.LibMediaLibrary;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {CorChannelMapper.class})
public interface LibFileLibraryMapper {


    LibFileLibraryDTO DB2DTO(LibFileLibrary libLibrary);

    List<LibFileLibraryDTO> DBs2DTOs(List<LibFileLibrary> libLibraries);

    @Mapping(source = "channel", target = "channel")
    LibFileLibrary DTO2DB(LibFileLibraryDTO libLibraryDTO, @Context CorChannel corChannel);

    default List<LibFileLibrary> DTOs2DBs(List<LibFileLibraryDTO> libLibraryDTOS, CorChannel corChannel) {
        List<LibFileLibrary> libraries = new ArrayList<>();
        if (libLibraryDTOS.isEmpty() || libLibraryDTOS == null) {
            return null;
        }
        for (LibFileLibraryDTO dto : libLibraryDTOS) {
            libraries.add(DTO2DB(dto, corChannel));
        }
        return libraries;
    }

    @AfterMapping
    default void LibFileLibraryDTOToLibFileLibraryAfterMapping(LibFileLibraryDTO dto, @MappingTarget LibFileLibrary entity, @Context CorChannel corNetwork) {
        entity.setChannel(corNetwork);
    }
}
