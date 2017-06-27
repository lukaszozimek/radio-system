package io.protone.application.web.rest.mapper;

import io.protone.domain.CorContact;
import io.protone.domain.CorNetwork;
import io.protone.web.rest.dto.cor.CoreContactDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Mapper for the entity CorContact and its DTO CorContactDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorContactMapper {

    CoreContactDTO DB2DTO(CorContact cORContact);

    List<CoreContactDTO> DBs2DTOs(Set<CorContact> cORContacts);


    CorContact DTO2DB(CoreContactDTO cORContactDTO, @Context CorNetwork network);

    default List<CorContact> DTOs2DBs(List<CoreContactDTO> cORContactDTOs, CorNetwork networkId) {
        List<CorContact> corContacts = new ArrayList<>();
        if (cORContactDTOs.isEmpty() || cORContactDTOs == null) {
            return null;
        }
        for (CoreContactDTO dto : cORContactDTOs) {
            corContacts.add(DTO2DB(dto, networkId));
        }
        return corContacts;
    }
    @AfterMapping
    default void coreContactPTToCorContactAfterMapping(CoreContactDTO dto, @MappingTarget CorContact entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

}
