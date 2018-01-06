package io.protone.crm.service;


import io.protone.crm.domain.*;
import io.protone.crm.repostiory.CrmOpportunityRepository;
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
public class CrmOpportunityService {

    private final Logger log = LoggerFactory.getLogger(CrmOpportunityService.class);
    private static final String CONVERTED = "conv";

    @Inject
    private CrmOpportunityRepository opportunityRepository;

    @Inject
    private CrmTaskService crmTaskService;


    public Slice<CrmOpportunity> getAllOpportunity(String organizationShortcut, String channelShortcut, Pageable pageable) {
        return opportunityRepository.findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(organizationShortcut, channelShortcut, pageable);
    }

    public CrmOpportunity saveOpportunity(CrmOpportunity crmOpportunity) {
        log.debug("Persisting CrmOpportunity: {}", crmOpportunity);
        return opportunityRepository.saveAndFlush(crmOpportunity);
    }

    public void deleteOpportunity(String shortcut, String organizationShortcut, String channelShortcut) {
        crmTaskService.deleteByOpportunity_ShortnameAndChannel_Organization_Shortcut(shortcut, organizationShortcut, channelShortcut);
        opportunityRepository.deleteByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
    }

    public CrmOpportunity getOpportunity(String shortcut, String organizationShortcut, String channelShortcut) {
        return opportunityRepository.findOneByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
    }


    public CrmTask saveOrUpdateTaskAssociatiedWithOpportunity(CrmTask crmTask, String shortcut, String organizationShortcut, String channelShortcut) {
        CrmOpportunity crmOpportunity = opportunityRepository.findOneByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
        if (crmOpportunity != null) {
            CrmTask task = crmTaskService.saveOrUpdateTaskAssociatiedWithOpportunity(crmOpportunity, crmTask);
            crmOpportunity.addTasks(task);
            opportunityRepository.saveAndFlush(crmOpportunity);
            return task;
        }
        return null;
    }

    public Slice<CrmTask> getTasksAssociatedWithOpportunity(String shortcut, String organizationShortcut, String channelShortcut, Pageable pageable) {
        return crmTaskService.findAllByOpportunity_ShortNameAndChannel_Organization_Shortcut(shortcut, organizationShortcut, channelShortcut, pageable);
    }

    public CrmTask getTaskAssociatedWithOpportunity(Long taskId, String organizationShortcut, String channelShortcut) {
        return crmTaskService.findOneByIdAndChannel_Organization_Shortcut(taskId, organizationShortcut, channelShortcut);
    }


    public void deleteOpportunityTask(String shortcut, Long taskId, String organizationShortcut, String channelShortcut) {
        CrmOpportunity crmOpportunity = opportunityRepository.findOneByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
        CrmTask crmTask = crmTaskService.findOneByIdAndChannel_Organization_Shortcut(taskId, organizationShortcut, channelShortcut);
        if (crmTask != null) {
            crmOpportunity.removeTasks(crmTask);
            crmTaskService.saveOrUpdateTaskAssociatiedWithOpportunity(crmOpportunity, crmTask);
            opportunityRepository.saveAndFlush(crmOpportunity);
            crmTaskService.deleteByIdAndChannel_Organization_Shortcut(taskId, organizationShortcut, channelShortcut);
        }
    }


    public void deleteOpportunityTaskComment(Long taskId, Long id, String organizationShortcut, String channelShortcut) {
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

    public CrmOpportunity convertLeadToOpportunity(CrmLead lead) {
        CrmOpportunity opportunity = new CrmOpportunity();
        opportunity.setShortName(CONVERTED + lead.getShortname());
        opportunity.setLead(lead);
        opportunity.setKeeper(lead.getKeeper());
        opportunity.setChannel(lead.getChannel());

        return saveOpportunity(opportunity);
    }

    public CrmOpportunity convertContactToOpportunity(CrmContact crmContact) {
        CrmOpportunity opportunity = new CrmOpportunity();

        opportunity.setShortName(CONVERTED + crmContact.getShortName());
        opportunity.contact(crmContact);
        opportunity.setKeeper(crmContact.getKeeper());
        opportunity.setChannel(crmContact.getChannel());

        return saveOpportunity(opportunity);
    }

    public CrmOpportunity convertAccountToOpportunity(CrmAccount crmAccount) {
        CrmOpportunity opportunity = new CrmOpportunity();
        opportunity.setAccount(crmAccount);
        opportunity.setShortName(CONVERTED + crmAccount.getShortName());
        opportunity.setKeeper(crmAccount.getKeeper());
        opportunity.setChannel(crmAccount.getChannel());

        return saveOpportunity(opportunity);
    }

}
