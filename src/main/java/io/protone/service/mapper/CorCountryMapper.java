package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CorCountryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CorCountry and its DTO CorCountryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorCountryMapper {

    @Mapping(source = "tax.id", target = "taxId")
    @Mapping(source = "currnecy.id", target = "currnecyId")
    @Mapping(source = "network.id", target = "networkId")
    CorCountryDTO corCountryToCorCountryDTO(CorCountry corCountry);

    List<CorCountryDTO> corCountriesToCorCountryDTOs(List<CorCountry> corCountries);

    @Mapping(source = "taxId", target = "tax")
    @Mapping(source = "currnecyId", target = "currnecy")
    @Mapping(source = "networkId", target = "network")
    CorCountry corCountryDTOToCorCountry(CorCountryDTO corCountryDTO);

    List<CorCountry> corCountryDTOsToCorCountries(List<CorCountryDTO> corCountryDTOs);

    default CorTax corTaxFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorTax corTax = new CorTax();
        corTax.setId(id);
        return corTax;
    }

    default CorCurrency corCurrencyFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorCurrency corCurrency = new CorCurrency();
        corCurrency.setId(id);
        return corCurrency;
    }

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
