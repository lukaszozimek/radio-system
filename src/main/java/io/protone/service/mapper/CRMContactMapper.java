package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CRMContactDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CRMContact and its DTO CRMContactDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CRMContactMapper {

    @Mapping(source = "network.id", target = "networkId")
    CRMContactDTO cRMContactToCRMContactDTO(CRMContact cRMContact);

    List<CRMContactDTO> cRMContactsToCRMContactDTOs(List<CRMContact> cRMContacts);

    @Mapping(source = "networkId", target = "network")
    CRMContact cRMContactDTOToCRMContact(CRMContactDTO cRMContactDTO);

    List<CRMContact> cRMContactDTOsToCRMContacts(List<CRMContactDTO> cRMContactDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
