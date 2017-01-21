package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TRADiscountDTO;

import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for the entity TRADiscount and its DTO TRADiscountDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TRADiscountMapper {

    TRADiscountDTO tRADiscountToTRADiscountDTO(TRADiscount tRADiscount);

    List<TRADiscountDTO> tRADiscountsToTRADiscountDTOs(List<TRADiscount> tRADiscounts);

    TRADiscount tRADiscountDTOToTRADiscount(TRADiscountDTO tRADiscountDTO);

    List<TRADiscount> tRADiscountDTOsToTRADiscounts(List<TRADiscountDTO> tRADiscountDTOs);


}
