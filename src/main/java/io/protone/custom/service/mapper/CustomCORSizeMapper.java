package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreSizePT;
import io.protone.domain.CORNetwork;
import io.protone.domain.CORSize;
import io.protone.service.dto.CORSizeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CORSize and its DTO CORSizeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCORSizeMapper {

    @Mapping(source = "network.id", target = "networkId")
    CoreSizePT cORSizeToCORSizeDTO(CORSize cORSize);

    List<CoreSizePT> cORSizesToCORSizeDTOs(List<CORSize> cORSizes);

    @Mapping(source = "networkId", target = "network")
    CORSize cORSizeDTOToCORSize(CoreSizePT cORSizeDTO);

    List<CORSize> cORSizeDTOsToCORSizes(List<CoreSizePT> cORSizeDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
