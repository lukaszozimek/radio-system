package io.protone.custom.service;

import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.mapper.CustomCRMAccountMapper;
import io.protone.custom.service.mapper.CustomCRMTaskMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
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
    private CustomCRMAccountMapper crmAccountMapper;

    @Inject
    private CORAddressRepository addressRepository;

    @Inject
    private CORContactRepository corContactRepository;

    @Inject
    private CORPersonRepository personRepository;

    @Inject
    private CRMTaskRepository crmTaskRepository;

    @Inject
    private CustomCRMTaskMapper customCRMTaskMapper;

    public List<CrmAccountPT> getAllCustomer(CORNetwork corNetwork) {
        return accountRepository.findByNetwork(corNetwork).stream().map(crmAccount1 -> crmAccountMapper.buildContactDTOFromEntities(crmAccount1)).collect(toList());
    }

    public CrmAccountPT saveCustomer(CrmAccountPT crmAccountPT, CORNetwork corNetwork) {
        CRMAccount crmAccount = crmAccountMapper.createCrmAcountEntity(crmAccountPT, corNetwork);
        CORAddress address = addressRepository.save(crmAccount.getAddres());
        List<CORContact> corContact = corContactRepository.save(crmAccount.getPerson().getPersonContacts());
        CORPerson person = personRepository.save(crmAccount.getPerson());
        person.setPersonContacts(corContact.stream().collect(Collectors.toSet()));
        crmAccount.setAddres(address);
        crmAccount.person(person);
        crmAccount = accountRepository.save(crmAccount);
        return crmAccountMapper.buildContactDTOFromEntities(crmAccount);
    }

    public void deleteCustomer(String shorName, CORNetwork corNetwork) {
        accountRepository.deleteByShortNameAndNetwork(shorName, corNetwork);
    }


    public CrmAccountPT getCustomer(String shortcut, CORNetwork corNetwork) {
        return crmAccountMapper.buildContactDTOFromEntities(accountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork));
    }

    public CrmTaskPT createTasksAssociatedWithLead(String shortcut, CrmTaskPT taskPT, CORNetwork corNetwork) {
        CRMAccount crmAccount = accountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        CRMTask crmTask = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(taskPT));
        crmAccount.addTask(crmTask);
        accountRepository.save(crmAccount);
        return customCRMTaskMapper.createCrmTask(crmTask);
    }

    public List<CrmTaskPT> getTasksAssociatedWithLead(String shortcut, CORNetwork corNetwork) {
        CRMAccount crmLead = accountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        return customCRMTaskMapper.createCrmTasks(crmLead.getTasks());
    }

    public CrmTaskPT getTaskAssociatedWithLead(String shortcut, Long taskId, CORNetwork corNetwork) {
        CRMAccount crmLead = accountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        return customCRMTaskMapper.createCrmTask(crmLead.getTasks().stream().filter(crmTask -> crmTask.getId().equals(taskId)).findFirst().get());
    }

    public void deleteLeadTask(String shortcut, Long taskId, CORNetwork corNetwork) {
        CRMAccount crmAccount = accountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        CRMTask crmTask = crmTaskRepository.findOne(taskId);
        crmAccount.removeTask(crmTask);
        accountRepository.save(crmAccount);
        crmTaskRepository.delete(crmTask);
    }
}
