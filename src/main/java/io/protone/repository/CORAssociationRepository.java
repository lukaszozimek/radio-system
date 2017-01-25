package io.protone.repository;

import io.protone.domain.CORAssociation;
import io.protone.domain.CORNetwork;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

/**
 * Spring Data JPA repository for the CORAssociation entity.
 */
@SuppressWarnings("unused")
public interface CORAssociationRepository extends JpaRepository<CORAssociation, Long> {

    List<CORAssociation> findByNameAndSourceIdAndTargetClass(String name, Long sourceId, String targetClass);

    List<CORAssociation> findBySourceIdAndTargetClass(Long sourceId, String targetClass);

    List<CORAssociation> findBySourceIdAndTargetClassAndNetwork(Long sourceId, String targetClass, CORNetwork network);

    List<CORAssociation> findBySourceIdAndTargetIdAndNetwork(Long sourceId, Long targetId, CORNetwork network);

    CORAssociation findBySourceIdAndTargetIdAndTargetClassAndNetwork(Long sourceId, Long targetId, String targetClass, CORNetwork network);

    List<CORAssociation> findByTargetIdAndSourceClassAndNetwork(Long targetId, String sourceClass, CORNetwork network);

    void deleteBySourceIdAndTargetClassAndNetwork(Long sourceId, String targetClass, CORNetwork network);

    void deleteBySourceIdAndTargetIdAndNetwork(Long sourceId, Long targetId, CORNetwork network);

    void deleteByTargetIdAndSourceClassAndNetwork(Long targetId, String sourceClass, CORNetwork network);

    List<CORAssociation> findBySourceClassAndSourceId(String sourceClass, Long sourceId);
}
