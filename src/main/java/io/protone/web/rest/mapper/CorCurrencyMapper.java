package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.ConfCurrencyPT;
import io.protone.domain.CorCurrency;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity CorRange and its DTO CorRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorCurrencyMapper {

    CorCurrency DTO2DB(ConfCurrencyPT cORRangeDTO);

    ConfCurrencyPT DB2DTO(CorCurrency cORRange);

    List<ConfCurrencyPT> DBs2DTOs(List<CorCurrency> cORRanges);

    List<CorCurrency> DTOs2DBs(List<ConfCurrencyPT> cORRangeDTOs);

}
