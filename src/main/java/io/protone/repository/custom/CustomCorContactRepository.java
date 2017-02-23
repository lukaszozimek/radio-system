package io.protone.repository.custom;

import io.protone.domain.CorContact;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CorContact entity.
 */
@SuppressWarnings("unused")
public interface CustomCorContactRepository extends JpaRepository<CorContact,Long> {

}
