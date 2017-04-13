package io.protone.custom.service;

import io.protone.custom.service.dto.CrmLeadPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.mapper.CustomCrmLeadMapper;
import io.protone.custom.service.mapper.CustomCrmTaskMapper;
import io.protone.domain.*;
import io.protone.repository.custom.*;
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
public class CrmLeadService {

    @Inject
    private CustomCrmLeadMapper customCrmLeadMapper;

    @Inject
    private CustomCrmLeadRepository crmLeadRepository;

    @Inject
    private CustomCorAddressRepository addressRepository;

    @Inject
    private CustomCorContactRepository corContactRepository;

    @Inject
    private CustomCorPersonRepository personRepository;

    @Inject
    private CustomCrmTaskRepository crmTaskRepository;

    @Inject
    private CustomCrmTaskMapper customCrmTaskMapper;

    @Transactional
    public List<CrmLeadPT> getAllLeads(CorNetwork corNetwork) {
        return crmLeadRepository.findByNetwork(corNetwork).stream().map(crmLead -> customCrmLeadMapper.createDTOFromEntites(crmLead)).collect(toList());
    }

    public CrmLeadPT saveLead(CrmLeadPT lead, CorNetwork corNetwork) {
        CrmLead lead1 = customCrmLeadMapper.createLeadEntity(lead, corNetwork);
        CorAddress address = addressRepository.save(lead1.getAddres());
        List<CorContact> corContact = corContactRepository.save(lead1.getPerson().getContacts());
        lead1.getPerson().setContacts(corContact.stream().collect(Collectors.toSet()));
        CorPerson person = personRepository.save(lead1.getPerson());
        corContact.stream().forEach(corContact1 -> corContactRepository.save(corContact1.person(person)));
        lead1.setAddres(address);
        lead1.person(person);
        lead1 = crmLeadRepository.save(lead1);
        return customCrmLeadMapper.createDTOFromEntites(lead1);
    }

    public void deleteLead(String shortcut, CorNetwork corNetwork) {
        crmLeadRepository.deleteByShortnameAndNetwork(shortcut, corNetwork);
    }

    public CrmLeadPT getLead(String shortcut, CorNetwork corNetwork) {
        return customCrmLeadMapper.createDTOFromEntites(crmLeadRepository.findOneByShortnameAndNetwork(shortcut, corNetwork));
    }


    public List<CrmTaskPT> getTasksAssociatedWithLead(String shortcut, CorNetwork corNetwork) {
        CrmLead crmLead = crmLeadRepository.findOneByShortnameAndNetwork(shortcut, corNetwork);
        return customCrmTaskMapper.createCrmTasks(crmLead.getTasks());
    }

    public CrmTaskPT getTaskAssociatedWithLead(String shortcut, Long taskId, CorNetwork corNetwork) {
        CrmLead crmLead = crmLeadRepository.findOneByShortnameAndNetwork(shortcut, corNetwork);
        return customCrmTaskMapper.createCrmTask(crmLead.getTasks().stream().filter(crmTask -> crmTask.getId().equals(taskId)).findFirst().get());
    }

    public void deleteLeadTask(String shortcut, Long taskId, CorNetwork corNetwork) {
        CrmLead crmLead = crmLeadRepository.findOneByShortnameAndNetwork(shortcut, corNetwork);
        CrmTask crmTask = crmTaskRepository.findOne(taskId);
        crmLead.removeTasks(crmTask);
        crmLeadRepository.save(crmLead);
        crmTaskRepository.delete(crmTask);
    }

    public CrmTaskPT createTasksAssociatedWithLead(String shortcut, CrmTaskPT taskPT, CorNetwork corNetwork) {
        CrmLead crmLead = crmLeadRepository.findOneByShortnameAndNetwork(shortcut, corNetwork);
        CrmTask crmTask = crmTaskRepository.save(customCrmTaskMapper.createTaskEntity(taskPT));
        crmLead.addTasks(crmTask);
        crmLeadRepository.save(crmLead);
        return customCrmTaskMapper.createCrmTask(crmTask);
    }

}
