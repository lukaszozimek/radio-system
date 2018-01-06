package io.protone.crm.mapper;


import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmDiscount;
import io.protone.crm.api.dto.CrmDiscountDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity TraDiscount and its DTO CrmDiscountDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CrmDiscountMapper {

    CrmDiscountDTO DB2DTO(CrmDiscount traDiscount);

    List<CrmDiscountDTO> DBs2DTOs(List<CrmDiscount> traDiscounts);

    CrmDiscount DTO2DB(CrmDiscountDTO crmDiscountDTO, @Context CorChannel network);

    default List<CrmDiscount> DTOs2DBs(List<CrmDiscountDTO> crmDiscountDTOS, @Context CorChannel network) {
        List<CrmDiscount> traDiscounts = new ArrayList<>();
        if (crmDiscountDTOS.isEmpty() || crmDiscountDTOS == null) {
            return null;
        }
        for (CrmDiscountDTO dto : crmDiscountDTOS) {
            traDiscounts.add(DTO2DB(dto, network));
        }
        return traDiscounts;
    }

    @AfterMapping
    default void confDiscountPTToTraDiscountAfterMapping(CrmDiscountDTO dto, @MappingTarget CrmDiscount entity, @Context CorChannel corChannel) {
        entity.setChannel(corChannel);
    }
}
