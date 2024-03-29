package io.protone.core.repository;


import io.protone.core.domain.CorNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the CorNetwork entity.
 */
@SuppressWarnings("unused")
public interface CorNetworkRepository extends JpaRepository<CorNetwork, Long> {
    CorNetwork findOneByShortcut(String shortcut);

    void deleteByShortcut(String shortcut);

    Optional<CorNetwork> findOneByShortcutOrName(String shortcut, String name);
}
