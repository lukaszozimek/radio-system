package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.LibLabelDTO;
import io.protone.domain.CorNetwork;
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

    LibLabelDTO DB2DTO(LibLabel lIBLabel);

    List<LibLabelDTO> DBs2DTOs(List<LibLabel> lIBLabels);

    LibLabel DTO2DB(LibLabelDTO dto, @Context CorNetwork network);

    default List<LibLabel> DTOs2DBs(List<LibLabelDTO> dtos, @Context CorNetwork network) {
        List<LibLabel> corPeople = new ArrayList<>();
        if (dtos.isEmpty() || dtos == null) {
            return null;
        }
        for (LibLabelDTO dto : dtos) {
            corPeople.add(DTO2DB(dto, network));
        }
        return corPeople;
    }

    @AfterMapping
    default void libLabelPTToLibLabelAfterMapping(LibLabelDTO dto, @MappingTarget LibLabel entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

}
