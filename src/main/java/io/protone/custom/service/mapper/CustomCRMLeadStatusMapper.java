package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfLeadStatusPT;
import io.protone.domain.CORNetwork;
import io.protone.domain.CRMLeadStatus;
import io.protone.service.dto.CRMLeadStatusDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CRMLeadStatus and its DTO CRMLeadStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCRMLeadStatusMapper {

    ConfLeadStatusPT cRMLeadStatusToCRMLeadStatusDTO(CRMLeadStatus cRMLeadStatus);

    List<ConfLeadStatusPT> cRMLeadStatusesToCRMLeadStatusDTOs(List<CRMLeadStatus> cRMLeadStatuses);

    CRMLeadStatus cRMLeadStatusDTOToCRMLeadStatus(ConfLeadStatusPT cRMLeadStatusDTO);

    List<CRMLeadStatus> cRMLeadStatusDTOsToCRMLeadStatuses(List<ConfLeadStatusPT> cRMLeadStatusDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
