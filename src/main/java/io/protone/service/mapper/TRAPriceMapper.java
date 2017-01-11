package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TRAPriceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TRAPrice and its DTO TRAPriceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TRAPriceMapper {

    @Mapping(source = "network.id", target = "networkId")
    TRAPriceDTO tRAPriceToTRAPriceDTO(TRAPrice tRAPrice);

    List<TRAPriceDTO> tRAPricesToTRAPriceDTOs(List<TRAPrice> tRAPrices);

    @Mapping(source = "networkId", target = "network")
    TRAPrice tRAPriceDTOToTRAPrice(TRAPriceDTO tRAPriceDTO);

    List<TRAPrice> tRAPriceDTOsToTRAPrices(List<TRAPriceDTO> tRAPriceDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
