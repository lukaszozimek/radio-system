package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfIndustryPT;
import io.protone.domain.CORNetwork;
import io.protone.domain.TRAIndustry;
import io.protone.service.dto.TRAIndustryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity TRAIndustry and its DTO TRAIndustryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomTRAIndustryMapper {

    @Mapping(source = "network.id", target = "networkId")
    ConfIndustryPT tRAIndustryToTRAIndustryDTO(TRAIndustry tRAIndustry);

    List<ConfIndustryPT> tRAIndustriesToTRAIndustryDTOs(List<TRAIndustry> tRAIndustries);

    @Mapping(source = "networkId", target = "network")
    TRAIndustry tRAIndustryDTOToTRAIndustry(ConfIndustryPT tRAIndustryDTO);

    List<TRAIndustry> tRAIndustryDTOsToTRAIndustries(List<ConfIndustryPT> tRAIndustryDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
