package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfIndustryPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraIndustry;
import io.protone.domain.TraIndustry;
import io.protone.service.dto.TraIndustryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity TraIndustry and its DTO TraIndustryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomTraIndustryMapper {

    @Mapping(source = "network.id", target = "networkId")
    ConfIndustryPT tRAIndustryToTraIndustryDTO(TraIndustry tRAIndustry);

    List<ConfIndustryPT> tRAIndustriesToTraIndustryDTOs(List<TraIndustry> tRAIndustries);

    @Mapping(source = "networkId", target = "network")
    TraIndustry tRAIndustryDTOToTraIndustry(ConfIndustryPT tRAIndustryDTO);

    List<TraIndustry> tRAIndustryDTOsToTRAIndustries(List<ConfIndustryPT> tRAIndustryDTOs);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
