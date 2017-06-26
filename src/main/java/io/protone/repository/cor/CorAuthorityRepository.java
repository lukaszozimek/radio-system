package io.protone.repository.cor;

import io.protone.domain.CorAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface CorAuthorityRepository extends JpaRepository<CorAuthority, String> {

}
