package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.ConfCountryPt;
import io.protone.custom.service.dto.CorDictionaryPT;
import io.protone.domain.CorCountry;
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

    CorDictionaryPT DB2DTO(CorDictionary corDictionary);

    List<CorDictionaryPT> DBs2DTOs(List<CorDictionary> corDictionaries);

    CorDictionary DTO2DB(CorDictionaryPT corDictionaryDTO, @Context CorNetwork corNetwork);


    default List<CorDictionary> DTOs2DBs(List<CorDictionaryPT> corDictionaryPTS, CorNetwork networkId) {
        List<CorDictionary> corDictionaries = new ArrayList<>();
        if (corDictionaryPTS.isEmpty() || corDictionaryPTS == null) {
            return null;
        }
        for (CorDictionaryPT dto : corDictionaryPTS) {
            corDictionaries.add(DTO2DB(dto, networkId));
        }
        return corDictionaries;
    }

    @AfterMapping
    default void confCountryPtToCorCountryAfterMapping(CorDictionaryPT dto, @MappingTarget CorDictionary entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
