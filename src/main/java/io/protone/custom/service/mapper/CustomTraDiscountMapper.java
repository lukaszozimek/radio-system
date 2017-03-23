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

    @Mapping(source = "network.id", target = "networkId")
    ConfDiscountPT traDiscountToTraDiscountDTO(TraDiscount traDiscount);

    List<ConfDiscountPT> traDiscountsToTraDiscountDTOs(List<TraDiscount> traDiscounts);

    @Mapping(source = "networkId", target = "network")
    TraDiscount traDiscountDTOToTraDiscount(ConfDiscountPT traDiscountDTO);

    List<TraDiscount> traDiscountDTOsToTraDiscounts(List<ConfDiscountPT> traDiscountDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
