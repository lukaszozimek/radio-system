package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CFGMarkerConfigurationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CFGMarkerConfiguration and its DTO CFGMarkerConfigurationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CFGMarkerConfigurationMapper {

    @Mapping(source = "network.id", target = "networkId")
    CFGMarkerConfigurationDTO cFGMarkerConfigurationToCFGMarkerConfigurationDTO(CFGMarkerConfiguration cFGMarkerConfiguration);

    List<CFGMarkerConfigurationDTO> cFGMarkerConfigurationsToCFGMarkerConfigurationDTOs(List<CFGMarkerConfiguration> cFGMarkerConfigurations);

    @Mapping(source = "networkId", target = "network")
    CFGMarkerConfiguration cFGMarkerConfigurationDTOToCFGMarkerConfiguration(CFGMarkerConfigurationDTO cFGMarkerConfigurationDTO);

    List<CFGMarkerConfiguration> cFGMarkerConfigurationDTOsToCFGMarkerConfigurations(List<CFGMarkerConfigurationDTO> cFGMarkerConfigurationDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
