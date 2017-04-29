package io.protone.service.mapper;

import io.protone.custom.service.dto.CrmLeadPT;
import io.protone.custom.service.dto.LibraryPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmLead;
import io.protone.domain.LibLibrary;
import io.protone.domain.enumeration.LibCounterTypeEnum;
import io.protone.domain.enumeration.LibObjectTypeEnum;
import io.protone.repository.CorChannelRepository;
import io.protone.repository.UserRepository;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface LibLibraryMapper {

    LibraryPT DB2DTO(LibLibrary libLibrary);

    List<LibraryPT> DBs2DTOs(List<LibLibrary> libLibraries);

    LibLibrary DTO2DB(LibraryPT libLibraryDTO, @Context CorNetwork corNetwork);

    default List<LibLibrary> DTOs2DBs(List<LibraryPT> libraryPTS, CorNetwork networkId) {
        List<LibLibrary> crmLeads = new ArrayList<>();
        if (libraryPTS.isEmpty() || libraryPTS == null) {
            return null;
        }
        for (LibraryPT dto : libraryPTS) {
            crmLeads.add(DTO2DB(dto, networkId));
        }
        return crmLeads;
    }

    @AfterMapping
    default void crmLeadPTToCrmLeadAfterMapping(LibraryPT dto, @MappingTarget LibLibrary entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
