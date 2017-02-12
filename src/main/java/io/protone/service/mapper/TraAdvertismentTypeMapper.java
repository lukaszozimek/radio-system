package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TraAdvertismentTypeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TraAdvertismentType and its DTO TraAdvertismentTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraAdvertismentTypeMapper {

    TraAdvertismentTypeDTO traAdvertismentTypeToTraAdvertismentTypeDTO(TraAdvertismentType traAdvertismentType);

    List<TraAdvertismentTypeDTO> traAdvertismentTypesToTraAdvertismentTypeDTOs(List<TraAdvertismentType> traAdvertismentTypes);

    TraAdvertismentType traAdvertismentTypeDTOToTraAdvertismentType(TraAdvertismentTypeDTO traAdvertismentTypeDTO);

    List<TraAdvertismentType> traAdvertismentTypeDTOsToTraAdvertismentTypes(List<TraAdvertismentTypeDTO> traAdvertismentTypeDTOs);
}
