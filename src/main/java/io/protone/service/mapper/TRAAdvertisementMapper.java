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

    @Mapping(source = "mediaItem.id", target = "mediaItemId")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "industry.id", target = "industryId")
    TRAAdvertisementDTO tRAAdvertisementToTRAAdvertisementDTO(TRAAdvertisement tRAAdvertisement);

    List<TRAAdvertisementDTO> tRAAdvertisementsToTRAAdvertisementDTOs(List<TRAAdvertisement> tRAAdvertisements);

    @Mapping(source = "mediaItemId", target = "mediaItem")
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "industryId", target = "industry")
    TRAAdvertisement tRAAdvertisementDTOToTRAAdvertisement(TRAAdvertisementDTO tRAAdvertisementDTO);

    List<TRAAdvertisement> tRAAdvertisementDTOsToTRAAdvertisements(List<TRAAdvertisementDTO> tRAAdvertisementDTOs);

    default LIBMediaItem lIBMediaItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        LIBMediaItem lIBMediaItem = new LIBMediaItem();
        lIBMediaItem.setId(id);
        return lIBMediaItem;
    }

    default TRACustomer tRACustomerFromId(Long id) {
        if (id == null) {
            return null;
        }
        TRACustomer tRACustomer = new TRACustomer();
        tRACustomer.setId(id);
        return tRACustomer;
    }

    default TRAIndustry tRAIndustryFromId(Long id) {
        if (id == null) {
            return null;
        }
        TRAIndustry tRAIndustry = new TRAIndustry();
        tRAIndustry.setId(id);
        return tRAIndustry;
    }
}
