package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CORAreaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CORArea and its DTO CORAreaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CORAreaMapper {

    @Mapping(source = "network.id", target = "networkId")
    CORAreaDTO cORAreaToCORAreaDTO(CORArea cORArea);

    List<CORAreaDTO> cORAreasToCORAreaDTOs(List<CORArea> cORAreas);

    @Mapping(source = "networkId", target = "network")
    CORArea cORAreaDTOToCORArea(CORAreaDTO cORAreaDTO);

    List<CORArea> cORAreaDTOsToCORAreas(List<CORAreaDTO> cORAreaDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
