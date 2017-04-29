package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.CorDictionaryPT;
import io.protone.domain.CorDictionary;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity CorDictionary and its DTO CorDictionaryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorDictionaryMapper {

    CorDictionaryPT DB2DTO(CorDictionary corDictionary);

    List<CorDictionaryPT> DBs2DTOs(List<CorDictionary> corDictionaries);

    CorDictionary DTO2DB(CorDictionaryPT corDictionaryDTO);

    List<CorDictionary> DTOs2DBs(List<CorDictionaryPT> corDictionaryDTOs);

}
