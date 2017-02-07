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
 * Mapper for the entity CrmContact and its DTO CrmContactDTO.
 */
@Service
public class CustomCrmContactMapper {
    @Inject
    private CustomTRAPersonMapper customTRAPersonMapper;

    @Inject
    private CustomCorAddressMapper corAddressMapper;

    @Inject
    private CustomTraIndustryMapper industryMapper;

    @Inject
    private CustomCorRangeMapper customCorRangeMapper;

    @Inject
    private CustomCorAreaMapper customCorAreaMapper;

    @Inject
    private CustomCorSizeMapper customCorSizeMapper;

    @Inject
    private CustomCrmTaskMapper customCrmTaskMapper;

    @Inject
    private CustomCORUserMapper corUserMapper;

    public CrmContact createCrmContactEntity(CrmContactPT crmContactPT, CorNetwork corNetwork) {
        CrmContact crmContact = new CrmContact();
        crmContact.setId(crmContactPT.getId());
        return crmContact
            .name(crmContact.getName())
            .externalId1(crmContactPT.getIdNumber1())
            .externalId1(crmContactPT.getIdNumber2())
            .shortName(crmContactPT.getShortName())
            .vatNumber(crmContactPT.getVatNumber())
            .paymentDelay(crmContactPT.getPaymentDelay())
            .industry(industryMapper.tRAIndustryDTOToTraIndustry(crmContactPT.getIndustry()))
            //.keeper(corUserMapper.tranformUserDTO(crmContactPT.getAccount()))
            .size(customCorSizeMapper.cORSizeDTOToCorSize(crmContactPT.getSize()))
            .range(customCorRangeMapper.cORRangeDTOToCorRange(crmContactPT.getRange()))
            .addres(corAddressMapper.cORAddressDTOToCorAddress(crmContactPT.getAdress()))
            .area(customCorAreaMapper.cORAreaDTOToCorArea(crmContactPT.getArea()))
            .person(customTRAPersonMapper.createPersonEntity(crmContactPT.getPersons()))
            .network(corNetwork);
    }





    public CrmContactPT buildContactDTOFromEntities(CrmContact crmContact) {
        return new CrmContactPT()
            .id(crmContact.getId())
            .name(crmContact.getName())
            .idNumber1(crmContact.getExternalId1())
            .idNumber2(crmContact.getExternalId2())
            .shortName(crmContact.getShortName())
            .vatNumber(crmContact.getVatNumber())
            .paymentDelay(Math.toIntExact(crmContact.getPaymentDelay()))
            .industry(industryMapper.tRAIndustryToTraIndustryDTO(crmContact.getIndustry()))
            //.account(corUserMapper.corUserMapper(crmContact.getKeeper()))
            .size(customCorSizeMapper.cORSizeToCorSizeDTO(crmContact.getSize()))
            .range(customCorRangeMapper.cORRangeToCorRangeDTO(crmContact.getRange()))
            .adress(corAddressMapper.cORAddressToCorAddressDTO(crmContact.getAddres()))
            .persons(customTRAPersonMapper.createDTOObject(crmContact.getPerson()))
            .area(customCorAreaMapper.cORAreaToCorAreaDTO(crmContact.getArea()))
            .tasks(crmContact.getTasks().stream().map(crmTask -> customCrmTaskMapper.createCrmTask(crmTask)).collect(toList()))
            .networkId(crmContact.getId());

    }

}
