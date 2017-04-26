package io.protone.custom.service.mapper;

import io.protone.custom.service.TraCustomerService;
import io.protone.custom.service.dto.CorDictionaryPT;
import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.LibMediaItemPT;
import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.service.dto.thin.LibMediaItemThinPt;
import io.protone.custom.service.dto.thin.TraCustomerThinPT;
import io.protone.domain.*;
import io.protone.service.dto.TraAdvertisementDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomTRAAdvertismentMapper {
    @Mapping(source = "mediaItem", target = "mediaItemId")
    @Mapping(source = "customer", target = "customerId")
    @Mapping(source = "type", target = "typeId")
    TraAdvertisementPT DBToDTO(TraAdvertisement traAdvertisement);

    List<TraAdvertisementPT> DBsToDTOs(List<TraAdvertisement> traAdvertisements);

    @Mapping(source = "mediaItemId", target = "mediaItem")
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "industryId", target = "industry")
    @Mapping(source = "typeId", target = "type")
    TraAdvertisement DTOToDB(TraAdvertisementPT traAdvertisementDTO);

    List<TraAdvertisement> DTOsToDBs(List<TraAdvertisementPT> traAdvertisementDTOs);

    LibMediaItem libMediaItemFromLibMediaItemThinPt(LibMediaItemThinPt id);

    LibMediaItemThinPt libMediaItemThinPtFromLibMediaItem(LibMediaItem id);


    CrmAccount crmAccountFromTraCustomerThinPT(TraCustomerThinPT id);

    TraCustomerThinPT traCustomerThinPTFromCrmAccount(CrmAccount id);

    CorDictionary corDictionaryFromCorDictionaryPT(CorDictionaryPT coreUserThinPT);

    CorDictionaryPT corDictionaryPTFromCorDictionary(CorDictionary coreUserThinPT);

}
