package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CorContactDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CorContact and its DTO CorContactDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorContactMapper {

    @Mapping(source = "network.id", target = "networkId")
    @Mapping(source = "person.id", target = "personId")
    CorContactDTO corContactToCorContactDTO(CorContact corContact);

    List<CorContactDTO> corContactsToCorContactDTOs(List<CorContact> corContacts);

    @Mapping(source = "networkId", target = "network")
    @Mapping(source = "personId", target = "person")
    CorContact corContactDTOToCorContact(CorContactDTO corContactDTO);

    List<CorContact> corContactDTOsToCorContacts(List<CorContactDTO> corContactDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }

    default CorPerson corPersonFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorPerson corPerson = new CorPerson();
        corPerson.setId(id);
        return corPerson;
    }
}
