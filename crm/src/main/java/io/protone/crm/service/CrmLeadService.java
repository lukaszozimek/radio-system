package io.protone.crm.service;


import io.protone.core.domain.CorAddress;
import io.protone.core.domain.CorPerson;
import io.protone.core.service.CorAddressService;
import io.protone.core.service.CorPersonService;
import io.protone.crm.domain.CrmLead;
import io.protone.crm.domain.CrmTask;
import io.protone.crm.domain.CrmTaskComment;
import io.protone.crm.mapper.CrmLeadMapper;
import io.protone.crm.repostiory.CrmLeadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

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
    public Slice<CrmLead> getAllLeads(String corNetwork, Pageable pageable) {
        return crmLeadRepository.findSliceByNetwork_Shortcut(corNetwork, pageable);
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

    public Slice<CrmTask> getTasksAssociatedWithLead(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskService.findAllByLead_ShortnameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

    public CrmTask getTaskAssociatedWithLead(Long taskId, String corNetwork) {
        return crmTaskService.findOneByIdAndNetwork_Shortcut(taskId, corNetwork);
    }


    public void deleteLeadTask(String shortcut, Long taskId, String corNetwork) {
        CrmLead crmContact = crmLeadRepository.findOneByShortnameAndNetwork_Shortcut(shortcut, corNetwork);
        CrmTask crmTask = crmTaskService.findOneByIdAndNetwork_Shortcut(taskId, corNetwork);
        if (crmTask != null) {
            crmContact.removeTasks(crmTask);
            crmTaskService.saveOrUpdateTaskAssociatiedWithLead(crmContact, crmTask);
            crmLeadRepository.saveAndFlush(crmContact);
            crmTaskService.deleteByIdAndNetwork_Shortcut(taskId, corNetwork);
        }
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

    public Slice<CrmTaskComment> getTaskCommentsAssociatedWithTask(Long taskId, String networkShortcut, Pageable pagable) {
        return crmTaskService.getTaskCommentsAssociatedWithTask(taskId, networkShortcut, pagable);
    }
}
