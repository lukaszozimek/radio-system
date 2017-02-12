package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfMarkerConfigurationPT;
import io.protone.domain.CfgMarkerConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created by lukaszozimek on 16.01.2017.
 */

@Mapper(componentModel = "spring", uses = {})
public interface CustomCfgMarkerConfigurationMapper {
    ConfMarkerConfigurationPT cFGMarkerConfigurationToCfgMarkerConfigurationDTO(CfgMarkerConfiguration cFGMarkerConfiguration);

    List<ConfMarkerConfigurationPT> cFGMarkerConfigurationsToCfgMarkerConfigurationDTOs(List<CfgMarkerConfiguration> cFGMarkerConfigurations);

    CfgMarkerConfiguration cFGMarkerConfigurationDTOToCfgMarkerConfiguration(ConfMarkerConfigurationPT cFGMarkerConfigurationDTO);

    List<CfgMarkerConfiguration> cFGMarkerConfigurationDTOsToCfgMarkerConfigurations(List<ConfMarkerConfigurationPT> cFGMarkerConfigurationDTOs);


}
