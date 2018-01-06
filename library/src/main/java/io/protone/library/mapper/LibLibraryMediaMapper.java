package io.protone.library.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorChannelMapper;
import io.protone.library.api.dto.LibMediaItemDTO;
import io.protone.library.api.dto.LibMediaLibraryDTO;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {CorChannelMapper.class})
public interface LibLibraryMediaMapper {

    @Mapping(source = "mainImage.publicUrl", target = "publicUrl")
    LibMediaLibraryDTO DB2DTO(LibMediaLibrary libMediaLibrary);

    List<LibMediaLibraryDTO> DBs2DTOs(List<LibMediaLibrary> libLibraries);

    @Mapping(source = "channel", target = "channel")
    @Mapping(source = "sharedChannels", target = "sharedChannels")
    LibMediaLibrary DTO2DB(LibMediaLibraryDTO libMediaLibraryDTO, @Context CorChannel corChannel);

    default List<LibMediaLibrary> DTOs2DBs(List<LibMediaLibraryDTO> libMediaLibraryDTOS, CorChannel corChannel) {
        List<LibMediaLibrary> crmLeads = new ArrayList<>();
        if (libMediaLibraryDTOS.isEmpty() || libMediaLibraryDTOS == null) {
            return null;
        }
        for (LibMediaLibraryDTO dto : libMediaLibraryDTOS) {
            crmLeads.add(DTO2DB(dto, corChannel));
        }
        return crmLeads;
    }
    @AfterMapping
    default void LibMediaLibraryDTOToLibMediaLibraryAfterMapping(LibMediaLibraryDTO  dto, @MappingTarget LibMediaLibrary entity, @Context CorChannel corNetwork) {
        entity.setChannel(corNetwork);
    }
}

