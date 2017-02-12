package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CorCurrencyDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CorCurrency and its DTO CorCurrencyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorCurrencyMapper {

    @Mapping(source = "network.id", target = "networkId")
    CorCurrencyDTO corCurrencyToCorCurrencyDTO(CorCurrency corCurrency);

    List<CorCurrencyDTO> corCurrenciesToCorCurrencyDTOs(List<CorCurrency> corCurrencies);

    @Mapping(source = "networkId", target = "network")
    CorCurrency corCurrencyDTOToCorCurrency(CorCurrencyDTO corCurrencyDTO);

    List<CorCurrency> corCurrencyDTOsToCorCurrencies(List<CorCurrencyDTO> corCurrencyDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
