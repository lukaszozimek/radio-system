package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CORRangeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CORRange and its DTO CORRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CORRangeMapper {

    @Mapping(source = "network.id", target = "networkId")
    CORRangeDTO cORRangeToCORRangeDTO(CORRange cORRange);

    List<CORRangeDTO> cORRangesToCORRangeDTOs(List<CORRange> cORRanges);

    @Mapping(source = "networkId", target = "network")
    CORRange cORRangeDTOToCORRange(CORRangeDTO cORRangeDTO);

    List<CORRange> cORRangeDTOsToCORRanges(List<CORRangeDTO> cORRangeDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
