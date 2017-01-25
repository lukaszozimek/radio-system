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

    public List<TraCustomerPT> getAllCustomers(CORNetwork corNetwork) {

        return crmAccountRepository.findAll().stream().map(customers -> getCustomer(customers, corNetwork)).collect(toList());
    }

    public TraCustomerPT saveCustomers(TraCustomerPT traCustomerPT, CORNetwork corNetwork) {
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

    public void deleteCustomer(String shortcut, CORNetwork corNetwork) {
        CRMAccount crmAccount = crmAccountRepository.findOneByShortNameAndNetwork(shortcut,corNetwork);
        corAssociationRepository.deleteBySourceIdAndTargetClassAndNetwork(crmAccount.getId(), CORAddress.class.getName(),corNetwork);
        corAssociationRepository.deleteBySourceIdAndTargetClassAndNetwork(crmAccount.getId(), CORArea.class.getName(),corNetwork);
        corAssociationRepository.deleteBySourceIdAndTargetClassAndNetwork(crmAccount.getId(), CORSize.class.getName(),corNetwork);
        corAssociationRepository.deleteBySourceIdAndTargetClassAndNetwork(crmAccount.getId(), CORRange.class.getName(),corNetwork);
        corAssociationRepository.deleteBySourceIdAndTargetClassAndNetwork(crmAccount.getId(), TRAIndustry.class.getName(),corNetwork);
        List<CORAssociation> contactPersonAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmAccount.getId(), CORPerson.class.getName(),corNetwork);
        corAssociationRepository.deleteBySourceIdAndTargetClassAndNetwork(crmAccount.getId(), CRMTask.class.getName(),corNetwork);
        List<CORAssociation> contactPersonContactAssociation = new ArrayList<CORAssociation>();
        Map<CORPerson, List<CORContact>> fetchedEntites = new HashMap<>();
        contactPersonAssociation.stream().forEach(person -> {
            CORPerson corPerson = corPersonRepository.findOne(person.getTargetId());
            List<Long> corContacts = contactPersonContactAssociation.stream().filter(association -> corPerson.getId().equals(association.getSourceId())).map(CORAssociation::getTargetId).collect(toList());
            corContacts.stream().forEach(corContactID -> corContactRepository.delete(corContactID));
            corPersonRepository.delete(corPerson);
        });

    }

    public TraCustomerPT getCustomer(String shortcut, CORNetwork corNetwork) {
        CRMAccount crmAccount = crmAccountRepository.findOneByShortNameAndNetwork(shortcut,corNetwork);
        return getCustomer(crmAccount, corNetwork);
    }

    public TraCustomerPT update(TraCustomerPT traCustomerPT, CORNetwork corNetwork) {
        deleteCustomer(traCustomerPT.getShortName(), corNetwork);
        return saveCustomers(traCustomerPT, corNetwork);
    }

    public TraCustomerPT getCustomer(CRMAccount crmAccount, CORNetwork corNetwork) {

        List<CORAssociation> contactAddressAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmAccount.getId(), CORAddress.class.getName(),corNetwork);
        List<CORAssociation> contactAreaAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmAccount.getId(), CORArea.class.getName(),corNetwork);
        List<CORAssociation> contactSizeAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmAccount.getId(), CORSize.class.getName(),corNetwork);
        List<CORAssociation> contactRangeAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmAccount.getId(), CORRange.class.getName(),corNetwork);
        List<CORAssociation> contactIndustryAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmAccount.getId(), TRAIndustry.class.getName(),corNetwork);
        List<CORAssociation> contactPersonAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(crmAccount.getId(), CORPerson.class.getName(),corNetwork);
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
