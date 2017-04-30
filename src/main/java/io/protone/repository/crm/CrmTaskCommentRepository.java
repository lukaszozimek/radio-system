package io.protone.repository.crm;

import io.protone.domain.CrmTaskComment;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the CrmTaskComment entity.
 */
@SuppressWarnings("unused")
public interface CrmTaskCommentRepository extends JpaRepository<CrmTaskComment,Long> {

}
