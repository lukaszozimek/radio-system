package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.*;
import io.protone.custom.service.dto.thin.CoreUserThinPT;
import io.protone.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCrmAccountMapper {
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
    CrmAccount DTO2DB(CrmAccountPT crmAccountDTO);

    List<CrmAccount> DTOs2DBs(List<CrmAccountPT> crmAccountDTOs);

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
    CrmAccount traDTO2DB(TraCustomerPT crmAccountDTO);

    List<CrmAccount> traDTOs2DBs(List<TraCustomerPT> crmAccountDTOs);


    CorDictionary corDictionaryFromCorDictionaryPT(CorDictionaryPT coreUserThinPT);

    CorDictionaryPT corDictionaryPTFromCorDictionary(CorDictionary coreUserThinPT);

    CorUser corUserFromCoreUserThinPT(CoreUserThinPT coreUserThinPT);

    CoreUserThinPT coreUserThinPTFromCorUser(CorUser coreUserThinPT);

    CorAddress corAddressFromCoreAddressPT(CoreAddressPT coreUserThinPT);

    CoreAddressPT coreAddressPTFromCorAddress(CorAddress coreUserThinPT);

    CorPerson corPersonFromTraCustomerPersonPT(TraCustomerPersonPT coreUserThinPT);

    TraCustomerPersonPT traCustomerPersonPTFromCorPerson(CorPerson coreUserThinPT);

    CorContact corContactFromCoreContactPT(CoreContactPT coreUserThinPT);

    CoreContactPT coreContactPTFromCorContact(CorContact coreUserThinPT);

    Set<CorContact> corContactFromCoreContactPT(List<CoreContactPT> coreUserThinPT);

    List<CoreContactPT> coreContactPTFromCorContact(Set<CorContact> coreUserThinPT);


    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "assignedTo", target = "assignedTo")
    CrmTaskPT crmTaskPTFromCrmTask(CrmTask cORAddress);

    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "assignedTo", target = "assignedTo")
    CrmTask crmTaskFromCrmTaskPT(CrmTaskPT cORAddressDTO);


    Set<CrmTask> crmTaskFromCrmTaskPT(List<CrmTaskPT> coreUserThinPT);

    List<CrmTaskPT> crmTaskPTFromCrmTask(Set<CrmTask> coreUserThinPT);


}
