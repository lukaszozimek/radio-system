package io.protone.library.mapper;


import io.protone.core.domain.CorChannel;
import io.protone.library.api.dto.LibLabelDTO;
import io.protone.library.domain.LibLabel;
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

    LibLabel DTO2DB(LibLabelDTO dto, @Context CorChannel network);

    default List<LibLabel> DTOs2DBs(List<LibLabelDTO> dtos, @Context CorChannel network) {
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
    default void libLabelPTToLibLabelAfterMapping(LibLabelDTO dto, @MappingTarget LibLabel entity, @Context CorChannel corNetwork) {
        entity.setChannel(corNetwork);
    }

}
