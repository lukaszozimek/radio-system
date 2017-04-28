package io.protone.service.mapper;

import io.protone.custom.service.dto.*;
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
 * Created by lukaszozimek on 18.01.2017.
 */
@Mapper(componentModel = "spring", uses = {CrmTaskMapper.class, CorDictionaryMapper.class, CorAddressMapper.class, CustomCorPersonMapper.class})
public interface CrmLeadMapper {

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
    CrmLead DTO2DB(CrmLeadPT crmLeadDTO, @Context CorNetwork networkId);

    default List<CrmLead> DTOs2DBs(List<CrmLeadPT> crmLeadDTOs, CorNetwork networkId) {
        List<CrmLead> crmLeads = new ArrayList<>();
        if (crmLeadDTOs.isEmpty() || crmLeadDTOs == null) {
            return null;
        }
        for (CrmLeadPT dto : crmLeadDTOs) {
            crmLeads.add(DTO2DB(dto, networkId));
        }
        return crmLeads;
    }


}



