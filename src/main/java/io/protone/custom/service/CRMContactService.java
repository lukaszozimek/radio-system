package io.protone.custom.service;

import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.mapper.CustomCRMContactMapper;
import io.protone.custom.service.mapper.CustomCRMTaskMapper;
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
public class CRMContactService {

    @Inject
    private CustomCRMContactMapper customCRMContactMapper;

    @Inject
    private CRMContactRepository crmContactRepository;

    @Inject
    private CRMTaskRepository crmTaskRepository;

    @Inject
    private CustomCRMTaskMapper customCRMTaskMapper;

    @Inject
    private CORPersonRepository personRepository;

    @Inject
    private CORContactRepository corContactRepository;

    @Inject
    private CORAddressRepository addressRepository;

    public List<CrmContactPT> getAllContact(CORNetwork corNetwork) {
        return crmContactRepository.findByNetwork(corNetwork).stream().map(crmContact -> customCRMContactMapper.buildContactDTOFromEntities(crmContact)).collect(toList());
    }

    public CrmContactPT saveContact(CrmContactPT crmContactPT, CORNetwork corNetwork) {
        CRMContact contact = customCRMContactMapper.createCrmContactEntity(crmContactPT, corNetwork);
        CORAddress address = addressRepository.save(contact.getAddres());
        List<CORContact> corContact = corContactRepository.save(contact.getPerson().getPersonContacts());
        CORPerson person = personRepository.save(contact.getPerson());
        person.setPersonContacts(corContact.stream().collect(Collectors.toSet()));
        contact.setAddres(address);
        contact.person(person);
        contact = crmContactRepository.save(contact);
        return customCRMContactMapper.buildContactDTOFromEntities(contact);
    }

    public void deleteContact(String shortcut, CORNetwork corNetwork) {
        crmContactRepository.deleteByShortNameAndNetwork(shortcut, corNetwork);
    }

    public CrmContactPT getContact(String shortcut, CORNetwork corNetwork) {
        return customCRMContactMapper.buildContactDTOFromEntities(crmContactRepository.findOneByShortNameAndNetwork(shortcut, corNetwork));
    }

    public List<CrmTaskPT> getTasksAssociatedWithContact(String shortcut, CORNetwork corNetwork) {
        CRMContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        return customCRMTaskMapper.createCrmTasks(crmTaskRepository.findByCRMContactAndNetwork(crmContact, corNetwork));
    }

    public CrmTaskPT getTaskAssociatedWithContact(String shortcut, Long taskId, CORNetwork corNetwork) {
        CRMContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        return customCRMTaskMapper.createCrmTask(crmTaskRepository.findOneByCRMContactAndIdAndNetwork(crmContact, taskId, corNetwork));
    }


    public void deleteContactTask(String shortcut, Long taskId, CORNetwork corNetwork) {
        CRMContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        crmTaskRepository.deleteByCRMContactAndIdAndNetwork(crmContact, taskId, corNetwork);
    }

    public CrmTaskPT updateLeadTask(String shortcut, CrmTaskPT crmTask, CORNetwork corNetwork) {
        CRMContact crmContact = crmContactRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        CRMTask task = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(crmTask));
        crmContact.addTasks(task);
        crmContactRepository.save(crmContact);
        return customCRMTaskMapper.createCrmTask(task);
    }
}
