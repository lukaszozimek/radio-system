package io.protone.custom.service;

import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.mapper.CustomCrmAccountMapper;
import io.protone.custom.service.mapper.CustomCrmTaskMapper;
import io.protone.domain.*;
import io.protone.repository.custom.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */
@Service
public class CrmCustomerService {

    @Inject
    private CustomCrmAccountRepositoryEx accountRepository;

    @Inject
    private CustomCrmAccountMapper crmAccountMapper;

    @Inject
    private CustomCorAddressRepository addressRepository;

    @Inject
    private CustomCorContactRepository corContactRepository;

    @Inject
    private CustomCorPersonRepository personRepository;

    @Inject
    private CustomCrmTaskRepository crmTaskRepository;

    @Inject
    private CustomCrmTaskMapper customCrmTaskMapper;

    public List<CrmAccountPT> getAllCustomer(CorNetwork corNetwork) {
        return accountRepository.findByNetwork(corNetwork).stream().map(crmAccount1 -> crmAccountMapper.buildContactDTOFromEntities(crmAccount1)).collect(toList());
    }

    public CrmAccountPT saveCustomer(CrmAccountPT crmAccountPT, CorNetwork corNetwork) {
        CrmAccount crmAccount = crmAccountMapper.createCrmAcountEntity(crmAccountPT, corNetwork);
        CorAddress address = addressRepository.save(crmAccount.getAddres());
        List<CorContact> corContact = corContactRepository.save(crmAccount.getPerson().getContacts());
        CorPerson person = personRepository.save(crmAccount.getPerson());
        person.setContacts(corContact.stream().collect(Collectors.toSet()));
        crmAccount.setAddres(address);
        crmAccount.person(person);
        crmAccount = accountRepository.save(crmAccount);
        return crmAccountMapper.buildContactDTOFromEntities(crmAccount);
    }

    public void deleteCustomer(String shorName, CorNetwork corNetwork) {
        accountRepository.deleteByShortNameAndNetwork(shorName, corNetwork);
    }


    public CrmAccountPT getCustomer(String shortcut, CorNetwork corNetwork) {
        return crmAccountMapper.buildContactDTOFromEntities(accountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork));
    }

    public CrmTaskPT createTasksAssociatedWithLead(String shortcut, CrmTaskPT taskPT, CorNetwork corNetwork) {
        CrmAccount crmAccount = accountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        CrmTask crmTask = crmTaskRepository.save(customCrmTaskMapper.createTaskEntity(taskPT));
        crmAccount.addTasks(crmTask);
        accountRepository.save(crmAccount);
        return customCrmTaskMapper.createCrmTask(crmTask);
    }

    public List<CrmTaskPT> getTasksAssociatedWithLead(String shortcut, CorNetwork corNetwork) {
        CrmAccount crmLead = accountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        return customCrmTaskMapper.createCrmTasks(crmLead.getTasks());
    }

    public CrmTaskPT getTaskAssociatedWithLead(String shortcut, Long taskId, CorNetwork corNetwork) {
        CrmAccount crmLead = accountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        return customCrmTaskMapper.createCrmTask(crmLead.getTasks().stream().filter(crmTask -> crmTask.getId().equals(taskId)).findFirst().get());
    }

    public void deleteLeadTask(String shortcut, Long taskId, CorNetwork corNetwork) {
        CrmAccount crmAccount = accountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        CrmTask crmTask = crmTaskRepository.findOne(taskId);
        crmAccount.removeTasks(crmTask);
        accountRepository.save(crmAccount);
        crmTaskRepository.delete(crmTask);
    }
}
