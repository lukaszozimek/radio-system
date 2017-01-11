package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CRMOpportunityDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CRMOpportunity and its DTO CRMOpportunityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CRMOpportunityMapper {

    @Mapping(source = "network.id", target = "networkId")
    CRMOpportunityDTO cRMOpportunityToCRMOpportunityDTO(CRMOpportunity cRMOpportunity);

    List<CRMOpportunityDTO> cRMOpportunitiesToCRMOpportunityDTOs(List<CRMOpportunity> cRMOpportunities);

    @Mapping(source = "networkId", target = "network")
    CRMOpportunity cRMOpportunityDTOToCRMOpportunity(CRMOpportunityDTO cRMOpportunityDTO);

    List<CRMOpportunity> cRMOpportunityDTOsToCRMOpportunities(List<CRMOpportunityDTO> cRMOpportunityDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
