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
    private CustomCorUserMapperExt corUserMapper;

    public CrmAccount createCrmAcountEntity(CrmAccountPT crmAccountPT, CorNetwork corNetwork) {
        if (crmAccountPT == null || corNetwork == null) {
            return new CrmAccount();
        }
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
            .person(customTRAPersonMapper.createPersonEntity(crmAccountPT.getPersons(), corNetwork))
            .area(customCorAreaMapper.cORAreaDTOToCorArea(crmAccountPT.getArea()))
            .network(corNetwork);
    }

    public CrmAccount createCrmAcountEntity(TraCustomerPT traCustomerPT, CorNetwork network) {
        if (traCustomerPT == null || network == null) {
            return new CrmAccount();
        }
        CrmAccount crmAccount = new CrmAccount();
        crmAccount.setId(traCustomerPT.getId());
        crmAccount
            .name(traCustomerPT.getName())
            .shortName(traCustomerPT.getShortName())
            .externalId1(traCustomerPT.getIdNumber1())
            .externalId2(traCustomerPT.getIdNumber2())
            .shortName(traCustomerPT.getShortName())
            .vatNumber(traCustomerPT.getVatNumber())
            .paymentDelay(traCustomerPT.getPaymentDelay())
            .discount(traCustomerPT.getDiscount());
        if (traCustomerPT.getIndustry() != null) {
            crmAccount.industry(industryMapper.tRAIndustryDTOToTraIndustry(traCustomerPT.getIndustry()));
        }
        if (traCustomerPT.getSize() != null) {
            //crmAccount .keeper(corUserMapper.tranformUserDTO(traCustomerPT.getAccount()))
            crmAccount.size(customCorSizeMapper.cORSizeDTOToCorSize(traCustomerPT.getSize()));
        }
        if (traCustomerPT.getRange() != null) {
            crmAccount.range(customCorRangeMapper.cORRangeDTOToCorRange(traCustomerPT.getRange()));
        }
        if (traCustomerPT.getAdress() != null) {
            crmAccount.addres(corAddressMapper.cORAddressDTOToCorAddress(traCustomerPT.getAdress()));
        }
        if (traCustomerPT.getPersons() != null) {
            crmAccount.person(customTRAPersonMapper.createPersonEntity(traCustomerPT.getPersons(), network));
        }
        if (traCustomerPT.getArea() != null) {
            crmAccount.area(customCorAreaMapper.cORAreaDTOToCorArea(traCustomerPT.getArea()));
        }
        crmAccount.network(network);
        return crmAccount;
    }

    public TraCustomerPT createCustomerTrafficDTO(CrmAccount traCustomerPT) {
        if (traCustomerPT == null) {
            return new TraCustomerPT();
        }
        TraCustomerPT crmAccount = new TraCustomerPT();
        crmAccount.setId(traCustomerPT.getId());
        crmAccount.id(traCustomerPT.getId())
            .name(traCustomerPT.getName())
            .shortName(crmAccount.getShortName())
            .idNumber1(traCustomerPT.getExternalId1())
            .idNumber2(traCustomerPT.getExternalId2())
            .shortName(traCustomerPT.getShortName())
            .vatNumber(traCustomerPT.getVatNumber());
        if (traCustomerPT.getPaymentDelay() != null) {
            crmAccount.paymentDelay(Math.toIntExact(traCustomerPT.getPaymentDelay()));
        }
        if (traCustomerPT.getIndustry() != null) {
            crmAccount.industry(industryMapper.tRAIndustryToTraIndustryDTO(traCustomerPT.getIndustry()));
        }
        // .account(corUserMapper.corUserMapper(traCustomerPT.getKeeper()))
        if (traCustomerPT.getSize() != null) {
            crmAccount.size(customCorSizeMapper.cORSizeToCorSizeDTO(traCustomerPT.getSize()));
        }
        if (traCustomerPT.getRange() != null) {
            crmAccount.range(customCorRangeMapper.cORRangeToCorRangeDTO(traCustomerPT.getRange()));
        }
        if (traCustomerPT.getAddres() != null) {
            crmAccount.adress(corAddressMapper.cORAddressToCorAddressDTO(traCustomerPT.getAddres()));
        }
        if (traCustomerPT.getPerson() != null) {
            crmAccount.persons(customTRAPersonMapper.createDTOObject(traCustomerPT.getPerson()));
        }
        if (traCustomerPT.getArea() != null) {
            crmAccount.area(customCorAreaMapper.cORAreaToCorAreaDTO(traCustomerPT.getArea()));
        }
        return crmAccount;
    }

    public CrmAccountPT buildContactDTOFromEntities(CrmAccount crmAccount) {
        if (crmAccount == null) {
            return new CrmAccountPT();
        }
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
            .account(corUserMapper.userToCoreUserPT(crmAccount.getKeeper()))
            .size(customCorSizeMapper.cORSizeToCorSizeDTO(crmAccount.getSize()))
            .range(customCorRangeMapper.cORRangeToCorRangeDTO(crmAccount.getRange()))
            .adress(corAddressMapper.cORAddressToCorAddressDTO(crmAccount.getAddres()))
            .persons(customTRAPersonMapper.createDTOObject(crmAccount.getPerson()))
            .area(customCorAreaMapper.cORAreaToCorAreaDTO(crmAccount.getArea()))
            .tasks(crmAccount.getTasks().stream().map(crmTask -> customCrmTaskMapper.createCrmTask(crmTask)).collect(toList()));

    }


}
