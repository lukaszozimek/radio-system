package io.protone.repository;

import io.protone.domain.CorNetwork;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the CorNetwork entity.
 */
@SuppressWarnings("unused")
public interface CorNetworkRepository extends JpaRepository<CorNetwork,Long> {

}
