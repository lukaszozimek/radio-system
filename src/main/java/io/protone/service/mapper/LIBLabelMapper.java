package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.LIBLabelDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LIBLabel and its DTO LIBLabelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LIBLabelMapper {

    @Mapping(source = "network.id", target = "networkId")
    LIBLabelDTO lIBLabelToLIBLabelDTO(LIBLabel lIBLabel);

    List<LIBLabelDTO> lIBLabelsToLIBLabelDTOs(List<LIBLabel> lIBLabels);

    @Mapping(source = "networkId", target = "network")
    LIBLabel lIBLabelDTOToLIBLabel(LIBLabelDTO lIBLabelDTO);

    List<LIBLabel> lIBLabelDTOsToLIBLabels(List<LIBLabelDTO> lIBLabelDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
