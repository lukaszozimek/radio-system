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

    @Mapping(source = "networkId", target = "network")
    CorCurrency DTO2DB(ConfCurrencyPT cORRangeDTO);

    @Mapping(source = "network.id", target = "networkId")
    ConfCurrencyPT DB2DTO(CorCurrency cORRange);

    List<ConfCurrencyPT> DBs2DTOs(List<CorCurrency> cORRanges);

    List<CorCurrency> DTOs2DBs(List<ConfCurrencyPT> cORRangeDTOs);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
