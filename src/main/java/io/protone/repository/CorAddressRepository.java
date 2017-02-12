package io.protone.repository;

import io.protone.domain.CorAddress;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CorAddress entity.
 */
@SuppressWarnings("unused")
public interface CorAddressRepository extends JpaRepository<CorAddress,Long> {

}
