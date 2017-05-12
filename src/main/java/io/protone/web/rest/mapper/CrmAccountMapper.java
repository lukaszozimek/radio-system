package io.protone.web.rest.mapper;

import io.protone.web.rest.dto.crm.CrmAccountDTO;
import io.protone.web.rest.dto.traffic.TraCustomerDTO;
import io.protone.web.rest.dto.traffic.thin.TraCustomerThinDTO;
import io.protone.domain.*;
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
    CrmAccountDTO DB2DTO(CrmAccount crmAccount);

    List<CrmAccountDTO> DBs2DTOs(List<CrmAccount> crmAccounts);

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

    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "keeper", target = "account")
    @Mapping(source = "range", target = "range")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    TraCustomerDTO traDB2DTO(CrmAccount crmAccount);

    List<TraCustomerDTO> traDBs2DTOs(List<CrmAccount> crmAccounts);

    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "account", target = "keeper")
    @Mapping(source = "range", target = "range")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    CrmAccount traDTO2DB(TraCustomerDTO crmAccountDTO, @Context CorNetwork networkId);

    default List<CrmAccount> traDTOs2DBs(List<TraCustomerDTO> crmAccountDTOs, CorNetwork networkId) {
        List<CrmAccount> crmAccounts = new ArrayList<>();
        if (crmAccountDTOs.isEmpty() || crmAccountDTOs == null) {
            return null;
        }
        for (TraCustomerDTO dto : crmAccountDTOs) {
            crmAccounts.add(traDTO2DB(dto, networkId));
        }
        return crmAccounts;
    }

    @AfterMapping
    default void traCustomerPTToCrmAccountAfterMapping(TraCustomerDTO dto, @MappingTarget CrmAccount entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

    CrmAccount crmAccountFromTraCustomerThinPT(TraCustomerThinDTO id);

    TraCustomerThinDTO traCustomerThinPTFromCrmAccount(CrmAccount id);

}
