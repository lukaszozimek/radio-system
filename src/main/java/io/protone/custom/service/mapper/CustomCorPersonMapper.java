package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfPersonPT;
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
public interface CustomCorPersonMapper {

    ConfPersonPT DB2DTO(CorPerson cORPerson);

    List<ConfPersonPT> DBs2DTOs(List<CorPerson> cORPeople);

    CorPerson DTO2DB(ConfPersonPT cORPersonDTO);

    List<CorPerson> DTOs2DBs(List<ConfPersonPT> cORPersonDTOs);

    LibPersonPT db2Dto(CorPerson db);

    List<LibPersonPT> dBs2DTOs(List<CorPerson> cORPeople);

    CorPerson dto2Db(LibPersonPT dto);

    List<CorPerson> dtos2Dbs(List<LibPersonPT> dtos);

}
