package io.protone.repository.crm;

import io.protone.domain.CrmTaskComment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmTaskComment entity.
 */
@SuppressWarnings("unused")
public interface CrmTaskCommentRepository extends JpaRepository<CrmTaskComment, Long> {


    List<CrmTaskComment> findAllByTaskComment_IdAndNetwork_Shortcut(Long taskId, String networkShortcut);

    CrmTaskComment findOneByIdAndTaskComment_IdAndNetwork_Shortcut(Long commentId, Long taskId, String networkShortcut);

    void deleteAllByTaskComment_IdAndNetwork_Shortcut(Long taskId, String networkShortuct);
}
