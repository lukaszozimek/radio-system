package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.ConfMarkerConfigurationPT;
import io.protone.domain.CfgMarkerConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created by lukaszozimek on 16.01.2017.
 */

@Mapper(componentModel = "spring", uses = {})
public interface CfgMarkerConfigurationMapper {

    ConfMarkerConfigurationPT DB2DTO(CfgMarkerConfiguration cFGMarkerConfiguration);

    List<ConfMarkerConfigurationPT> DBs2DTOs(List<CfgMarkerConfiguration> cFGMarkerConfigurations);

    CfgMarkerConfiguration DTO2DB(ConfMarkerConfigurationPT cFGMarkerConfigurationDTO);

    List<CfgMarkerConfiguration> DTOs2DBs(List<ConfMarkerConfigurationPT> cFGMarkerConfigurationDTOs);


}
