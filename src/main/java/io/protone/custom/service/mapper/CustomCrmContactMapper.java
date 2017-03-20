package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CrmContactPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmContact;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

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
    private CustomCorUserMapperExt corUserMapper;
    @Inject
    private CustomCorNetworkMapper customCorNetworkMapper;

    public CrmContact createCrmContactEntity(CrmContactPT crmContactPT, CorNetwork corNetwork) {
        if (crmContactPT == null || corNetwork == null) {
            return new CrmContact();
        }
        CrmContact crmContact = new CrmContact();
        crmContact.setId(crmContactPT.getId());
        return crmContact
            .name(crmContactPT.getName())
            .externalId1(crmContactPT.getIdNumber1())
            .externalId2(crmContactPT.getIdNumber2())
            .shortName(crmContactPT.getShortName())
            .vatNumber(crmContactPT.getVatNumber())
            .paymentDelay(crmContactPT.getPaymentDelay())
            .industry(industryMapper.tRAIndustryDTOToTraIndustry(crmContactPT.getIndustry()))
            .keeper(corUserMapper.coreUserPTToUser(crmContactPT.getAccount()))
            .size(customCorSizeMapper.cORSizeDTOToCorSize(crmContactPT.getSize()))
            .range(customCorRangeMapper.cORRangeDTOToCorRange(crmContactPT.getRange()))
            .addres(corAddressMapper.cORAddressDTOToCorAddress(crmContactPT.getAdress()))
            .area(customCorAreaMapper.cORAreaDTOToCorArea(crmContactPT.getArea()))
            .person(customTRAPersonMapper.createPersonEntity(crmContactPT.getPersons(), corNetwork))
            .network(corNetwork);
    }


    public CrmContactPT buildContactDTOFromEntities(CrmContact crmContact) {
        if (crmContact == null) {
            return new CrmContactPT();
        }
        return new CrmContactPT()
            .id(crmContact.getId())
            .name(crmContact.getName())
            .idNumber1(crmContact.getExternalId1())
            .idNumber2(crmContact.getExternalId2())
            .shortName(crmContact.getShortName())
            .vatNumber(crmContact.getVatNumber())
            .paymentDelay(crmContact.getPaymentDelay())
            .industry(industryMapper.tRAIndustryToTraIndustryDTO(crmContact.getIndustry()))
            //.account(corUserMapper.corUserMapper(crmContact.getKeeper()))
            .size(customCorSizeMapper.cORSizeToCorSizeDTO(crmContact.getSize()))
            .range(customCorRangeMapper.cORRangeToCorRangeDTO(crmContact.getRange()))
            .adress(corAddressMapper.cORAddressToCorAddressDTO(crmContact.getAddres()))
            .persons(customTRAPersonMapper.createDTOObject(crmContact.getPerson()))
            .area(customCorAreaMapper.cORAreaToCorAreaDTO(crmContact.getArea()))
            .tasks(crmContact.getTasks().stream().map(crmTask -> customCrmTaskMapper.createCrmTask(crmTask)).collect(toList()))
            .networkId(customCorNetworkMapper.cORNetworkToCorNetworkDTO(crmContact.getNetwork()));

    }

}
