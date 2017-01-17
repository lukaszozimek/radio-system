package io.protone.repository;

import io.protone.domain.CORAssociation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORAssociation entity.
 */
@SuppressWarnings("unused")
public interface CORAssociationRepository extends JpaRepository<CORAssociation, Long> {

    List<CORAssociation> findBySourceIdAndTargetClass(Long sourceId, String targetClass);

    List<CORAssociation> findBySourceIdAndTargetId(Long sourceId, Long targetId);

    List<CORAssociation> findByTargetIdAndSourceClass(Long targetId, String sourceClass);

}
