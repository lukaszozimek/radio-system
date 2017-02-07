package io.protone.repository;

import io.protone.domain.CorUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CorUser entity.
 */
@SuppressWarnings("unused")
public interface CorUserRepository extends JpaRepository<CorUser,Long> {

}
