package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CORPropertyValueDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CORPropertyValue and its DTO CORPropertyValueDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CORPropertyValueMapper {

    @Mapping(source = "propertyKey.id", target = "propertyKeyId")
    CORPropertyValueDTO cORPropertyValueToCORPropertyValueDTO(CORPropertyValue cORPropertyValue);

    List<CORPropertyValueDTO> cORPropertyValuesToCORPropertyValueDTOs(List<CORPropertyValue> cORPropertyValues);

    @Mapping(source = "propertyKeyId", target = "propertyKey")
    CORPropertyValue cORPropertyValueDTOToCORPropertyValue(CORPropertyValueDTO cORPropertyValueDTO);

    List<CORPropertyValue> cORPropertyValueDTOsToCORPropertyValues(List<CORPropertyValueDTO> cORPropertyValueDTOs);

    default CORPropertyKey cORPropertyKeyFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORPropertyKey cORPropertyKey = new CORPropertyKey();
        cORPropertyKey.setId(id);
        return cORPropertyKey;
    }
}
