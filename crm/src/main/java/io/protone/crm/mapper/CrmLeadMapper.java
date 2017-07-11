package io.protone.crm.mapper;

import io.protone.core.domain.CorNetwork;
import io.protone.core.mapper.CorAddressMapper;
import io.protone.core.mapper.CorDictionaryMapper;
import io.protone.core.mapper.CorPersonMapper;
import io.protone.crm.api.dto.CrmLeadDTO;
import io.protone.crm.api.dto.thin.CrmLeadThinDTO;
import io.protone.crm.domain.CrmLead;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 18.01.2017.
 */
@Mapper(componentModel = "spring", uses = {CrmTaskMapper.class, CorDictionaryMapper.class, CorAddressMapper.class, CorPersonMapper.class})
public interface CrmLeadMapper {

    @Mapping(source = "leadStatus", target = "status")
    @Mapping(source = "leadSource", target = "source")
    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "keeper", target = "owner")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    CrmLeadDTO DB2DTO(CrmLead crmLead);

    List<CrmLeadDTO> DBs2DTOs(List<CrmLead> crmLeads);

    @Mapping(source = "leadStatus", target = "status")
    @Mapping(source = "leadSource", target = "source")
    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "keeper", target = "owner")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    CrmLeadThinDTO DB2ThinDTO(CrmLead crmLead);


    List<CrmLeadThinDTO> DBs2ThinDTOs(List<CrmLead> crmLeads);


    @Mapping(source = "status", target = "leadStatus")
    @Mapping(source = "source", target = "leadSource")
    @Mapping(source = "person", target = "person")
    @Mapping(source = "addres", target = "addres")
    @Mapping(source = "owner", target = "keeper")
    @Mapping(source = "industry", target = "industry")
    @Mapping(source = "area", target = "area")
    CrmLead DTO2DB(CrmLeadDTO crmLeadDTO, @Context CorNetwork networkId);

    default List<CrmLead> DTOs2DBs(List<CrmLeadDTO> crmLeadDTOs, CorNetwork networkId) {
        List<CrmLead> crmLeads = new ArrayList<>();
        if (crmLeadDTOs.isEmpty() || crmLeadDTOs == null) {
            return null;
        }
        for (CrmLeadDTO dto : crmLeadDTOs) {
            crmLeads.add(DTO2DB(dto, networkId));
        }
        return crmLeads;
    }

    @AfterMapping
    default void crmLeadPTToCrmLeadAfterMapping(CrmLeadDTO dto, @MappingTarget CrmLead entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

}



