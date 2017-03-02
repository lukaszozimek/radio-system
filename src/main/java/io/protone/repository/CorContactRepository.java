package io.protone.repository;

import io.protone.domain.CorContact;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the CorContact entity.
 */
@SuppressWarnings("unused")
public interface CorContactRepository extends JpaRepository<CorContact,Long> {

}
