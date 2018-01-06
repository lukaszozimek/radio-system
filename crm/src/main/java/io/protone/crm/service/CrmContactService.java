package io.protone.crm.service;


import io.protone.core.domain.CorAddress;
import io.protone.core.domain.CorImageItem;
import io.protone.core.domain.CorPerson;
import io.protone.core.service.CorAddressService;
import io.protone.core.service.CorImageItemService;
import io.protone.core.service.CorPersonService;
import io.protone.crm.domain.CrmContact;
import io.protone.crm.domain.CrmLead;
import io.protone.crm.domain.CrmTask;
import io.protone.crm.domain.CrmTaskComment;
import io.protone.crm.mapper.CrmContactMapper;
import io.protone.crm.repostiory.CrmContactRepository;
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
public class CrmContactService {
    private final Logger log = LoggerFactory.getLogger(CrmContactService.class);

    @Inject
    private CrmContactRepository crmContactRepository;

    @Inject
    private CrmTaskService crmTaskService;

    @Inject
    private CorPersonService corPersonService;

    @Inject
    private CorAddressService corAddressService;

    @Inject
    private CorImageItemService corImageItemService;

    @Inject
    private CrmContactMapper crmContactMapper;

    public Slice<CrmContact> getAllContact(String organizationShortcut, String channelShortcut, Pageable pageable) {
        return crmContactRepository.findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(organizationShortcut, channelShortcut, pageable);
    }

    public CrmContact saveContact(CrmContact contact) {
        if (contact.getAddres() != null) {
            CorAddress address = corAddressService.saveCoreAdress(contact.getAddres());
            contact.setAddres(address);
        }

        if (contact.getPerson() != null) {
            CorPerson corPerson = corPersonService.savePerson(contact.getPerson());
            contact.person(corPerson);
        }

        log.debug("Persisting CrmContact: {}", contact);
        contact = crmContactRepository.saveAndFlush(contact);
        return contact;
    }

    public CrmContact saveContactWithImage(CrmContact contact, MultipartFile avatar) throws IOException, TikaException, SAXException {
        CorImageItem corImageItem = corImageItemService.saveImageItem(avatar, contact.getChannel().getOrganization());
        contact.setCorImageItem(corImageItem);
        return this.saveContact(contact);
    }

    public void deleteContact(String shortcut, String organizationShortcut, String channelShortcut) {
        crmTaskService.deleteByContactByShortNameAndNetworkByShortcut(shortcut, organizationShortcut, channelShortcut);
        crmContactRepository.deleteByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
    }

    public CrmContact getContact(String shortcut, String organizationShortcut, String channelShortcut) {
        return crmContactRepository.findOneByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);

    }

    public Slice<CrmTask> getTasksAssociatedWithContact(String shortcut, String organizationShortcut, String channelShortcut, Pageable pageable) {
        return crmTaskService.findAllByContactByShortNameAndNetworkByShortcut(shortcut, organizationShortcut, channelShortcut, pageable);
    }

    public CrmTask getTaskAssociatedWithContact(Long taskId, String organizationShortcut, String channelShortcut) {
        return crmTaskService.findOneByIdAndChannel_Organization_Shortcut(taskId, organizationShortcut, channelShortcut);
    }


    public void deleteContactTask(String shortcut, Long taskId, String organizationShortcut, String channelShortcut) {
        CrmContact crmContact = crmContactRepository.findOneByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
        CrmTask crmTask = crmTaskService.findOneByIdAndChannel_Organization_Shortcut(taskId, organizationShortcut, channelShortcut);
        if (crmTask != null) {
            crmContact.removeTasks(crmTask);
            crmTaskService.saveOrUpdateTaskAssociatiedWithContact(crmContact, crmTask);
            crmContactRepository.saveAndFlush(crmContact);
            crmTaskService.deleteByIdAndChannel_Organization_Shortcut(taskId, organizationShortcut, channelShortcut);
        }
    }

    public CrmTask saveOrUpdateTaskAssociatiedWithAccount(CrmTask crmTask, String shortcut, String organizationShortcut, String channelShortcut) {
        CrmContact crmContact = crmContactRepository.findOneByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
        if (crmContact != null) {
            CrmTask crmTask1 = crmTaskService.saveOrUpdateTaskAssociatiedWithContact(crmContact, crmTask);
            crmContact.addTasks(crmTask1);
            crmContactRepository.saveAndFlush(crmContact);
            return crmTask1;
        }
        return null;
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

    public CrmContact convertCrmLeadToContact(CrmLead crmLead) {
        CrmContact crmContact = crmContactMapper.crmLeadToCrmContact(crmLead);
        crmContact.setCrmLead(crmLead);
        crmContact.setId(null);
        CrmContact savedContact = saveContact(crmContact);
        return savedContact;
    }
}
