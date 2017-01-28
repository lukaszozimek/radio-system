package io.protone.repository;

import io.protone.domain.CORAddress;

import io.protone.domain.CORArea;
import io.protone.domain.CORNetwork;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORAddress entity.
 */
@SuppressWarnings("unused")
public interface CORAddressRepository extends JpaRepository<CORAddress,Long> {

    List<CORAddress> findByNetwork(CORNetwork network);
}
