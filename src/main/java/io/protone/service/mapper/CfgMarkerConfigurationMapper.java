package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CfgMarkerConfigurationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CfgMarkerConfiguration and its DTO CfgMarkerConfigurationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CfgMarkerConfigurationMapper {

    @Mapping(source = "network.id", target = "networkId")
    CfgMarkerConfigurationDTO cfgMarkerConfigurationToCfgMarkerConfigurationDTO(CfgMarkerConfiguration cfgMarkerConfiguration);

    List<CfgMarkerConfigurationDTO> cfgMarkerConfigurationsToCfgMarkerConfigurationDTOs(List<CfgMarkerConfiguration> cfgMarkerConfigurations);

    @Mapping(source = "networkId", target = "network")
    CfgMarkerConfiguration cfgMarkerConfigurationDTOToCfgMarkerConfiguration(CfgMarkerConfigurationDTO cfgMarkerConfigurationDTO);

    List<CfgMarkerConfiguration> cfgMarkerConfigurationDTOsToCfgMarkerConfigurations(List<CfgMarkerConfigurationDTO> cfgMarkerConfigurationDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
