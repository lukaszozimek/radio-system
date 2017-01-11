package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CRMLeadSourceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CRMLeadSource and its DTO CRMLeadSourceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CRMLeadSourceMapper {

    @Mapping(source = "network.id", target = "networkId")
    CRMLeadSourceDTO cRMLeadSourceToCRMLeadSourceDTO(CRMLeadSource cRMLeadSource);

    List<CRMLeadSourceDTO> cRMLeadSourcesToCRMLeadSourceDTOs(List<CRMLeadSource> cRMLeadSources);

    @Mapping(source = "networkId", target = "network")
    CRMLeadSource cRMLeadSourceDTOToCRMLeadSource(CRMLeadSourceDTO cRMLeadSourceDTO);

    List<CRMLeadSource> cRMLeadSourceDTOsToCRMLeadSources(List<CRMLeadSourceDTO> cRMLeadSourceDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
