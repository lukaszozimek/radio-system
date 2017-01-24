package io.protone.custom.service;

import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.service.mapper.CustomCRMAccountMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lukaszozimek on 17.01.2017.
 */
@Service
public class CRMCustomerService {

    @Inject
    private CRMAccountRepository accountRepository;

    @Inject
    private CRMTaskRepository taskRepository;

    @Inject
    private CORAssociationRepository corAssociationRepository;

    @Inject
    private CORRangeRepository rangeRepository;

    @Inject
    private CORSizeRepository sizeRepository;

    @Inject
    private TRAIndustryRepository industryRepository;

    @Inject
    private CustomCRMAccountMapper customCRMAccountMapper;

    @Inject
    private CORPersonRepository corPersonRepository;

    @Inject
    private CORContactRepository corContactRepository;

    public List<CrmAccountPT> getAllCustomer() {

        return null;
    }

    public CrmAccountPT saveCustomer(CrmAccountPT crmAccountPT) {
        CRMAccount crmAccount = customCRMAccountMapper.createCrmAcountEntity(crmAccountPT);
        accountRepository.save(crmAccount);
        Map<CORPerson, List<CORContact>> personCORContactMap = customCRMAccountMapper.createMapPersonContact(crmAccountPT);
        CORAddress addres = customCRMAccountMapper.createAdressEntity(crmAccountPT);
        CORArea area = customCRMAccountMapper.createCorAreaEntity(crmAccountPT);
        TRAIndustry industry = customCRMAccountMapper.createIndustryEntity(crmAccountPT);
        CORSize corSize = customCRMAccountMapper.createCorSizeEntity(crmAccountPT);
        CORRange corRange = customCRMAccountMapper.createRangeEntity(crmAccountPT);
        corAssociationRepository.save(customCRMAccountMapper.createAccountIndustryAssociationEntity(crmAccount, industry));
        corAssociationRepository.save(customCRMAccountMapper.createAccountAreaAssociationEntity(crmAccount, area));
        corAssociationRepository.save(customCRMAccountMapper.createAccountSizeAssociationEntity(crmAccount, corSize));
        corAssociationRepository.save(customCRMAccountMapper.createAccountRangeAssociationEntity(crmAccount, corRange));
        corAssociationRepository.save(customCRMAccountMapper.createAddressAssociationEntity(crmAccount, addres));
        Map<CORPerson, List<CORContact>> savedEntities = new HashMap<>();
        personCORContactMap.keySet().stream().forEach(person -> {
            CORPerson personSaved = corPersonRepository.save(person);
            List<CORContact> personContactList = corContactRepository.save(personCORContactMap.get(person));
            corAssociationRepository.save(customCRMAccountMapper.createAccountPersonAssociationEntity(crmAccount, personSaved));
            corAssociationRepository.save(customCRMAccountMapper.createPersonAccountAssociationEntity(person, personContactList));
            savedEntities.put(personSaved, personContactList);
        });
        return null;
    }

    public CrmAccountPT update(CrmAccountPT crmAccountPT) {
        deleteCustomer(crmAccountPT.getShortName());
        return saveCustomer(crmAccountPT);
    }

    public void deleteCustomer(String shorName) {

    }

    public CrmAccountPT getCustomer(String shortcut) {
        return null;
    }
}
