package io.protone.core.repository;


import io.protone.core.domain.CorPerson;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CorPerson entity.
 */
@SuppressWarnings("unused")
public interface CorPersonRepository extends JpaRepository<CorPerson,Long> {

}
