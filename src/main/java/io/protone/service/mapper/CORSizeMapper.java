package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CORSizeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CORSize and its DTO CORSizeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CORSizeMapper {

    @Mapping(source = "network.id", target = "networkId")
    CORSizeDTO cORSizeToCORSizeDTO(CORSize cORSize);

    List<CORSizeDTO> cORSizesToCORSizeDTOs(List<CORSize> cORSizes);

    @Mapping(source = "networkId", target = "network")
    CORSize cORSizeDTOToCORSize(CORSizeDTO cORSizeDTO);

    List<CORSize> cORSizeDTOsToCORSizes(List<CORSizeDTO> cORSizeDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
