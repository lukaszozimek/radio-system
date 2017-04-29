package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.ConfTaxPT;
import io.protone.domain.CorTax;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity CorRange and its DTO CorRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorTaxMapper {

    CorTax DTO2DB(ConfTaxPT confTaxPT);

    ConfTaxPT DB2DTO(CorTax corTax);

    List<ConfTaxPT> DBs2DTOs(List<CorTax> corTaxes);

    List<CorTax> DTOs2DBs(List<ConfTaxPT> confTaxPTList);

}
