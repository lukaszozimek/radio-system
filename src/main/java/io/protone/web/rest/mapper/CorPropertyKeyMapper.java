package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.CoreKeyPT;
import io.protone.custom.service.dto.LibPersonPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorPerson;
import io.protone.domain.CorPropertyKey;
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

    CoreKeyPT DB2DTO(CorPropertyKey cORPropertyKey);

    List<CoreKeyPT> DBs2DTOs(List<CorPropertyKey> cORPropertyKeys);

    CorPropertyKey DTO2DB(CoreKeyPT cORPropertyKeyDTO, @Context CorNetwork network);

    default List<CorPropertyKey> DTOs2DBs(List<CoreKeyPT> cORPropertyKeyDTOs, @Context CorNetwork network) {
        List<CorPropertyKey> corPeople = new ArrayList<>();
        if (cORPropertyKeyDTOs.isEmpty() || cORPropertyKeyDTOs == null) {
            return null;
        }
        for (CoreKeyPT dto : cORPropertyKeyDTOs) {
            corPeople.add(DTO2DB(dto, network));
        }
        return corPeople;
    }

    @AfterMapping
    default void coreKeyPTToCorPropertyKeyAfterMapping(CoreKeyPT dto, @MappingTarget CorPropertyKey entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }

}
