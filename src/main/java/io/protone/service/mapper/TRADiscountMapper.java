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

    @Mapping(source = "customer.id", target = "customerId")
    TRADiscountDTO tRADiscountToTRADiscountDTO(TRADiscount tRADiscount);

    List<TRADiscountDTO> tRADiscountsToTRADiscountDTOs(List<TRADiscount> tRADiscounts);

    @Mapping(source = "customerId", target = "customer")
    TRADiscount tRADiscountDTOToTRADiscount(TRADiscountDTO tRADiscountDTO);

    List<TRADiscount> tRADiscountDTOsToTRADiscounts(List<TRADiscountDTO> tRADiscountDTOs);

    default TRACustomer tRACustomerFromId(Long id) {
        if (id == null) {
            return null;
        }
        TRACustomer tRACustomer = new TRACustomer();
        tRACustomer.setId(id);
        return tRACustomer;
    }
}
