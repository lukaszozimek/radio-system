package io.protone.web.rest.mapper;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmContact;
import io.protone.web.rest.dto.crm.CrmContactDTO;
import io.protone.web.rest.dto.crm.thin.CrmContactThinDTO;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity CrmContact and its DTO CrmContactDTO.
 */

@Mapper(componentModel = "spring", uses = {CrmTaskMapper.class, CorDictionaryMapper.class, CorAddressMapper.class, CorPersonMapper.class})
public interface CrmContactMapper {
    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "keeper", target = "account")
    @Mapping(source = "range", target = "range")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    CrmContactDTO DB2DTO(CrmContact crmAccount);

    List<CrmContactDTO> DBs2DTOs(List<CrmContact> crmContacts);

    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "keeper", target = "account")
    @Mapping(source = "range", target = "range")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    CrmContactThinDTO DB2ThinDTO(CrmContact crmContact);

    List<CrmContactThinDTO> DBs2ThinDTOs(List<CrmContact> crmContacts);

    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "account", target = "keeper")
    @Mapping(source = "range", target = "range")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    CrmContact DTO2DB(CrmContactDTO crmAccountDTO, @Context CorNetwork networkId);

    default List<CrmContact> DTOs2DBs(List<CrmContactDTO> crmAccountDTOs, CorNetwork networkId) {
        List<CrmContact> crmContacts = new ArrayList<>();
        if (crmAccountDTOs.isEmpty() || crmAccountDTOs == null) {
            return null;
        }
        for (CrmContactDTO dto : crmAccountDTOs) {
            crmContacts.add(DTO2DB(dto, networkId));
        }
        return crmContacts;
    }

    @AfterMapping
    default void crmContactPTToCrmContactAfterMapping(CrmContactDTO dto, @MappingTarget CrmContact entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
