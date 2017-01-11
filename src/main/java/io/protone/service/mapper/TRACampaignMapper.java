package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TRACampaignDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TRACampaign and its DTO TRACampaignDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TRACampaignMapper {

    @Mapping(source = "customer.id", target = "customerId")
    TRACampaignDTO tRACampaignToTRACampaignDTO(TRACampaign tRACampaign);

    List<TRACampaignDTO> tRACampaignsToTRACampaignDTOs(List<TRACampaign> tRACampaigns);

    @Mapping(source = "customerId", target = "customer")
    TRACampaign tRACampaignDTOToTRACampaign(TRACampaignDTO tRACampaignDTO);

    List<TRACampaign> tRACampaignDTOsToTRACampaigns(List<TRACampaignDTO> tRACampaignDTOs);

    default TRACustomer tRACustomerFromId(Long id) {
        if (id == null) {
            return null;
        }
        TRACustomer tRACustomer = new TRACustomer();
        tRACustomer.setId(id);
        return tRACustomer;
    }
}
