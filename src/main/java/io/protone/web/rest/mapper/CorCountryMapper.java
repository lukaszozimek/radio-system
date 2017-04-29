package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.ConfCountryPt;
import io.protone.domain.CorCountry;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity CorRange and its DTO CorRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorCountryMapper {

    CorCountry DTO2DB(ConfCountryPt confCountryPt);

    ConfCountryPt DB2DTO(CorCountry corCountry);

    List<ConfCountryPt> DBs2DTOs(List<CorCountry> corCountries);

    List<CorCountry> DTOs2DBs(List<ConfCountryPt> confCountryPts);

}
