package io.protone.core.mapper;


import io.protone.core.api.dto.CorNetworkDTO;
import io.protone.core.domain.CorNetwork;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

/**
 * Mapper for the entity CorNetwork and its DTO CorNetworkDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorNetworkMapper {

    @Mapping(source = "corImageItem.publicUrl",target = "publicUrl")
    CorNetworkDTO DB2DTO(CorNetwork cORNetwork);

    List<CorNetworkDTO> DBs2DTOs(List<CorNetwork> cORNetworks);

    CorNetwork DTO2DB(CorNetworkDTO cORNetworkDTO);

    List<CorNetwork> DTOs2DBs(List<CorNetworkDTO> cORNetworkDTOs);

    default CorNetworkDTO DBs2DTO(Set<CorNetwork> cORNetworks) {
        if (cORNetworks == null || cORNetworks.isEmpty()) {
            return null;
        }

        return this.DB2DTO(cORNetworks.stream().findFirst().get());
    }

}
