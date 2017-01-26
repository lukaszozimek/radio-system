package io.protone.repository;

import io.protone.domain.CORArea;
import io.protone.domain.CORContact;

import io.protone.domain.CORNetwork;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORContact entity.
 */
@SuppressWarnings("unused")
public interface CORContactRepository extends JpaRepository<CORContact,Long> {

    List<CORContact> findByNetwork(CORNetwork network);
}
