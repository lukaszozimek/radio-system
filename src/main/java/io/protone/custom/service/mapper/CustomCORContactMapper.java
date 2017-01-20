package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreContactPT;
import io.protone.domain.CORContact;
import io.protone.domain.CORNetwork;
import io.protone.service.dto.CORContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CORContact and its DTO CORContactDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCORContactMapper {


    @Mapping(source = "network.id", target = "networkId")
    CoreContactPT cORContactToCORContactDTO(CORContact cORContact);

    List<CoreContactPT> cORContactsToCORContactDTOs(List<CORContact> cORContacts);

    @Mapping(source = "networkId", target = "network")
    CORContact cORContactDTOToCORContact(CoreContactPT cORContactDTO);

    List<CORContact> cORContactDTOsToCORContacts(List<CoreContactPT> cORContactDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
