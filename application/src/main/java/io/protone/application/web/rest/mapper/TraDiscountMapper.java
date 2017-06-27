package io.protone.application.web.rest.mapper;

import io.protone.domain.CorNetwork;
import io.protone.domain.TraDiscount;
import io.protone.web.rest.dto.traffic.TraDiscountDTO;
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

    TraDiscountDTO DB2DTO(TraDiscount traDiscount);

    List<TraDiscountDTO> DBs2DTOs(List<TraDiscount> traDiscounts);

    TraDiscount DTO2DB(TraDiscountDTO traDiscountDTO, @Context CorNetwork network);

    default List<TraDiscount> DTOs2DBs(List<TraDiscountDTO> traDiscountDTOs, @Context CorNetwork network) {
        List<TraDiscount> traDiscounts = new ArrayList<>();
        if (traDiscountDTOs.isEmpty() || traDiscountDTOs == null) {
            return null;
        }
        for (TraDiscountDTO dto : traDiscountDTOs) {
            traDiscounts.add(DTO2DB(dto, network));
        }
        return traDiscounts;
    }

    @AfterMapping
    default void confDiscountPTToTraDiscountAfterMapping(TraDiscountDTO dto, @MappingTarget TraDiscount entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
