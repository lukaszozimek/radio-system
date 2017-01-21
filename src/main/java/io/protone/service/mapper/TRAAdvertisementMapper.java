package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TRAAdvertisementDTO;

import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for the entity TRAAdvertisement and its DTO TRAAdvertisementDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TRAAdvertisementMapper {
    TRAAdvertisementDTO tRAAdvertisementToTRAAdvertisementDTO(TRAAdvertisement tRAAdvertisement);

    List<TRAAdvertisementDTO> tRAAdvertisementsToTRAAdvertisementDTOs(List<TRAAdvertisement> tRAAdvertisements);


    TRAAdvertisement tRAAdvertisementDTOToTRAAdvertisement(TRAAdvertisementDTO tRAAdvertisementDTO);

    List<TRAAdvertisement> tRAAdvertisementDTOsToTRAAdvertisements(List<TRAAdvertisementDTO> tRAAdvertisementDTOs);

}
