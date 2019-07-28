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

    public Slice<CrmTaskComment> findByCrmTaskId(Long taskId, String networkShortuct, Pageable pageable) {
        return crmTaskCommentRepository.findSliceByTaskComment_IdAndNetwork_Shortcut(taskId, networkShortuct,pageable);
    }

    public CrmTaskComment findByCrmTaskId(Long commentId, Long taskId, String networkShortuct) {
        return crmTaskCommentRepository.findOneByIdAndTaskComment_IdAndNetwork_Shortcut(commentId, taskId, networkShortuct);
    }

    public void deleteByCrmTaskId(Long taskId, String networkShortuct) {
        crmTaskCommentRepository.deleteAllByTaskComment_IdAndNetwork_Shortcut(taskId, networkShortuct);
    }
    public void deleteByCrmTaskIdAndCommentId(Long taskId,Long id, String networkShortuct) {
        crmTaskCommentRepository.deleteAllByIdAndTaskComment_IdAndNetwork_Shortcut(id,taskId, networkShortuct);
    }
}
