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
    private CustomCorDictionaryMapper corDictionaryMapper;

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
            .industry(corDictionaryMapper.corDictionaryDTOToCorDictionary(crmContactPT.getIndustry()))
            .keeper(corUserMapper.coreUserPTToUser(crmContactPT.getAccount()))
            .size(corDictionaryMapper.corDictionaryDTOToCorDictionary(crmContactPT.getSize()))
            .range(corDictionaryMapper.corDictionaryDTOToCorDictionary(crmContactPT.getRange()))
            .addres(corAddressMapper.cORAddressDTOToCorAddress(crmContactPT.getAdress()))
            .area(corDictionaryMapper.corDictionaryDTOToCorDictionary(crmContactPT.getArea()))
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
            .industry(corDictionaryMapper.corDictionaryToCorDictionaryDTO(crmContact.getIndustry()))
            //.account(corUserMapper.corUserMapper(crmContact.getKeeper()))
            .size(corDictionaryMapper.corDictionaryToCorDictionaryDTO(crmContact.getSize()))
            .range(corDictionaryMapper.corDictionaryToCorDictionaryDTO(crmContact.getRange()))
            .adress(corAddressMapper.cORAddressToCorAddressDTO(crmContact.getAddres()))
            .persons(customTRAPersonMapper.createDTOObject(crmContact.getPerson()))
            .area(corDictionaryMapper.corDictionaryToCorDictionaryDTO(crmContact.getArea()))
            .tasks(crmContact.getTasks().stream().map(crmTask -> customCrmTaskMapper.createCrmTask(crmTask)).collect(toList()));

    }

}
