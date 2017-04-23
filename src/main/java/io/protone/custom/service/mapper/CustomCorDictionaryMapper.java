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

    @Mapping(source = "network.id", target = "networkId")
    CorDictionaryPT corDictionaryToCorDictionaryDTO(CorDictionary corDictionary);

    List<CorDictionaryPT> corDictionariesToCorDictionaryDTOs(List<CorDictionary> corDictionaries);

    @Mapping(source = "networkId", target = "network")
    CorDictionary corDictionaryDTOToCorDictionary(CorDictionaryPT corDictionaryDTO);

    List<CorDictionary> corDictionaryDTOsToCorDictionaries(List<CorDictionaryPT> corDictionaryDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }


}
