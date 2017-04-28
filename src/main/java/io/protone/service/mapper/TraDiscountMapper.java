package io.protone.service.mapper;

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
public interface TraDiscountMapper {

    ConfDiscountPT DB2DTO(TraDiscount traDiscount);

    List<ConfDiscountPT> DBs2DTOs(List<TraDiscount> traDiscounts);

    TraDiscount DTO2DB(ConfDiscountPT traDiscountDTO);

    List<TraDiscount> DTOs2DBs(List<ConfDiscountPT> traDiscountDTOs);
}
