package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreUserPT;
import io.protone.custom.service.dto.LibPersonPT;
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
public interface CustomCORPersonMapperExt {

    @Mapping(source = "network.id", target = "networkId")
    LibPersonPT DB2DTO(CORPerson db);

    List<LibPersonPT> DBs2DTOs(List<CORPerson> cORPeople);

    @Mapping(source = "networkId", target = "network")
    CORPerson DTO2DB(LibPersonPT dto);

    List<CORPerson> DTOs2DBs(List<LibPersonPT> dtos);

    default CORNetwork mapCORNetwork(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
