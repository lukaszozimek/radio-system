package io.protone.web.rest.mapper;

import io.protone.web.rest.dto.cor.CorCountryDTO;
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

    CorCountry DTO2DB(CorCountryDTO corCountryDTO, @Context CorNetwork corNetwork);

    CorCountryDTO DB2DTO(CorCountry corCountry);

    List<CorCountryDTO> DBs2DTOs(List<CorCountry> corCountries);

    default List<CorCountry> DTOs2DBs(List<CorCountryDTO> corCountryDTOS, CorNetwork networkId) {
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
    default void confCountryPtToCorCountryAfterMapping(CorCountryDTO dto, @MappingTarget CorCountry entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
