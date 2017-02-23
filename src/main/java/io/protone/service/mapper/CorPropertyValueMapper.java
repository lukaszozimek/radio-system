package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CorPropertyValueDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CorPropertyValue and its DTO CorPropertyValueDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorPropertyValueMapper {

    @Mapping(source = "propertyKey.id", target = "propertyKeyId")
    @Mapping(source = "libItemPropertyValue.id", target = "libItemPropertyValueId")
    CorPropertyValueDTO corPropertyValueToCorPropertyValueDTO(CorPropertyValue corPropertyValue);

    List<CorPropertyValueDTO> corPropertyValuesToCorPropertyValueDTOs(List<CorPropertyValue> corPropertyValues);

    @Mapping(source = "propertyKeyId", target = "propertyKey")
    @Mapping(source = "libItemPropertyValueId", target = "libItemPropertyValue")
    CorPropertyValue corPropertyValueDTOToCorPropertyValue(CorPropertyValueDTO corPropertyValueDTO);

    List<CorPropertyValue> corPropertyValueDTOsToCorPropertyValues(List<CorPropertyValueDTO> corPropertyValueDTOs);

    default CorPropertyKey corPropertyKeyFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorPropertyKey corPropertyKey = new CorPropertyKey();
        corPropertyKey.setId(id);
        return corPropertyKey;
    }

    default LibMediaItem libMediaItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibMediaItem libMediaItem = new LibMediaItem();
        libMediaItem.setId(id);
        return libMediaItem;
    }
}
