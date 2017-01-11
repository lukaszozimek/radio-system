package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TRABlockPriceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TRABlockPrice and its DTO TRABlockPriceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TRABlockPriceMapper {

    TRABlockPriceDTO tRABlockPriceToTRABlockPriceDTO(TRABlockPrice tRABlockPrice);

    List<TRABlockPriceDTO> tRABlockPricesToTRABlockPriceDTOs(List<TRABlockPrice> tRABlockPrices);

    TRABlockPrice tRABlockPriceDTOToTRABlockPrice(TRABlockPriceDTO tRABlockPriceDTO);

    List<TRABlockPrice> tRABlockPriceDTOsToTRABlockPrices(List<TRABlockPriceDTO> tRABlockPriceDTOs);
}
