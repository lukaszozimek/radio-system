package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CRMLeadDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CRMLead and its DTO CRMLeadDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CRMLeadMapper {

    @Mapping(source = "network.id", target = "networkId")
    CRMLeadDTO cRMLeadToCRMLeadDTO(CRMLead cRMLead);

    List<CRMLeadDTO> cRMLeadsToCRMLeadDTOs(List<CRMLead> cRMLeads);

    @Mapping(source = "networkId", target = "network")
    CRMLead cRMLeadDTOToCRMLead(CRMLeadDTO cRMLeadDTO);

    List<CRMLead> cRMLeadDTOsToCRMLeads(List<CRMLeadDTO> cRMLeadDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
