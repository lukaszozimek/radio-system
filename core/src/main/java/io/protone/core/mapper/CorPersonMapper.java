package io.protone.core.mapper;


import io.protone.core.api.dto.CorPersonDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorPerson;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity CorPerson and its DTO CorPersonDTO.
 */
@Mapper(componentModel = "spring", uses = {CorContactMapper.class})
public interface CorPersonMapper {

    CorPersonDTO DB2DTO(CorPerson cORPerson);

    List<CorPersonDTO> DBs2DTOs(List<CorPerson> cORPeople);

    CorPerson DTO2DB(CorPersonDTO cORPersonDTO, @Context CorChannel corNetwork);
    @AfterMapping
    default void corPersonDTOToCorPersonAfterMapping(CorPersonDTO dto, @MappingTarget CorPerson entity, @Context CorChannel corNetwork) {
        entity.setChannel(corNetwork);
    }
    default  List<CorPerson> DTOs2DBs(List<CorPersonDTO> cORPersonDTOs, @Context CorChannel network){
        List<CorPerson> corPeople = new ArrayList<>();
        if (cORPersonDTOs.isEmpty() || cORPersonDTOs == null) {
            return null;
        }
        for (CorPersonDTO dto : cORPersonDTOs) {
            corPeople.add(DTO2DB(dto, network));
        }
        return corPeople;
    }



}
