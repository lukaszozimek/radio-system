package io.protone.custom.service;

import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.service.mapper.CrmContactMapper;
import io.protone.service.mapper.CrmTaskMapper;
import io.protone.domain.*;
import io.protone.repository.custom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.mail.search.SearchTerm;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
        CorAddress address = addressRepository.save(contact.getAddres());
        log.debug("Persisting CorContact: {}", contact.getPerson().getContacts());
        List<CorContact> corContact = corContactRepository.save(contact.getPerson().getContacts());
        log.debug("Persisting CorPerson: {}", contact.getPerson());
        CorPerson person = personRepository.save(contact.getPerson());
        log.debug("Persisting CorPerson Contacts");
        corContact.stream().forEach(corContact1 -> corContactRepository.save(corContact1.person(person)));
        person.contacts(corContact.stream().collect(Collectors.toSet()));
        contact.setAddres(address);
        contact.person(person);
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
        return crmTaskRepository.findAllByContact_ShortNameAndNetwork(shortcut, corNetwork, pageable);
    }

    public CrmTask getTaskAssociatedWithContact(String shortcut, Long taskId, String corNetwork) {
        return crmTaskRepository.findOneByIdAndNetwork_Shortcut(taskId, corNetwork);
    }


    public void deleteContactTask(String shortcut, Long taskId, CorNetwork corNetwork) {
        crmTaskRepository.deleteByIdAndNetwork(taskId, corNetwork);
    }

    public CrmTask updateContactTask(String shortcut, CrmTask crmTask, String corNetwork) {
        CrmContact crmContact = crmContactRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
        CrmTask task = crmTaskRepository.save(crmTask);
        log.debug("Persisting CrmTask: {}, for CrmContact: ", task);
        crmContact.addTasks(task);
        crmContactRepository.save(crmContact);
        return task;
    }
}
