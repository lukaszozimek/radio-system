package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfPersonPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorPerson;
import io.protone.service.dto.CorPersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorPerson and its DTO CorPersonDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCorPersonMapper {

    @Mapping(source = "network.id", target = "networkId")
    ConfPersonPT cORPersonToCorPersonDTO(CorPerson cORPerson);

    List<ConfPersonPT> cORPeopleToCorPersonDTOs(List<CorPerson> cORPeople);

    @Mapping(source = "networkId", target = "network")
    CorPerson cORPersonDTOToCorPerson(ConfPersonPT cORPersonDTO);

    List<CorPerson> cORPersonDTOsToCORPeople(List<ConfPersonPT> cORPersonDTOs);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
