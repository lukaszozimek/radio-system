package io.protone.custom.repository;

import io.protone.domain.CORNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CORNetwork entity.
 */
@SuppressWarnings("unused")
public interface CustomCORNetworkRepository extends JpaRepository<CORNetwork, Long> {
    CORNetwork findByShortcut(String shortcut);

    Long deleteByShortcut(String shortcut);
}
