package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreContactPT;
import io.protone.domain.CorContact;
import io.protone.domain.CorNetwork;
import io.protone.service.dto.CorContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

/**
 * Mapper for the entity CorContact and its DTO CorContactDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCorContactMapper {


    @Mapping(source = "network.id", target = "networkId")
    CoreContactPT cORContactToCorContactDTO(CorContact cORContact);

    List<CoreContactPT> cORContactsToCorContactDTOs(Set<CorContact> cORContacts);

    @Mapping(source = "networkId", target = "network")
    CorContact cORContactDTOToCorContact(CoreContactPT cORContactDTO);

    List<CorContact> cORContactDTOsToCorContacts(List<CoreContactPT> cORContactDTOs);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
