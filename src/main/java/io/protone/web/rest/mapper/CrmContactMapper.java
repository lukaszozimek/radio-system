package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.*;
import io.protone.domain.*;
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
    CrmContactPT DB2DTO(CrmContact crmAccount);

    List<CrmContactPT> DBs2DTOs(List<CrmContact> crmAccounts);

    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "account", target = "keeper")
    @Mapping(source = "range", target = "range")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    CrmContact DTO2DB(CrmContactPT crmAccountDTO, @Context CorNetwork networkId);

    default List<CrmContact> DTOs2DBs(List<CrmContactPT> crmAccountDTOs, CorNetwork networkId) {
        List<CrmContact> crmContacts = new ArrayList<>();
        if (crmAccountDTOs.isEmpty() || crmAccountDTOs == null) {
            return null;
        }
        for (CrmContactPT dto : crmAccountDTOs) {
            crmContacts.add(DTO2DB(dto, networkId));
        }
        return crmContacts;
    }

    @AfterMapping
    default void crmContactPTToCrmContactAfterMapping(CrmContactPT dto, @MappingTarget CrmContact entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
