package io.protone.repository;

import io.protone.domain.CORArea;
import io.protone.domain.CORNetwork;
import io.protone.domain.CORPropertyKey;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORPropertyKey entity.
 */
@SuppressWarnings("unused")
public interface CORPropertyKeyRepository extends JpaRepository<CORPropertyKey,Long> {

    List<CORPropertyKey> findByNetwork(CORNetwork network);
}
