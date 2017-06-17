package io.protone.service.crm;

import io.protone.domain.CrmOpportunity;
import io.protone.domain.CrmTask;
import io.protone.domain.CrmTaskComment;
import io.protone.repository.crm.CrmOpportunityRepository;
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
public class CrmOpportunityService {

    private final Logger log = LoggerFactory.getLogger(CrmOpportunityService.class);

    @Inject
    private CrmOpportunityRepository opportunityRepository;

    @Inject
    private CrmTaskService crmTaskService;

    public List<CrmOpportunity> getAllOpportunity(String corNetwork, Pageable pageable) {
        return opportunityRepository.findAllByNetwork_Shortcut(corNetwork, pageable);
    }

    public CrmOpportunity saveOpportunity(CrmOpportunity crmOpportunity) {
        log.debug("Persisting CrmOpportunity: {}", crmOpportunity);
        return opportunityRepository.saveAndFlush(crmOpportunity);
    }

    public void deleteOpportunity(String shortcut, String corNetwork) {
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

    public List<CrmTask> getTasksAssociatedWithOpportunity(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskService.findAllByOpportunity_ShortNameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

    public CrmTask getTaskAssociatedWithOpportunity(Long taskId, String corNetwork) {
        return crmTaskService.findOneByIdAndNetwork_Shortcut(taskId, corNetwork);
    }


    public void deleteOpportunityTask(String shortcut, Long taskId, String corNetwork) {
        CrmOpportunity crmOpportunity = opportunityRepository.findOneByShortNameAndNetwork_Shortcut(shortcut, corNetwork);
        crmOpportunity.getTasks().removeIf(crmTask -> crmTask.getId() == taskId);
        opportunityRepository.save(crmOpportunity);
        crmTaskService.deleteByIdAndNetwork_Shortcut(taskId, corNetwork);
    }


    public void deleteOpportunityTaskComment(Long taskId, Long id, String networkShortcut) {
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
