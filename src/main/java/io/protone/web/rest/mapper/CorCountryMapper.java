package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.ConfCountryPt;
import io.protone.domain.CorCountry;
import io.protone.domain.CorNetwork;
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
public interface CorCountryMapper {

    CorCountry DTO2DB(ConfCountryPt confCountryPt, @Context CorNetwork corNetwork);

    ConfCountryPt DB2DTO(CorCountry corCountry);

    List<ConfCountryPt> DBs2DTOs(List<CorCountry> corCountries);

    default List<CorCountry> DTOs2DBs(List<ConfCountryPt> confCountryPts, CorNetwork networkId) {
        List<CorCountry> corCountries = new ArrayList<>();
        if (confCountryPts.isEmpty() || confCountryPts == null) {
            return null;
        }
        for (ConfCountryPt dto : confCountryPts) {
            corCountries.add(DTO2DB(dto, networkId));
        }
        return corCountries;
    }

    @AfterMapping
    default void confCountryPtToCorCountryAfterMapping(ConfCountryPt dto, @MappingTarget CorCountry entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
