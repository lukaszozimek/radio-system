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

    CORAssociation findBySourceIdAndTargetIdAndTargetClass(Long sourceId, Long targetId, String targetClass);

    List<CORAssociation> findByTargetIdAndSourceClass(Long targetId, String sourceClass);

    void deleteBySourceIdAndTargetClass(Long sourceId, String targetClass);

    void deleteBySourceIdAndTargetId(Long sourceId, Long targetId);

    void deleteByTargetIdAndSourceClass(Long targetId, String sourceClass);

}
