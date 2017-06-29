package io.protone.core.mapper;


import io.protone.core.api.dto.CorCurrencyDTO;
import io.protone.core.domain.CorCurrency;
import io.protone.core.domain.CorNetwork;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity CorRange and its DTO CorRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorCurrencyMapper {

    CorCurrency DTO2DB(CorCurrencyDTO cORRangeDTO, @Context CorNetwork corNetwork);

    CorCurrencyDTO DB2DTO(CorCurrency cORRange);

    List<CorCurrencyDTO> DBs2DTOs(List<CorCurrency> cORRanges);

    default List<CorCurrency> DTOs2DBs(List<CorCurrencyDTO> corCurrencyDTOS, CorNetwork corNetwork) {
        List<CorCurrency> corCurrencies = new ArrayList<>();
        if (corCurrencyDTOS.isEmpty() || corCurrencyDTOS == null) {
            return null;
        }
        for (CorCurrencyDTO dto : corCurrencyDTOS) {
            corCurrencies.add(DTO2DB(dto, corNetwork));
        }
        return corCurrencies;
    }

    @AfterMapping
    default void confCurrencyPTToCorCurrencyAfterMapping(CorCurrencyDTO dto, @MappingTarget CorCurrency entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
