package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibraryPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibLibrary;
import io.protone.domain.enumeration.LibCounterTypeEnum;
import io.protone.domain.enumeration.LibObjectTypeEnum;
import io.protone.repository.CorChannelRepository;
import io.protone.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface CustomLibLibraryMapperExt {

    LibraryPT DB2DTO(LibLibrary libLibrary);

    List<LibraryPT> DBs2DTOs(List<LibLibrary> libLibraries);

    LibLibrary DTO2DB(LibraryPT libLibraryDTO);

    List<LibLibrary> DTOs2DBs(List<LibraryPT> libLibraryDTOs);

}
