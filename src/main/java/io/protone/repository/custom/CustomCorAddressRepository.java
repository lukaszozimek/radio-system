package io.protone.repository.custom;

import io.protone.domain.CorAddress;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CorAddress entity.
 */
@SuppressWarnings("unused")
public interface CustomCorAddressRepository extends JpaRepository<CorAddress,Long> {

}
