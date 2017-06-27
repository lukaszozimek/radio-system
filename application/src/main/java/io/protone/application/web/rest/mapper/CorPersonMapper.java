package io.protone.application.web.rest.mapper;

import io.protone.domain.CorNetwork;
import io.protone.domain.CorPerson;
import io.protone.web.rest.dto.cor.CorPersonDTO;
import io.protone.web.rest.dto.library.LibPersonDTO;
import io.protone.web.rest.dto.traffic.TraCustomerPersonDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity CorPerson and its DTO CorPersonDTO.
 */
@Mapper(componentModel = "spring", uses = {CorContactMapper.class})
public interface CorPersonMapper {

    CorPersonDTO DB2DTO(CorPerson cORPerson);

    List<CorPersonDTO> DBs2DTOs(List<CorPerson> cORPeople);

    CorPerson DTO2DB(CorPersonDTO cORPersonDTO, @Context CorNetwork corNetwork);
    @AfterMapping
    default void corPersonDTOToCorPersonAfterMapping(CorPersonDTO dto, @MappingTarget CorPerson entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
    default  List<CorPerson> DTOs2DBs(List<CorPersonDTO> cORPersonDTOs, @Context CorNetwork network){
        List<CorPerson> corPeople = new ArrayList<>();
        if (cORPersonDTOs.isEmpty() || cORPersonDTOs == null) {
            return null;
        }
        for (CorPersonDTO dto : cORPersonDTOs) {
            corPeople.add(DTO2DB(dto, network));
        }
        return corPeople;
    }

    LibPersonDTO corPerson2LibPersonPT(CorPerson db);

    List<LibPersonDTO> corPersons2LibPersonPTs(List<CorPerson> cORPeople);

    CorPerson libPersonPT2CorPerson(LibPersonDTO dto, @Context CorNetwork network);

    default List<CorPerson> libPersonPTs2corPersons(List<LibPersonDTO> dtos, @Context CorNetwork network) {
        List<CorPerson> corPeople = new ArrayList<>();
        if (dtos.isEmpty() || dtos == null) {
            return null;
        }
        for (LibPersonDTO dto : dtos) {
            corPeople.add(libPersonPT2CorPerson(dto, network));
        }
        return corPeople;
    }

    @AfterMapping
    default void libPersonPTToCorPersonAfterMapping(LibPersonDTO dto, @MappingTarget CorPerson entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

    TraCustomerPersonDTO corPerson2TraCustomerPersonPT(CorPerson person);

    CorPerson traCustomerPersonPT2CorPerson(TraCustomerPersonDTO personPT, @Context CorNetwork network);

    default List<CorPerson> traCustomerPersonPTs2CorPersons(List<TraCustomerPersonDTO> dtos, @Context CorNetwork networkId) {
        List<CorPerson> corPeople = new ArrayList<>();
        if (dtos.isEmpty() || dtos == null) {
            return null;
        }
        for (TraCustomerPersonDTO dto : dtos) {
            corPeople.add(traCustomerPersonPT2CorPerson(dto, networkId));
        }
        return corPeople;
    }


    @AfterMapping
    default void traCustomerPersonPTToCorPersonAfterMapping(TraCustomerPersonDTO dto, @MappingTarget CorPerson entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
