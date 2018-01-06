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
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.IOException;

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

    public Slice<CrmAccount> getAllCustomers(String organizationShortcut, String channelShortcut, Pageable pageable) {
        return accountRepository.findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(organizationShortcut, channelShortcut, pageable);
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

        return accountRepository.findOneByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(crmAccount.getShortName(), crmAccount.getChannel().getOrganization().getShortcut(), crmAccount.getChannel().getShortcut());
    }

    public CrmAccount saveCustomerWithImage(CrmAccount crmAccount, MultipartFile avatar) throws IOException, TikaException, SAXException {
        log.debug("Persisting CorImage: {}", avatar);
        CorImageItem corImageItem = corImageItemService.saveImageItem(avatar, crmAccount.getChannel().getOrganization());
        crmAccount.setCorImageItem(corImageItem);
        return this.saveCustomer(crmAccount);
    }

    public void deleteCustomer(String shortName, String organizationShortcut, String channelShortcut) {
        crmTaskService.deleteByAccount_ShortNameAndChannel_Organization_Shortcut(shortName, organizationShortcut, channelShortcut);
        accountRepository.deleteByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortName, organizationShortcut, channelShortcut);
    }


    public CrmAccount getCustomer(String shortcut, String organizationShortcut, String channelShortcut) {
        return accountRepository.findOneByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
    }

    public CrmTask saveOrUpdateTaskAssociatiedWithAccount(CrmTask crmTask, String shortcut, String organizationShortcut, String channelShortcut) {
        CrmAccount crmAccount = accountRepository.findOneByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
        if (crmAccount != null) {
            CrmTask crmTask1 = crmTaskService.saveOrUpdateTaskAssociatiedWithCustomer(crmAccount, crmTask);
            crmAccount.addTasks(crmTask1);
            accountRepository.saveAndFlush(crmAccount);
            return crmTask1;
        }
        return null;
    }

    public Slice<CrmTask> getTasksAssociatedWithAccount(String shortcut, String organizationShortcut, String channelShortcut, Pageable pageable) {
        return crmTaskService.findAllByAccount_ShortNameAndChannel_Organization_Shortcut(shortcut, organizationShortcut, channelShortcut, pageable);
    }

    public CrmTask getTaskAssociatedWithAccount(Long taskId, String organizationShortcut, String channelShortcut) {
        return crmTaskService.findOneByIdAndChannel_Organization_Shortcut(taskId, organizationShortcut, channelShortcut);
    }


    public void deleteCustomerTask(String shortcut, Long taskId, String organizationShortcut, String channelShortcut) {
        CrmAccount crmAccount = accountRepository.findOneByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
        CrmTask crmTask = crmTaskService.findOneByIdAndChannel_Organization_Shortcut(taskId, organizationShortcut, channelShortcut);
        if (crmTask != null) {
            crmAccount.removeTasks(crmTask);
            crmTaskService.saveOrUpdateTaskAssociatiedWithCustomer(crmAccount, crmTask);
            accountRepository.saveAndFlush(crmAccount);
            crmTaskService.deleteByIdAndChannel_Organization_Shortcut(taskId, organizationShortcut, channelShortcut);
        }
    }

    public void deleteCustomerTaskComment(Long taskId, Long id, String organizationShortcut, String channelShortcut) {
        crmTaskService.deleteCustomerTaskComment(taskId, id, organizationShortcut, channelShortcut);
    }

    public CrmTaskComment getTaskCommentAssociatedWithTask(String organizationShortcut, String channelShortcut, Long taskId, Long id) {
        return crmTaskService.getTaskCommentAssociatedWithTask(organizationShortcut, channelShortcut, taskId, id);
    }

    public CrmTaskComment saveOrUpdateTaskCommentAssociatedWithTask(CrmTaskComment requestEnitity, Long taskId, String organizationShortcut, String channelShortcut) {
        return crmTaskService.saveOrUpdateTaskCommentAssociatedWithTask(requestEnitity, taskId, organizationShortcut, channelShortcut);
    }

    public Slice<CrmTaskComment> getTaskCommentsAssociatedWithTask(Long taskId, String organizationShortcut, String channelShortcut, Pageable pagable) {
        return crmTaskService.getTaskCommentsAssociatedWithTask(taskId, organizationShortcut, channelShortcut, pagable);
    }
}
