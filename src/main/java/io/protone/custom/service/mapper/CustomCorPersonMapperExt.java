package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibPersonPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorPerson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorPerson and its DTO CorPersonDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCorPersonMapperExt {

    @Mapping(source = "network.id", target = "networkId")
    LibPersonPT DB2DTO(CorPerson db);

    List<LibPersonPT> DBs2DTOs(List<CorPerson> cORPeople);

    @Mapping(source = "networkId", target = "network")
    CorPerson DTO2DB(LibPersonPT dto);

    List<CorPerson> DTOs2DBs(List<LibPersonPT> dtos);

    default CorNetwork mapCorNetwork(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
