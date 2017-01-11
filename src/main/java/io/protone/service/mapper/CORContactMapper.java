package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CORContactDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CORContact and its DTO CORContactDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CORContactMapper {

    @Mapping(source = "network.id", target = "networkId")
    CORContactDTO cORContactToCORContactDTO(CORContact cORContact);

    List<CORContactDTO> cORContactsToCORContactDTOs(List<CORContact> cORContacts);

    @Mapping(source = "networkId", target = "network")
    CORContact cORContactDTOToCORContact(CORContactDTO cORContactDTO);

    List<CORContact> cORContactDTOsToCORContacts(List<CORContactDTO> cORContactDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
