package io.protone.service.crm;

import io.protone.domain.CorAddress;
import io.protone.domain.CorPerson;
import io.protone.domain.CrmContact;
import io.protone.domain.CrmTask;
import io.protone.repository.crm.CrmContactRepository;
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


    public List<CrmContact> getAllContact(String corNetwork, Pageable pageable) {
        return crmContactRepository.findAllByNetwork_Shortcut(corNetwork, pageable);
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

    public void deleteContact(String shortcut, String corNetwork) {
        crmTaskService.deleteByContactByShortNameAndNetworkByShortcut(shortcut, corNetwork);
        crmContactRepository.deleteByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
    }

    public CrmContact getContact(String shortcut, String corNetwork) {
        return crmContactRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
    }

    public List<CrmTask> getTasksAssociatedWithContact(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskService.findAllByContactByShortNameAndNetworkByShortcut(shortcut, corNetwork, pageable);
    }

    public CrmTask getTaskAssociatedWithContact(Long taskId, String corNetwork) {
        return crmTaskService.findOneByIdAndNetwork_Shortcut(taskId, corNetwork);
    }


    public void deleteContactTask(String shortcut, Long taskId, String corNetwork) {
        CrmContact crmContact = crmContactRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
        crmContact.getTasks().removeIf(crmTask -> crmTask.getId() == taskId);
        crmContactRepository.save(crmContact);
        crmTaskService.deleteCrmTask(taskId, corNetwork);
    }

    public CrmTask saveOrUpdateTaskAssociatiedWithAccount(CrmTask crmTask, String shortcut, String corNetwork) {
        CrmContact crmContact = crmContactRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
        if (crmContact != null) {
            CrmTask crmTask1 = crmTaskService.saveOrUpdateTaskAssociatiedWithContact(crmContact, crmTask);
            crmContact.addTasks(crmTask1);
            crmContactRepository.saveAndFlush(crmContact);
        }
        return null;
    }
}
