package io.protone.core.mapper;


import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorDictionaryType;
import io.protone.core.domain.CorModule;
import io.protone.core.domain.CorNetwork;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity CorDictionary and its DTO CorDictionaryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorDictionaryMapper {

    CorDictionaryDTO DB2DTO(CorDictionary corDictionary);

    List<CorDictionaryDTO> DBs2DTOs(List<CorDictionary> corDictionaries);

    CorDictionary DTO2DB(CorDictionaryDTO corDictionaryDTO, @Context CorNetwork corNetwork, @Context CorModule module, @Context CorDictionaryType dictionaryType);


    default List<CorDictionary> DTOs2DBs(List<CorDictionaryDTO> corDictionaryDTOS, CorNetwork networkId, CorModule module, CorDictionaryType dictionaryType) {
        List<CorDictionary> corDictionaries = new ArrayList<>();
        if (corDictionaryDTOS.isEmpty() || corDictionaryDTOS == null) {
            return null;
        }
        for (CorDictionaryDTO dto : corDictionaryDTOS) {
            corDictionaries.add(DTO2DB(dto, networkId, module, dictionaryType));
        }
        return corDictionaries;
    }

    @AfterMapping
    default void confCountryPtToCorCountryAfterMapping(CorDictionaryDTO dto, @MappingTarget CorDictionary entity, @Context CorNetwork corNetwork, @Context CorModule module, @Context CorDictionaryType dictionaryType) {
        entity.setNetwork(corNetwork);
        entity.corModule(module.getName());
        entity.dictionaryType(dictionaryType.getName());
    }
}
