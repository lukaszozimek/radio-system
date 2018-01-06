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
        crmTask.setChannel(contact.getChannel());
        CrmTask task = crmTaskRepository.saveAndFlush(crmTask);
        log.debug("Persisting CrmTask: {}, for CrmContact: ", task);
        return task;
    }

    @Transactional
    public CrmTask saveOrUpdateTaskAssociatiedWithCustomer(CrmAccount crmAccount, CrmTask crmTask) {
        crmTask.setChannel(crmAccount.getChannel());
        CrmTask task = crmTaskRepository.saveAndFlush(crmTask);
        log.debug("Persisting CrmTask: {}, for CrmAccount: ", task);
        return task;
    }

    @Transactional
    public CrmTask saveOrUpdateTaskAssociatiedWithLead(CrmLead crmLead, CrmTask crmTask) {
        crmTask.setChannel(crmLead.getChannel());
        CrmTask task = crmTaskRepository.saveAndFlush(crmTask);
        log.debug("Persisting CrmTask: {}, for CrmLead: ", task);
        return crmTask;
    }

    @Transactional
    public CrmTask saveOrUpdateTaskAssociatiedWithOpportunity(CrmOpportunity crmOpportunity, CrmTask crmTask) {
        crmTask.setChannel(crmOpportunity.getChannel());
        CrmTask task = crmTaskRepository.saveAndFlush(crmTask);
        log.debug("Persisting CrmTask: {}, for CrmOpportunity: ", task);
        return task;
    }

    @Transactional
    public void deleteCrmTask(Long taskId, String organizationShortcut, String channelShortcut) {
        crmTaskRepository.deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(taskId, organizationShortcut, channelShortcut);
    }

    @Transactional
    public void deleteByContactByShortNameAndNetworkByShortcut(String shortcut, String organizationShortcut, String channelShortcut) {
        List<CrmTask> crmTasks = crmTaskRepository.findAllByContact_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
        if (crmTasks != null && !crmTasks.isEmpty()) {
            crmTasks.stream().forEach(crmTask -> {
                this.crmTaskCommentService.deleteByCrmTaskId(crmTask.getId(), organizationShortcut, channelShortcut);

            });
            crmTaskRepository.delete(crmTasks);
        }
        crmTaskRepository.deleteByContact_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
    }

    @Transactional
    public Slice<CrmTask> findAllByContactByShortNameAndNetworkByShortcut(String shortcut, String organizationShortcut, String channelShortcut, Pageable pageable) {
        return crmTaskRepository.findSliceByContact_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut, pageable);
    }

    public CrmTask findOneByIdAndChannel_Organization_Shortcut(Long taskId, String organizationShortcut, String channelShortcut) {
        return crmTaskRepository.findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(taskId, organizationShortcut, channelShortcut);
    }

    public Slice<CrmTask> findAllByAccount_ShortNameAndChannel_Organization_Shortcut(String shortcut, String organizationShortcut, String channelShortcut, Pageable pageable) {
        return crmTaskRepository.findSliceByAccount_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut, pageable);
    }

    @Transactional
    public void deleteByAccount_ShortNameAndChannel_Organization_Shortcut(String shortName, String organizationShortcut, String channelShortcut) {
        List<CrmTask> crmTasks = crmTaskRepository.findAllByAccount_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortName, organizationShortcut, channelShortcut);
        if (crmTasks != null && !crmTasks.isEmpty()) {
            crmTasks.stream().forEach(crmTask -> {
                this.crmTaskCommentService.deleteByCrmTaskId(crmTask.getId(), organizationShortcut, channelShortcut);

            });
            crmTaskRepository.delete(crmTasks);
        }
        crmTaskRepository.deleteByAccount_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortName, organizationShortcut, channelShortcut);

    }


    @Transactional
    public void deleteByIdAndChannel_Organization_Shortcut(Long taskId, String organizationShortcut, String channelShortcut) {
        crmTaskRepository.deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(taskId, organizationShortcut, channelShortcut);
    }

    @Transactional
    public void deleteByLead_ShortnameAndChannel_Organization_Shortcut(String shortcut, String organizationShortcut, String channelShortcut) {
        List<CrmTask> crmTasks = crmTaskRepository.findAllByLead_ShortnameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
        if (crmTasks != null && !crmTasks.isEmpty()) {
            crmTasks.stream().forEach(crmTask -> {
                this.crmTaskCommentService.deleteByCrmTaskId(crmTask.getId(), organizationShortcut, channelShortcut);

            });
            crmTaskRepository.delete(crmTasks);
        }
        crmTaskRepository.deleteByLead_ShortnameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
    }

    @Transactional
    public void deleteByOpportunity_ShortnameAndChannel_Organization_Shortcut(String shortcut, String organizationShortcut, String channelShortcut) {
        List<CrmTask> crmTasks = crmTaskRepository.findAllByOpportunity_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
        if (crmTasks != null && !crmTasks.isEmpty()) {
            crmTasks.stream().forEach(crmTask -> {
                this.crmTaskCommentService.deleteByCrmTaskId(crmTask.getId(), organizationShortcut, channelShortcut);

            });
            crmTaskRepository.delete(crmTasks);
        }
        crmTaskRepository.deleteByLead_ShortnameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut);
    }

    public Slice<CrmTask> findAllByLead_ShortnameAndChannel_Organization_Shortcut(String shortcut, String organizationShortcut, String channelShortcut, Pageable pageable) {
        return crmTaskRepository.findSliceByLead_ShortnameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut, pageable);
    }

    public Slice<CrmTask> findAllByOpportunity_ShortNameAndChannel_Organization_Shortcut(String shortcut, String organizationShortcut, String channelShortcut, Pageable pageable) {
        return crmTaskRepository.findSliceByOpportunity_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut, pageable);
    }

    public void deleteCustomerTaskComment(Long taskId, Long id, String organizationShortcut, String channelShortcut) {
        CrmTask crmTask = crmTaskRepository.findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(taskId, organizationShortcut, channelShortcut);
        crmTask.getComments().removeIf(crmTaskComment -> crmTaskComment.getId() == id);
        crmTaskRepository.save(crmTask);
        crmTaskCommentService.deleteByCrmTaskIdAndCommentId(taskId, id, organizationShortcut, channelShortcut);

    }

    public CrmTaskComment getTaskCommentAssociatedWithTask(String organizationShortcut, String channelShortcut, Long taskId, Long id) {
        return crmTaskCommentService.findByCrmTaskId(id, taskId, organizationShortcut, channelShortcut);
    }

    public CrmTaskComment saveOrUpdateTaskCommentAssociatedWithTask(CrmTaskComment requestEnitity, Long taskId, String organizationShortcut, String channelShortcut) {
        CrmTask crmTask = crmTaskRepository.findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(taskId, organizationShortcut, channelShortcut);
        if (requestEnitity != null) {
            CrmTaskComment crmTaskComment = crmTaskCommentService.saveCrmTaskComment(requestEnitity);
            crmTask.addComments(crmTaskComment);
            crmTaskRepository.saveAndFlush(crmTask);
            return crmTaskComment;
        }
        return null;

    }

    public Slice<CrmTaskComment> getTaskCommentsAssociatedWithTask(Long taskId, String organizationShortcut, String channelShortcut, Pageable pagable) {
        return crmTaskCommentService.findByCrmTaskId(taskId, organizationShortcut, channelShortcut, pagable);
    }

}
