package io.protone.core.mapper;


import io.protone.core.api.dto.CorValueDTO;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorPropertyValue;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity CorPropertyKey and its DTO CorPropertyKeyDTO.
 */
@Mapper(componentModel = "spring", uses = {CorPropertyKeyMapper.class})
public interface CorPropertyValueMapper {

    @Mapping(source = "propertyKey",target = "propertyKeyId")
    CorValueDTO DB2DTO(CorPropertyValue corPropertyValue);

    List<CorValueDTO> DBs2DTOs(List<CorPropertyValue> corPropertyValues);

    @Mapping(source = "propertyKeyId",target = "propertyKey")
    CorPropertyValue DTO2DB(CorValueDTO corValueDTO, @Context CorNetwork network);

    default List<CorPropertyValue> DTOs2DBs(List<CorValueDTO> corValueDTOS, @Context CorNetwork network) {
        List<CorPropertyValue> corPeople = new ArrayList<>();
        if (corValueDTOS.isEmpty() || corValueDTOS == null) {
            return null;
        }
        for (CorValueDTO dto : corValueDTOS) {
            corPeople.add(DTO2DB(dto, network));
        }
        return corPeople;
    }

}
