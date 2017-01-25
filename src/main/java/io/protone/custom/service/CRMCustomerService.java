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
    public List<CrmAccountPT> getAllCustomer(CORNetwork corNetwork) {
        List<CRMAccount> crmAccount = accountRepository.findAll();
        return crmAccount.stream().map(crmAccount1->fetchCustomer(crmAccount1,corNetwork)).collect(toList());
    }

    public CrmAccountPT saveCustomer(CrmAccountPT crmAccountPT,CORNetwork corNetwork) {
        CRMAccount crmAccount = customCRMAccountMapper.createCrmAcountEntity(crmAccountPT);
        accountRepository.save(crmAccount);
        Map<CORPerson, List<CORContact>> personCORContactMap = customCRMAccountMapper.createMapPersonContact(crmAccountPT);
        CORAddress addres = customCRMAccountMapper.createAdressEntity(crmAccountPT);
        CORArea area = customCRMAccountMapper.createCorAreaEntity(crmAccountPT);
        TRAIndustry industry = customCRMAccountMapper.createIndustryEntity(crmAccountPT);
        CORSize corSize = customCRMAccountMapper.createCorSizeEntity(crmAccountPT);
        CORRange corRange = customCRMAccountMapper.createRangeEntity(crmAccountPT);
        List<CRMTask> crmTask = crmTaskRepository.save(customCRMAccountMapper.createTaskEntities(crmAccountPT));

        corAssociationRepository.save(customCRMAccountMapper.createAccountIndustryAssociationEntity(crmAccount, industry));
        corAssociationRepository.save(customCRMAccountMapper.createAccountAreaAssociationEntity(crmAccount, area));
        corAssociationRepository.save(customCRMAccountMapper.createAccountSizeAssociationEntity(crmAccount, corSize));
        corAssociationRepository.save(customCRMAccountMapper.createAccountRangeAssociationEntity(crmAccount, corRange));
        corAssociationRepository.save(customCRMAccountMapper.createAddressAssociationEntity(crmAccount, addres));
        corAssociationRepository.save(customCRMAccountMapper.createAccountTasksAssociationEntity(crmAccount, crmTask));

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

    public CrmAccountPT update(CrmAccountPT crmAccountPT,CORNetwork corNetwork) {
        deleteCustomer(crmAccountPT.getShortName(),corNetwork);
        return saveCustomer(crmAccountPT,corNetwork);
    }

    public void deleteCustomer(String shorName,CORNetwork corNetwork) {
        CRMAccount crmContact = accountRepository.findByShortName(shorName);
        deleteCustomer(crmContact,corNetwork);
    }

    private void deleteCustomer(CRMAccount crmAccount,CORNetwork corNetwork) {
        List<CORAssociation> contactAddressAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORAddress.class.getName());
        List<CORAssociation> contactAreaAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORArea.class.getName());
        List<CORAssociation> contactSizeAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORSize.class.getName());
        List<CORAssociation> contactRangeAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORRange.class.getName());
        List<CORAssociation> contactIndustryAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), TRAIndustry.class.getName());
        List<CORAssociation> contactPersonAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CORPerson.class.getName());
        List<CORAssociation> contactTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmAccount.getId(), CRMTask.class.getName());
        List<CORAssociation> contactPersonContactAssociation = new ArrayList<CORAssociation>();
        contactPersonAssociation.stream().forEach(person -> {
            CORPerson corPerson = corPersonRepository.findOne(person.getTargetId());
            contactPersonContactAssociation.stream().filter(association -> corPerson.getId().equals(association.getId())).map(CORAssociation::getTargetId).collect(toList())
                .forEach(contactID -> {
                    corContactRepository.delete(contactID);
                });

        });
        corAddressRepository.delete(contactAddressAssociation.get(0).getTargetId());
        contactTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList()).forEach(taskId -> {
            crmTaskRepository.delete(taskId);
        });
        corAssociationRepository.delete(contactPersonContactAssociation);
        corAssociationRepository.delete(contactPersonAssociation);
        corAssociationRepository.delete(contactIndustryAssociation);
        corAssociationRepository.delete(contactRangeAssociation);
        corAssociationRepository.delete(contactSizeAssociation);
        corAssociationRepository.delete(contactAreaAssociation);
        corAssociationRepository.delete(contactAddressAssociation);
        accountRepository.delete(crmAccount);

    }

    private CrmAccountPT fetchCustomer(CRMAccount crmAccount,CORNetwork corNetwork) {
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
        List<Long> tasksID = contactTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        List<CRMTask> taskList = crmTaskRepository.findAll(tasksID);
        return customCRMAccountMapper.buildContactDTOFromEntities(crmAccount, address, size, range, area, taskList, fetchedEntites, industry, new CoreManagedUserPT());
    }

    public CrmTaskPT createTasksAssociatedWithLead(String shortcut, CrmTaskPT taskPT,CORNetwork corNetwork) {
        CRMAccount crmAccount = accountRepository.findByShortName(shortcut);
        CRMTask crmTask = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(taskPT));
        corAssociationRepository.save(customCRMAccountMapper.createAccountTaskAssociationEntity(crmAccount, crmTask));
        return customCRMTaskMapper.createCrmTask(crmTask);
    }

    public CrmAccountPT getCustomer(String shortcut,CORNetwork corNetwork) {
        CRMAccount crmAccount = accountRepository.findByShortName(shortcut);
        return fetchCustomer(crmAccount,corNetwork);
    }

    public List<CrmTaskPT> getTasksAssociatedWithLead(String shortcut,CORNetwork corNetwork) {
        CRMAccount crmLead = accountRepository.findByShortName(shortcut);
        List<CORAssociation> leadTaskAssociation = corAssociationRepository.findBySourceIdAndTargetClass(crmLead.getId(), CRMTask.class.getName());
        List<Long> tasksID = leadTaskAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        List<CRMTask> taskList = crmTaskRepository.findAll(tasksID);
        return customCRMTaskMapper.transformTasksFromEntity(taskList);
    }

    public CrmTaskPT getTaskAssociatedWithLead(String shortcut, Long taskId,CORNetwork corNetwork) {
        CRMAccount crmAccount = accountRepository.findByShortName(shortcut);
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClass(crmAccount.getId(), taskId, CRMTask.class.getName());
        CRMTask crmTask = crmTaskRepository.findOne(task.getTargetId());
        return customCRMTaskMapper.createCrmTask(crmTask);
    }

    public void deleteLeadTask(String shortcut, Long taskId,CORNetwork corNetwork) {
        CRMAccount crmAccount = accountRepository.findByShortName(shortcut);
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClass(crmAccount.getId(), taskId, CRMTask.class.getName());
        crmTaskRepository.delete(task.getTargetId());
        corAssociationRepository.delete(task);
    }

    public CrmTaskPT updateLeadTask(String shortcut, CrmTaskPT crmTask,CORNetwork corNetwork) {
        CRMAccount crmAccount = accountRepository.findByShortName(shortcut);
        CORAssociation task = corAssociationRepository.findBySourceIdAndTargetIdAndTargetClass(crmAccount.getId(), crmTask.getId(), CRMTask.class.getName());
        CRMTask task1= taskRepository.save(customCRMTaskMapper.createTaskEntity(crmTask));
        return customCRMTaskMapper.createCrmTask(task1);
    }


}
