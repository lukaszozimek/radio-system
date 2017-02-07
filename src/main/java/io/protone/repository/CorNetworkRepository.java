package io.protone.repository;

import io.protone.domain.CorNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CorNetwork entity.
 */
@SuppressWarnings("unused")
public interface CorNetworkRepository extends JpaRepository<CorNetwork, Long> {
    CorNetwork findOneByShortcut(String shortcut);

    void deleteByShortcut(String shortcut);
}
