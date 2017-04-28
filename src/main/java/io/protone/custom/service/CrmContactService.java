package io.protone.custom.service;

import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.service.mapper.CrmContactMapper;
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
public class CrmContactService {
    private final Logger log = LoggerFactory.getLogger(CrmContactService.class);

    @Inject
    private CrmContactMapper customCrmContactMapper;

    @Inject
    private CustomCrmContactRepository crmContactRepository;

    @Inject
    private CustomCrmTaskRepository crmTaskRepository;

    @Inject
    private CrmTaskMapper customCrmTaskMapper;

    @Inject
    private CustomCorPersonRepository personRepository;

    @Inject
    private CustomCorContactRepository corContactRepository;

    @Inject
    private CustomCorAddressRepository addressRepository;

    public List<CrmContactPT> getAllContact(CorNetwork corNetwork) {
        return crmContactRepository.findByNetwork(corNetwork).stream().map(crmContact -> customCrmContactMapper.DB2DTO(crmContact)).collect(toList());
    }

    public CrmContactPT saveContact(CrmContactPT crmContactPT, CorNetwork corNetwork) {
        CrmContact contact = customCrmContactMapper.DTO2DB(crmContactPT, corNetwork);
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
        return customCrmContactMapper.DB2DTO(contact);
    }

    public void deleteContact(String shortcut, CorNetwork corNetwork) {
        crmContactRepository.deleteByShortNameAndNetwork(shortcut, corNetwork);
    }

    public CrmContactPT getContact(String shortcut, CorNetwork corNetwork) {
        return customCrmContactMapper.DB2DTO(crmContactRepository.findOneByShortNameAndNetwork(shortcut, corNetwork));
    }

    public List<CrmTaskPT> getTasksAssociatedWithContact(String shortcut, CorNetwork corNetwork) {
        CrmContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        return customCrmTaskMapper.DBs2DTOs(crmContact.getTasks());
    }

    public CrmTaskPT getTaskAssociatedWithContact(String shortcut, Long taskId, CorNetwork corNetwork) {
        CrmContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        return customCrmTaskMapper.DB2DTO(crmTaskRepository.findOneByIdAndNetwork(taskId, corNetwork));
    }


    public void deleteContactTask(String shortcut, Long taskId, CorNetwork corNetwork) {
        CrmContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        crmTaskRepository.deleteByIdAndNetwork(taskId, corNetwork);
    }

    public CrmTaskPT updateLeadTask(String shortcut, CrmTaskPT crmTask, CorNetwork corNetwork) {
        CrmContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        CrmTask task = crmTaskRepository.save(customCrmTaskMapper.DTO2DB(crmTask));

        log.debug("Persisting CrmTask: {}, for CrmContact: ", task);
        crmContact.addTasks(task);
        crmContactRepository.save(crmContact);
        return customCrmTaskMapper.DB2DTO(task);
    }
}
