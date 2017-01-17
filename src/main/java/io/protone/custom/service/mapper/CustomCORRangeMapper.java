package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreRangePT;
import io.protone.domain.CORNetwork;
import io.protone.domain.CORRange;
import io.protone.service.dto.CORRangeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CORRange and its DTO CORRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCORRangeMapper {

    @Mapping(source = "network.id", target = "networkId")
    CoreRangePT cORRangeToCORRangeDTO(CORRange cORRange);

    List<CoreRangePT> cORRangesToCORRangeDTOs(List<CORRange> cORRanges);

    @Mapping(source = "networkId", target = "network")
    CORRange cORRangeDTOToCORRange(CoreRangePT cORRangeDTO);

    List<CORRange> cORRangeDTOsToCORRanges(List<CoreRangePT> cORRangeDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
