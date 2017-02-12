package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CorSizeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CorSize and its DTO CorSizeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorSizeMapper {

    @Mapping(source = "network.id", target = "networkId")
    CorSizeDTO corSizeToCorSizeDTO(CorSize corSize);

    List<CorSizeDTO> corSizesToCorSizeDTOs(List<CorSize> corSizes);

    @Mapping(source = "networkId", target = "network")
    CorSize corSizeDTOToCorSize(CorSizeDTO corSizeDTO);

    List<CorSize> corSizeDTOsToCorSizes(List<CorSizeDTO> corSizeDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
