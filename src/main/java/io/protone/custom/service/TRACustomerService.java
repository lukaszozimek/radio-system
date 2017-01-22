package io.protone.custom.service;

import io.protone.custom.service.dto.*;
import io.protone.custom.service.mapper.CustomCRMAccountMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service

@Transactional
public class TRACustomerService {

    @Inject
    private CORAssociationRepository corAssociationRepository;

    @Inject
    private CRMAccountRepository crmAccountRepository;

    @Inject
    private CustomCRMAccountMapper customCRMAccountMapper;

    @Inject
    private CORPersonRepository corPersonRepository;

    @Inject
    private CORContactRepository corContactRepository;

    @Inject
    private CORAddressRepository corAddressRepository;

    @Inject
    private CORAreaRepository corAreaRepository;

    @Inject
    private TRAIndustryRepository traIndustryRepository;

    @Inject
    private CORRangeRepository corRangeRepository;

    @Inject
    private CORSizeRepository corSizeRepository;

    public List<TraCustomerPT> getAllCustomers() {

        return crmAccountRepository.findAll().stream().map(this::getCustomer).collect(toList());
    }

    public TraCustomerPT saveCustomers(TraCustomerPT traCustomerPT) {
        CRMAccount crmAccount = customCRMAccountMapper.createCrmAcountEntity(traCustomerPT);
        crmAccountRepository.save(crmAccount);
        Map<CORPerson, List<CORContact>> personCORContactMap = customCRMAccountMapper.createMapPersonContact(traCustomerPT);
        CORAddress addres = customCRMAccountMapper.createAdressEntity(traCustomerPT);
        CORArea area = customCRMAccountMapper.createCorAreaEntity(traCustomerPT);
        TRAIndustry industry = customCRMAccountMapper.createIndustryEntity(traCustomerPT);
        CORSize corSize = customCRMAccountMapper.createCorSizeEntity(traCustomerPT);
        CORRange corRange = customCRMAccountMapper.createRangeEntity(traCustomerPT);
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
        return customCRMAccountMapper.createCustomerTrafficDTO(crmAccount, addres, corSize, corRange, area, savedEntities, industry, new CoreManagedUserPT());
    }

    public void deleteCustomer(String shortcut) {

    }

    public TraCustomerPT getCustomer(String shortcut) {
        CRMAccount crmAccount = crmAccountRepository.findByShortName(shortcut);
        return getCustomer(crmAccount);
    }

    public TraCustomerPT getCustomer(CRMAccount crmAccount) {

        List<CORAssociation> contactAddressAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORAddress.class.getName());
        List<CORAssociation> contactAreaAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORArea.class.getName());
        List<CORAssociation> contactSizeAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORSize.class.getName());
        List<CORAssociation> contactRangeAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORRange.class.getName());
        List<CORAssociation> contactIndustryAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), TRAIndustry.class.getName());
        List<CORAssociation> contactPersonAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORPerson.class.getName());
        List<CORAssociation> contactTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CRMTask.class.getName());
        List<CORAssociation> contactPersonContactAssociation = new ArrayList<CORAssociation>();
        Map<CORPerson, List<CORContact>> fetchedEntites = new HashMap<>();
        contactPersonAssociation.stream().forEach(person -> {
            CORPerson corPerson = corPersonRepository.findOne(person.getTargetId());
            List<Long> corContacts = contactPersonContactAssociation.stream().filter(association -> corPerson.getId().equals(association.getId())).map(CORAssociation::getTargetId).collect(toList());
            List<CORContact> contacts = corContactRepository.findAll(corContacts);
            fetchedEntites.put(corPerson, contacts);
        });

        CORAddress address = corAddressRepository.findOne(contactAddressAssociation.get(0).getTargetId());
        CORArea area = corAreaRepository.findOne(contactAreaAssociation.get(0).getTargetId());
        TRAIndustry industry = traIndustryRepository.findOne(contactIndustryAssociation.get(0).getTargetId());
        CORRange range = corRangeRepository.findOne(contactRangeAssociation.get(0).getTargetId());
        CORSize size = corSizeRepository.findOne(contactSizeAssociation.get(0).getTargetId());
        return customCRMAccountMapper.createCustomerTrafficDTO(crmAccount, address, size, range, area, fetchedEntites, industry, new CoreManagedUserPT());
    }


    public TraInvoicePT getCustomerInvoice(String shortcut) {
        return null;
    }

}
