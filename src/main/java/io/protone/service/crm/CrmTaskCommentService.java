package io.protone.service.crm;

import io.protone.domain.CrmTask;
import io.protone.domain.CrmTaskComment;
import io.protone.repository.crm.CrmTaskCommentRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.Id;
import java.util.List;

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

    public List<CrmTaskComment> findByCrmTaskId(Long taskId, String networkShortuct) {
        return crmTaskCommentRepository.findAllByTaskComment_IdAndNetwork_Shortcut(taskId, networkShortuct);
    }

    public CrmTaskComment findByCrmTaskId(Long commentId, Long taskId, String networkShortuct) {
        return crmTaskCommentRepository.findOneByIdAndTaskComment_IdAndNetwork_Shortcut(commentId, taskId, networkShortuct);
    }

    public void deleteByCrmTaskId(Long taskId, String networkShortuct) {
        crmTaskCommentRepository.deleteAllByTaskComment_IdAndNetwork_Shortcut(taskId, networkShortuct);
    }
}
