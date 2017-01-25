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

    public TraAdvertisementPT transformEntityToDTO(TRAAdvertisement traAdvertisement, TRAIndustry traIndustry, CRMAccount crmAccount, LibItemPT libItemPT) {
        return new TraAdvertisementPT()
            .id(traAdvertisement.getId())
            .name(traAdvertisement.getName())
            .description(traAdvertisement.getDescription())
            .industryId(customTRAIndustryMapper.tRAIndustryToTRAIndustryDTO(traIndustry))
            .mediaItemId(libItemPT)
            .customerId(customerService.getCustomer(crmAccount, traIndustry.getNetwork()));

    }

    public CORAssociation createAdvertismentIndustryAssociation(TRAAdvertisement traAdvertisement, TRAIndustry traIndustry) {
        return new CORAssociation().name("Advertisment industry")
            .sourceId(traAdvertisement.getId())
            .sourceClass(TRAAdvertisement.class.getName())
            .targetId(traIndustry.getId())
            .targetClass(TRAIndustry.class.getName());
    }

    public CORAssociation createAdvertismentCrmAccountAssociation(TRAAdvertisement traAdvertisement, CRMAccount crmAccount) {
        return new CORAssociation().name("Advertisment customer")
            .sourceId(traAdvertisement.getId())
            .sourceClass(TRAAdvertisement.class.getName())
            .targetId(crmAccount.getId())
            .targetClass(CRMAccount.class.getName());
    }

    public CORAssociation createAdvertismentMediaItemAssociation(TRAAdvertisement traAdvertisement, LIBMediaItem libMediaItem) {
        return new CORAssociation().name("Advertisment media item")
            .sourceId(traAdvertisement.getId())
            .sourceClass(TRAAdvertisement.class.getName())
            .targetId(libMediaItem.getId())
            .targetClass(LIBMediaItem.class.getName());
    }


}
