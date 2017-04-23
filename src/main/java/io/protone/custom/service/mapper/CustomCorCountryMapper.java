package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfCountryPt;
import io.protone.custom.service.dto.ConfTaxPT;
import io.protone.domain.CorCountry;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorTax;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorRange and its DTO CorRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCorCountryMapper {

    CorCountry DTO2DB(ConfCountryPt confCountryPt);

    ConfCountryPt DB2DTO(CorCountry corCountry);

    List<ConfCountryPt> DBs2DTOs(List<CorCountry> corCountries);

    List<CorCountry> DTOs2DBs(List<ConfCountryPt> confCountryPts);

}
