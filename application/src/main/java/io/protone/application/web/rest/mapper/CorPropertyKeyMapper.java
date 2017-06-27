package io.protone.application.web.rest.mapper;

import io.protone.domain.CorNetwork;
import io.protone.domain.CorPropertyKey;
import io.protone.web.rest.dto.cor.CorKeyDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity CorPropertyKey and its DTO CorPropertyKeyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorPropertyKeyMapper {

    CorKeyDTO DB2DTO(CorPropertyKey cORPropertyKey);

    List<CorKeyDTO> DBs2DTOs(List<CorPropertyKey> cORPropertyKeys);

    CorPropertyKey DTO2DB(CorKeyDTO cORPropertyKeyDTO, @Context CorNetwork network);

    default List<CorPropertyKey> DTOs2DBs(List<CorKeyDTO> cORPropertyKeyDTOs, @Context CorNetwork network) {
        List<CorPropertyKey> corPeople = new ArrayList<>();
        if (cORPropertyKeyDTOs.isEmpty() || cORPropertyKeyDTOs == null) {
            return null;
        }
        for (CorKeyDTO dto : cORPropertyKeyDTOs) {
            corPeople.add(DTO2DB(dto, network));
        }
        return corPeople;
    }

    @AfterMapping
    default void coreKeyPTToCorPropertyKeyAfterMapping(CorKeyDTO dto, @MappingTarget CorPropertyKey entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

}
