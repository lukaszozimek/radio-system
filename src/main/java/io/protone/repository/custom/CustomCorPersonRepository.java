package io.protone.repository.custom;

import io.protone.domain.CorPerson;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CorPerson entity.
 */
@SuppressWarnings("unused")
public interface CustomCorPersonRepository extends JpaRepository<CorPerson,Long> {

}
