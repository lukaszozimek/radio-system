package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CorAreaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CorArea and its DTO CorAreaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorAreaMapper {

    @Mapping(source = "network.id", target = "networkId")
    CorAreaDTO corAreaToCorAreaDTO(CorArea corArea);

    List<CorAreaDTO> corAreasToCorAreaDTOs(List<CorArea> corAreas);

    @Mapping(source = "networkId", target = "network")
    CorArea corAreaDTOToCorArea(CorAreaDTO corAreaDTO);

    List<CorArea> corAreaDTOsToCorAreas(List<CorAreaDTO> corAreaDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
