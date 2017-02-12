package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfLeadStatusPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmLeadStatus;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity CrmLeadStatus and its DTO CrmLeadStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCrmLeadStatusMapper {

    ConfLeadStatusPT cRMLeadStatusToCrmLeadStatusDTO(CrmLeadStatus cRMLeadStatus);

    List<ConfLeadStatusPT> cRMLeadStatusesToCrmLeadStatusDTOs(List<CrmLeadStatus> cRMLeadStatuses);

    CrmLeadStatus cRMLeadStatusDTOToCrmLeadStatus(ConfLeadStatusPT cRMLeadStatusDTO);

    List<CrmLeadStatus> cRMLeadStatusDTOsToCrmLeadStatuses(List<ConfLeadStatusPT> cRMLeadStatusDTOs);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
