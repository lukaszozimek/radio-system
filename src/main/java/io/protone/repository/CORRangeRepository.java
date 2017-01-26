package io.protone.repository;

import io.protone.domain.CORArea;
import io.protone.domain.CORNetwork;
import io.protone.domain.CORRange;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORRange entity.
 */
@SuppressWarnings("unused")
public interface CORRangeRepository extends JpaRepository<CORRange,Long> {

    List<CORRange> findByNetwork(CORNetwork network);
}
