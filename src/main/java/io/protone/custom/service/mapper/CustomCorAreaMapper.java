
package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreAreaPT;
import io.protone.domain.CorArea;
import io.protone.domain.CorNetwork;
import io.protone.service.dto.CorAreaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorArea and its DTO CorAreaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCorAreaMapper {

    @Mapping(source = "network.id", target = "networkId")
    CoreAreaPT cORAreaToCorAreaDTO(CorArea cORArea);

    List<CoreAreaPT> cORAreasToCorAreaDTOs(List<CorArea> cORAreas);

    @Mapping(source = "networkId", target = "network")
    CorArea cORAreaDTOToCorArea(CoreAreaPT cORAreaDTO);

    List<CorArea> cORAreaDTOsToCorAreas(List<CoreAreaPT> cORAreaDTOs);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
