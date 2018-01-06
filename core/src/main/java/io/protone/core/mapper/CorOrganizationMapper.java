package io.protone.core.mapper;

import io.protone.core.api.dto.CorNetworkDTO;
import io.protone.core.api.dto.CorOrganizationDTO;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorOrganization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {})
public interface CorOrganizationMapper {

    @Mapping(source = "corImageItem.publicUrl", target = "publicUrl")
    CorOrganizationDTO DB2DTO(CorOrganization cORNetwork);

    List<CorOrganizationDTO> DBs2DTOs(List<CorOrganization> cORNetworks);

    CorOrganization DTO2DB(CorOrganizationDTO cORNetworkDTO);

    List<CorOrganization> DTOs2DBs(List<CorOrganizationDTO> cORNetworkDTOs);


}
