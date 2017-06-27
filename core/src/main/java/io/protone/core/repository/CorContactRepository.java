package io.protone.core.repository;

import io.protone.domain.CorContact;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CorContact entity.
 */
@SuppressWarnings("unused")
public interface CorContactRepository extends JpaRepository<CorContact,Long> {

}
