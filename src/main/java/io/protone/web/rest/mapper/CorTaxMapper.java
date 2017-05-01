package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.ConfTaxPT;
import io.protone.custom.service.dto.LibPersonPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorPerson;
import io.protone.domain.CorTax;
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

    CorTax DTO2DB(ConfTaxPT confTaxPT, @Context CorNetwork network);

    ConfTaxPT DB2DTO(CorTax corTax);

    List<ConfTaxPT> DBs2DTOs(List<CorTax> corTaxes);

    default List<CorTax> DTOs2DBs(List<ConfTaxPT> confTaxPTList, @Context CorNetwork network) {
        List<CorTax> corTaxes = new ArrayList<>();
        if (confTaxPTList.isEmpty() || confTaxPTList == null) {
            return null;
        }
        for (ConfTaxPT dto : confTaxPTList) {
            corTaxes.add(DTO2DB(dto, network));
        }
        return corTaxes;
    }

    @AfterMapping
    default void confTaxPTToCorTaxAfterMapping(ConfTaxPT dto, @MappingTarget CorTax entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

}
