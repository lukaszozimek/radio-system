package io.protone.repository;

import io.protone.domain.CORNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CORNetwork entity.
 */
@SuppressWarnings("unused")
public interface CCORNetworkRepository extends JpaRepository<CORNetwork, Long> {
    CORNetwork findByShortcut(String shortcut);
    void deleteByShortcut(String shortcut);
}
