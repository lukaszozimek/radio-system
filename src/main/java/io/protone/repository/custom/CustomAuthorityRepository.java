package io.protone.repository.custom;

import io.protone.domain.Authority;
import io.protone.domain.CorAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface CustomAuthorityRepository extends JpaRepository<CorAuthority, String> {

}
