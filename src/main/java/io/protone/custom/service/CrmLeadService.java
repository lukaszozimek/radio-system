package io.protone.custom.service;

import io.protone.custom.service.dto.CrmLeadPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.mapper.CustomCrmLeadMapper;
import io.protone.custom.service.mapper.CustomCrmTaskMapper;
import io.protone.custom.web.rest.network.ApiNetworkImpl;
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
public class CrmLeadService {

    private final Logger log = LoggerFactory.getLogger(CrmLeadService.class);

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
        return crmLeadRepository.findByNetwork(corNetwork).stream().map(crmLead -> customCrmLeadMapper.DB2DTO(crmLead)).collect(toList());
    }

    public CrmLeadPT saveLead(CrmLeadPT crmLeadPT, CorNetwork corNetwork) {
        CrmLead lead = customCrmLeadMapper.DTO2DB(crmLeadPT);
        lead.network(corNetwork);
        log.debug("Persisting CorAddress: {}", lead.getAddres());
        CorAddress address = addressRepository.save(lead.getAddres());

        log.debug("Persisting CorContact: {}", lead.getPerson().getContacts());
        List<CorContact> corContact = corContactRepository.save(lead.getPerson().getContacts());
        lead.getPerson().setContacts(corContact.stream().collect(Collectors.toSet()));

        log.debug("Persisting CorPerson: {}", lead.getPerson());
        CorPerson person = personRepository.save(lead.getPerson());

        log.debug("Persisting CorPerson with Contact", lead.getPerson());
        corContact.stream().forEach(corContact1 -> corContactRepository.save(corContact1.person(person)));
        lead.setAddres(address);
        lead.person(person);

        log.debug("Persisting CrmLead", lead);
        lead = crmLeadRepository.save(lead);
        return customCrmLeadMapper.DB2DTO(lead);
    }

    public void deleteLead(String shortcut, CorNetwork corNetwork) {
        crmLeadRepository.deleteByShortnameAndNetwork(shortcut, corNetwork);
    }

    public CrmLeadPT getLead(String shortcut, CorNetwork corNetwork) {
        return customCrmLeadMapper.DB2DTO(crmLeadRepository.findOneByShortnameAndNetwork(shortcut, corNetwork));
    }


    public List<CrmTaskPT> getTasksAssociatedWithLead(String shortcut, CorNetwork corNetwork) {
        CrmLead crmLead = crmLeadRepository.findOneByShortnameAndNetwork(shortcut, corNetwork);
        return customCrmTaskMapper.DBs2DTOs(crmLead.getTasks());
    }

    public CrmTaskPT getTaskAssociatedWithLead(String shortcut, Long taskId, CorNetwork corNetwork) {
        CrmLead crmLead = crmLeadRepository.findOneByShortnameAndNetwork(shortcut, corNetwork);
        return customCrmTaskMapper.DB2DTO(crmLead.getTasks().stream().filter(crmTask -> crmTask.getId().equals(taskId)).findFirst().get());
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
        CrmTask crmTask = crmTaskRepository.save(customCrmTaskMapper.DTO2DB(taskPT));
        log.debug("Persisting CrmTask: {}, for CrmLead: ", crmTask);
        crmLead.addTasks(crmTask);
        crmLeadRepository.save(crmLead);
        return customCrmTaskMapper.DB2DTO(crmTask);
    }

}
