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

/**
 * Created by lukaszozimek on 18.01.2017.
 */
@Mapper(componentModel = "spring", uses = {CustomCrmTaskMapper.class, CorDictionaryMapper.class, CorAddressMapper.class})
public interface CustomCrmLeadMapper {

    @Mapping(source = "leadStatus", target = "status")
    @Mapping(source = "leadSource", target = "source")
    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "keeper", target = "owner")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    CrmLeadPT DB2DTO(CrmLead crmLead);

    List<CrmLeadPT> DBs2DTOs(List<CrmLead> crmLeads);

    @Mapping(source = "status", target = "leadStatus")
    @Mapping(source = "source", target = "leadSource")
    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "owner", target = "keeper")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    CrmLead DTO2DB(CrmLeadPT crmLeadDTO);

    List<CrmLead> DTOs2DBs(List<CrmLeadPT> crmLeadDTOs);

    CorPerson corPersonFromTraCustomerPersonPT(TraCustomerPersonPT coreUserThinPT);

    TraCustomerPersonPT traCustomerPersonPTFromCorPerson(CorPerson coreUserThinPT);

    CorContact corContactFromCoreContactPT(CoreContactPT coreUserThinPT);

    CoreContactPT coreContactPTFromCorContact(CorContact coreUserThinPT);

    Set<CorContact> corContactFromCoreContactPT(List<CoreContactPT> coreUserThinPT);

    List<CoreContactPT> coreContactPTFromCorContact(Set<CorContact> coreUserThinPT);

}



