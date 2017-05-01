package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.LibLabelPT;
import io.protone.custom.service.dto.LibPersonPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorPerson;
import io.protone.domain.LibLabel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity LibLabel and its DTO LibLabelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibLabelMapper {

    LibLabelPT DB2DTO(LibLabel lIBLabel);

    List<LibLabelPT> DBs2DTOs(List<LibLabel> lIBLabels);

    LibLabel DTO2DB(LibLabelPT dto, @Context CorNetwork network);

    default List<LibLabel> DTOs2DBs(List<LibLabelPT> dtos, @Context CorNetwork network) {
        List<LibLabel> corPeople = new ArrayList<>();
        if (dtos.isEmpty() || dtos == null) {
            return null;
        }
        for (LibLabelPT dto : dtos) {
            corPeople.add(DTO2DB(dto, network));
        }
        return corPeople;
    }

    @AfterMapping
    default void libLabelPTToLibLabelAfterMapping(LibPersonPT dto, @MappingTarget CorPerson entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

}
