package io.protone.service.crm;

import io.protone.custom.service.CorPersonService;
import io.protone.repository.cor.CorAddressRepository;
import io.protone.repository.crm.CrmLeadRepository;
import io.protone.repository.crm.CrmTaskRepository;
import io.protone.web.rest.mapper.CrmLeadMapper;
import io.protone.web.rest.mapper.CrmTaskMapper;
import io.protone.domain.*;
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
public class CrmLeadService {

    private final Logger log = LoggerFactory.getLogger(CrmLeadService.class);

    @Inject
    private CrmLeadMapper customCrmLeadMapper;

    @Inject
    private CrmLeadRepository crmLeadRepository;

    @Inject
    private CorAddressRepository addressRepository;

    @Inject
    private CrmTaskRepository crmTaskRepository;

    @Inject
    private CrmTaskMapper customCrmTaskMapper;

    @Inject
    private CorPersonService corPersonService;

    @Transactional
    public List<CrmLead> getAllLeads(String corNetwork, Pageable pageable) {
        return crmLeadRepository.findAllByNetwork_Shortcut(corNetwork, pageable);
    }

    public CrmLead saveLead(CrmLead crmLead) {

        log.debug("Persisting CorAddress: {}", crmLead.getAddres());
        if (crmLead.getAddres() != null) {
            CorAddress address = addressRepository.save(crmLead.getAddres());
            crmLead.setAddres(address);
        }
        if (crmLead.getPerson() != null) {

            CorPerson corPerson = corPersonService.savePerson(crmLead.getPerson());
            crmLead.person(corPerson);
        }
        log.debug("Persisting CrmLead: {}", crmLead);
        crmLead = crmLeadRepository.save(crmLead);
        return crmLead;
    }

    public void deleteLead(String shortcut, String corNetwork) {
        crmTaskRepository.deleteByLead_ShortnameAndNetwork_Shortcut(shortcut, corNetwork);
        crmLeadRepository.deleteByShortnameAndNetwork_Shortcut(shortcut, corNetwork);
    }

    public CrmLead getLead(String shortcut, String corNetwork) {
        return crmLeadRepository.findOneByShortnameAndNetwork_Shortcut(shortcut, corNetwork);
    }

    public List<CrmTask> getTasksAssociatedWithLead(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskRepository.findAllByLead_ShortnameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

    public CrmTask getTaskAssociatedWithLead(Long taskId, String corNetwork) {
        return crmTaskRepository.findOneByIdAndNetwork_Shortcut(taskId, corNetwork);
    }


    public void deleteLeadTask(String shortcut, Long taskId, String corNetwork) {
        CrmLead crmContact = crmLeadRepository.findOneByShortnameAndNetwork_Shortcut(shortcut, corNetwork);
        crmContact.getTasks().removeIf(crmTask -> crmTask.getId() == taskId);
        crmLeadRepository.save(crmContact);
        crmTaskRepository.deleteByIdAndNetwork_Shortcut(taskId, corNetwork);

    }

    public CrmTask saveOrUpdateTaskAssociatiedWithLead(CrmTask crmTask, String shortcut, String corNetwork) {
        CrmLead crmLead = crmLeadRepository.findOneByShortnameAndNetwork_Shortcut(shortcut, corNetwork);
        if (crmLead != null) {
            crmTask.setLead(crmLead);
            crmTask.setNetwork(crmLead.getNetwork());
            CrmTask task = crmTaskRepository.save(crmTask);
            log.debug("Persisting CrmTask: {}, for CrmLead: ", task);
            crmLead.addTasks(task);
            crmLeadRepository.save(crmLead);
            return task;
        }
        return null;
    }

}
