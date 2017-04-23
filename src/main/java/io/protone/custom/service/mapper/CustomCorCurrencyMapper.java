package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfCurrencyPT;
import io.protone.custom.service.dto.ConfTaxPT;
import io.protone.domain.CorCurrency;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorTax;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorRange and its DTO CorRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCorCurrencyMapper {

    CorCurrency DTO2DB(ConfCurrencyPT cORRangeDTO);

    ConfCurrencyPT DB2DTO(CorCurrency cORRange);

    List<ConfCurrencyPT> DBs2DTOs(List<CorCurrency> cORRanges);

    List<CorCurrency> DTOs2DBs(List<ConfCurrencyPT> cORRangeDTOs);

}
