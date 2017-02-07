package io.protone.custom.service;

import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.mapper.CustomCrmContactMapper;
import io.protone.custom.service.mapper.CustomCrmTaskMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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

    @Inject
    private CustomCrmContactMapper customCrmContactMapper;

    @Inject
    private CrmContactRepository crmContactRepository;

    @Inject
    private CrmTaskRepository crmTaskRepository;

    @Inject
    private CustomCrmTaskMapper customCrmTaskMapper;

    @Inject
    private CorPersonRepository personRepository;

    @Inject
    private CorContactRepository corContactRepository;

    @Inject
    private CorAddressRepository addressRepository;

    public List<CrmContactPT> getAllContact(CorNetwork corNetwork) {
        return crmContactRepository.findByNetwork(corNetwork).stream().map(crmContact -> customCrmContactMapper.buildContactDTOFromEntities(crmContact)).collect(toList());
    }

    public CrmContactPT saveContact(CrmContactPT crmContactPT, CorNetwork corNetwork) {
        CrmContact contact = customCrmContactMapper.createCrmContactEntity(crmContactPT, corNetwork);
        CorAddress address = addressRepository.save(contact.getAddres());
        List<CorContact> corContact = corContactRepository.save(contact.getPerson().getContacts());
        CorPerson person = personRepository.save(contact.getPerson());
        person.contacts(corContact.stream().collect(Collectors.toSet()));
        contact.setAddres(address);
        contact.person(person);
        contact = crmContactRepository.save(contact);
        return customCrmContactMapper.buildContactDTOFromEntities(contact);
    }

    public void deleteContact(String shortcut, CorNetwork corNetwork) {
        crmContactRepository.deleteByShortNameAndNetwork(shortcut, corNetwork);
    }

    public CrmContactPT getContact(String shortcut, CorNetwork corNetwork) {
        return customCrmContactMapper.buildContactDTOFromEntities(crmContactRepository.findOneByShortNameAndNetwork(shortcut, corNetwork));
    }

    public List<CrmTaskPT> getTasksAssociatedWithContact(String shortcut, CorNetwork corNetwork) {
        CrmContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        return customCrmTaskMapper.createCrmTasks(crmTaskRepository.findByCrmContactAndNetwork(crmContact, corNetwork));
    }

    public CrmTaskPT getTaskAssociatedWithContact(String shortcut, Long taskId, CorNetwork corNetwork) {
        CrmContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        return customCrmTaskMapper.createCrmTask(crmTaskRepository.findOneByCrmContactAndIdAndNetwork(crmContact, taskId, corNetwork));
    }


    public void deleteContactTask(String shortcut, Long taskId, CorNetwork corNetwork) {
        CrmContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        crmTaskRepository.deleteByCrmContactAndIdAndNetwork(crmContact, taskId, corNetwork);
    }

    public CrmTaskPT updateLeadTask(String shortcut, CrmTaskPT crmTask, CorNetwork corNetwork) {
        CrmContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        CrmTask task = crmTaskRepository.save(customCrmTaskMapper.createTaskEntity(crmTask));
        crmContact.addTasks(task);
        crmContactRepository.save(crmContact);
        return customCrmTaskMapper.createCrmTask(task);
    }
}
