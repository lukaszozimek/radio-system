package io.protone.repository;

import io.protone.domain.CrmTaskComment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmTaskComment entity.
 */
@SuppressWarnings("unused")
public interface CrmTaskCommentRepository extends JpaRepository<CrmTaskComment,Long> {

}