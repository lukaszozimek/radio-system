package io.protone.crm.service;


import io.protone.core.domain.CorAddress;
import io.protone.core.domain.CorPerson;
import io.protone.core.service.CorAddressService;
import io.protone.core.service.CorPersonService;
import io.protone.crm.domain.CrmLead;
import io.protone.crm.domain.CrmTask;
import io.protone.crm.domain.CrmTaskComment;
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
    private CrmLeadRepository crmLeadRepository;

    @Inject
    private CorAddressService corAddressService;

    @Inject
    private CrmTaskService crmTaskService;

    @Inject
    private CorPersonService corPersonService;

    @Transactional
    public Slice<CrmLead> getAllLeads(String organizationShortcut, String channelShortcut, Pageable pageable) {
        return crmLeadRepository.findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(organizationShortcut, channelShortcut, pageable);
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

    public void deleteLead(String shortcut, String organizationShortcut, String channelShortcut) {
        crmTaskService.deleteByLead_ShortnameAndChannel_Organization_Shortcut(shortcut, organizationShortcut, channelShortcut);
        crmLeadRepository.deleteByShortnameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
    }

    public CrmLead getLead(String shortcut, String organizationShortcut, String channelShortcut) {
        return crmLeadRepository.findOneByShortnameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
    }

    public Slice<CrmTask> getTasksAssociatedWithLead(String shortcut, String organizationShortcut, String channelShortcut, Pageable pageable) {
        return crmTaskService.findAllByLead_ShortnameAndChannel_Organization_Shortcut(shortcut, organizationShortcut, channelShortcut, pageable);
    }

    public CrmTask getTaskAssociatedWithLead(Long taskId, String organizationShortcut, String channelShortcut) {
        return crmTaskService.findOneByIdAndChannel_Organization_Shortcut(taskId, organizationShortcut, channelShortcut);
    }


    public void deleteLeadTask(String shortcut, Long taskId, String organizationShortcut, String channelShortcut) {
        CrmLead crmContact = crmLeadRepository.findOneByShortnameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
        CrmTask crmTask = crmTaskService.findOneByIdAndChannel_Organization_Shortcut(taskId, organizationShortcut, channelShortcut);
        if (crmTask != null) {
            crmContact.removeTasks(crmTask);
            crmTaskService.saveOrUpdateTaskAssociatiedWithLead(crmContact, crmTask);
            crmLeadRepository.saveAndFlush(crmContact);
            crmTaskService.deleteByIdAndChannel_Organization_Shortcut(taskId, organizationShortcut, channelShortcut);
        }
    }

    public CrmTask saveOrUpdateTaskAssociatiedWithLead(CrmTask crmTask, String shortcut, String organizationShortcut, String channelShortcut) {
        CrmLead crmLead = crmLeadRepository.findOneByShortnameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
        if (crmLead != null) {
            CrmTask crmTask1 = crmTaskService.saveOrUpdateTaskAssociatiedWithLead(crmLead, crmTask);
            crmLead.addTasks(crmTask1);
            crmLeadRepository.saveAndFlush(crmLead);
            return crmTask1;
        }
        return null;
    }


    public void deleteLeadTaskComment(Long taskId, Long id, String organizationShortcut, String channelShortcut) {
        crmTaskService.deleteCustomerTaskComment(taskId, id, organizationShortcut, channelShortcut);
    }

    public CrmTaskComment getTaskCommentAssociatedWithTask(String organizationShortcut, String channelShortcut, Long taskId, Long id) {
        return crmTaskService.getTaskCommentAssociatedWithTask(organizationShortcut, channelShortcut, taskId, id);
    }

    public CrmTaskComment saveOrUpdateTaskCommentAssociatedWithTask(CrmTaskComment requestEnitity, Long taskId, String organizationShortcut, String channelShortcut) {
        return crmTaskService.saveOrUpdateTaskCommentAssociatedWithTask(requestEnitity, taskId, organizationShortcut, channelShortcut);
    }

    public Slice<CrmTaskComment> getTaskCommentsAssociatedWithTask(Long taskId, String organizationShortcut, String channelShortcut, Pageable pagable) {
        return crmTaskService.getTaskCommentsAssociatedWithTask(taskId, organizationShortcut, channelShortcut, pagable);
    }
}
