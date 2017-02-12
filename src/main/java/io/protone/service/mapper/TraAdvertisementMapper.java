package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TraAdvertisementDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TraAdvertisement and its DTO TraAdvertisementDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraAdvertisementMapper {

    @Mapping(source = "mediaItem.id", target = "mediaItemId")
    @Mapping(source = "libitem.id", target = "libitemId")
    @Mapping(source = "industry.id", target = "industryId")
    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "network.id", target = "networkId")
    TraAdvertisementDTO traAdvertisementToTraAdvertisementDTO(TraAdvertisement traAdvertisement);

    List<TraAdvertisementDTO> traAdvertisementsToTraAdvertisementDTOs(List<TraAdvertisement> traAdvertisements);

    @Mapping(source = "mediaItemId", target = "mediaItem")
    @Mapping(source = "libitemId", target = "libitem")
    @Mapping(source = "industryId", target = "industry")
    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "networkId", target = "network")
    TraAdvertisement traAdvertisementDTOToTraAdvertisement(TraAdvertisementDTO traAdvertisementDTO);

    List<TraAdvertisement> traAdvertisementDTOsToTraAdvertisements(List<TraAdvertisementDTO> traAdvertisementDTOs);

    default LibMediaItem libMediaItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibMediaItem libMediaItem = new LibMediaItem();
        libMediaItem.setId(id);
        return libMediaItem;
    }

    default TraIndustry traIndustryFromId(Long id) {
        if (id == null) {
            return null;
        }
        TraIndustry traIndustry = new TraIndustry();
        traIndustry.setId(id);
        return traIndustry;
    }

    default TraAdvertismentType traAdvertismentTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        TraAdvertismentType traAdvertismentType = new TraAdvertismentType();
        traAdvertismentType.setId(id);
        return traAdvertismentType;
    }

    default CrmAccount crmAccountFromId(Long id) {
        if (id == null) {
            return null;
        }
        CrmAccount crmAccount = new CrmAccount();
        crmAccount.setId(id);
        return crmAccount;
    }

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }
}
