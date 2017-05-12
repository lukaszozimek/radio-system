package io.protone.repository.cor;

import io.protone.domain.CorContact;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CorContact entity.
 */
@SuppressWarnings("unused")
public interface CorContactRepository extends JpaRepository<CorContact,Long> {

}
