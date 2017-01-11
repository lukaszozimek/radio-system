package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TRAIndustryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TRAIndustry and its DTO TRAIndustryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TRAIndustryMapper {

    @Mapping(source = "network.id", target = "networkId")
    TRAIndustryDTO tRAIndustryToTRAIndustryDTO(TRAIndustry tRAIndustry);

    List<TRAIndustryDTO> tRAIndustriesToTRAIndustryDTOs(List<TRAIndustry> tRAIndustries);

    @Mapping(source = "networkId", target = "network")
    TRAIndustry tRAIndustryDTOToTRAIndustry(TRAIndustryDTO tRAIndustryDTO);

    List<TRAIndustry> tRAIndustryDTOsToTRAIndustries(List<TRAIndustryDTO> tRAIndustryDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
