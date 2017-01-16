package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreKeyValuePT;
import io.protone.custom.service.dto.CoreValuePT;
import io.protone.domain.CORPropertyKey;
import io.protone.domain.CORPropertyValue;
import io.protone.service.dto.CORPropertyValueDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CORPropertyValue and its DTO CORPropertyValueDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCORPropertyValueMapper {

    @Mapping(source = "propertyKey.id", target = "propertyKeyId")
    CoreValuePT cORPropertyValueToCORPropertyValueDTO(CORPropertyValue cORPropertyValue);

    List<CoreValuePT> cORPropertyValuesToCORPropertyValueDTOs(List<CORPropertyValue> cORPropertyValues);

    @Mapping(source = "propertyKeyId", target = "propertyKey")
    CORPropertyValue cORPropertyValueDTOToCORPropertyValue(CoreValuePT cORPropertyValueDTO);

    List<CORPropertyValue> cORPropertyValueDTOsToCORPropertyValues(List<CoreValuePT> cORPropertyValueDTOs);

    default CORPropertyKey cORPropertyKeyFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORPropertyKey cORPropertyKey = new CORPropertyKey();
        cORPropertyKey.setId(id);
        return cORPropertyKey;
    }
}
