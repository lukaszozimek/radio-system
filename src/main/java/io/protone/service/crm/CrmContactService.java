package io.protone.service.crm;

import io.protone.custom.service.CorPersonService;
import io.protone.domain.*;
import io.protone.repository.custom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class CrmContactService {
    private final Logger log = LoggerFactory.getLogger(CrmContactService.class);


    @Inject
    private CustomCrmContactRepository crmContactRepository;

    @Inject
    private CustomCrmTaskRepository crmTaskRepository;

    @Inject
    private CorPersonService corPersonService;

    @Inject
    private CustomCorPersonRepository personRepository;

    @Inject
    private CustomCorContactRepository corContactRepository;

    @Inject
    private CustomCorAddressRepository addressRepository;


    public List<CrmContact> getAllContact(String corNetwork, Pageable pageable) {
        return crmContactRepository.findAllByNetwork_Shortcut(corNetwork, pageable);
    }

    public CrmContact saveContact(CrmContact contact) {

        log.debug("Persisting CorAddress: {}", contact.getAddres());
        if (contact.getAddres() != null) {
            CorAddress address = addressRepository.save(contact.getAddres());
            contact.setAddres(address);
        }
        if (contact.getPerson() != null) {

            CorPerson corPerson = corPersonService.savePerson(contact.getPerson());
            contact.person(corPerson);
        }
        log.debug("Persisting CrmContact: {}", contact);
        contact = crmContactRepository.save(contact);
        return contact;
    }

    public void deleteContact(String shortcut, String corNetwork) {
        crmContactRepository.deleteByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
    }

    public CrmContact getContact(String shortcut, String corNetwork) {
        return crmContactRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
    }

    public List<CrmTask> getTasksAssociatedWithContact(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskRepository.findAllByContact_ShortNameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

    public CrmTask getTaskAssociatedWithContact(Long taskId, String corNetwork) {
        return crmTaskRepository.findOneByIdAndNetwork_Shortcut(taskId, corNetwork);
    }


    public void deleteContactTask(String shortcut, Long taskId, String corNetwork) {
        CrmContact crmContact = crmContactRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
        crmContact.getTasks().removeIf(crmTask -> crmTask.getId() == taskId);
        crmContactRepository.save(crmContact);
        crmTaskRepository.deleteByIdAndNetwork_Shortcut(taskId, corNetwork);

    }

    public CrmTask updateContactTask(CrmTask crmTask, String shortcut, String corNetwork) {
        CrmContact crmContact = crmContactRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
        if (crmContact != null) {
            crmTask.setContact(crmContact);
            crmTask.setNetwork(crmContact.getNetwork());
            CrmTask task = crmTaskRepository.save(crmTask);
            log.debug("Persisting CrmTask: {}, for CrmContact: ", task);
            crmContact.addTasks(task);
            crmContactRepository.save(crmContact);
            return task;
        }
        return null;
    }
}
