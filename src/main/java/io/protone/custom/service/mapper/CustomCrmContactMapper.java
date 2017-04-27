package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.*;
import io.protone.custom.service.dto.thin.CoreUserThinPT;
import io.protone.domain.*;
import io.protone.service.mapper.CorAddressMapper;
import io.protone.service.mapper.CorDictionaryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * Mapper for the entity CrmContact and its DTO CrmContactDTO.
 */

@Mapper(componentModel = "spring", uses = {CustomCrmTaskMapper.class, CorDictionaryMapper.class, CorAddressMapper.class})
public interface CustomCrmContactMapper {
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
    CrmContact DTO2DB(CrmContactPT crmAccountDTO);

    List<CrmContact> DTOs2DBs(List<CrmContactPT> crmAccountDTOs);

    CorPerson corPersonFromTraCustomerPersonPT(TraCustomerPersonPT coreUserThinPT);

    TraCustomerPersonPT traCustomerPersonPTFromCorPerson(CorPerson coreUserThinPT);

    CorContact corContactFromCoreContactPT(CoreContactPT coreUserThinPT);

    CoreContactPT coreContactPTFromCorContact(CorContact coreUserThinPT);

    Set<CorContact> corContactFromCoreContactPT(List<CoreContactPT> coreUserThinPT);

    List<CoreContactPT> coreContactPTFromCorContact(Set<CorContact> coreUserThinPT);

}
