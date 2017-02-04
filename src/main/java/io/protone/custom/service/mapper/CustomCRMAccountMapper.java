package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomCRMAccountMapper {
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
    private CustomTRAOrderMapper traOrderMapper;
    @Inject
    private CustomTRACampaignMapper customTRACampaignMapper;
    @Inject
    private CustomCORUserMapper corUserMapper;

    public CRMAccount createCrmAcountEntity(CrmAccountPT crmAccountPT, CORNetwork corNetwork) {
        CRMAccount crmAccount = new CRMAccount();
        crmAccount.setId(crmAccountPT.getId());
        return crmAccount
            .name(crmAccountPT.getName())
            .idNumber1(crmAccountPT.getIdNumber1())
            .idNumber2(crmAccountPT.getIdNumber2())
            .getShortName(crmAccountPT.getShortName())
            .vatNumber(crmAccountPT.getVatNumber())
            .paymentDelay(Long.valueOf(crmAccountPT.getPaymentDelay()))
            .industry(industryMapper.tRAIndustryDTOToTRAIndustry(crmAccountPT.getIndustry()))
            .keeper(corUserMapper.tranformUserDTO(crmAccountPT.getAccount()))
            .size(customCORSizeMapper.cORSizeDTOToCORSize(crmAccountPT.getSize()))
            .range(customCORRangeMapper.cORRangeDTOToCORRange(crmAccountPT.getRange()))
            .addres(corAddressMapper.cORAddressDTOToCORAddress(crmAccountPT.getAdress()))
            .person(customTRAPersonMapper.createPersonEntity(crmAccountPT.getPersons()))
            .area(customCORAreaMapper.cORAreaDTOToCORArea(crmAccountPT.getArea()))
            .network(corNetwork);
    }

    public CRMAccount createCrmAcountEntity(TraCustomerPT traCustomerPT, CORNetwork network) {
        CRMAccount crmAccount = new CRMAccount();
        crmAccount.setId(traCustomerPT.getId());
        return crmAccount
            .name(traCustomerPT.getName())
            .idNumber1(traCustomerPT.getIdNumber1())
            .idNumber2(traCustomerPT.getIdNumber2())
            .getShortName(traCustomerPT.getShortName())
            .vatNumber(traCustomerPT.getVatNumber())
            .paymentDelay(Long.valueOf(traCustomerPT.getPaymentDelay()))
            .industry(industryMapper.tRAIndustryDTOToTRAIndustry(traCustomerPT.getIndustry()))
            .keeper(corUserMapper.tranformUserDTO(traCustomerPT.getAccount()))
            .size(customCORSizeMapper.cORSizeDTOToCORSize(traCustomerPT.getSize()))
            .range(customCORRangeMapper.cORRangeDTOToCORRange(traCustomerPT.getRange()))
            .addres(corAddressMapper.cORAddressDTOToCORAddress(traCustomerPT.getAdress()))
            .person(customTRAPersonMapper.createPersonEntity(traCustomerPT.getPersons()))
            .area(customCORAreaMapper.cORAreaDTOToCORArea(traCustomerPT.getArea()))
            .network(network);
    }

    public TraCustomerPT createCustomerTrafficDTO(CRMAccount traCustomerPT) {
        TraCustomerPT crmAccount = new TraCustomerPT();
        crmAccount.setId(traCustomerPT.getId());
        return crmAccount.
            id(traCustomerPT.getId())
            .name(traCustomerPT.getName())
            .idNumber1(traCustomerPT.getIdNumber1())
            .idNumber2(traCustomerPT.getIdNumber2())
            .shortName(traCustomerPT.getShortName())
            .vatNumber(traCustomerPT.getVatNumber())
            .paymentDelay(Math.toIntExact(traCustomerPT.getPaymentDelay()))
            .industry(industryMapper.tRAIndustryToTRAIndustryDTO(traCustomerPT.getIndustry()))
            .account(corUserMapper.corUserMapper(traCustomerPT.getKeeper()))
            .size(customCORSizeMapper.cORSizeToCORSizeDTO(traCustomerPT.getSize()))
            .range(customCORRangeMapper.cORRangeToCORRangeDTO(traCustomerPT.getRange()))
            .adress(corAddressMapper.cORAddressToCORAddressDTO(traCustomerPT.getAddres()))
            .persons(customTRAPersonMapper.createDTOObject(traCustomerPT.getPerson()))
            .area(customCORAreaMapper.cORAreaToCORAreaDTO(traCustomerPT.getArea()));
    }

    public CrmAccountPT buildContactDTOFromEntities(CRMAccount crmAccount) {
        return new CrmAccountPT()
            .id(crmAccount.getId())
            .name(crmAccount.getName())
            .idNumber1(crmAccount.getIdNumber1())
            .idNumber2(crmAccount.getIdNumber2())
            .shortName(crmAccount.getShortName())
            .vatNumber(crmAccount.getVatNumber())
            .paymentDelay(Math.toIntExact(crmAccount.getPaymentDelay()))
            .industry(industryMapper.tRAIndustryToTRAIndustryDTO(crmAccount.getIndustry()))
            .account(corUserMapper.corUserMapper(crmAccount.getKeeper()))
            .size(customCORSizeMapper.cORSizeToCORSizeDTO(crmAccount.getSize()))
            .range(customCORRangeMapper.cORRangeToCORRangeDTO(crmAccount.getRange()))
            .adress(corAddressMapper.cORAddressToCORAddressDTO(crmAccount.getAddres()))
            .persons(customTRAPersonMapper.createDTOObject(crmAccount.getPerson()))
            .area(customCORAreaMapper.cORAreaToCORAreaDTO(crmAccount.getArea()))
            .tasks(crmAccount.getTasks().stream().map(crmTask -> customCRMTaskMapper.createCrmTask(crmTask)).collect(toList()));

    }


}
