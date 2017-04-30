package io.protone.repository.cor;

import io.protone.domain.CorPerson;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CorPerson entity.
 */
@SuppressWarnings("unused")
public interface CorPersonRepository extends JpaRepository<CorPerson,Long> {

}
