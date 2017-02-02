package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreManagedUserPT;
import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.dto.TraCustomerPersonPT;
import io.protone.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Mapper for the entity CRMContact and its DTO CRMContactDTO.
 */
@Service
public class CustomCRMContactMapper {
    @Inject
    CustomTRAPersonMapper customTRAPersonMapper;

    @Inject
    CustomCORAddressMapper corAddressMapper;

    @Inject
    CustomTRAIndustryMapper industryMapper;

    @Inject
    CustomCORRangeMapper customCORRangeMapper;

    @Inject
    CustomCORAreaMapper customCORAreaMapper;

    @Inject
    CustomCORSizeMapper customCORSizeMapper;

    @Inject
    CustomCRMTaskMapper customCRMTaskMapper;

    public CRMContact createCrmContactEntity(CrmContactPT crmContactPT, CORNetwork corNetwork) {
        CRMContact crmContact = new CRMContact();
        crmContact.setId(crmContactPT.getId());
        return crmContact
            .paymentDelay(Long.parseLong(crmContactPT.getPaymentDelay().toString()))
            .idNumber1(crmContactPT.getIdNumber1())
            .idNumber2(crmContactPT.getIdNumber2())
            .name(crmContactPT.getName())
            .shortName(crmContactPT.getShortName())
            .vatNumber(crmContactPT.getVatNumber())
            .network(corNetwork);
    }

    public CORAddress createAdressEntity(CrmContactPT crmContactPT) {
        return corAddressMapper.cORAddressDTOToCORAddress(crmContactPT.getAdress());
    }

    public TRAIndustry createIndustryEntity(CrmContactPT crmContactPT) {
        return industryMapper.tRAIndustryDTOToTRAIndustry(crmContactPT.getIndustry());
    }

    public CORArea createCorAreaEntity(CrmContactPT crmContactPT) {
        return customCORAreaMapper.cORAreaDTOToCORArea(crmContactPT.getArea());
    }

    public CORSize createCorSizeEntity(CrmContactPT crmContactPT) {
        return customCORSizeMapper.cORSizeDTOToCORSize(crmContactPT.getSize());
    }

    public CORRange createRangeEntity(CrmContactPT crmContactPT) {
        return customCORRangeMapper.cORRangeDTOToCORRange(crmContactPT.getRange());
    }

    public Map<CORPerson, List<CORContact>> createMapPersonContact(CrmContactPT crmContactPT) {
        return customTRAPersonMapper.createMapPersonToContact(crmContactPT.getPersons());
    }


    public CrmContactPT buildContactDTOFromEntities(CRMContact crmContact) {
        return new CrmContactPT()
            .id(crmContact.getId())
            .name(crmContact.getName())
            .idNumber1(crmContact.getIdNumber1())
            .idNumber2(crmContact.getIdNumber2())
            .shortName(crmContact.getShortName())
            .vatNumber(crmContact.getVatNumber())
            .paymentDelay(crmContact.getPaymentDelay())
            .industry(industryMapper.tRAIndustryToTRAIndustryDTO(crmContact.getIndustry()))
            .account(crmContact.getKeeper())
            .size(customCORSizeMapper.cORSizeToCORSizeDTO(crmContact.getSize()))
            .range(customCORRangeMapper.cORRangeToCORRangeDTO(crmContact.getRange()))
            .adress(corAddressMapper.cORAddressToCORAddressDTO(crmContact.getAddres()))
            .persons(customTRAPersonMapper.createDTOObject(crmContact.getPerson()))
            .area(customCORAreaMapper.cORAreaToCORAreaDTO(crmContact.getArea()))
            .tasks(crmContact.getTasks())
            .networkId(crmContact.getId());

    }

}
