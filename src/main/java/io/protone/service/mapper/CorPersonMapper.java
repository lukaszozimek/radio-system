package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CorPersonDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CorPerson and its DTO CorPersonDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorPersonMapper {

    @Mapping(source = "network.id", target = "networkId")
    CorPersonDTO corPersonToCorPersonDTO(CorPerson corPerson);

    List<CorPersonDTO> corPeopleToCorPersonDTOs(List<CorPerson> corPeople);

    @Mapping(source = "networkId", target = "network")
    @Mapping(target = "contacts", ignore = true)
    CorPerson corPersonDTOToCorPerson(CorPersonDTO corPersonDTO);

    List<CorPerson> corPersonDTOsToCorPeople(List<CorPersonDTO> corPersonDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
