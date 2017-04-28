package io.protone.service.mapper;

import io.protone.custom.service.dto.CoreContactPT;
import io.protone.custom.service.dto.LibItemPT;
import io.protone.domain.CorContact;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibMediaItem;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
        List<CorContact> libMediaItems = new ArrayList<>();
        if (cORContactDTOs.isEmpty() || cORContactDTOs == null) {
            return null;
        }
        for (CoreContactPT dto : cORContactDTOs) {
            libMediaItems.add(DTO2DB(dto, networkId));
        }
        return libMediaItems;
    }


}
