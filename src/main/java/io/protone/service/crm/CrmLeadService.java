package io.protone.service.crm;

import io.protone.domain.*;
import io.protone.repository.crm.CrmLeadRepository;
import io.protone.service.cor.CorAddressService;
import io.protone.service.cor.CorPersonService;
import io.protone.web.rest.mapper.CrmLeadMapper;
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
    private CorAddressService corAddressService;

    @Inject
    private CrmTaskService crmTaskService;

    @Inject
    private CorPersonService corPersonService;

    @Transactional
    public List<CrmLead> getAllLeads(String corNetwork, Pageable pageable) {
        return crmLeadRepository.findAllByNetwork_Shortcut(corNetwork, pageable);
    }

    public CrmLead saveLead(CrmLead crmLead) {

        log.debug("Persisting CorAddress: {}", crmLead.getAddres());
        if (crmLead.getAddres() != null) {
            CorAddress address = corAddressService.saveCoreAdress(crmLead.getAddres());
            crmLead.setAddres(address);
        }
        if (crmLead.getPerson() != null) {

            CorPerson corPerson = corPersonService.savePerson(crmLead.getPerson());
            crmLead.person(corPerson);
        }
        log.debug("Persisting CrmLead: {}", crmLead);
        crmLead = crmLeadRepository.saveAndFlush(crmLead);
        return crmLead;
    }

    public void deleteLead(String shortcut, String corNetwork) {
        crmTaskService.deleteByLead_ShortnameAndNetwork_Shortcut(shortcut, corNetwork);
        crmLeadRepository.deleteByShortnameAndNetwork_Shortcut(shortcut, corNetwork);
    }

    public CrmLead getLead(String shortcut, String corNetwork) {
        return crmLeadRepository.findOneByShortnameAndNetwork_Shortcut(shortcut, corNetwork);
    }

    public List<CrmTask> getTasksAssociatedWithLead(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskService.findAllByLead_ShortnameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

    public CrmTask getTaskAssociatedWithLead(Long taskId, String corNetwork) {
        return crmTaskService.findOneByIdAndNetwork_Shortcut(taskId, corNetwork);
    }


    public void deleteLeadTask(String shortcut, Long taskId, String corNetwork) {
        CrmLead crmContact = crmLeadRepository.findOneByShortnameAndNetwork_Shortcut(shortcut, corNetwork);
        crmContact.getTasks().removeIf(crmTask -> crmTask.getId() == taskId);
        crmLeadRepository.save(crmContact);
        crmTaskService.deleteByIdAndNetwork_Shortcut(taskId, corNetwork);

    }

    public CrmTask saveOrUpdateTaskAssociatiedWithLead(CrmTask crmTask, String shortcut, String corNetwork) {
        CrmLead crmLead = crmLeadRepository.findOneByShortnameAndNetwork_Shortcut(shortcut, corNetwork);
        if (crmLead != null) {
            CrmTask crmTask1 = crmTaskService.saveOrUpdateTaskAssociatiedWithLead(crmLead, crmTask);
            crmLead.addTasks(crmTask1);
            crmLeadRepository.saveAndFlush(crmLead);
            return crmTask1;
        }
        return null;
    }


    public void deleteLeadTaskComment(Long taskId, Long id, String networkShortcut) {
        crmTaskService.deleteCustomerTaskComment(taskId, id, networkShortcut);
    }

    public CrmTaskComment getTaskCommentAssociatedWithTask(String networkShortcut, Long taskId, Long id) {
        return crmTaskService.getTaskCommentAssociatedWithTask(networkShortcut, taskId, id);
    }

    public CrmTaskComment saveOrUpdateTaskCommentAssociatedWithTask(CrmTaskComment requestEnitity, Long taskId, String networkShortcut) {
        return crmTaskService.saveOrUpdateTaskCommentAssociatedWithTask(requestEnitity, taskId, networkShortcut);
    }

    public List<CrmTaskComment> getTaskCommentsAssociatedWithTask(Long taskId, String networkShortcut, Pageable pagable) {
        return crmTaskService.getTaskCommentsAssociatedWithTask(taskId, networkShortcut, pagable);
    }
}
