package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.CoreChannelPT;
import io.protone.custom.service.dto.CoreContactPT;
import io.protone.domain.CorChannel;
import io.protone.domain.CorContact;
import io.protone.domain.CorNetwork;
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

    CoreContactPT DB2DTO(CorContact cORContact);

    List<CoreContactPT> DBs2DTOs(Set<CorContact> cORContacts);


    CorContact DTO2DB(CoreContactPT cORContactDTO, @Context CorNetwork network);

    default List<CorContact> DTOs2DBs(List<CoreContactPT> cORContactDTOs, CorNetwork networkId) {
        List<CorContact> corContacts = new ArrayList<>();
        if (cORContactDTOs.isEmpty() || cORContactDTOs == null) {
            return null;
        }
        for (CoreContactPT dto : cORContactDTOs) {
            corContacts.add(DTO2DB(dto, networkId));
        }
        return corContacts;
    }
    @AfterMapping
    default void coreContactPTToCorContactAfterMapping(CoreContactPT dto, @MappingTarget CorContact entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

}
