package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TraPriceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TraPrice and its DTO TraPriceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraPriceMapper {

    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "network.id", target = "networkId")
    TraPriceDTO traPriceToTraPriceDTO(TraPrice traPrice);

    List<TraPriceDTO> traPricesToTraPriceDTOs(List<TraPrice> traPrices);

    @Mapping(source = "currencyId", target = "currency")
    @Mapping(source = "networkId", target = "network")
    TraPrice traPriceDTOToTraPrice(TraPriceDTO traPriceDTO);

    List<TraPrice> traPriceDTOsToTraPrices(List<TraPriceDTO> traPriceDTOs);

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
