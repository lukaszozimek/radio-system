package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibPersonPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorPerson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorPerson and its DTO CorPersonDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCorPersonMapperExt {

    LibPersonPT DB2DTO(CorPerson db);

    List<LibPersonPT> DBs2DTOs(List<CorPerson> cORPeople);

    CorPerson DTO2DB(LibPersonPT dto);

    List<CorPerson> DTOs2DBs(List<LibPersonPT> dtos);

}
