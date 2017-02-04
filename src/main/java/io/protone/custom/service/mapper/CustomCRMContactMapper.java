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

import static java.util.stream.Collectors.toList;

/**
 * Mapper for the entity CRMContact and its DTO CRMContactDTO.
 */
@Service
public class CustomCRMContactMapper {
    @Inject
    private CustomTRAPersonMapper customTRAPersonMapper;

    @Inject
    private CustomCORAddressMapper corAddressMapper;

    @Inject
    private CustomTRAIndustryMapper industryMapper;

    @Inject
    private CustomCORRangeMapper customCORRangeMapper;

    @Inject
    private CustomCORAreaMapper customCORAreaMapper;

    @Inject
    private CustomCORSizeMapper customCORSizeMapper;

    @Inject
    private CustomCRMTaskMapper customCRMTaskMapper;

    @Inject
    private CustomCORUserMapper corUserMapper;

    public CRMContact createCrmContactEntity(CrmContactPT crmContactPT, CORNetwork corNetwork) {
        CRMContact crmContact = new CRMContact();
        crmContact.setId(crmContactPT.getId());
        return crmContact
            .name(crmContact.getName())
            .idNumber1(crmContactPT.getIdNumber1())
            .idNumber2(crmContactPT.getIdNumber2())
            .shortName(crmContactPT.getShortName())
            .vatNumber(crmContactPT.getVatNumber())
            .paymentDelay(Long.valueOf(crmContactPT.getPaymentDelay()))
            .industry(industryMapper.tRAIndustryDTOToTRAIndustry(crmContactPT.getIndustry()))
            .keeper(corUserMapper.tranformUserDTO(crmContactPT.getAccount()))
            .size(customCORSizeMapper.cORSizeDTOToCORSize(crmContactPT.getSize()))
            .range(customCORRangeMapper.cORRangeDTOToCORRange(crmContactPT.getRange()))
            .addres(corAddressMapper.cORAddressDTOToCORAddress(crmContactPT.getAdress()))
            .area(customCORAreaMapper.cORAreaDTOToCORArea(crmContactPT.getArea()))
            .person(customTRAPersonMapper.createPersonEntity(crmContactPT.getPersons()))
            .network(corNetwork);
    }





    public CrmContactPT buildContactDTOFromEntities(CRMContact crmContact) {
        return new CrmContactPT()
            .id(crmContact.getId())
            .name(crmContact.getName())
            .idNumber1(crmContact.getIdNumber1())
            .idNumber2(crmContact.getIdNumber2())
            .shortName(crmContact.getShortName())
            .vatNumber(crmContact.getVatNumber())
            .paymentDelay(Math.toIntExact(crmContact.getPaymentDelay()))
            .industry(industryMapper.tRAIndustryToTRAIndustryDTO(crmContact.getIndustry()))
            .account(corUserMapper.corUserMapper(crmContact.getKeeper()))
            .size(customCORSizeMapper.cORSizeToCORSizeDTO(crmContact.getSize()))
            .range(customCORRangeMapper.cORRangeToCORRangeDTO(crmContact.getRange()))
            .adress(corAddressMapper.cORAddressToCORAddressDTO(crmContact.getAddres()))
            .persons(customTRAPersonMapper.createDTOObject(crmContact.getPerson()))
            .area(customCORAreaMapper.cORAreaToCORAreaDTO(crmContact.getArea()))
            .tasks(crmContact.getTasks().stream().map(crmTask -> customCRMTaskMapper.createCrmTask(crmTask)).collect(toList()))
            .networkId(crmContact.getId());

    }

}
