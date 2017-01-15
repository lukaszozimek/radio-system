package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfLeadSourcePT;
import io.protone.domain.CORNetwork;
import io.protone.domain.CRMLeadSource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CRMLeadSource and its DTO CRMLeadSourceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCRMLeadSourceMapper {

    ConfLeadSourcePT cRMLeadSourceToCRMLeadSourceDTO(CRMLeadSource cRMLeadSource);

    List<ConfLeadSourcePT> cRMLeadSourcesToCRMLeadSourceDTOs(List<CRMLeadSource> cRMLeadSources);

    CRMLeadSource cRMLeadSourceDTOToCRMLeadSource(ConfLeadSourcePT cRMLeadSourceDTO);

    List<ConfLeadSourcePT> cRMLeadSourceDTOsToCRMLeadSources(List<ConfLeadSourcePT> cRMLeadSourceDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
