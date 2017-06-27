package io.protone.core.repository;

import io.protone.domain.CorAddress;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CorAddress entity.
 */
@SuppressWarnings("unused")
public interface CorAddressRepository extends JpaRepository<CorAddress,Long> {

}
