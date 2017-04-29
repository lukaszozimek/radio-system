package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.CoreNetworkPT;
import io.protone.domain.CorNetwork;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity CorNetwork and its DTO CorNetworkDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorNetworkMapper {

    CoreNetworkPT DB2DTO(CorNetwork cORNetwork);

    List<CoreNetworkPT> DBs2DTOs(List<CorNetwork> cORNetworks);

    CorNetwork DTO2DB(CoreNetworkPT cORNetworkDTO);

    List<CorNetwork> DTOs2DBs(List<CoreNetworkPT> cORNetworkDTOs);
}
