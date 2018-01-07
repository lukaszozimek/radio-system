package io.protone.core.mapper;


import io.protone.core.api.dto.CorNetworkDTO;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorOrganization;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for the entity CorNetwork and its DTO CorNetworkDTO.
 */
@Mapper(componentModel = "spring", uses = {CorOrganizationMapper.class})
public interface CorNetworkMapper {

    @Mapping(source = "corImageItem.publicUrl", target = "publicUrl")
    @Mapping(source = "organization", target = "corOrganizationDTO")
    CorNetworkDTO DB2DTO(CorNetwork cORNetwork);

    List<CorNetworkDTO> DBs2DTOs(List<CorNetwork> cORNetworks);

    CorNetwork DTO2DB(CorNetworkDTO cORNetworkDTO, @Context CorOrganization organization);

    List<CorNetwork> DTOs2DBs(List<CorNetworkDTO> cORNetworkDTOs);

    @AfterMapping
    default void corNetworkDTOToCorNetworkAfterMapping(CorNetworkDTO dto, @MappingTarget CorNetwork entity, @Context CorOrganization organization) {
        entity.setOrganization(organization);
    }

}
