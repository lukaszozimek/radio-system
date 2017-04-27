package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreContactPT;
import io.protone.domain.CorContact;
import io.protone.domain.CorNetwork;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

/**
 * Mapper for the entity CorContact and its DTO CorContactDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCorContactMapper {

    CoreContactPT DB2DTO(CorContact cORContact);

    List<CoreContactPT> DBs2DTOs(Set<CorContact> cORContacts);

    CorContact DTO2DB(CoreContactPT cORContactDTO);

    List<CorContact> DTOs2DBs(List<CoreContactPT> cORContactDTOs);

}
