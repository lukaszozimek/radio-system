package io.protone.core.mapper;


import io.protone.core.api.dto.CorCountryDTO;
import io.protone.core.domain.CorCountry;
import io.protone.core.domain.CorOrganization;
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

    CorCountry DTO2DB(CorCountryDTO corCountryDTO, @Context CorOrganization corNetwork);

    CorCountryDTO DB2DTO(CorCountry corCountry);

    List<CorCountryDTO> DBs2DTOs(List<CorCountry> corCountries);

    default List<CorCountry> DTOs2DBs(List<CorCountryDTO> corCountryDTOS, CorOrganization networkId) {
        List<CorCountry> corCountries = new ArrayList<>();
        if (corCountryDTOS.isEmpty() || corCountryDTOS == null) {
            return null;
        }
        for (CorCountryDTO dto : corCountryDTOS) {
            corCountries.add(DTO2DB(dto, networkId));
        }
        return corCountries;
    }

    @AfterMapping
    default void confCountryPtToCorCountryAfterMapping(CorCountryDTO dto, @MappingTarget CorCountry entity, @Context CorOrganization corNetwork) {
        entity.setOrganization(corNetwork);
    }
}
