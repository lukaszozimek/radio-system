package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfTaxPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorTax;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorRange and its DTO CorRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCorTaxMapper {

    CorTax DTO2DB(ConfTaxPT confTaxPT);

    ConfTaxPT DB2DTO(CorTax corTax);

    List<ConfTaxPT> DBs2DTOs(List<CorTax> corTaxes);

    List<CorTax> DTOs2DBs(List<ConfTaxPT> confTaxPTList);

}
