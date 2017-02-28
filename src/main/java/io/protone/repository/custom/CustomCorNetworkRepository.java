package io.protone.repository.custom;

import io.protone.domain.CorNetwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

/**
 * Spring Data JPA repository for the CorNetwork entity.
 */
@SuppressWarnings("unused")
public interface CustomCorNetworkRepository extends JpaRepository<CorNetwork, Long> {
    CorNetwork findOneByShortcut(String shortcut);

    void deleteByShortcut(String shortcut);

    Optional<CorNetwork> findOneByShortcutOrName(String shortcut, String name);
}
