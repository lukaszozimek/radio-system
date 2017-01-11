package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TRAOrderDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TRAOrder and its DTO TRAOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TRAOrderMapper {

    @Mapping(source = "campaign.id", target = "campaignId")
    @Mapping(source = "customer.id", target = "customerId")
    TRAOrderDTO tRAOrderToTRAOrderDTO(TRAOrder tRAOrder);

    List<TRAOrderDTO> tRAOrdersToTRAOrderDTOs(List<TRAOrder> tRAOrders);

    @Mapping(source = "campaignId", target = "campaign")
    @Mapping(source = "customerId", target = "customer")
    TRAOrder tRAOrderDTOToTRAOrder(TRAOrderDTO tRAOrderDTO);

    List<TRAOrder> tRAOrderDTOsToTRAOrders(List<TRAOrderDTO> tRAOrderDTOs);

    default TRACampaign tRACampaignFromId(Long id) {
        if (id == null) {
            return null;
        }
        TRACampaign tRACampaign = new TRACampaign();
        tRACampaign.setId(id);
        return tRACampaign;
    }

    default TRACustomer tRACustomerFromId(Long id) {
        if (id == null) {
            return null;
        }
        TRACustomer tRACustomer = new TRACustomer();
        tRACustomer.setId(id);
        return tRACustomer;
    }
}
