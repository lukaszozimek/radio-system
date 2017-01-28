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

    TRACampaignDTO tRACampaignToTRACampaignDTO(TRACampaign tRACampaign);

    List<TRACampaignDTO> tRACampaignsToTRACampaignDTOs(List<TRACampaign> tRACampaigns);

    TRACampaign tRACampaignDTOToTRACampaign(TRACampaignDTO tRACampaignDTO);

    List<TRACampaign> tRACampaignDTOsToTRACampaigns(List<TRACampaignDTO> tRACampaignDTOs);


}
