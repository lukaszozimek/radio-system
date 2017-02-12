package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CrmLeadSourceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CrmLeadSource and its DTO CrmLeadSourceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CrmLeadSourceMapper {

    @Mapping(source = "network.id", target = "networkId")
    CrmLeadSourceDTO crmLeadSourceToCrmLeadSourceDTO(CrmLeadSource crmLeadSource);

    List<CrmLeadSourceDTO> crmLeadSourcesToCrmLeadSourceDTOs(List<CrmLeadSource> crmLeadSources);

    @Mapping(source = "networkId", target = "network")
    CrmLeadSource crmLeadSourceDTOToCrmLeadSource(CrmLeadSourceDTO crmLeadSourceDTO);

    List<CrmLeadSource> crmLeadSourceDTOsToCrmLeadSources(List<CrmLeadSourceDTO> crmLeadSourceDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
