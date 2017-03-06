package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfTaxPT;
import io.protone.custom.service.dto.CoreRangePT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorRange;
import io.protone.domain.CorTax;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorRange and its DTO CorRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCorTaxMapper {

    @Mapping(source = "networkId", target = "network")
    CorTax DTO2DB(ConfTaxPT cORRangeDTO);

    @Mapping(source = "network.id", target = "networkId")
    ConfTaxPT DB2DTO(CorTax cORRange);

    List<ConfTaxPT> DBs2DTOs(List<CorTax> cORRanges);

    List<CorTax> DTOs2DBs(List<ConfTaxPT> cORRangeDTOs);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
