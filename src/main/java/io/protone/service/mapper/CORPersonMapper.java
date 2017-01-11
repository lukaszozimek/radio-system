package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CORPersonDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CORPerson and its DTO CORPersonDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CORPersonMapper {

    @Mapping(source = "network.id", target = "networkId")
    CORPersonDTO cORPersonToCORPersonDTO(CORPerson cORPerson);

    List<CORPersonDTO> cORPeopleToCORPersonDTOs(List<CORPerson> cORPeople);

    @Mapping(source = "networkId", target = "network")
    CORPerson cORPersonDTOToCORPerson(CORPersonDTO cORPersonDTO);

    List<CORPerson> cORPersonDTOsToCORPeople(List<CORPersonDTO> cORPersonDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
