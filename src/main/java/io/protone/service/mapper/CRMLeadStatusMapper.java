package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CRMLeadStatusDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CRMLeadStatus and its DTO CRMLeadStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CRMLeadStatusMapper {

    @Mapping(source = "network.id", target = "networkId")
    CRMLeadStatusDTO cRMLeadStatusToCRMLeadStatusDTO(CRMLeadStatus cRMLeadStatus);

    List<CRMLeadStatusDTO> cRMLeadStatusesToCRMLeadStatusDTOs(List<CRMLeadStatus> cRMLeadStatuses);

    @Mapping(source = "networkId", target = "network")
    CRMLeadStatus cRMLeadStatusDTOToCRMLeadStatus(CRMLeadStatusDTO cRMLeadStatusDTO);

    List<CRMLeadStatus> cRMLeadStatusDTOsToCRMLeadStatuses(List<CRMLeadStatusDTO> cRMLeadStatusDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
