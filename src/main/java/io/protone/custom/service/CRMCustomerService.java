package io.protone.custom.service;

import io.protone.custom.service.dto.*;
import io.protone.custom.service.mapper.CustomCRMAccountMapper;
import io.protone.custom.service.mapper.CustomCRMTaskMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import io.protone.service.UserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
    @Inject
    private CRMTaskRepository crmTaskRepository;
    @Inject
    private CustomCRMTaskMapper customCRMTaskMapper;
    @Inject
    private UserService userService;
    @Inject
    private CRMTaskService crmTaskService;

    public List<CrmAccountPT> getAllCustomer(CORNetwork corNetwork) {
        List<CRMAccount> crmAccount = accountRepository.findAll();
        return crmAccount.stream().map(crmAccount1 -> fetchCustomer(crmAccount1, corNetwork)).collect(toList());
    }

    public CrmAccountPT saveCustomer(CrmAccountPT crmAccountPT, CORNetwork corNetwork) {
        CRMAccount crmAccount = customCRMAccountMapper.createCrmAcountEntity(crmAccountPT);
        accountRepository.save(crmAccount);
        Map<CORPerson, List<CORContact>> personCORContactMap = customCRMAccountMapper.createMapPersonContact(crmAccountPT);
        CORAddress addres = customCRMAccountMapper.createAdressEntity(crmAccountPT);
        CORArea area = customCRMAccountMapper.createCorAreaEntity(crmAccountPT);
        TRAIndustry industry = customCRMAccountMapper.createIndustryEntity(crmAccountPT);
        CORSize corSize = customCRMAccountMapper.createCorSizeEntity(crmAccountPT);
        CORRange corRange = customCRMAccountMapper.createRangeEntity(crmAccountPT);
        List<CrmTaskPT> crmTask = crmTaskService.saveCustomerTasks(crmAccountPT, crmAccount, corNetwork);

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
        return customCRMAccountMapper.buildContactDTOFromEntities(crmAccount, addres, corSize, corRange, area, crmTask, savedEntities, industry, new CoreManagedUserPT());

    }

    public CrmAccountPT update(CrmAccountPT crmAccountPT, CORNetwork corNetwork) {
        deleteCustomer(crmAccountPT.getShortName(), corNetwork);
        return saveCustomer(crmAccountPT, corNetwork);
    }

    public void deleteCustomer(String shorName, CORNetwork corNetwork) {
        CRMAccount crmContact = accountRepository.findByShortName(shorName);
        deleteCustomer(crmContact, corNetwork);
    }

    private void deleteCustomer(CRMAccount crmAccount, CORNetwork corNetwork) {
        List<CORAssociation> contactAddressAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORAddress.class.getName());
        List<CORAssociation> contactAreaAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORArea.class.getName());
        List<CORAssociation> contactSizeAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORSize.class.getName());
        List<CORAssociation> contactRangeAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORRange.class.getName());
        List<CORAssociation> contactIndustryAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), TRAIndustry.class.getName());
        List<CORAssociation> contactPersonAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORPerson.class.getName());
        List<CORAssociation> contactPersonContactAssociation = new ArrayList<CORAssociation>();
        contactPersonAssociation.stream().forEach(person -> {
            CORPerson corPerson = corPersonRepository.findOne(person.getTargetId());
            contactPersonContactAssociation.stream().filter(association -> corPerson.getId().equals(association.getId())).map(CORAssociation::getTargetId).collect(toList())
                .forEach(contactID -> {
                    corContactRepository.delete(contactID);
                });

        });
        corAddressRepository.delete(contactAddressAssociation.get(0).getTargetId());

        crmTaskService.deleteCustomerTasks(crmAccount, corNetwork);
        corAssociationRepository.delete(contactPersonContactAssociation);
        corAssociationRepository.delete(contactPersonAssociation);
        corAssociationRepository.delete(contactIndustryAssociation);
        corAssociationRepository.delete(contactRangeAssociation);
        corAssociationRepository.delete(contactSizeAssociation);
        corAssociationRepository.delete(contactAreaAssociation);
        corAssociationRepository.delete(contactAddressAssociation);
        accountRepository.delete(crmAccount);

    }

    private CrmAccountPT fetchCustomer(CRMAccount crmAccount, CORNetwork corNetwork) {
        List<CORAssociation> contactAddressAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORAddress.class.getName());
        List<CORAssociation> contactAreaAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORArea.class.getName());
        List<CORAssociation> contactSizeAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORSize.class.getName());
        List<CORAssociation> contactRangeAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORRange.class.getName());
        List<CORAssociation> contactIndustryAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), TRAIndustry.class.getName());
        List<CORAssociation> contactPersonAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORPerson.class.getName());
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

        List<CrmTaskPT> taskList = crmTaskService.fetchCustomerTasks(crmAccount, corNetwork);

        return customCRMAccountMapper.buildContactDTOFromEntities(crmAccount, address, size, range, area, taskList, fetchedEntites, industry, new CoreManagedUserPT());
    }


    public CrmAccountPT getCustomer(String shortcut, CORNetwork corNetwork) {
        CRMAccount crmAccount = accountRepository.findByShortName(shortcut);
        return fetchCustomer(crmAccount, corNetwork);
    }

    public CrmTaskPT createTasksAssociatedWithLead(String shortcut, CrmTaskPT taskPT, CORNetwork corNetwork) {
        CRMAccount crmAccount = accountRepository.findByShortName(shortcut);
        return crmTaskService.createTasksAssociatedWithCustomer(crmAccount, taskPT, corNetwork);
    }

    public List<CrmTaskPT> getTasksAssociatedWithLead(String shortcut, CORNetwork corNetwork) {
        CRMAccount crmAccount = accountRepository.findByShortName(shortcut);
        return crmTaskService.getTasksAssociatedWithCustomer(crmAccount, corNetwork);
    }

    public CrmTaskPT getTaskAssociatedWithLead(String shortcut, Long taskId, CORNetwork corNetwork) {
        CRMAccount crmAccount = accountRepository.findByShortName(shortcut);
        return crmTaskService.getTaskAssociatedWithCustomer(crmAccount, taskId, corNetwork);
    }

    public void deleteLeadTask(String shortcut, Long taskId, CORNetwork corNetwork) {
        CRMAccount crmAccount = accountRepository.findByShortName(shortcut);
        crmTaskService.deleteCustomerTask(crmAccount, taskId, corNetwork);
    }

    public CrmTaskPT updateLeadTask(String shortcut, CrmTaskPT crmTask, CORNetwork corNetwork) {
        CRMAccount crmAccount = accountRepository.findByShortName(shortcut);
        return crmTaskService.updateCustomerTask(crmAccount, crmTask, corNetwork);
    }


}
