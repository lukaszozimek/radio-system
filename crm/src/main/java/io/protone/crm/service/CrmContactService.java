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
import java.util.List;

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

    public Slice<CrmContact> getAllContact(String corNetwork, Pageable pageable) {
        return crmContactRepository.findSliceByNetwork_Shortcut(corNetwork, pageable);
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
        CorImageItem corImageItem = corImageItemService.saveImageItem(avatar);
        contact.setCorImageItem(corImageItem);
        return this.saveContact(contact);
    }

    public void deleteContact(String shortcut, String corNetwork) {
        crmTaskService.deleteByContactByShortNameAndNetworkByShortcut(shortcut, corNetwork);
        crmContactRepository.deleteByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
    }

    public CrmContact getContact(String shortcut, String corNetwork) {
        return crmContactRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);

    }

    public Slice<CrmTask> getTasksAssociatedWithContact(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskService.findAllByContactByShortNameAndNetworkByShortcut(shortcut, corNetwork, pageable);
    }

    public CrmTask getTaskAssociatedWithContact(Long taskId, String corNetwork) {
        return crmTaskService.findOneByIdAndNetwork_Shortcut(taskId, corNetwork);
    }


    public void deleteContactTask(String shortcut, Long taskId, String corNetwork) {
        CrmContact crmContact = crmContactRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
        CrmTask crmTask = crmTaskService.findOneByIdAndNetwork_Shortcut(taskId, corNetwork);
        if (crmTask != null) {
            crmContact.removeTasks(crmTask);
            crmTaskService.saveOrUpdateTaskAssociatiedWithContact(crmContact, crmTask);
            crmContactRepository.saveAndFlush(crmContact);
            crmTaskService.deleteByIdAndNetwork_Shortcut(taskId, corNetwork);
        }
    }

    public CrmTask saveOrUpdateTaskAssociatiedWithAccount(CrmTask crmTask, String shortcut, String corNetwork) {
        CrmContact crmContact = crmContactRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
        if (crmContact != null) {
            CrmTask crmTask1 = crmTaskService.saveOrUpdateTaskAssociatiedWithContact(crmContact, crmTask);
            crmContact.addTasks(crmTask1);
            crmContactRepository.saveAndFlush(crmContact);
            return crmTask1;
        }
        return null;
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

    public Slice<CrmTaskComment> getTaskCommentsAssociatedWithTask(Long taskId, String networkShortcut, Pageable pagable) {
        return crmTaskService.getTaskCommentsAssociatedWithTask(taskId, networkShortcut, pagable);
    }

    public CrmContact convertCrmLeadToContact(CrmLead crmLead) {
        CrmContact crmContact = crmContactMapper.crmLeadToCrmContact(crmLead);
        crmContact.setCrmLead(crmLead);
        crmContact.setId(null);
        CrmContact savedContact = saveContact(crmContact);
        return savedContact;
    }
}
