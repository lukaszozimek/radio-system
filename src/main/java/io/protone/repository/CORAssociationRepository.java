package io.protone.repository;

import io.protone.domain.CORAssociation;

import io.protone.domain.CORNetwork;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORAssociation entity.
 */
@SuppressWarnings("unused")
public interface CORAssociationRepository extends JpaRepository<CORAssociation, Long> {

    List<CORAssociation> findBySourceIdAndTargetClassAndNetwork(Long sourceId, String targetClass, CORNetwork network);

    CORAssociation findOneBySourceIdAndTargetClassAndNetworkAndName(Long sourceId, String targetClass, CORNetwork network, String name);

    List<CORAssociation> findBySourceIdAndTargetIdAndNetwork(Long sourceId, Long targetId, CORNetwork network);

    CORAssociation findBySourceIdAndTargetIdAndTargetClassAndNetwork(Long sourceId, Long targetId, String targetClass, CORNetwork network);


    List<CORAssociation> findByTargetIdAndSourceClassAndNetwork(Long targetId, String sourceClass, CORNetwork network);

    void deleteBySourceIdAndTargetClassAndNetworkAndName(Long sourceId, String targetClass, CORNetwork network, String name);

    void deleteBySourceIdAndTargetClassAndNetwork(Long sourceId, String targetClass, CORNetwork network);

    void deleteBySourceIdAndTargetIdAndNetwork(Long sourceId, Long targetId, CORNetwork network);

    void deleteByTargetIdAndSourceClassAndNetwork(Long targetId, String sourceClass, CORNetwork network);

}
