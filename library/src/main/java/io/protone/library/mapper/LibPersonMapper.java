package io.protone.library.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorPerson;
import io.protone.core.mapper.CorContactMapper;
import io.protone.library.api.dto.LibPersonDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 28/06/2017.
 */
@Mapper(componentModel = "spring", uses = {CorContactMapper.class})
public interface LibPersonMapper {
    LibPersonDTO corPerson2LibPersonPT(CorPerson db);

    List<LibPersonDTO> corPersons2LibPersonPTs(List<CorPerson> cORPeople);

    CorPerson libPersonPT2CorPerson(LibPersonDTO dto, @Context CorChannel network);

    default List<CorPerson> libPersonPTs2corPersons(List<LibPersonDTO> dtos, @Context CorChannel network) {
        List<CorPerson> corPeople = new ArrayList<>();
        if (dtos.isEmpty() || dtos == null) {
            return null;
        }
        for (LibPersonDTO dto : dtos) {
            corPeople.add(libPersonPT2CorPerson(dto, network));
        }
        return corPeople;
    }

    @AfterMapping
    default void libPersonPTToCorPersonAfterMapping(LibPersonDTO dto, @MappingTarget CorPerson entity, @Context CorChannel corNetwork) {
        entity.setChannel(corNetwork);
    }

}
