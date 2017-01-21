package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.domain.TRAAdvertisement;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomTRAAdvertismentMapper {
    @Inject
    private CustomTRAIndustryMapper customTRAIndustryMapper;

    public TRAAdvertisement transformDTOToEntity(TraAdvertisementPT traAdvertisementPT) {
       /* TRAAdvertisement advertisement = new TRAAdvertisement();
        return advertisement.name(traAdvertisementPT.getName())
            .description(traAdvertisementPT.getDescription())
            .industry(customTRAIndustryMapper.tRAIndustryDTOToTRAIndustry(traAdvertisementPT.getIndustryId()))
            .mediaItem(new LIBMediaItem())
            .customer(new TRACustomer());
    */
        return null;
    }

    public TraAdvertisementPT transformEntityToDTO(TRAAdvertisement traAdvertisement) {
      /*  return new TraAdvertisementPT()
            .id(traAdvertisement.getId())
            .name(traAdvertisement.getName())
            .description(traAdvertisement.getDescription())
            .industryId(customTRAIndustryMapper.tRAIndustryToTRAIndustryDTO(traAdvertisement.getIndustry()))
            .mediaItemId(new LibItemPT())
            .customerId(new TraCustomerPT());
    */
        return null;
    }

}
