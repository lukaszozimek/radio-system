package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LibLabelDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LibLabel and its DTO LibLabelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibLabelMapper {

    @Mapping(source = "network.id", target = "networkId")
    LibLabelDTO libLabelToLibLabelDTO(LibLabel libLabel);

    List<LibLabelDTO> libLabelsToLibLabelDTOs(List<LibLabel> libLabels);

    @Mapping(source = "networkId", target = "network")
    LibLabel libLabelDTOToLibLabel(LibLabelDTO libLabelDTO);

    List<LibLabel> libLabelDTOsToLibLabels(List<LibLabelDTO> libLabelDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
