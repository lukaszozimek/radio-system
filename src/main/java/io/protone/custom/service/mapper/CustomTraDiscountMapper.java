package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfDiscountPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraDiscount;
import io.protone.service.dto.TraDiscountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity TraDiscount and its DTO TraDiscountDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomTraDiscountMapper {

    ConfDiscountPT traDiscountToTraDiscountDTO(TraDiscount traDiscount);

    List<ConfDiscountPT> traDiscountsToTraDiscountDTOs(List<TraDiscount> traDiscounts);

    TraDiscount traDiscountDTOToTraDiscount(ConfDiscountPT traDiscountDTO);

    List<TraDiscount> traDiscountDTOsToTraDiscounts(List<ConfDiscountPT> traDiscountDTOs);
}
