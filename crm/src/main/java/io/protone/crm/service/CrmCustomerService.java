package io.protone.crm.service;


import io.protone.core.domain.CorAddress;
import io.protone.core.domain.CorImageItem;
import io.protone.core.domain.CorPerson;
import io.protone.core.service.CorAddressService;
import io.protone.core.service.CorImageItemService;
import io.protone.core.service.CorPersonService;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.domain.CrmTask;
import io.protone.crm.domain.CrmTaskComment;
import io.protone.crm.repostiory.CrmAccountRepository;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

    @Inject
    private CorImageItemService corImageItemService;

    public List<CrmAccount> getAllCustomers(String corNetwork, Pageable pageable) {
        return accountRepository.findAllByNetwork_Shortcut(corNetwork, pageable).stream()
                .map(corAccount -> corAccount.avatar(corImageItemService.getValidLinkToResource(corAccount.getCorImageItem()))).collect(toList());
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

    public CrmAccount saveCustomerWithImage(CrmAccount crmAccount, MultipartFile avatar) throws IOException, TikaException, SAXException {
        log.debug("Persisting CorImage: {}", avatar);
        CorImageItem corImageItem = corImageItemService.saveImageItem(avatar);
        crmAccount.setCorImageItem(corImageItem);
        return this.saveCustomer(crmAccount);
    }

    public void deleteCustomer(String shortName, String corNetwork) {
        crmTaskService.deleteByAccount_ShortNameAndNetwork_Shortcut(shortName, corNetwork);
        accountRepository.deleteByShortNameAndNetwork_Shortcut(shortName, corNetwork);
    }


    public CrmAccount getCustomer(String shortcut, String corNetwork) {
        CrmAccount crmAccount = accountRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
        if (crmAccount != null) {
            crmAccount.avatar(corImageItemService.getValidLinkToResource(crmAccount.getCorImageItem()));
        }
        return crmAccount;
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

    public List<CrmTask> getTasksAssociatedWithAccount(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskService.findAllByAccount_ShortNameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

    public CrmTask getTaskAssociatedWithAccount(Long taskId, String corNetwork) {
        return crmTaskService.findOneByIdAndNetwork_Shortcut(taskId, corNetwork);
    }


    public void deleteCustomerTask(String shortcut, Long taskId, String corNetwork) {
        CrmAccount crmAccount = accountRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
        crmAccount.getTasks().removeIf(crmTask -> crmTask.getId() == taskId);
        accountRepository.save(crmAccount);
        crmTaskService.deleteByIdAndNetwork_Shortcut(taskId, corNetwork);
    }

    public void deleteCustomerTaskComment(Long taskId, Long id, String networkShortcut) {
        crmTaskService.deleteCustomerTaskComment(taskId, id, networkShortcut);
    }

    public CrmTaskComment getTaskCommentAssociatedWithTask(String networkShortcut, Long taskId, Long id) {
        return crmTaskService.getTaskCommentAssociatedWithTask(networkShortcut, taskId, id);
    }

    public CrmTaskComment saveOrUpdateTaskCommentAssociatedWithTask(CrmTaskComment requestEnitity, Long taskId, String networkShortcut) {
        return crmTaskService.saveOrUpdateTaskCommentAssociatedWithTask(requestEnitity, taskId, networkShortcut);
    }

    public List<CrmTaskComment> getTaskCommentsAssociatedWithTask(Long taskId, String networkShortcut, Pageable pagable) {
        return crmTaskService.getTaskCommentsAssociatedWithTask(taskId, networkShortcut, pagable);
    }
}
