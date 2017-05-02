package io.protone.web.rest.mapper;

import io.protone.web.rest.dto.cor.CorDictionaryDTO;
import io.protone.domain.CorDictionary;
import io.protone.domain.CorNetwork;
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

    CorDictionary DTO2DB(CorDictionaryDTO corDictionaryDTO, @Context CorNetwork corNetwork);


    default List<CorDictionary> DTOs2DBs(List<CorDictionaryDTO> corDictionaryDTOS, CorNetwork networkId) {
        List<CorDictionary> corDictionaries = new ArrayList<>();
        if (corDictionaryDTOS.isEmpty() || corDictionaryDTOS == null) {
            return null;
        }
        for (CorDictionaryDTO dto : corDictionaryDTOS) {
            corDictionaries.add(DTO2DB(dto, networkId));
        }
        return corDictionaries;
    }

    @AfterMapping
    default void confCountryPtToCorCountryAfterMapping(CorDictionaryDTO dto, @MappingTarget CorDictionary entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
