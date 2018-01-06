package io.protone.crm.service;


import io.protone.crm.domain.CrmTaskComment;
import io.protone.crm.repostiory.CrmTaskCommentRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 15/06/2017.
 */
@Service
public class CrmTaskCommentService {

    @Inject
    private CrmTaskCommentRepository crmTaskCommentRepository;

    public CrmTaskComment saveCrmTaskComment(CrmTaskComment crmTaskComment) {
        return crmTaskCommentRepository.saveAndFlush(crmTaskComment);
    }

    public Slice<CrmTaskComment> findByCrmTaskId(Long taskId, String organizationShortcut, String channelShortcut, Pageable pageable) {
        return crmTaskCommentRepository.findSliceByTaskComment_IdAndChannel_Organization_ShortcutAndChannel_Shortcut(taskId, organizationShortcut, channelShortcut, pageable);
    }

    public CrmTaskComment findByCrmTaskId(Long commentId, Long taskId, String organizationShortcut, String channelShortcut) {
        return crmTaskCommentRepository.findOneByIdAndTaskComment_IdAndChannel_Organization_ShortcutAndChannel_Shortcut(commentId, taskId, organizationShortcut, channelShortcut);
    }

    public void deleteByCrmTaskId(Long taskId, String organizationShortcut, String channelShortcut) {
        crmTaskCommentRepository.deleteAllByTaskComment_IdAndChannel_Organization_ShortcutAndChannel_Shortcut(taskId, organizationShortcut, channelShortcut);
    }

    public void deleteByCrmTaskIdAndCommentId(Long taskId, Long id, String organizationShortcut, String channelShortcut) {
        crmTaskCommentRepository.deleteAllByIdAndTaskComment_IdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, taskId, organizationShortcut, channelShortcut);
    }
}
