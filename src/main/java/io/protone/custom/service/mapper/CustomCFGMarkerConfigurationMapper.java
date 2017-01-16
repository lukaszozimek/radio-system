package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfMarkerConfigurationPT;
import io.protone.domain.CFGMarkerConfiguration;
import io.protone.domain.CORNetwork;
import io.protone.service.dto.CFGMarkerConfigurationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Created by lukaszozimek on 16.01.2017.
 */

@Mapper(componentModel = "spring", uses = {})
public interface CustomCFGMarkerConfigurationMapper {
    ConfMarkerConfigurationPT cFGMarkerConfigurationToCFGMarkerConfigurationDTO(CFGMarkerConfiguration cFGMarkerConfiguration);

    List<ConfMarkerConfigurationPT> cFGMarkerConfigurationsToCFGMarkerConfigurationDTOs(List<CFGMarkerConfiguration> cFGMarkerConfigurations);

    CFGMarkerConfiguration cFGMarkerConfigurationDTOToCFGMarkerConfiguration(ConfMarkerConfigurationPT cFGMarkerConfigurationDTO);

    List<CFGMarkerConfiguration> cFGMarkerConfigurationDTOsToCFGMarkerConfigurations(List<ConfMarkerConfigurationPT> cFGMarkerConfigurationDTOs);


}
