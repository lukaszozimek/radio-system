package io.protone.service.mapper;

import io.protone.custom.service.dto.*;
import io.protone.custom.service.dto.thin.TraCustomerThinPT;
import io.protone.custom.service.mapper.CustomCorPersonMapper;
import io.protone.domain.*;
import io.protone.service.mapper.CorAddressMapper;
import io.protone.service.mapper.CorContactMapper;
import io.protone.service.mapper.CorDictionaryMapper;
import io.protone.service.mapper.CrmTaskMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Mapper(componentModel = "spring", uses = {CrmTaskMapper.class, CorDictionaryMapper.class, CorAddressMapper.class, CustomCorPersonMapper.class})
public interface CrmAccountMapper {
    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "keeper", target = "account")
    @Mapping(source = "range", target = "range")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    CrmAccountPT DB2DTO(CrmAccount crmAccount);

    List<CrmAccountPT> DBs2DTOs(List<CrmAccount> crmAccounts);

    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "account", target = "keeper")
    @Mapping(source = "range", target = "range")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    CrmAccount DTO2DB(CrmAccountPT crmAccountDTO, @Context CorNetwork networkId);

    default List<CrmAccount> DTOs2DBs(List<CrmAccountPT> crmAccountDTOs, CorNetwork networkId) {
        List<CrmAccount> crmAccounts = new ArrayList<>();
        if (crmAccountDTOs.isEmpty() || crmAccountDTOs == null) {
            return null;
        }
        for (CrmAccountPT dto : crmAccountDTOs) {
            crmAccounts.add(DTO2DB(dto, networkId));
        }
        return crmAccounts;
    }

    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "keeper", target = "account")
    @Mapping(source = "range", target = "range")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    TraCustomerPT traDB2DTO(CrmAccount crmAccount);

    List<TraCustomerPT> traDBs2DTOs(List<CrmAccount> crmAccounts);

    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "account", target = "keeper")
    @Mapping(source = "range", target = "range")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    CrmAccount traDTO2DB(TraCustomerPT crmAccountDTO, @Context CorNetwork networkId);

    default List<CrmAccount> traDTOs2DBs(List<TraCustomerPT> crmAccountDTOs, CorNetwork networkId) {
        List<CrmAccount> crmAccounts = new ArrayList<>();
        if (crmAccountDTOs.isEmpty() || crmAccountDTOs == null) {
            return null;
        }
        for (TraCustomerPT dto : crmAccountDTOs) {
            crmAccounts.add(traDTO2DB(dto, networkId));
        }
        return crmAccounts;
    }

    CrmAccount crmAccountFromTraCustomerThinPT(TraCustomerThinPT id);

    TraCustomerThinPT traCustomerThinPTFromCrmAccount(CrmAccount id);

}
