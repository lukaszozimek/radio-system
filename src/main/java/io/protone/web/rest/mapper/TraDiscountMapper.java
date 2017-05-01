package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.ConfDiscountPT;
import io.protone.custom.service.dto.LibPersonPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorPerson;
import io.protone.domain.TraDiscount;
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

    ConfDiscountPT DB2DTO(TraDiscount traDiscount);

    List<ConfDiscountPT> DBs2DTOs(List<TraDiscount> traDiscounts);

    TraDiscount DTO2DB(ConfDiscountPT traDiscountDTO, @Context CorNetwork network);

    default List<TraDiscount> DTOs2DBs(List<ConfDiscountPT> traDiscountDTOs, @Context CorNetwork network) {
        List<TraDiscount> traDiscounts = new ArrayList<>();
        if (traDiscountDTOs.isEmpty() || traDiscountDTOs == null) {
            return null;
        }
        for (ConfDiscountPT dto : traDiscountDTOs) {
            traDiscounts.add(DTO2DB(dto, network));
        }
        return traDiscounts;
    }

    @AfterMapping
    default void confDiscountPTToTraDiscountAfterMapping(ConfDiscountPT dto, @MappingTarget TraDiscount entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
