package io.protone.repository;

import io.protone.domain.CORAssociation;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the CORAssociation entity.
 */
@SuppressWarnings("unused")
public interface CORAssociationRepository extends JpaRepository<CORAssociation, Long> {

    List<CORAssociation> findByNameAndSourceIdAndTargetClass(String name, Long sourceId, String targetClass);

    List<CORAssociation> findBySourceIdAndTargetId(Long sourceId, Long targetId);

    List<CORAssociation> findByTargetIdAndSourceClass(Long targetId, String sourceClass);

    List<CORAssociation> findBySourceClassAndSourceId(String sourceClass, Long sourceId);
}
