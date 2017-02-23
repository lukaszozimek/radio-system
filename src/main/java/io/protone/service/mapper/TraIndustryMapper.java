package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TraIndustryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TraIndustry and its DTO TraIndustryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraIndustryMapper {

    @Mapping(source = "network.id", target = "networkId")
    TraIndustryDTO traIndustryToTraIndustryDTO(TraIndustry traIndustry);

    List<TraIndustryDTO> traIndustriesToTraIndustryDTOs(List<TraIndustry> traIndustries);

    @Mapping(source = "networkId", target = "network")
    TraIndustry traIndustryDTOToTraIndustry(TraIndustryDTO traIndustryDTO);

    List<TraIndustry> traIndustryDTOsToTraIndustries(List<TraIndustryDTO> traIndustryDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
