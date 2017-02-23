package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomCrmAccountMapper {
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
    private CustomTraOrderMapper traOrderMapper;

    @Inject
    private CustomTRACampaignMapper customTRACampaignMapper;

    @Inject
    private CustomCORUserMapper corUserMapper;

    public CrmAccount createCrmAcountEntity(CrmAccountPT crmAccountPT, CorNetwork corNetwork) {
        CrmAccount crmAccount = new CrmAccount();
        crmAccount.setId(crmAccountPT.getId());
        return crmAccount
            .name(crmAccountPT.getName())
            .shortName(crmAccountPT.getShortName())
            .externalId1(crmAccountPT.getIdNumber1())
            .externalId2(crmAccountPT.getIdNumber2())
            .shortName(crmAccountPT.getShortName())
            .vatNumber(crmAccountPT.getVatNumber())
            .paymentDelay(crmAccountPT.getPaymentDelay())
            .industry(industryMapper.tRAIndustryDTOToTraIndustry(crmAccountPT.getIndustry()))
            //.keeper(corUserMapper.tranformUserDTO(crmAccountPT.getAccount()))
            .size(customCorSizeMapper.cORSizeDTOToCorSize(crmAccountPT.getSize()))
            .range(customCorRangeMapper.cORRangeDTOToCorRange(crmAccountPT.getRange()))
            .addres(corAddressMapper.cORAddressDTOToCorAddress(crmAccountPT.getAdress()))
            .person(customTRAPersonMapper.createPersonEntity(crmAccountPT.getPersons()))
            .area(customCorAreaMapper.cORAreaDTOToCorArea(crmAccountPT.getArea()))
            .network(corNetwork);
    }

    public CrmAccount createCrmAcountEntity(TraCustomerPT traCustomerPT, CorNetwork network) {
        CrmAccount crmAccount = new CrmAccount();
        crmAccount.setId(traCustomerPT.getId());
        return crmAccount
            .name(traCustomerPT.getName())

            .shortName(traCustomerPT.getShortName())
            .externalId1(traCustomerPT.getIdNumber1())
            .externalId2(traCustomerPT.getIdNumber2())
            .shortName(traCustomerPT.getShortName())
            .vatNumber(traCustomerPT.getVatNumber())
            .paymentDelay(traCustomerPT.getPaymentDelay())
            .industry(industryMapper.tRAIndustryDTOToTraIndustry(traCustomerPT.getIndustry()))
            //.keeper(corUserMapper.tranformUserDTO(traCustomerPT.getAccount()))
            .size(customCorSizeMapper.cORSizeDTOToCorSize(traCustomerPT.getSize()))
            .range(customCorRangeMapper.cORRangeDTOToCorRange(traCustomerPT.getRange()))
            .addres(corAddressMapper.cORAddressDTOToCorAddress(traCustomerPT.getAdress()))
            .person(customTRAPersonMapper.createPersonEntity(traCustomerPT.getPersons()))
            .area(customCorAreaMapper.cORAreaDTOToCorArea(traCustomerPT.getArea()))
            .network(network);
    }

    public TraCustomerPT createCustomerTrafficDTO(CrmAccount traCustomerPT) {
        TraCustomerPT crmAccount = new TraCustomerPT();
        crmAccount.setId(traCustomerPT.getId());
        return crmAccount.
            id(traCustomerPT.getId())
            .name(traCustomerPT.getName())

            .shortName(crmAccount.getShortName())
            .idNumber1(traCustomerPT.getExternalId1())
            .idNumber2(traCustomerPT.getExternalId2())
            .shortName(traCustomerPT.getShortName())
            .vatNumber(traCustomerPT.getVatNumber())
            .paymentDelay(Math.toIntExact(traCustomerPT.getPaymentDelay()))
            .industry(industryMapper.tRAIndustryToTraIndustryDTO(traCustomerPT.getIndustry()))
           // .account(corUserMapper.corUserMapper(traCustomerPT.getKeeper()))
            .size(customCorSizeMapper.cORSizeToCorSizeDTO(traCustomerPT.getSize()))
            .range(customCorRangeMapper.cORRangeToCorRangeDTO(traCustomerPT.getRange()))
            .adress(corAddressMapper.cORAddressToCorAddressDTO(traCustomerPT.getAddres()))
            .persons(customTRAPersonMapper.createDTOObject(traCustomerPT.getPerson()))
            .area(customCorAreaMapper.cORAreaToCorAreaDTO(traCustomerPT.getArea()));
    }

    public CrmAccountPT buildContactDTOFromEntities(CrmAccount crmAccount) {
        return new CrmAccountPT()
            .id(crmAccount.getId())
            .name(crmAccount.getName())
            .shortName(crmAccount.getShortName())
            .idNumber1(crmAccount.getExternalId1())
            .idNumber2(crmAccount.getExternalId2())
            .shortName(crmAccount.getShortName())
            .vatNumber(crmAccount.getVatNumber())
            .paymentDelay(Math.toIntExact(crmAccount.getPaymentDelay()))
            .industry(industryMapper.tRAIndustryToTraIndustryDTO(crmAccount.getIndustry()))
           // .account(corUserMapper.corUserMapper(crmAccount.getKeeper()))
            .size(customCorSizeMapper.cORSizeToCorSizeDTO(crmAccount.getSize()))
            .range(customCorRangeMapper.cORRangeToCorRangeDTO(crmAccount.getRange()))
            .adress(corAddressMapper.cORAddressToCorAddressDTO(crmAccount.getAddres()))
            .persons(customTRAPersonMapper.createDTOObject(crmAccount.getPerson()))
            .area(customCorAreaMapper.cORAreaToCorAreaDTO(crmAccount.getArea()))
            .tasks(crmAccount.getTasks().stream().map(crmTask -> customCrmTaskMapper.createCrmTask(crmTask)).collect(toList()));

    }


}
