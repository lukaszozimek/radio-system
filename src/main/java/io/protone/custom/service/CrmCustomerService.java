package io.protone.custom.service;

import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.mapper.CustomCrmAccountMapper;
import io.protone.custom.service.mapper.CustomCrmTaskMapper;
import io.protone.custom.web.rest.network.ApiNetworkImpl;
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
        return accountRepository.findByNetwork(corNetwork).stream().map(crmAccount1 -> crmAccountMapper.DB2DTO(crmAccount1)).collect(toList());
    }

    public CrmAccountPT saveCustomer(CrmAccountPT crmAccountPT, CorNetwork corNetwork) {
        CrmAccount crmAccount = crmAccountMapper.DTO2DB(crmAccountPT);
        crmAccount.setNetwork(corNetwork);
        log.debug("Persisting CorAddress: {}", crmAccount.getAddres());
        CorAddress address = addressRepository.saveAndFlush(crmAccount.getAddres());

        log.debug("Persisting CorPerson: {}", crmAccount.getPerson());
        CorPerson person = personRepository.saveAndFlush(crmAccount.getPerson());

        log.debug("Persisting CorContact: {}", crmAccount.getPerson().getContacts());
        List<CorContact> corContact = corContactRepository.save(crmAccount.getPerson().getContacts());
        crmAccount.getPerson().setContacts(corContact.stream().collect(Collectors.toSet()));

        log.debug("Persisting Contact With Person");
        corContact.stream().forEach(corContact1 -> corContactRepository.save(corContact1.person(person)));
        crmAccount.setAddres(address);
        crmAccount.person(person);

        log.debug("Persisting CrmAccount: {}", crmAccount);
        crmAccount = accountRepository.saveAndFlush(crmAccount);
        return crmAccountMapper.DB2DTO(crmAccount);
    }

    public void deleteCustomer(String shorName, CorNetwork corNetwork) {
        accountRepository.deleteByShortNameAndNetwork(shorName, corNetwork);
    }


    public CrmAccountPT getCustomer(String shortcut, CorNetwork corNetwork) {
        return crmAccountMapper.DB2DTO(accountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork));
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
