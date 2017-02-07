package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreRangePT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorRange;
import io.protone.service.dto.CorRangeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorRange and its DTO CorRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCorRangeMapper {

    @Mapping(source = "networkId", target = "network")
    CorRange cORRangeDTOToCorRange(CoreRangePT cORRangeDTO);

    @Mapping(source = "network.id", target = "networkId")
    CoreRangePT cORRangeToCorRangeDTO(CorRange cORRange);

    List<CoreRangePT> cORRangesToCorRangeDTOs(List<CorRange> cORRanges);

    List<CorRange> cORRangeDTOsToCorRanges(List<CoreRangePT> cORRangeDTOs);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
