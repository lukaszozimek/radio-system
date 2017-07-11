package io.protone.core.mapper;


import io.protone.core.api.dto.CorTaxDTO;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorTax;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity CorRange and its DTO CorRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorTaxMapper {

    CorTax DTO2DB(CorTaxDTO corTaxDTO, @Context CorNetwork network);

    CorTaxDTO DB2DTO(CorTax corTax);

    List<CorTaxDTO> DBs2DTOs(List<CorTax> corTaxes);

    default List<CorTax> DTOs2DBs(List<CorTaxDTO> corTaxDTOList, @Context CorNetwork network) {
        List<CorTax> corTaxes = new ArrayList<>();
        if (corTaxDTOList.isEmpty() || corTaxDTOList == null) {
            return null;
        }
        for (CorTaxDTO dto : corTaxDTOList) {
            corTaxes.add(DTO2DB(dto, network));
        }
        return corTaxes;
    }

    @AfterMapping
    default void confTaxPTToCorTaxAfterMapping(CorTaxDTO dto, @MappingTarget CorTax entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

}
