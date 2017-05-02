package io.protone.repository.cor;

import java.util.List;

import io.protone.domain.CorPropertyKey;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the CorPropertyKey entity.
 */
@SuppressWarnings("unused")
public interface CorPropertyKeyRepository extends JpaRepository<CorPropertyKey, Long> {

    List<CorPropertyKey> findByNetwork_Shortcut(String networkShortcut, Pageable pageable);

    void deleteByIdAndNetwork_Shortcut(Long id, String networkShortcut);

    CorPropertyKey findByIdAndNetwork_Shortcut(Long id, String networkShortcut);
}
