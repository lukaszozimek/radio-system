package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfPersonPT;
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

    ConfPersonPT cORPersonToCorPersonDTO(CorPerson cORPerson);

    List<ConfPersonPT> cORPeopleToCorPersonDTOs(List<CorPerson> cORPeople);

    CorPerson cORPersonDTOToCorPerson(ConfPersonPT cORPersonDTO);

    List<CorPerson> cORPersonDTOsToCORPeople(List<ConfPersonPT> cORPersonDTOs);

}
