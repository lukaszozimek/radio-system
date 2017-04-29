package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.ConfDiscountPT;
import io.protone.domain.TraDiscount;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity TraDiscount and its DTO TraDiscountDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraDiscountMapper {

    ConfDiscountPT DB2DTO(TraDiscount traDiscount);

    List<ConfDiscountPT> DBs2DTOs(List<TraDiscount> traDiscounts);

    TraDiscount DTO2DB(ConfDiscountPT traDiscountDTO);

    List<TraDiscount> DTOs2DBs(List<ConfDiscountPT> traDiscountDTOs);
}
