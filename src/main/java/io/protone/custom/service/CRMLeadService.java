package io.protone.custom.service;

import io.protone.custom.service.dto.CrmLeadPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.mapper.CustomCRMLeadMapper;
import io.protone.custom.service.mapper.CustomCRMTaskMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import io.protone.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class CRMLeadService {

    @Inject
    private CustomCRMLeadMapper customCRMLeadMapper;

    @Inject
    private CRMLeadRepository crmLeadRepository;

    @Inject
    private CORAddressRepository addressRepository;

    @Inject
    private CORContactRepository corContactRepository;

    @Inject
    private CORPersonRepository personRepository;

    @Inject
    private CRMTaskRepository crmTaskRepository;

    @Inject
    private CustomCRMTaskMapper customCRMTaskMapper;


    @Transactional
    public List<CrmLeadPT> getAllLeads(CORNetwork corNetwork) {
        return crmLeadRepository.findByNetwork(corNetwork).stream().map(crmLead -> customCRMLeadMapper.createDTOFromEntites(crmLead)).collect(toList());
    }

    public CrmLeadPT saveLead(CrmLeadPT lead, CORNetwork corNetwork) {
        CRMLead lead1 = customCRMLeadMapper.createLeadEntity(lead, corNetwork);
        CORAddress address = addressRepository.save(lead1.getAddres());
        List<CORContact> corContact = corContactRepository.save(lead1.getPerson().getPersonContacts());
        CORPerson person = personRepository.save(lead1.getPerson());
        person.setPersonContacts(corContact.stream().collect(Collectors.toSet()));
        lead1.setAddres(address);
        lead1.person(person);
        lead1 = crmLeadRepository.save(lead1);
        return customCRMLeadMapper.createDTOFromEntites(lead1);
    }

    public void deleteLead(String shortcut, CORNetwork corNetwork) {
        crmLeadRepository.deleteByShortnameAndNetwork(shortcut, corNetwork);
    }

    public CrmLeadPT getLead(String shortcut, CORNetwork corNetwork) {
        return customCRMLeadMapper.createDTOFromEntites(crmLeadRepository.findOneByShortnameAndNetwork(shortcut, corNetwork));
    }


    public List<CrmTaskPT> getTasksAssociatedWithLead(String shortcut, CORNetwork corNetwork) {
        CRMLead crmLead = crmLeadRepository.findOneByShortnameAndNetwork(shortcut, corNetwork);
        return customCRMTaskMapper.createCrmTasks(crmLead.getTasks());
    }

    public CrmTaskPT getTaskAssociatedWithLead(String shortcut, Long taskId, CORNetwork corNetwork) {
        CRMLead crmLead = crmLeadRepository.findOneByShortnameAndNetwork(shortcut, corNetwork);
        return customCRMTaskMapper.createCrmTask(crmLead.getTasks().stream().filter(crmTask -> crmTask.getId().equals(taskId)).findFirst().get());
    }

    public void deleteLeadTask(String shortcut, Long taskId, CORNetwork corNetwork) {
        CRMLead crmLead = crmLeadRepository.findOneByShortnameAndNetwork(shortcut, corNetwork);
        CRMTask crmTask = crmTaskRepository.findOne(taskId);
        crmLead.removeTasks(crmTask);
        crmLeadRepository.save(crmLead);
        crmTaskRepository.delete(crmTask);
    }

    public CrmTaskPT createTasksAssociatedWithLead(String shortcut, CrmTaskPT taskPT, CORNetwork corNetwork) {
        CRMLead crmLead = crmLeadRepository.findOneByShortnameAndNetwork(shortcut, corNetwork);
        CRMTask crmTask = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(taskPT));
        crmLead.addTasks(crmTask);
        crmLeadRepository.save(crmLead);
        return customCRMTaskMapper.createCrmTask(crmTask);
    }

}
