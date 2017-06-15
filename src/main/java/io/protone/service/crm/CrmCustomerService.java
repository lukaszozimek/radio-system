package io.protone.service.crm;

import io.protone.domain.CorAddress;
import io.protone.domain.CorPerson;
import io.protone.domain.CrmAccount;
import io.protone.domain.CrmTask;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.service.cor.CorAddressService;
import io.protone.service.cor.CorPersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 17.01.2017.
 */
@Service
@Transactional
public class CrmCustomerService {

    private final Logger log = LoggerFactory.getLogger(CrmCustomerService.class);

    @Inject
    private CrmAccountRepository accountRepository;

    @Inject
    private CorAddressService corAddressService;

    @Inject
    private CorPersonService personService;

    @Inject
    private CrmTaskService crmTaskService;

    public List<CrmAccount> getAllCustomers(String corNetwork, Pageable pageable) {
        return accountRepository.findAllByNetwork_Shortcut(corNetwork, pageable);
    }

    public CrmAccount saveCustomer(CrmAccount crmAccount) {
        log.debug("Persisting CorAddress: {}", crmAccount.getAddres());
        if (crmAccount.getAddres() != null) {
            CorAddress address = corAddressService.saveCoreAdress(crmAccount.getAddres());
            crmAccount.setAddres(address);
        }
        if (crmAccount.getPerson() != null) {
            CorPerson corPerson = personService.savePerson(crmAccount.getPerson());
            crmAccount.person(corPerson);
        }
        log.debug("Persisting CrmAccount: {}", crmAccount);
        crmAccount = accountRepository.saveAndFlush(crmAccount);
        return crmAccount;
    }

    public void deleteCustomer(String shortName, String corNetwork) {
        crmTaskService.deleteByAccount_ShortNameAndNetwork_Shortcut(shortName, corNetwork);
        accountRepository.deleteByShortNameAndNetwork_Shortcut(shortName, corNetwork);
    }


    public CrmAccount getCustomer(String shortcut, String corNetwork) {
        return accountRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
    }

    public CrmTask saveOrUpdateTaskAssociatiedWithAccount(CrmTask crmTask, String shortcut, String corNetwork) {
        CrmAccount crmAccount = accountRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
        if (crmAccount != null) {
            CrmTask crmTask1 = crmTaskService.saveOrUpdateTaskAssociatiedWithCustomer(crmAccount, crmTask);
            crmAccount.addTasks(crmTask1);
            accountRepository.saveAndFlush(crmAccount);
            return crmTask1;
        }
        return null;
    }

    public List<CrmTask> getTasksAssociatedWithContact(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskService.findAllByAccount_ShortNameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

    public CrmTask getTaskAssociatedWithContact(Long taskId, String corNetwork) {
        return crmTaskService.findOneByIdAndNetwork_Shortcut(taskId, corNetwork);
    }


    public void deleteCustomerTask(String shortcut, Long taskId, String corNetwork) {
        CrmAccount crmAccount = accountRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
        crmAccount.getTasks().removeIf(crmTask -> crmTask.getId() == taskId);
        accountRepository.save(crmAccount);
        crmTaskService.deleteByIdAndNetwork_Shortcut(taskId, corNetwork);
    }
}
