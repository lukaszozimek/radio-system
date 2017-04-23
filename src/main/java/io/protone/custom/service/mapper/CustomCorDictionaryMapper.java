package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CorDictionaryPT;
import io.protone.domain.CorDictionary;
import io.protone.domain.CorDictionaryType;
import io.protone.domain.CorModule;
import io.protone.domain.CorNetwork;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorDictionary and its DTO CorDictionaryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCorDictionaryMapper {

    CorDictionaryPT corDictionaryToCorDictionaryDTO(CorDictionary corDictionary);

    List<CorDictionaryPT> corDictionariesToCorDictionaryDTOs(List<CorDictionary> corDictionaries);

    CorDictionary corDictionaryDTOToCorDictionary(CorDictionaryPT corDictionaryDTO);

    List<CorDictionary> corDictionaryDTOsToCorDictionaries(List<CorDictionaryPT> corDictionaryDTOs);

}
