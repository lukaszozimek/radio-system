package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TraDiscountDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TraDiscount and its DTO TraDiscountDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraDiscountMapper {

    @Mapping(source = "network.id", target = "networkId")
    TraDiscountDTO traDiscountToTraDiscountDTO(TraDiscount traDiscount);

    List<TraDiscountDTO> traDiscountsToTraDiscountDTOs(List<TraDiscount> traDiscounts);

    @Mapping(source = "networkId", target = "network")
    TraDiscount traDiscountDTOToTraDiscount(TraDiscountDTO traDiscountDTO);

    List<TraDiscount> traDiscountDTOsToTraDiscounts(List<TraDiscountDTO> traDiscountDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
