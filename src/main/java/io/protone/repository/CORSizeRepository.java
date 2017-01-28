package io.protone.repository;

import io.protone.domain.CORArea;
import io.protone.domain.CORNetwork;
import io.protone.domain.CORSize;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORSize entity.
 */
@SuppressWarnings("unused")
public interface CORSizeRepository extends JpaRepository<CORSize,Long> {

    List<CORSize> findByNetwork(CORNetwork network);
}
