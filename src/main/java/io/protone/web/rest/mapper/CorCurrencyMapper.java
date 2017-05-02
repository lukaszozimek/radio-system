package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.ConfCurrencyPT;
import io.protone.domain.CorCurrency;
import io.protone.domain.CorNetwork;
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

    CorCurrency DTO2DB(ConfCurrencyPT cORRangeDTO, @Context CorNetwork corNetwork);

    ConfCurrencyPT DB2DTO(CorCurrency cORRange);

    List<ConfCurrencyPT> DBs2DTOs(List<CorCurrency> cORRanges);

    default List<CorCurrency> DTOs2DBs(List<ConfCurrencyPT> confCurrencyPTS, CorNetwork corNetwork) {
        List<CorCurrency> corCurrencies = new ArrayList<>();
        if (confCurrencyPTS.isEmpty() || confCurrencyPTS == null) {
            return null;
        }
        for (ConfCurrencyPT dto : confCurrencyPTS) {
            corCurrencies.add(DTO2DB(dto, corNetwork));
        }
        return corCurrencies;
    }

    @AfterMapping
    default void confCurrencyPTToCorCurrencyAfterMapping(ConfCurrencyPT dto, @MappingTarget CorCurrency entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
