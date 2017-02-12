package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfLeadSourcePT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmLeadSource;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity CrmLeadSource and its DTO CrmLeadSourceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCrmLeadSourceMapper {

    ConfLeadSourcePT cRMLeadSourceToCrmLeadSourceDTO(CrmLeadSource cRMLeadSource);

    List<ConfLeadSourcePT> cRMLeadSourcesToCrmLeadSourceDTOs(List<CrmLeadSource> cRMLeadSources);

    CrmLeadSource cRMLeadSourceDTOToCrmLeadSource(ConfLeadSourcePT cRMLeadSourceDTO);

    List<ConfLeadSourcePT> cRMLeadSourceDTOsToCrmLeadSources(List<ConfLeadSourcePT> cRMLeadSourceDTOs);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
