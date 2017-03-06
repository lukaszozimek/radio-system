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

    @Mapping(source = "networkId", target = "network")
    CorCountry DTO2DB(ConfCountryPt confCountryPt);

    @Mapping(source = "network.id", target = "networkId")
    ConfCountryPt DB2DTO(CorCountry corCountry);

    List<ConfCountryPt> DBs2DTOs(List<CorCountry> corCountries);

    List<CorCountry> DTOs2DBs(List<ConfCountryPt> confCountryPts);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
