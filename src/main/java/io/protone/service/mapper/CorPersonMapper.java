package io.protone.service.mapper;

import io.protone.custom.service.dto.ConfPersonPT;
import io.protone.custom.service.dto.LibPersonPT;
import io.protone.custom.service.dto.TraCustomerPersonPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorPerson;
import io.protone.service.mapper.CorContactMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity CorPerson and its DTO CorPersonDTO.
 */
@Mapper(componentModel = "spring", uses = {CorContactMapper.class})
public interface CorPersonMapper {

    ConfPersonPT DB2DTO(CorPerson cORPerson);

    List<ConfPersonPT> DBs2DTOs(List<CorPerson> cORPeople);

    CorPerson DTO2DB(ConfPersonPT cORPersonDTO);

    List<CorPerson> DTOs2DBs(List<ConfPersonPT> cORPersonDTOs);

    LibPersonPT corPerson2LibPersonPT(CorPerson db);

    List<LibPersonPT> corPersons2LibPersonPTs(List<CorPerson> cORPeople);

    CorPerson libPersonPT2CorPerson(LibPersonPT dto);

    List<CorPerson> libPersonPTs2corPersons(List<LibPersonPT> dtos);

    TraCustomerPersonPT corPerson2TraCustomerPersonPT(CorPerson person);

    CorPerson traCustomerPersonPT2CorPerson(TraCustomerPersonPT personPT, @Context CorNetwork network);

    default List<CorPerson> traCustomerPersonPTs2CorPersons(List<TraCustomerPersonPT> dtos, @Context CorNetwork networkId) {
        List<CorPerson> corPeople = new ArrayList<>();
        if (dtos.isEmpty() || dtos == null) {
            return null;
        }
        for (TraCustomerPersonPT dto : dtos) {
            corPeople.add(traCustomerPersonPT2CorPerson(dto, networkId));
        }
        return corPeople;
    }
}
