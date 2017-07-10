package io.protone.traffic.mapper;


import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmDiscount;
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

    TraDiscountDTO DB2DTO(CrmDiscount traDiscount);

    List<TraDiscountDTO> DBs2DTOs(List<CrmDiscount> traDiscounts);

    CrmDiscount DTO2DB(TraDiscountDTO traDiscountDTO, @Context CorNetwork network);

    default List<CrmDiscount> DTOs2DBs(List<TraDiscountDTO> traDiscountDTOs, @Context CorNetwork network) {
        List<CrmDiscount> traDiscounts = new ArrayList<>();
        if (traDiscountDTOs.isEmpty() || traDiscountDTOs == null) {
            return null;
        }
        for (TraDiscountDTO dto : traDiscountDTOs) {
            traDiscounts.add(DTO2DB(dto, network));
        }
        return traDiscounts;
    }

    @AfterMapping
    default void confDiscountPTToTraDiscountAfterMapping(TraDiscountDTO dto, @MappingTarget CrmDiscount entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
