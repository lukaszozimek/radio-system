package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreSizePT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorSize;
import io.protone.service.dto.CorSizeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorSize and its DTO CorSizeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCorSizeMapper {

    @Mapping(source = "network.id", target = "networkId")
    CoreSizePT cORSizeToCorSizeDTO(CorSize cORSize);

    List<CoreSizePT> cORSizesToCorSizeDTOs(List<CorSize> cORSizes);

    @Mapping(source = "networkId", target = "network")
    CorSize cORSizeDTOToCorSize(CoreSizePT cORSizeDTO);

    List<CorSize> cORSizeDTOsToCorSizes(List<CoreSizePT> cORSizeDTOs);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
