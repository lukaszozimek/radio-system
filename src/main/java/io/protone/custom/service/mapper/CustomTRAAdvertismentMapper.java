package io.protone.custom.service.mapper;

import io.protone.custom.service.TRACustomerService;
import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomTRAAdvertismentMapper {

    @Inject
    private CustomTRAIndustryMapper customTRAIndustryMapper;

    @Inject
    private CustomCRMAccountMapper customCRMAccountMapper;

    @Inject
    private TRACustomerService customerService;

    public TRAAdvertisement transformDTOToEntity(TraAdvertisementPT traAdvertisementPT) {
        TRAAdvertisement advertisement = new TRAAdvertisement();
        advertisement.setId(traAdvertisementPT.getId());
        return advertisement.name(traAdvertisementPT.getName())
            .description(traAdvertisementPT.getDescription());

    }

    public TraAdvertisementPT transformEntityToDTO(TRAAdvertisement traAdvertisement) {
        return new TraAdvertisementPT()
            .id(traAdvertisement.getId())
            .name(traAdvertisement.getName())
            .description(traAdvertisement.getDescription())
            .industryId(customTRAIndustryMapper.tRAIndustryToTRAIndustryDTO(traAdvertisement.getIndustry()))
            .mediaItemId(traAdvertisement.getLibitem())
            .customerId(customCRMAccountMapper.createCustomerTrafficDTO(traAdvertisement.getCRMAccount()));

    }




}
