package io.protone.repository;

import io.protone.domain.CORArea;
import io.protone.domain.CORNetwork;
import io.protone.domain.CORTag;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORTag entity.
 */
@SuppressWarnings("unused")
public interface CORTagRepository extends JpaRepository<CORTag,Long> {

    List<CORTag> findByNetwork(CORNetwork network);
}
