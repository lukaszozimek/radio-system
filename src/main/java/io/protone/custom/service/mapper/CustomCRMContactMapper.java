package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.service.dto.CrmLeadPT;
import io.protone.custom.service.dto.CrmOpportunityPT;
import io.protone.domain.*;
import io.protone.service.dto.CRMContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.xml.ws.ServiceMode;
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

    public CRMContact createCrmContactEntity(CrmContactPT crmContactPT) {
        return new CRMContact()
            .paymentDelay(crmContactPT.getPaymentDelay())
            .externalId1(crmContactPT.getIdNumber1())
            .externalId2(crmContactPT.getIdNumber2())
            .name(crmContactPT.getName())
            .shortName(crmContactPT.getShortName())
            .vatNumber(crmContactPT.getVatNumber());
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
}
