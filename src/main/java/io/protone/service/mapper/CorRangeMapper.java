package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CorRangeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CorRange and its DTO CorRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorRangeMapper {

    @Mapping(source = "network.id", target = "networkId")
    CorRangeDTO corRangeToCorRangeDTO(CorRange corRange);

    List<CorRangeDTO> corRangesToCorRangeDTOs(List<CorRange> corRanges);

    @Mapping(source = "networkId", target = "network")
    CorRange corRangeDTOToCorRange(CorRangeDTO corRangeDTO);

    List<CorRange> corRangeDTOsToCorRanges(List<CorRangeDTO> corRangeDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
