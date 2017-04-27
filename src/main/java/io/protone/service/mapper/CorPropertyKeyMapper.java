package io.protone.service.mapper;

import io.protone.custom.service.dto.CoreKeyPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorPropertyKey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorPropertyKey and its DTO CorPropertyKeyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorPropertyKeyMapper {

    CoreKeyPT DB2DTO(CorPropertyKey cORPropertyKey);

    List<CoreKeyPT> DBs2DTOs(List<CorPropertyKey> cORPropertyKeys);

    CorPropertyKey DTO2DB(CoreKeyPT cORPropertyKeyDTO);

    List<CorPropertyKey> DTOs2DBs(List<CoreKeyPT> cORPropertyKeyDTOs);

}
