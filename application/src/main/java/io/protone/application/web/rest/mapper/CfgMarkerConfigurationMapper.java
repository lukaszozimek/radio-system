package io.protone.application.web.rest.mapper;

import io.protone.domain.CfgMarkerConfiguration;
import io.protone.domain.CorNetwork;
import io.protone.web.rest.dto.library.LibMarkerConfigurationDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 16.01.2017.
 */

@Mapper(componentModel = "spring", uses = {})
public interface CfgMarkerConfigurationMapper {

    LibMarkerConfigurationDTO DB2DTO(CfgMarkerConfiguration cFGMarkerConfiguration);

    List<LibMarkerConfigurationDTO> DBs2DTOs(List<CfgMarkerConfiguration> cFGMarkerConfigurations);

    CfgMarkerConfiguration DTO2DB(LibMarkerConfigurationDTO cFGMarkerConfigurationDTO, @Context CorNetwork corNetwork);

    default List<CfgMarkerConfiguration> DTOs2DBs(List<LibMarkerConfigurationDTO> libMarkerConfigurationDTOS, CorNetwork networkId) {
        List<CfgMarkerConfiguration> crmLeads = new ArrayList<>();
        if (libMarkerConfigurationDTOS.isEmpty() || libMarkerConfigurationDTOS == null) {
            return null;
        }
        for (LibMarkerConfigurationDTO dto : libMarkerConfigurationDTOS) {
            crmLeads.add(DTO2DB(dto, networkId));
        }
        return crmLeads;
    }

    @AfterMapping
    default void confMarkerConfigurationPTToCfgMarkerConfigurationAfterMapping(LibMarkerConfigurationDTO dto, @MappingTarget CfgMarkerConfiguration entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

}
