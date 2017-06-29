package io.protone.crm.repostiory;


import io.protone.crm.domain.CrmTaskComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmTaskComment entity.
 */
@SuppressWarnings("unused")
public interface CrmTaskCommentRepository extends JpaRepository<CrmTaskComment, Long> {


    List<CrmTaskComment> findAllByTaskComment_IdAndNetwork_Shortcut(Long taskId, String networkShortcut, Pageable pageable);

    CrmTaskComment findOneByIdAndTaskComment_IdAndNetwork_Shortcut(Long commentId, Long taskId, String networkShortcut);

    void deleteAllByTaskComment_IdAndNetwork_Shortcut(Long taskId, String networkShortuct);

    void deleteAllByIdAndTaskComment_IdAndNetwork_Shortcut(Long id, Long taskId, String networkShortuct);
}
