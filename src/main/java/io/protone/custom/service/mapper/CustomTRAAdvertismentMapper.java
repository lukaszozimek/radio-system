package io.protone.custom.service.mapper;

import io.protone.custom.service.TraCustomerService;
import io.protone.custom.service.dto.LibItemPT;
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
    private CustomTraIndustryMapper customTraIndustryMapper;

    @Inject
    private CustomCrmAccountMapper customCrmAccountMapper;

    @Inject
    private TraCustomerService customerService;

    public TraAdvertisement transformDTOToEntity(TraAdvertisementPT traAdvertisementPT, CorNetwork corNetwork) {
        TraAdvertisement advertisement = new TraAdvertisement();
        advertisement.setId(traAdvertisementPT.getId());
        return advertisement.name(traAdvertisementPT.getName())
            .description(traAdvertisementPT.getDescription())
            .industry(customTraIndustryMapper.tRAIndustryDTOToTraIndustry(traAdvertisementPT.getIndustryId()))
            .libitem((new LibMediaItem()))
            .customer(customCrmAccountMapper.createCrmAcountEntity(traAdvertisementPT.getCustomerId(), corNetwork));

    }

    public TraAdvertisementPT transformEntityToDTO(TraAdvertisement traAdvertisement) {
        return new TraAdvertisementPT()
            .id(traAdvertisement.getId())
            .name(traAdvertisement.getName())
            .description(traAdvertisement.getDescription())
            .industryId(customTraIndustryMapper.tRAIndustryToTraIndustryDTO(traAdvertisement.getIndustry()))
            .mediaItemId(new LibItemPT())
            .customerId(customCrmAccountMapper.createCustomerTrafficDTO(traAdvertisement.getCustomer()));

    }


}
