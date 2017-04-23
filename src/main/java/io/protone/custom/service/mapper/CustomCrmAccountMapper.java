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
    private CustomCorDictionaryMapper corDictionaryMapper;


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
            .industry(corDictionaryMapper.corDictionaryDTOToCorDictionary(crmAccountPT.getIndustry()))
            .keeper(corUserMapper.coreUserPTToUser(crmAccountPT.getAccount()))
            .size(corDictionaryMapper.corDictionaryDTOToCorDictionary(crmAccountPT.getSize()))
            .range(corDictionaryMapper.corDictionaryDTOToCorDictionary(crmAccountPT.getRange()))
            .addres(corAddressMapper.cORAddressDTOToCorAddress(crmAccountPT.getAdress()))
            .person(customTRAPersonMapper.createPersonEntity(crmAccountPT.getPersons(), corNetwork))
            .area(corDictionaryMapper.corDictionaryDTOToCorDictionary(crmAccountPT.getArea()))
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
            crmAccount.industry(corDictionaryMapper.corDictionaryDTOToCorDictionary(traCustomerPT.getIndustry()));
        }
        if (traCustomerPT.getAccount() != null) {
            crmAccount.keeper(corUserMapper.coreUserPTToUser(traCustomerPT.getAccount()));
        }
        if (traCustomerPT.getSize() != null) {

            crmAccount.size(corDictionaryMapper.corDictionaryDTOToCorDictionary(traCustomerPT.getSize()));
        }
        if (traCustomerPT.getRange() != null) {
            crmAccount.range(corDictionaryMapper.corDictionaryDTOToCorDictionary(traCustomerPT.getRange()));
        }
        if (traCustomerPT.getAdress() != null) {
            crmAccount.addres(corAddressMapper.cORAddressDTOToCorAddress(traCustomerPT.getAdress()));
        }
        if (traCustomerPT.getPersons() != null) {
            crmAccount.person(customTRAPersonMapper.createPersonEntity(traCustomerPT.getPersons(), network));
        }
        if (traCustomerPT.getArea() != null) {
            crmAccount.area(corDictionaryMapper.corDictionaryDTOToCorDictionary(traCustomerPT.getArea()));
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
            crmAccount.industry(corDictionaryMapper.corDictionaryToCorDictionaryDTO(traCustomerPT.getIndustry()));
        }
        if (traCustomerPT.getKeeper() != null) {
            crmAccount.account(corUserMapper.userToCoreUserPT(traCustomerPT.getKeeper()));

        }
        if (traCustomerPT.getSize() != null) {
            crmAccount.size(corDictionaryMapper.corDictionaryToCorDictionaryDTO(traCustomerPT.getSize()));
        }
        if (traCustomerPT.getRange() != null) {
            crmAccount.range(corDictionaryMapper.corDictionaryToCorDictionaryDTO(traCustomerPT.getRange()));
        }
        if (traCustomerPT.getAddres() != null) {
            crmAccount.adress(corAddressMapper.cORAddressToCorAddressDTO(traCustomerPT.getAddres()));
        }
        if (traCustomerPT.getPerson() != null) {
            crmAccount.persons(customTRAPersonMapper.createDTOObject(traCustomerPT.getPerson()));
        }
        if (traCustomerPT.getArea() != null) {
            crmAccount.area(corDictionaryMapper.corDictionaryToCorDictionaryDTO(traCustomerPT.getArea()));
        }
        return crmAccount;
    }

    public CrmAccountPT buildContactDTOFromEntities(CrmAccount crmAccount) {
        if (crmAccount == null) {
            return new CrmAccountPT();
        }
        CrmAccountPT crmAccountPT = new CrmAccountPT()
            .id(crmAccount.getId())
            .name(crmAccount.getName())
            .shortName(crmAccount.getShortName())
            .idNumber1(crmAccount.getExternalId1())
            .idNumber2(crmAccount.getExternalId2())
            .shortName(crmAccount.getShortName())
            .vatNumber(crmAccount.getVatNumber());
        if (crmAccount.getPaymentDelay() != null) {
            crmAccountPT = crmAccountPT.paymentDelay(Math.toIntExact(crmAccount.getPaymentDelay()));
        }
        if (crmAccount.getIndustry() != null) {
            crmAccountPT = crmAccountPT.industry(corDictionaryMapper.corDictionaryToCorDictionaryDTO(crmAccount.getIndustry()));
        }
        if (crmAccount.getKeeper() != null) {
            crmAccountPT = crmAccountPT.account(corUserMapper.userToCoreUserPT(crmAccount.getKeeper()));
        }
        if (crmAccount.getSize() != null) {
            crmAccountPT = crmAccountPT.size(corDictionaryMapper.corDictionaryToCorDictionaryDTO(crmAccount.getSize()));
        }

        if (crmAccount.getRange() != null) {
            crmAccountPT = crmAccountPT.range(corDictionaryMapper.corDictionaryToCorDictionaryDTO(crmAccount.getRange()));
        }

        if (crmAccount.getAddres() != null) {
            crmAccountPT = crmAccountPT.adress(corAddressMapper.cORAddressToCorAddressDTO(crmAccount.getAddres()));
        }
        if (crmAccount.getPerson() != null) {
            crmAccountPT = crmAccountPT.persons(customTRAPersonMapper.createDTOObject(crmAccount.getPerson()));
        }
        if (crmAccount.getPerson() != null) {

            crmAccountPT = crmAccountPT.area(corDictionaryMapper.corDictionaryToCorDictionaryDTO(crmAccount.getArea()));

        }
        if (crmAccount.getTasks() != null) {

            crmAccountPT = crmAccountPT.tasks(crmAccount.getTasks().stream().map(crmTask -> customCrmTaskMapper.createCrmTask(crmTask)).collect(toList()));
        }
        return crmAccountPT;

    }


}
