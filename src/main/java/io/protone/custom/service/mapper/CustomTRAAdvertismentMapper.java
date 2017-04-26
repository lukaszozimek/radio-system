package io.protone.custom.service.mapper;

import io.protone.custom.service.TraCustomerService;
import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.LibMediaItemPT;
import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibMediaItem;
import io.protone.domain.TraAdvertisement;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomTRAAdvertismentMapper {

    @Inject
    private CustomCorDictionaryMapper corDictionaryMapper;

    @Inject
    private CustomCrmAccountMapper customCrmAccountMapper;

    @Inject
    private TraCustomerService customerService;

    @Inject
    private CustomLibMediaItemMapper customLibMediaItemMapper;


    public TraAdvertisement transformDTOToEntity(TraAdvertisementPT traAdvertisementPT, CorNetwork corNetwork) {
        if (traAdvertisementPT == null || corNetwork == null) {
            return new TraAdvertisement();
        }
        TraAdvertisement advertisement = new TraAdvertisement();
        advertisement.setId(traAdvertisementPT.getId());
        advertisement.name(traAdvertisementPT.getName())
            .network(corNetwork)
            .description(traAdvertisementPT.getDescription());
        if (traAdvertisementPT.getIndustryId() != null) {
            advertisement.industry(corDictionaryMapper.corDictionaryDTOToCorDictionary(traAdvertisementPT.getIndustryId()));
        }
        if (traAdvertisementPT.getMediaItemId() != null) {
            advertisement.mediaItem(customLibMediaItemMapper.lIBMediaItemPTToLibMediaItem(traAdvertisementPT.getMediaItemId()));
        }
        if (traAdvertisementPT.getCustomerId() != null) {
            advertisement.customer(customCrmAccountMapper.traDTO2DB(traAdvertisementPT.getCustomerId()));
        }
        if (traAdvertisementPT.getTypePT() != null) {
            advertisement.type(corDictionaryMapper.corDictionaryDTOToCorDictionary(traAdvertisementPT.getTypePT()));
        }
        return advertisement;
    }

    public TraAdvertisementPT transformEntityToDTO(TraAdvertisement traAdvertisement) {
        if (traAdvertisement == null) {
            return new TraAdvertisementPT();
        }
        return new TraAdvertisementPT()
            .id(traAdvertisement.getId())
            .name(traAdvertisement.getName())
            .description(traAdvertisement.getDescription())
            .industryId(corDictionaryMapper.corDictionaryToCorDictionaryDTO(traAdvertisement.getIndustry()))
            .mediaItemId(customLibMediaItemMapper.lIBMediaItemToLibMediaItemPT(traAdvertisement.getMediaItem()))
            .type(corDictionaryMapper.corDictionaryToCorDictionaryDTO(traAdvertisement.getType()))
            .customerId(customCrmAccountMapper.traDB2DTO(traAdvertisement.getCustomer()));

    }


}
