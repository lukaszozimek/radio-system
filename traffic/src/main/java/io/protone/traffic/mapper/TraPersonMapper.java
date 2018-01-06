package io.protone.traffic.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorPerson;
import io.protone.core.mapper.CorContactMapper;
import io.protone.traffic.api.dto.TraCustomerPersonDTO;
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
public interface TraPersonMapper {
    TraCustomerPersonDTO corPerson2TraCustomerPersonPT(CorPerson person);

    CorPerson traCustomerPersonPT2CorPerson(TraCustomerPersonDTO personPT, @Context CorChannel network);

    default List<CorPerson> traCustomerPersonPTs2CorPersons(List<TraCustomerPersonDTO> dtos, @Context CorChannel networkId) {
        List<CorPerson> corPeople = new ArrayList<>();
        if (dtos.isEmpty() || dtos == null) {
            return null;
        }
        for (TraCustomerPersonDTO dto : dtos) {
            corPeople.add(traCustomerPersonPT2CorPerson(dto, networkId));
        }
        return corPeople;
    }


    @AfterMapping
    default void traCustomerPersonPTToCorPersonAfterMapping(TraCustomerPersonDTO dto, @MappingTarget CorPerson entity, @Context CorChannel corNetwork) {
        entity.setChannel(corNetwork);
    }
}
