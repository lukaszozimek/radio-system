package io.protone.library.mapper;


import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.library.api.dto.LibMarkerConfigurationDTO;
import io.protone.library.domain.LibMarkerConfiguration;
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
public interface LibMarkerConfigurationMapper {

    LibMarkerConfigurationDTO DB2DTO(LibMarkerConfiguration cFGMarkerConfiguration);

    List<LibMarkerConfigurationDTO> DBs2DTOs(List<LibMarkerConfiguration> cFGMarkerConfigurations);

    LibMarkerConfiguration DTO2DB(LibMarkerConfigurationDTO cFGMarkerConfigurationDTO, @Context CorChannel channel);

    default List<LibMarkerConfiguration> DTOs2DBs(List<LibMarkerConfigurationDTO> libMarkerConfigurationDTOS, CorChannel channel) {
        List<LibMarkerConfiguration> crmLeads = new ArrayList<>();
        if (libMarkerConfigurationDTOS.isEmpty() || libMarkerConfigurationDTOS == null) {
            return null;
        }
        for (LibMarkerConfigurationDTO dto : libMarkerConfigurationDTOS) {
            crmLeads.add(DTO2DB(dto, channel));
        }
        return crmLeads;
    }

    @AfterMapping
    default void confMarkerConfigurationPTToCfgMarkerConfigurationAfterMapping(LibMarkerConfigurationDTO dto, @MappingTarget LibMarkerConfiguration entity, @Context CorChannel channel) {
        entity.setChannel(channel);
    }

}
