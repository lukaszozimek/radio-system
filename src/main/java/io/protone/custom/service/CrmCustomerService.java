package io.protone.custom.service;

import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.service.mapper.CrmAccountMapper;
import io.protone.service.mapper.CrmTaskMapper;
import io.protone.domain.*;
import io.protone.repository.custom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */
@Service
@Transactional
public class CrmCustomerService {

    private final Logger log = LoggerFactory.getLogger(CrmCustomerService.class);

    @Inject
    private CustomCrmAccountRepositoryEx accountRepository;


    @Inject
    private CustomCorAddressRepository addressRepository;

    @Inject
    private CorPersonService personService;

    @Inject
    private CustomCrmTaskRepository crmTaskRepository;

    @Inject
    private CrmTaskMapper customCrmTaskMapper;

    public List<CrmAccount> getAllCustomer(CorNetwork corNetwork) {
        return accountRepository.findByNetwork(corNetwork);
    }

    public CrmAccount saveCustomer(CrmAccount crmAccount) {
        log.debug("Persisting CorAddress: {}", crmAccount.getAddres());
        if (crmAccount.getAddres() != null) {
            CorAddress address = addressRepository.save(crmAccount.getAddres());
            crmAccount.setAddres(address);
        }
        if (crmAccount.getPerson() != null) {
            CorPerson corPerson = personService.savePerson(crmAccount.getPerson());
            crmAccount.person(corPerson);
        }
        log.debug("Persisting CrmAccount: {}", crmAccount);
        crmAccount = accountRepository.save(crmAccount);
        return crmAccount;
    }

    public void deleteCustomer(String shorName, CorNetwork corNetwork) {
        accountRepository.deleteByShortNameAndNetwork(shorName, corNetwork);
    }


    public CrmAccount getCustomer(String shortcut, CorNetwork corNetwork) {
        return accountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
    }

    public CrmTaskPT createTasksAssociatedWithLead(String shortcut, CrmTaskPT taskPT, CorNetwork corNetwork) {
        CrmAccount crmAccount = accountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        CrmTask crmTask = crmTaskRepository.save(customCrmTaskMapper.DTO2DB(taskPT));
        log.debug("Persisting CrmTask: {}, for CrmAccount: ", crmTask);
        crmAccount.addTasks(crmTask);
        accountRepository.save(crmAccount);
        return customCrmTaskMapper.DB2DTO(crmTask);
    }

    public List<CrmTaskPT> getTasksAssociatedWithLead(String shortcut, CorNetwork corNetwork) {
        CrmAccount crmLead = accountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        return customCrmTaskMapper.DBs2DTOs(crmLead.getTasks());
    }

    public CrmTaskPT getTaskAssociatedWithLead(String shortcut, Long taskId, CorNetwork corNetwork) {
        CrmAccount crmLead = accountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        return customCrmTaskMapper.DB2DTO(crmLead.getTasks().stream().filter(crmTask -> crmTask.getId().equals(taskId)).findFirst().get());
    }

    public void deleteLeadTask(String shortcut, Long taskId, CorNetwork corNetwork) {
        CrmAccount crmAccount = accountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        CrmTask crmTask = crmTaskRepository.findOne(taskId);
        crmAccount.removeTasks(crmTask);
        accountRepository.save(crmAccount);
        crmTaskRepository.delete(crmTask);
    }
}
