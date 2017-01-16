package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfPersonPT;
import io.protone.domain.CORNetwork;
import io.protone.domain.CORPerson;
import io.protone.service.dto.CORPersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CORPerson and its DTO CORPersonDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCORPersonMapper {

    @Mapping(source = "network.id", target = "networkId")
    ConfPersonPT cORPersonToCORPersonDTO(CORPerson cORPerson);

    List<ConfPersonPT> cORPeopleToCORPersonDTOs(List<CORPerson> cORPeople);

    @Mapping(source = "networkId", target = "network")
    CORPerson cORPersonDTOToCORPerson(ConfPersonPT cORPersonDTO);

    List<CORPerson> cORPersonDTOsToCORPeople(List<ConfPersonPT> cORPersonDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
