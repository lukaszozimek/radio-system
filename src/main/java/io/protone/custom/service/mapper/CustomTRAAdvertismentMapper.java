package io.protone.custom.service.mapper;

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


    public TRAAdvertisement transformDTOToEntity(TraAdvertisementPT traAdvertisementPT) {
        TRAAdvertisement advertisement = new TRAAdvertisement();
        advertisement.setId(traAdvertisementPT.getId());
        return advertisement.name(traAdvertisementPT.getName())
            .description(traAdvertisementPT.getDescription());

    }

    public TraAdvertisementPT transformEntityToDTO(TRAAdvertisement traAdvertisement, TRAIndustry traIndustry, CRMAccount crmAccount, LibItemPT libItemPT) {
        return new TraAdvertisementPT()
            .id(traAdvertisement.getId())
            .name(traAdvertisement.getName())
            .description(traAdvertisement.getDescription())
            .industryId(customTRAIndustryMapper.tRAIndustryToTRAIndustryDTO(traIndustry))
            .mediaItemId(libItemPT)
            .customerId(customCRMAccountMapper.createCustomerTrafficDTO(crmAccount));

    }

    public CORAssociation createCrmAccountAssociation(TRAAdvertisement traAdvertisement, TRAIndustry traIndustry) {
        return new CORAssociation().name("Advertisment industry")
            .sourceId(traAdvertisement.getId())
            .sourceClass(TRAAdvertisement.class.getName())
            .targetId(traIndustry.getId())
            .targetClass(CRMAccount.class.getName());
    }

    public CORAssociation createCrmAccountAssociation(TRAAdvertisement traAdvertisement, CRMAccount crmAccount) {
        return new CORAssociation().name("Advertisment customer")
            .sourceId(traAdvertisement.getId())
            .sourceClass(TRAAdvertisement.class.getName())
            .targetId(crmAccount.getId())
            .targetClass(CRMAccount.class.getName());
    }

    public CORAssociation createTraCampaignAssociation(TRAAdvertisement traAdvertisement, TRACampaign campaign) {
        return new CORAssociation().name("Advertisment media item")
            .sourceId(traAdvertisement.getId())
            .sourceClass(TRAOrder.class.getName())
            .targetId(campaign.getId())
            .targetClass(TRACampaign.class.getName());
    }

}
