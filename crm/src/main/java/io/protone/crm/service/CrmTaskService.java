package io.protone.crm.service;

import io.protone.crm.domain.*;
import io.protone.crm.repostiory.CrmTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 15/06/2017.
 */
@Service
public class CrmTaskService {
    private final Logger log = LoggerFactory.getLogger(CrmTaskService.class);

    @Inject
    private CrmTaskRepository crmTaskRepository;

    @Inject
    private CrmTaskCommentService crmTaskCommentService;

    @Transactional
    public CrmTask saveOrUpdateTaskAssociatiedWithContact(CrmContact contact, CrmTask crmTask) {
        crmTask.setContact(contact);
        crmTask.setNetwork(contact.getNetwork());
        CrmTask task = crmTaskRepository.saveAndFlush(crmTask);
        log.debug("Persisting CrmTask: {}, for CrmContact: ", task);
        return task;
    }

    @Transactional
    public CrmTask saveOrUpdateTaskAssociatiedWithCustomer(CrmAccount crmAccount, CrmTask crmTask) {
        crmTask.setNetwork(crmAccount.getNetwork());
        CrmTask task = crmTaskRepository.saveAndFlush(crmTask);
        log.debug("Persisting CrmTask: {}, for CrmAccount: ", task);
        return task;
    }

    @Transactional
    public CrmTask saveOrUpdateTaskAssociatiedWithLead(CrmLead crmLead, CrmTask crmTask) {
        crmTask.setNetwork(crmLead.getNetwork());
        CrmTask task = crmTaskRepository.saveAndFlush(crmTask);
        log.debug("Persisting CrmTask: {}, for CrmLead: ", task);
        return crmTask;
    }

    @Transactional
    public CrmTask saveOrUpdateTaskAssociatiedWithOpportunity(CrmOpportunity crmOpportunity, CrmTask crmTask) {
        crmTask.setNetwork(crmOpportunity.getNetwork());
        CrmTask task = crmTaskRepository.saveAndFlush(crmTask);
        log.debug("Persisting CrmTask: {}, for CrmOpportunity: ", task);
        return task;
    }

    @Transactional
    public void deleteCrmTask(Long taskId, String corNetwork) {
        crmTaskRepository.deleteByIdAndNetwork_Shortcut(taskId, corNetwork);
    }

    @Transactional
    public void deleteByContactByShortNameAndNetworkByShortcut(String shortcut, String corNetwork) {
        List<CrmTask> crmTasks = crmTaskRepository.findAllByContact_ShortNameAndNetwork_Shortcut(shortcut, corNetwork);
        if (crmTasks != null && !crmTasks.isEmpty()) {
            crmTasks.stream().forEach(crmTask -> {
                this.crmTaskCommentService.deleteByCrmTaskId(crmTask.getId(), corNetwork);

            });
            crmTaskRepository.delete(crmTasks);
        }
        crmTaskRepository.deleteByContact_ShortNameAndNetwork_Shortcut(shortcut, corNetwork);
    }

    @Transactional
    public Slice<CrmTask> findAllByContactByShortNameAndNetworkByShortcut(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskRepository.findSliceByContact_ShortNameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

    public CrmTask findOneByIdAndNetwork_Shortcut(Long taskId, String corNetwork) {
        return crmTaskRepository.findOneByIdAndNetwork_Shortcut(taskId, corNetwork);
    }

    public Slice<CrmTask> findAllByAccount_ShortNameAndNetwork_Shortcut(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskRepository.findSliceByAccount_ShortNameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

    @Transactional
    public void deleteByAccount_ShortNameAndNetwork_Shortcut(String shortName, String corNetwork) {
        List<CrmTask> crmTasks = crmTaskRepository.findAllByAccount_ShortNameAndNetwork_Shortcut(shortName, corNetwork);
        if (crmTasks != null && !crmTasks.isEmpty()) {
            crmTasks.stream().forEach(crmTask -> {
                this.crmTaskCommentService.deleteByCrmTaskId(crmTask.getId(), corNetwork);

            });
            crmTaskRepository.delete(crmTasks);
        }
        crmTaskRepository.deleteByAccount_ShortNameAndNetwork_Shortcut(shortName, corNetwork);

    }


    @Transactional
    public void deleteByIdAndNetwork_Shortcut(Long taskId, String corNetwork) {
        crmTaskRepository.deleteByIdAndNetwork_Shortcut(taskId, corNetwork);
    }

    @Transactional
    public void deleteByLead_ShortnameAndNetwork_Shortcut(String shortcut, String corNetwork) {
        List<CrmTask> crmTasks = crmTaskRepository.findAllByLead_ShortnameAndNetwork_Shortcut(shortcut, corNetwork);
        if (crmTasks != null && !crmTasks.isEmpty()) {
            crmTasks.stream().forEach(crmTask -> {
                this.crmTaskCommentService.deleteByCrmTaskId(crmTask.getId(), corNetwork);

            });
            crmTaskRepository.delete(crmTasks);
        }
        crmTaskRepository.deleteByLead_ShortnameAndNetwork_Shortcut(shortcut, corNetwork);
    }

    @Transactional
    public void deleteByOpportunity_ShortnameAndNetwork_Shortcut(String shortcut, String corNetwork) {
        List<CrmTask> crmTasks = crmTaskRepository.findAllByOpportunity_ShortNameAndNetwork_Shortcut(shortcut, corNetwork);
        if (crmTasks != null && !crmTasks.isEmpty()) {
            crmTasks.stream().forEach(crmTask -> {
                this.crmTaskCommentService.deleteByCrmTaskId(crmTask.getId(), corNetwork);

            });
            crmTaskRepository.delete(crmTasks);
        }
        crmTaskRepository.deleteByLead_ShortnameAndNetwork_Shortcut(shortcut, corNetwork);
    }

    public Slice<CrmTask> findAllByLead_ShortnameAndNetwork_Shortcut(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskRepository.findSliceByLead_ShortnameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

    public Slice<CrmTask> findAllByOpportunity_ShortNameAndNetwork_Shortcut(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskRepository.findSliceByOpportunity_ShortNameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

    public void deleteCustomerTaskComment(Long taskId, Long id, String networkShortcut) {
        CrmTask crmTask = crmTaskRepository.findOneByIdAndNetwork_Shortcut(taskId, networkShortcut);
        crmTask.getComments().removeIf(crmTaskComment -> crmTaskComment.getId() == id);
        crmTaskRepository.save(crmTask);
        crmTaskCommentService.deleteByCrmTaskIdAndCommentId(taskId, id, networkShortcut);

    }

    public CrmTaskComment getTaskCommentAssociatedWithTask(String networkShortcut, Long taskId, Long id) {
        return crmTaskCommentService.findByCrmTaskId(id, taskId, networkShortcut);
    }

    public CrmTaskComment saveOrUpdateTaskCommentAssociatedWithTask(CrmTaskComment requestEnitity, Long taskId, String networkShortcut) {
        CrmTask crmTask = crmTaskRepository.findOneByIdAndNetwork_Shortcut(taskId, networkShortcut);
        if (requestEnitity != null) {
            CrmTaskComment crmTaskComment = crmTaskCommentService.saveCrmTaskComment(requestEnitity);
            crmTask.addComments(crmTaskComment);
            crmTaskRepository.saveAndFlush(crmTask);
            return crmTaskComment;
        }
        return null;

    }

    public Slice<CrmTaskComment> getTaskCommentsAssociatedWithTask(Long taskId, String networkShortcut, Pageable pagable) {
        return crmTaskCommentService.findByCrmTaskId(taskId, networkShortcut, pagable);
    }

}
