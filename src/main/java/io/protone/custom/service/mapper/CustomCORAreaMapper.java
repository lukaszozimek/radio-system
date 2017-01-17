
package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreAreaPT;
import io.protone.domain.CORArea;
import io.protone.domain.CORNetwork;
import io.protone.service.dto.CORAreaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CORArea and its DTO CORAreaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCORAreaMapper {

    @Mapping(source = "network.id", target = "networkId")
    CoreAreaPT cORAreaToCORAreaDTO(CORArea cORArea);

    List<CoreAreaPT> cORAreasToCORAreaDTOs(List<CORArea> cORAreas);

    @Mapping(source = "networkId", target = "network")
    CORArea cORAreaDTOToCORArea(CoreAreaPT cORAreaDTO);

    List<CORArea> cORAreaDTOsToCORAreas(List<CoreAreaPT> cORAreaDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
