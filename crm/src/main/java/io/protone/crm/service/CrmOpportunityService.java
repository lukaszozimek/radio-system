package io.protone.crm.service;


import io.protone.crm.domain.*;
import io.protone.crm.mapper.CrmOpportunityMapper;
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

    @Inject
    private CrmOpportunityMapper crmOpportunityMapper;

    public Slice<CrmOpportunity> getAllOpportunity(String corNetwork, Pageable pageable) {
        return opportunityRepository.findSliceByNetwork_Shortcut(corNetwork, pageable);
    }

    public CrmOpportunity saveOpportunity(CrmOpportunity crmOpportunity) {
        log.debug("Persisting CrmOpportunity: {}", crmOpportunity);
        return opportunityRepository.saveAndFlush(crmOpportunity);
    }

    public void deleteOpportunity(String shortcut, String corNetwork) {
        crmTaskService.deleteByOpportunity_ShortnameAndNetwork_Shortcut(shortcut, corNetwork);
        opportunityRepository.deleteByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
    }

    public CrmOpportunity getOpportunity(String shortcut, String corNetwork) {
        return opportunityRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
    }


    public CrmTask saveOrUpdateTaskAssociatiedWithOpportunity(CrmTask crmTask, String shortcut, String corNetwork) {
        CrmOpportunity crmOpportunity = opportunityRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
        if (crmOpportunity != null) {
            CrmTask task = crmTaskService.saveOrUpdateTaskAssociatiedWithOpportunity(crmOpportunity, crmTask);
            crmOpportunity.addTasks(task);
            opportunityRepository.saveAndFlush(crmOpportunity);
            return task;
        }
        return null;
    }

    public Slice<CrmTask> getTasksAssociatedWithOpportunity(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskService.findAllByOpportunity_ShortNameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

    public CrmTask getTaskAssociatedWithOpportunity(Long taskId, String corNetwork) {
        return crmTaskService.findOneByIdAndNetwork_Shortcut(taskId, corNetwork);
    }


    public void deleteOpportunityTask(String shortcut, Long taskId, String corNetwork) {
        CrmOpportunity crmOpportunity = opportunityRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
        CrmTask crmTask = crmTaskService.findOneByIdAndNetwork_Shortcut(taskId, corNetwork);
        if (crmTask != null) {
            crmOpportunity.removeTasks(crmTask);
            crmTaskService.saveOrUpdateTaskAssociatiedWithOpportunity(crmOpportunity, crmTask);
            opportunityRepository.saveAndFlush(crmOpportunity);
            crmTaskService.deleteByIdAndNetwork_Shortcut(taskId, corNetwork);
        }
    }


    public void deleteOpportunityTaskComment(Long taskId, Long id, String organizationShortcut) {
        crmTaskService.deleteCustomerTaskComment(taskId, id, organizationShortcut);
    }

    public CrmTaskComment getTaskCommentAssociatedWithTask(String organizationShortcut, Long taskId, Long id) {
        return crmTaskService.getTaskCommentAssociatedWithTask(organizationShortcut, taskId, id);
    }

    public CrmTaskComment saveOrUpdateTaskCommentAssociatedWithTask(CrmTaskComment requestEnitity, Long taskId, String organizationShortcut) {
        return crmTaskService.saveOrUpdateTaskCommentAssociatedWithTask(requestEnitity, taskId, organizationShortcut);
    }

    public Slice<CrmTaskComment> getTaskCommentsAssociatedWithTask(Long taskId, String organizationShortcut, Pageable pagable) {
        return crmTaskService.getTaskCommentsAssociatedWithTask(taskId, organizationShortcut, pagable);
    }

    public CrmOpportunity convertLeadToOpportunity(CrmLead lead) {
        CrmOpportunity opportunity = new CrmOpportunity();
        opportunity.setShortName(CONVERTED + lead.getShortname());
        opportunity.setLead(lead);
        opportunity.setKeeper(lead.getKeeper());
        opportunity.setNetwork(lead.getNetwork());

        return saveOpportunity(opportunity);
    }

    public CrmOpportunity convertContactToOpportunity(CrmContact crmContact) {
        CrmOpportunity opportunity = new CrmOpportunity();

        opportunity.setShortName(CONVERTED + crmContact.getShortName());
        opportunity.contact(crmContact);
        opportunity.setKeeper(crmContact.getKeeper());
        opportunity.setNetwork(crmContact.getNetwork());

        return saveOpportunity(opportunity);
    }

    public CrmOpportunity convertAccountToOpportunity(CrmAccount crmAccount) {
        CrmOpportunity opportunity = new CrmOpportunity();
        opportunity.setAccount(crmAccount);
        opportunity.setShortName(CONVERTED + crmAccount.getShortName());
        opportunity.setKeeper(crmAccount.getKeeper());
        opportunity.setNetwork(crmAccount.getNetwork());

        return saveOpportunity(opportunity);
    }

}
