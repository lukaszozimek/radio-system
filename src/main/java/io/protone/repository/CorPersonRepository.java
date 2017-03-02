package io.protone.repository;

import io.protone.domain.CorPerson;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the CorPerson entity.
 */
@SuppressWarnings("unused")
public interface CorPersonRepository extends JpaRepository<CorPerson,Long> {

}
