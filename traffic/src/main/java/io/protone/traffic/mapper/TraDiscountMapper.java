package io.protone.traffic.mapper;


import io.protone.core.domain.CorDiscount;
import io.protone.core.domain.CorNetwork;
import io.protone.traffic.api.dto.TraDiscountDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity TraDiscount and its DTO TraDiscountDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraDiscountMapper {

    TraDiscountDTO DB2DTO(CorDiscount traDiscount);

    List<TraDiscountDTO> DBs2DTOs(List<CorDiscount> traDiscounts);

    CorDiscount DTO2DB(TraDiscountDTO traDiscountDTO, @Context CorNetwork network);

    default List<CorDiscount> DTOs2DBs(List<TraDiscountDTO> traDiscountDTOs, @Context CorNetwork network) {
        List<CorDiscount> traDiscounts = new ArrayList<>();
        if (traDiscountDTOs.isEmpty() || traDiscountDTOs == null) {
            return null;
        }
        for (TraDiscountDTO dto : traDiscountDTOs) {
            traDiscounts.add(DTO2DB(dto, network));
        }
        return traDiscounts;
    }

    @AfterMapping
    default void confDiscountPTToTraDiscountAfterMapping(TraDiscountDTO dto, @MappingTarget CorDiscount entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
