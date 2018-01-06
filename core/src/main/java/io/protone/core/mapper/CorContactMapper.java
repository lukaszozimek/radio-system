package io.protone.core.mapper;


import io.protone.core.api.dto.CoreContactDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorContact;
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


    CorContact DTO2DB(CoreContactDTO cORContactDTO, @Context CorChannel network);

    default List<CorContact> DTOs2DBs(List<CoreContactDTO> cORContactDTOs, CorChannel networkId) {
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
    default void coreContactPTToCorContactAfterMapping(CoreContactDTO dto, @MappingTarget CorContact entity, @Context CorChannel corNetwork) {
        entity.setChannel(corNetwork);
    }

}
