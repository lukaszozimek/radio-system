package io.protone.crm.mapper;

import io.protone.core.api.dto.CoreContactDTO;
import io.protone.core.domain.CorContact;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorPerson;
import io.protone.core.mapper.CorAddressMapper;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.core.mapper.CorPersonMapper;
import io.protone.crm.api.dto.CrmAccountDTO;
import io.protone.crm.api.dto.CrmCustomerPersonDTO;
import io.protone.crm.api.dto.thin.CrmAccountThinDTO;
import io.protone.crm.domain.CrmAccount;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Mapper(componentModel = "spring", uses = {CrmTaskMapper.class, CorDictionaryMapper.class, CorAddressMapper.class, CorPersonMapper.class})
public interface CrmAccountMapper {
    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "keeper", target = "account")
    @Mapping(source = "range", target = "range")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    @Mapping(source = "corImageItem.publicUrl", target = "publicUrl")
    CrmAccountDTO DB2DTO(CrmAccount crmAccount);

    List<CrmAccountDTO> DBs2DTOs(List<CrmAccount> crmAccounts);

    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "keeper", target = "account")
    @Mapping(source = "range", target = "range")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    @Mapping(source = "corImageItem.publicUrl", target = "publicUrl")
    CrmAccountThinDTO DB2ThinDTO(CrmAccount crmAccount);

    List<CrmAccountThinDTO> DBs2ThinDTOs(List<CrmAccount> crmAccounts);


    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "account", target = "keeper")
    @Mapping(source = "range", target = "range")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    CrmAccount DTO2DB(CrmAccountDTO crmAccountDTO, @Context CorNetwork networkId);

    default List<CrmAccount> DTOs2DBs(List<CrmAccountDTO> crmAccountDTOs, CorNetwork networkId) {
        List<CrmAccount> crmAccounts = new ArrayList<>();
        if (crmAccountDTOs.isEmpty() || crmAccountDTOs == null) {
            return null;
        }
        for (CrmAccountDTO dto : crmAccountDTOs) {
            crmAccounts.add(DTO2DB(dto, networkId));
        }
        return crmAccounts;
    }

    @AfterMapping
    default void crmAccountPTToCrmAccountAfterMapping(CrmAccountDTO dto, @MappingTarget CrmAccount entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

    @AfterMapping
    default void crmCustomerPersonDTOToCorPersonAfterMapping(CrmCustomerPersonDTO dto, @MappingTarget CorPerson entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

    @AfterMapping
    default void coreContactDTOToCorContactAfterMapping(CoreContactDTO dto, @MappingTarget CorContact entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
