package io.protone.repository;

import io.protone.domain.CORNetwork;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORNetwork entity.
 */
@SuppressWarnings("unused")
public interface CORNetworkRepository extends JpaRepository<CORNetwork,Long> {

}
